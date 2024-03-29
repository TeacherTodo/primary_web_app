package edu.nau.coe_stic_app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

import java.util.Date;

public class RequirementInstance {

    /*
     * Statuses:
     *  Complete
     *  Incomplete
     *  In Progress
     */

    private int id;
    @JsonProperty("requirement_id")
    private int requirement_id;
    @JsonProperty("student_uid")
    private String student;
    private String status;
    @Nullable
    @JsonProperty("doc_guid")
    private String doc_guid;
    @Nullable
    @JsonProperty("retake_date")
    private Date retake_date;

    public RequirementInstance() {
    }

    public RequirementInstance(int id, int requirement_id, String student, String status, @Nullable String doc_guid, @Nullable Date retake_date) {
        this.id = id;
        this.requirement_id = requirement_id;
        this.student = student;
        this.status = status;
        this.doc_guid = doc_guid;
        this.retake_date = retake_date;
    }

    public int getID() {
        return this.id;
    }

    @JsonProperty("requirement_id")
    public int getRequirementID() {
        return this.requirement_id;
    }

    @JsonProperty("student_uid")
    public String getStudentUID() {
        return this.student;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonProperty("doc_guid")
    public String getDocGUID() {
        return this.doc_guid;
    }

    @JsonProperty("retake_date")
    public Date getRetakeDate() {
        return this.retake_date;
    }

    @Override
    public String toString() {
        return "RequirementInstance{" +
                "id=" + id +
                ", requirement_id=" + requirement_id +
                ", student='" + student + '\'' +
                ", status='" + status + '\'' +
                ", doc_guid='" + doc_guid + '\'' +
                ", retake_date=" + retake_date +
                '}';
    }
}
