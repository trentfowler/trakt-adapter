package net.tfowler.traktadapter.resources;

import com.codahale.metrics.annotation.Timed;
import net.tfowler.traktadapter.api.Saying;
import net.tfowler.traktadapter.cli.Command;
import net.tfowler.traktadapter.cli.Result;
import net.tfowler.traktadapter.cli.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/movies/download")
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("Duplicates")
public class RadarrDownloadResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadarrDownloadResource.class);

    private final String defaultName;

    private final AtomicLong counter;

    public RadarrDownloadResource(String defaultName) {
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying download(@QueryParam("name") Optional<String> name) {
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
        Result traktarrResult = Command.execute(Shell.Bash, String.format("traktarr movie -id \"%s\"", tRaktResult.getStdout()));
        if (traktarrResult.getReturnCode() != 0) {
            return new Saying(counter.incrementAndGet(), "traktarr-error");
        }
        return new Saying(counter.incrementAndGet(), "success");
    }
}
