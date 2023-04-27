package edu.nau.coe_stic_app.models;

public class CreateStudentRequest {
    public String uid;
    public String major;
    public String grad_term;
    public int grad_year;

    public String first_name;

    public String last_name;

    public CreateStudentRequest(String uid, String major, String grad_term, int grad_year, String first_name, String last_name) {
        this.uid = uid;
        this.major = major;
        this.grad_term = grad_term;
        this.grad_year = grad_year;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
