package net.tfowler.traktadapter.utils;

import net.tfowler.traktadapter.cli.Command;
import net.tfowler.traktadapter.cli.Result;
import net.tfowler.traktadapter.cli.Shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlexMovieDbUpdater implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlexMovieDbUpdater.class);

    private static final String URL = "jdbc:sqlite:/data/plex.db";

    private static final String INSERT = "INSERT OR IGNORE INTO movies (title) VALUES (?)";

    @Override
    public void run() {
        Result r = Command.execute(Shell.Bash, "list-movies");
        BufferedReader reader = new BufferedReader(new StringReader(r.getStdout()));
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            String movie;
            while ((movie = reader.readLine()) != null) {
                statement.setString(1, movie);
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            LOGGER.error("Encountered a SQL exception while updating the plex movie database", sqle);
        } catch (IOException ioe) {
            LOGGER.error("Encountered a read exception while updating plex movie database", ioe);
        }
    }
}
