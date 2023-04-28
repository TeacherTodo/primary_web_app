package edu.nau.coe_stic_app.models;

public class CookieValues {
    private String uid;
    private String name;
    private String role;
    private boolean isRegisteredStudent;
    private String token;

    public CookieValues(String uid, String name, String role, boolean isRegisteredStudent, String token) {
        this.uid = uid;
        this.name = name;
        this.role = role;
        this.isRegisteredStudent = isRegisteredStudent;
        this.token = token;
    }

    public String getUid() {

        return uid;
    }

    public String getName() {

        return name;
    }

    public String getRole() {

        return role;
    }

    public boolean isRegisteredStudent() {

        return isRegisteredStudent;
    }

    public String getToken() {

        return token;
    }
}
