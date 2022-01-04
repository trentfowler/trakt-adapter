package net.tfowler.traktadapter.cli;

@SuppressWarnings("unused")
public enum Shell {
    Bash("/bin/bash"),
    Sh("/bin/sh"),
    Zsh("/bin/zsh"),
    Ash("/bin/ash"),
    Csh("/bin/csh"),
    Dash("/bin/dash")
    ;

    private final String path;

    Shell(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
