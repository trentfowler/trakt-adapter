package net.tfowler.traktadapter.resources;

import com.codahale.metrics.annotation.Timed;
import net.tfowler.traktadapter.api.Saying;
import net.tfowler.traktadapter.models.Season;
import net.tfowler.traktadapter.models.Show;
import net.tfowler.traktadapter.utils.Sonarr;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/shows/search")
@Produces(MediaType.APPLICATION_JSON)
public class SonarrSearchResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SonarrSearchResource.class);

    private static final int MAX_SEASON_RESULTS_PER_PAGE = 5;

    private static final int SPECIALS_SEASON_NUMBER = 0;

    // Valid characters in titles are alphanumerics, spaces, parentheses,
    // exclamation points, apostrophes, dashes, ampersand. Note that when
    // there is more than one show with the same title or if the show is a
    // remake then the title from tvdb or trakt may also contain the year.
    // Valid examples: "Grey's Anatomy", "Jeopardy!", "Hawaii Five-0 (2010)"
    private static final String TITLE_RE = "[^a-zA-Z0-9'!&\\-()\\s]";

    private final String defaultName;

    private final AtomicLong counter;

    public SonarrSearchResource(String defaultName) {
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying search(@QueryParam("name") Optional<String> name) {
        final String query = name.orElse(defaultName);
        if (query.equals(defaultName) || query.trim().isEmpty()) {
            return new Saying(counter.incrementAndGet(), "empty-query");
        }
        List<Show> searchResults = Sonarr.search(query);
        searchResults.forEach(searchResult -> {
            // Replace titles like "tttttt (yyyy)" with "tttttt" since we will
            // append the year when displaying it.
            searchResult.setTitle(searchResult.getTitle()
                    .replaceAll("\\(\\d{4}\\)$", "").trim());
            // Remove specials from search results
            List<Season> seasons = searchResult.getSeasons();
            for (int i = 0; i < seasons.size(); i++) {
                if (seasons.get(i).getSeasonNumber() == SPECIALS_SEASON_NUMBER) {
                    seasons.remove(i);
                    break;
                }
            }
            // Display recent season first if there are too many results to display
            // on a single page.
            if (seasons.size() > MAX_SEASON_RESULTS_PER_PAGE) {
                Collections.reverse(seasons);
            }
        });
        // De-list weird titles that might not display correctly
        List<Show> weirdResults = new ArrayList<>();
        for (Show searchResult: searchResults) {
            String title = StringUtils.stripAccents(searchResult.getTitle())
                    .replaceAll(TITLE_RE, "");
            if (!searchResult.getTitle().equals(title)) {
                weirdResults.add(searchResult);
            }
        }
        searchResults.removeAll(weirdResults);
        return new Saying(counter.incrementAndGet(), Sonarr.toJson(searchResults));
    }
}
