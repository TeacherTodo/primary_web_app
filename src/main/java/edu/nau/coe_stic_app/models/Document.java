package edu.nau.coe_stic_app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Document
{
    private String guid;
    private String file_extension;
    @JsonProperty("approval_status")
    private String status;
    private int requirement_instance_id;
    @JsonProperty("student_uid")
    private String student;
    private String student_name;
    private Date upload_timestamp;

    public Document(String guid, String file_extension, String status, int requirement_instance_id, String student, String student_name, Date upload_timestamp)
    {
        this.guid = guid;
        this.file_extension = file_extension;
        this.status = status;
        this.requirement_instance_id = requirement_instance_id;
        this.student = student;
        this.student_name = student_name;
        this.upload_timestamp = upload_timestamp;
    }

    public String getGUID()
    {
        return this.guid;
    }

    public String getFileExtension()
    {
        return this.file_extension;
    }

    public String getApprovalStatus()
    {
        return this.status;
    }

    public int getRequirementInstanceID()
    {
        return this.requirement_instance_id;
    }
    public String getStudentUID()
    {
        return this.student;
    }
    public String getStudentName() {return this.student_name;}

    public Date getUploadTimestamp()
    {
        return this.upload_timestamp;
    }
}
