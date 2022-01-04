package net.tfowler.traktadapter.resources;

import com.codahale.metrics.annotation.Timed;
import net.tfowler.traktadapter.api.Saying;
import net.tfowler.traktadapter.models.Season;
import net.tfowler.traktadapter.models.Show;
import net.tfowler.traktadapter.utils.Sonarr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/shows/download")
@Produces(MediaType.APPLICATION_JSON)
public class SonarrDownloadResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SonarrDownloadResource.class);

    private final String defaultName;

    private final AtomicLong counter;

    public SonarrDownloadResource(String defaultName) {
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying download(@QueryParam("name") String formattedName,
                           @QueryParam("season") String formattedSeason) {
        if (formattedName == null || formattedName.trim().isEmpty()) {
            LOGGER.info("empty-name");
            return new Saying(counter.incrementAndGet(), "empty-name");
        }
        if (formattedSeason == null || formattedSeason.trim().isEmpty()) {
            LOGGER.info("empty-season");
            return new Saying(counter.incrementAndGet(), "empty-season");
        }
        String formattedTitle = formattedName.replaceAll("%20", " ");
        int seasonNumber = Integer.parseInt(formattedSeason
                .replaceAll("%20", "")
                .replaceAll("[^\\d.]", ""));
        for (Show show: Sonarr.existingShows()) {
            if (formattedTitle.equals(show.getTitle()) ||
                    formattedTitle.equals(String.format("%s (%s)", show.getTitle(), show.getYear()))) {
                List<Season> seasons = show.getSeasons();
                seasons.forEach(s -> {
                    if (s.getSeasonNumber() == seasonNumber) {
                        s.setMonitored(true);
                    }
                });
                show.setSeasons(seasons);
                Sonarr.update(show);
                return new Saying(counter.incrementAndGet(), "success-updated");
            }
        }
        // Hack! It is not enough to search sonarr for a show, set the desired
        // season to monitored, and then add the show. When you do an httppost
        // sonarr only sets the season-level monitored flag and not the
        // episode-level monitored flag. To fix this I set all seasons to
        // unmonitored when adding the show, then retrieve the newly added show
        // from the list of existing shows, set the desired season to monitored,
        // then call update which does an httpput which seems to have the
        // correct behavior. This is not a good solution but it works.
        for (Show show: Sonarr.search(formattedTitle)) {
            if (formattedTitle.equals(show.getTitle()) ||
                    formattedTitle.equals(String.format("%s (%s)", show.getTitle(), show.getYear()))) {
                List<Season> seasons = show.getSeasons();
                seasons.forEach(s -> s.setMonitored(false));
                show.setSeasons(seasons);
                Sonarr.add(show);
                break;
            }
        }
        for (Show show: Sonarr.existingShows()) {
            if (formattedTitle.equals(show.getTitle()) ||
                    formattedTitle.equals(String.format("%s (%s)", show.getTitle(), show.getYear()))) {
                List<Season> seasons = show.getSeasons();
                for (Season season: seasons) {
                    if (season.getSeasonNumber() == seasonNumber) {
                        season.setMonitored(true);
                    }
                }
                show.setSeasons(seasons);
                Sonarr.update(show);
                return new Saying(counter.incrementAndGet(), "success-added");
            }
        }
        return new Saying(counter.incrementAndGet(), "no-match");
    }
}
