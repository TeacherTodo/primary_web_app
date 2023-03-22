package edu.nau.coe_stic_app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Requirement
{
    @JsonProperty("id")
    public int id;
    @JsonProperty("major")
    public String major;
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("documentationRequired")
    public boolean documentation_required;

    public Requirement() {}

    public Requirement(int id, String major, String title, String description, boolean documentation_required)
    {
        this.id = id;
        this.major = major;
        this.title = title;
        this.description = description;
        this.documentation_required = documentation_required;
    }

    @JsonProperty("id")
    public int getID()
    {
        return this.id;
    }

    @JsonProperty("title")
    public String getTitle() {return this.title;}

    @JsonProperty("major")
    public String getMajor()
    {
        return this.major;
    }

    @JsonProperty("description")
    public String getDescription()
    {
        return this.description;
    }

    @JsonProperty("documentationRequired")
    public boolean isDocumentationRequired()
    {
        return this.documentation_required;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("major")
    public void setMajor(String major) {
        this.major = major;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("documentationRequired")
    public void setDocumentation_required(boolean documentation_required) {
        this.documentation_required = documentation_required;
    }

    @Override
    public String toString()
    {
        return "Requirement{" +
                "id=" + id +
                ", major='" + major + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", documentation_required=" + documentation_required +
                '}';
    }
}
