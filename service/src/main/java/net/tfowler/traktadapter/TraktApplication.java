package net.tfowler.traktadapter;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.tfowler.traktadapter.resources.RadarrDownloadResource;
import net.tfowler.traktadapter.resources.RadarrRequestResource;
import net.tfowler.traktadapter.resources.SonarrDownloadResource;
import net.tfowler.traktadapter.resources.SonarrSearchResource;
import net.tfowler.traktadapter.utils.PlexMovieDbUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TraktApplication extends Application<TraktConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraktApplication.class);

    private static final String URL = "jdbc:sqlite:/data/plex.db";

    public static void main(String[] args) throws Exception {
        new TraktApplication().run(args);
    }

    @Override
    public String getName() {
        return "trakt-adapter";
    }

    @Override
    public void initialize(Bootstrap<TraktConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        initializeApplicationDatabase();
    }

    @Override
    public void run(TraktConfiguration configuration,
                    Environment environment) {
        LOGGER.info(configuration.toString());
        final RadarrRequestResource radarrRequestResource = new RadarrRequestResource(
                configuration.getDefaultName()
        );
        final RadarrDownloadResource radarrDownloadResource = new RadarrDownloadResource(
                configuration.getDefaultName()
        );
        final SonarrSearchResource sonarrSearchResource = new SonarrSearchResource(
                configuration.getDefaultName()
        );
        final SonarrDownloadResource sonarrDownloadResource = new SonarrDownloadResource(
                configuration.getDefaultName()
        );
        environment.jersey().register(radarrRequestResource);
        environment.jersey().register(radarrDownloadResource);
        environment.jersey().register(sonarrSearchResource);
        environment.jersey().register(sonarrDownloadResource);
        scheduleDatabaseUpdates();
    }

    private void initializeApplicationDatabase() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            final String create =
                    "CREATE TABLE IF NOT EXISTS movies (id integer PRIMARY KEY, title text NOT NULL UNIQUE, year integer);";
            Statement statement = connection.createStatement();
            statement.executeUpdate(create);
        } catch (SQLException sqle) {
            LOGGER.error("Error initializing application database", sqle);
            System.exit(1);
        }
    }

    private void scheduleDatabaseUpdates() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new PlexMovieDbUpdater(), 0, 4, TimeUnit.HOURS);
    }
}
