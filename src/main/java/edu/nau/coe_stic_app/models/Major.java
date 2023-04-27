package edu.nau.coe_stic_app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Major {
    private String major;

    public Major() {
    }

    public Major(String major) {
        this.major = major;
    }

    @JsonProperty("major")
    public String getMajor() {
        return major;
    }

    @JsonProperty("major")
    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Major{" +
                "major='" + major + '\'' +
                '}';
    }
}
