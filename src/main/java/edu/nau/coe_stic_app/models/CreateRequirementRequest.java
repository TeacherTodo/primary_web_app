package edu.nau.coe_stic_app.models;

public class CreateRequirementRequest
{
    public String major;
    public String description;
    public boolean documentation_required;

    public CreateRequirementRequest(String major, String description, boolean documentation_required)
    {
        this.major = major;
        this.description = description;
        this.documentation_required = documentation_required;
    }
}
