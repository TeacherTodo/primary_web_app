package edu.nau.coe_stic_app.models;

public class Requirement
{
    private int id;
    private String major;
    private String title;
    private String description;
    private boolean documentation_required;

    public Requirement(int id, String major, String title, String description, boolean documentation_required)
    {
        this.id = id;
        this.major = major;
        this.title = title;
        this.description = description;
        this.documentation_required = documentation_required;
    }

    public Requirement() {}

    public int getID()
    {
        return this.id;
    }

    public String getTitle() {return this.title;}

    public String getMajor()
    {
        return this.major;
    }

    public String getDescription()
    {
        return this.description;
    }

    public boolean isDocumentationRequired()
    {
        return this.documentation_required;
    }
}
