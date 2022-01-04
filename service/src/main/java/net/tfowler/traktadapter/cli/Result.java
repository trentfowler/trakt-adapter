package net.tfowler.traktadapter.cli;

public class Result {
    private final int returnCode;

    private final String stdout;

    public Result(int returnCode, String stdout) {
        this.returnCode = returnCode;
        this.stdout = stdout;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public String getStdout() {
        return stdout;
    }
}
