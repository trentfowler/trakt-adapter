package net.tfowler.traktadapter.resources;

import net.tfowler.traktadapter.api.Saying;
import com.codahale.metrics.annotation.Timed;

import net.tfowler.traktadapter.cli.Command;
import net.tfowler.traktadapter.cli.Result;
import net.tfowler.traktadapter.cli.Shell;
import net.tfowler.traktadapter.db.Query;
import net.tfowler.traktadapter.models.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/movies/request")
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("Duplicates")
public class RadarrRequestResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadarrRequestResource.class);

    private static final String URL = "jdbc:sqlite:/data/plex.db";

    private final String defaultName;

    private final AtomicLong counter;

    public RadarrRequestResource(String defaultName) {
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
        Result tRaktResult = Command.execute(Shell.Bash, String.format("search-movie \"%s\"", query));
        if (tRaktResult.getReturnCode() != 0) {
            return new Saying(counter.incrementAndGet(), "tRakt-error");
        }
        if (tRaktResult.getStdout().isEmpty()) {
            return new Saying(counter.incrementAndGet(), "no-match");
        }
        String movieTitle = Command.execute(Shell.Bash, String.format("search-movie-proper \"%s\"", query)).getStdout();
        if (movieTitle != null && !movieTitle.trim().isEmpty()) {
            try (Connection connection = DriverManager.getConnection(URL)) {
                Movie movie = Query.with(connection).fetch(Movie.class, "SELECT * FROM movies WHERE title = ? LIMIT 1", movieTitle);
                if (movie != null) {
                    LOGGER.info("Found match on plex for {}", movie.getTitle());
                    return new Saying(counter.incrementAndGet(), "match-on-plex");
                }
            } catch (Exception sqle) {
                LOGGER.error("Encountered exception checking database for movie", sqle);
            }
        }
        Result traktarrResult = Command.execute(Shell.Bash, String.format("traktarr movie -id \"%s\"", tRaktResult.getStdout()));
        if (traktarrResult.getReturnCode() != 0) {
            return new Saying(counter.incrementAndGet(), "traktarr-error");
        }
        return new Saying(counter.incrementAndGet(), "success");
    }
}
