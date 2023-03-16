package edu.nau.coe_stic_app.models;

public class Requirement
{
    private int id;
    private String major;
    private String description;
    private boolean documentation_required;

    public Requirement(int id, String major, String description, boolean documentation_required)
    {
        this.id = id;
        this.major = major;
        this.description = description;
        this.documentation_required = documentation_required;
    }

    public int getID()
    {
        return this.id;
    }

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
