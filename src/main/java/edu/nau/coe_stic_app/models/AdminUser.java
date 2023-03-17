package edu.nau.coe_stic_app.models;

public class AdminUser
{
    private String uid;

    private String name;

    public AdminUser(String uid)
    {
        this.uid = uid;
    }

    public String getUID()
    {
        return this.uid;
    }
    public String getName() {return this.name;}
}
