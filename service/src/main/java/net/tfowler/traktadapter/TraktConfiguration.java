package net.tfowler.traktadapter;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class TraktConfiguration extends Configuration {
    @NotEmpty
    private String defaultName = "trakt-adapter";

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @Override
    public String toString() {
        return "TraktConfiguration{" +
                "defaultName='" + defaultName + '\'' +
                '}';
    }
}

