package edu.nau.coe_stic_app.models;

import jakarta.annotation.Nullable;

import java.util.Date;

public class RequirementInstance
{
    private int id;
    private int requirement_id;
    private String student_uid;
    private String status;
    @Nullable
    private String doc_guid;
    @Nullable
    private Date retake_date;

    public RequirementInstance() {}

    public RequirementInstance(int id, int requirement_id, String student_uid, String status)
    {
        this.id = id;
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = null;
        this.retake_date = null;
    }

    public RequirementInstance(int id, int requirement_id, String student_uid, String status, String doc_guid)
    {
        this.id = id;
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = doc_guid;
        this.retake_date = null;
    }

    public RequirementInstance(int id, int requirement_id, String student_uid, String status, Date retake_date)
    {
        this.id = id;
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = null;
        this.retake_date = retake_date;
    }

    public RequirementInstance(int id, int requirement_id, String student_uid, String status, String doc_guid, Date retake_date)
    {
        this.id = id;
        this.requirement_id = requirement_id;
        this.student_uid = student_uid;
        this.status = status;
        this.doc_guid = doc_guid;
        this.retake_date = retake_date;
    }

    public int getID()
    {
        return this.id;
    }

    public int getRequirementID()
    {
        return this.requirement_id;
    }

    public String getStudentUID()
    {
        return this.student_uid;
    }

    public String getStatus()
    {
        return this.status;
    }

    public String getDocGUID()
    {
        return this.doc_guid;
    }

    public Date getRetakeDate()
    {
        return this.retake_date;
    }
}
