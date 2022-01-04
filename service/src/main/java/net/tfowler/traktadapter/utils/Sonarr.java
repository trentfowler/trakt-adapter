package net.tfowler.traktadapter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.tfowler.traktadapter.cli.Command;
import net.tfowler.traktadapter.cli.Shell;
import net.tfowler.traktadapter.models.Show;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates,unused")
public class Sonarr {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sonarr.class);

    private static final String SONARR_HOST_IP = "10.0.0.14";

    private static final int SONARR_PORT = 8989;

    private static final String SONARR_API_KEY = "SONARR_API_KEY";

    private static final String SONARR_ROOT_FOLDER_PATH = "Y:\\";

    private static final int SONARR_DEFAULT_PROFILE_ID = 1;

    private static final int MAXIMUM_SEARCH_RESULTS = 4;

    private Sonarr() {
    }

    private static void seriesSearch(int seriesId) {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String cmd = String.format("curl 'http://%s:%d/api/command' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'X-Api-Key: %s' -H 'Content-Type: application/json' --data-binary '{\"name\":\"seriesSearch\",\"seriesId\":%d}' --compressed --insecure",
                        SONARR_HOST_IP,
                        SONARR_PORT,
                        SONARR_API_KEY,
                        seriesId);
                Command.execute(Shell.Bash, cmd);
            }
        };
        timer.schedule(task, 15000);
    }

    // curl 'http://10.0.0.14:8989/api/command' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'X-Api-Key: SONARR_API_KEY' -H 'Content-Type: application/json' --data-binary '{"name":"missingEpisodeSearch"}' --compressed --insecure
    private static void missingEpisodeSearch() {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String cmd = String.format("curl 'http://%s:%s/api/command' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'X-Api-Key: %s' -H 'Content-Type: application/json' --data-binary '{\"name\":\"missingEpisodeSearch\"}' --compressed --insecure",
                        SONARR_HOST_IP,
                        SONARR_PORT,
                        SONARR_API_KEY);
                Command.execute(Shell.Bash, cmd);
            }
        };
        timer.schedule(task, 15000);
    }

    public static void update(Show show) {
        if (show == null) {
            LOGGER.error("No show was provided!");
            return;
        }
        if (show.getRootFolderPath() == null ||
                show.getRootFolderPath().trim().isEmpty()) {
            LOGGER.warn("Root folder path was not set, using default path: \"{}\"", SONARR_ROOT_FOLDER_PATH);
            show.setRootFolderPath(SONARR_ROOT_FOLDER_PATH);
        }
        if (show.getProfileId() == 0) {
            LOGGER.warn("ProfileId must be non-zero, using default profileId: \"{}\"", SONARR_DEFAULT_PROFILE_ID);
            show.setProfileId(SONARR_DEFAULT_PROFILE_ID);
        }
        if (show.getPath() == null ||
                show.getPath().trim().isEmpty()) {
            String path = String.format("%s%s", SONARR_ROOT_FOLDER_PATH, show.getTitleSlug());
            LOGGER.warn("Path was not set, using default path: \"{}\"", path);
            show.setPath(path);
        }
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(String.format("http://%s:%d/api/series",
                    SONARR_HOST_IP,
                    SONARR_PORT));
            ObjectMapper mapper = new ObjectMapper();
            HttpEntity entity = new StringEntity(mapper.writeValueAsString(show));
            httpPut.setEntity(entity);
            httpPut.setHeader("X-Api-Key", SONARR_API_KEY);
            CloseableHttpResponse response = httpClient.execute(httpPut);
            Sonarr.seriesSearch(show.getId());
        } catch (IOException ioe) {
            LOGGER.error("Encountered an exception trying to update existing show", ioe);
        }
    }

    // curl --request GET "10.0.0.14:8989/api/series/?apikey=SONARR_API_KEY"
    public static List<Show> existingShows() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(String.format("http://%s:%d/api/series/?apikey=%s",
                    SONARR_HOST_IP,
                    SONARR_PORT,
                    SONARR_API_KEY));
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StringBuilder builder = new StringBuilder();
            Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
            LOGGER.info("{}", httpResponse.getStatusLine());
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine()).append("\n");
            }
            String json = builder.toString();
            if (json.length() > 0 && json.charAt(json.length() - 1) == '\n') {
                json = json.substring(0, json.length() - 1);
            }
            ObjectMapper mapper = new ObjectMapper();
            return Arrays.stream(mapper.readValue(json, Show[].class))
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            LOGGER.error("Encountered an error searching existing sonarr library for content - was a field added/removed from the model?", ioe);
        }
        return Collections.emptyList();
    }

    // curl --request GET "10.0.0.14:8989/api/series/lookup?term=The%20Blacklist&apikey=SONARR_APT_KEY"
    public static List<Show> search(String searchQuery) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(String.format("http://%s:%d/api/series/lookup?term=%s&apikey=%s",
                    SONARR_HOST_IP,
                    SONARR_PORT,
                    searchQuery.replaceAll("\\s", "%20"),
                    SONARR_API_KEY));
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StringBuilder builder = new StringBuilder();
            Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
            LOGGER.info("{}", httpResponse.getStatusLine());
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine()).append("\n");
            }
            String json = builder.toString();
            if (json.length() > 0 && json.charAt(json.length() - 1) == '\n') {
                json = json.substring(0, json.length() - 1);
            }
            ObjectMapper mapper = new ObjectMapper();
            return Arrays.stream(mapper.readValue(json, Show[].class))
                    .filter(s -> s.getYear() != 0)
                    .limit(MAXIMUM_SEARCH_RESULTS)
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            LOGGER.error("Encountered an error searching sonarr for: \"{}\"", searchQuery);
        }
        return Collections.emptyList();
    }

    public static void add(Show show) {
        if (show == null) {
            LOGGER.error("No show was provided!");
            return;
        }
        if (show.getRootFolderPath() == null ||
                show.getRootFolderPath().trim().isEmpty()) {
            LOGGER.warn("Root folder path was not set, using default path: \"{}\"", SONARR_ROOT_FOLDER_PATH);
            show.setRootFolderPath(SONARR_ROOT_FOLDER_PATH);
        }
        if (show.getProfileId() == 0) {
            LOGGER.warn("ProfileId must be non-zero, using default profileId: \"{}\"", SONARR_DEFAULT_PROFILE_ID);
            show.setProfileId(SONARR_DEFAULT_PROFILE_ID);
        }
        if (show.getPath() == null ||
                show.getPath().trim().isEmpty()) {
            String path = String.format("%s%s", SONARR_ROOT_FOLDER_PATH, show.getTitleSlug());
            LOGGER.warn("Path was not set, using default path: \"{}\"", path);
            show.setPath(path);
        }
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(String.format("http://%s:%d/api/series",
                    SONARR_HOST_IP,
                    SONARR_PORT));
            ObjectMapper mapper = new ObjectMapper();
            HttpEntity entity = new StringEntity(mapper.writeValueAsString(show));
            httpPost.setEntity(entity);
            httpPost.setHeader("X-Api-Key", SONARR_API_KEY);
            CloseableHttpResponse response = httpClient.execute(httpPost);
        } catch (IOException ioe) {
            LOGGER.error("Could not add the requested show to sonarr: {}", show, ioe);
        }
    }

    public static String toJson(List<Show> shows) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(shows);
            // Normalize special characters in results
            json = StringUtils.stripAccents(json);
            return json;
        } catch (JsonProcessingException jpe) {
            LOGGER.error("Could not write object out to json string", jpe);
        }
        return "";
    }

    public static String toJson(Show show) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(show);
            // Normalize special characters in results
            json = StringUtils.stripAccents(json);
            return json;
        } catch (JsonProcessingException jpe) {
            LOGGER.error("Could not write object out to json string", jpe);
        }
        return "";
    }
}
