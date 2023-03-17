package edu.nau.coe_stic_app.models;

public class CreateStudentRequest
{
    public String uid;
    public String major;
    public String grad_term;
    public int grad_year;

    public CreateStudentRequest(String uid, String major, String grad_term, int grad_year)
    {
        this.uid = uid;
        this.major = major;
        this.grad_term = grad_term;
        this.grad_year = grad_year;
    }
}
