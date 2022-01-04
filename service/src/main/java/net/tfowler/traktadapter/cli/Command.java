package net.tfowler.traktadapter.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);

    private static final int EXIT_CODE_FAILURE = 1;

    private Command() {
        // Do not instantiate
    }

    public static Result execute(String[] cmd) {
        StringBuilder builder = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            LOGGER.info("executing: {}", String.join(" ", cmd));
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            int returnCode = process.waitFor();
            String stdout = builder.toString();
            // Remove last trailing newline character
            if (stdout.length() > 0 && stdout.charAt(stdout.length() - 1) == '\n') {
                stdout = stdout.substring(0, stdout.length() - 1);
            }
            LOGGER.info("stdout: {}", stdout);
            LOGGER.info("return code: {}", returnCode);
            return new Result(returnCode, stdout);
        } catch (IOException ioe) {
            LOGGER.error("Command could not be started", ioe);
        } catch (InterruptedException ie) {
            LOGGER.error("Command was interrupted during execution", ie);
        }
        return new Result(EXIT_CODE_FAILURE, builder.toString());
    }

    public static Result execute(Shell shell, String cmd) {
        return Command.execute(new String[]{shell.getPath(), "-c", cmd});
    }
}