package edu.nau.coe_stic_app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student
{
    private String uid;
    private String major;
    private String grad_term;
    private int grad_year;
    private String first_name;
    private String last_name;

    public Student() {}

    public Student(String uid, String major, String grad_term, int grad_year)
    {
        this.uid = uid;
        this.major = major;
        this.grad_term = grad_term;
        this.grad_year = grad_year;
    }

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @JsonProperty("major")
    public String getMajor() {
        return major;
    }

    @JsonProperty("major")
    public void setMajor(String major) {
        this.major = major;
    }

    @JsonProperty("gradTerm")
    public String getGrad_term() {
        return grad_term;
    }

    @JsonProperty("firstName")
    public String getFirst_name() { return first_name; }

    @JsonProperty("lastName")
    public String getLastName() { return last_name; }

    @JsonProperty("gradTerm")
    public void setGrad_term(String grad_term) {
        this.grad_term = grad_term;
    }

    @JsonProperty("gradYear")
    public int getGrad_year() {
        return grad_year;
    }

    @JsonProperty("gradYear")
    public void setGrad_year(int grad_year) {
        this.grad_year = grad_year;
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "uid='" + uid + '\'' +
                ", major='" + major + '\'' +
                ", grad_term='" + grad_term + '\'' +
                ", grad_year=" + grad_year +
                '}';
    }
}
