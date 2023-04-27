package edu.nau.coe_stic_app.models;

import jakarta.annotation.Nullable;

import java.util.Date;

public class CreateRequirementInstanceRequest {
    public int requirement_id;
    public String student_uid;
    public String status;
    @Nullable
    public String doc_guid;
    @Nullable
    public Date retake_date;

    public CreateRequirementInstanceRequest(int requirement_id, String student_uid, String status) {
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = null;
        this.retake_date = null;
    }

    public CreateRequirementInstanceRequest(int requirement_id, String student_uid, String status, String doc_guid) {
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = doc_guid;
        this.retake_date = null;
    }

    public CreateRequirementInstanceRequest(int requirement_id, String student_uid, String status, Date retake_date) {
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = null;
        this.retake_date = retake_date;
    }

    public CreateRequirementInstanceRequest(int requirement_id, String student_uid, String status, String doc_guid, Date retake_date) {
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = doc_guid;
        this.retake_date = retake_date;
    }
}
