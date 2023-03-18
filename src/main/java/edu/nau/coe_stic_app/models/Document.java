package edu.nau.coe_stic_app.models;

import java.util.Date;

public class Document
{
    private String guid;
    private String file_extension;
    private String approval_status;
    private int requirement_instance_id;
    private String student_uid;
    private String student_name;
    private Date upload_timestamp;

    public Document(String guid, String file_extension, String approval_status, int requirement_instance_id, String student_uid, String student_name, Date upload_timestamp)
    {
        this.guid = guid;
        this.file_extension = file_extension;
        this.approval_status = approval_status;
        this.requirement_instance_id = requirement_instance_id;
        this.student_uid = student_uid;
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
        return this.approval_status;
    }

    public int getRequirementInstanceID()
    {
        return this.requirement_instance_id;
    }
    public String getStudentUID()
    {
        return this.student_uid;
    }
    public String getStudentName() {return this.student_name;}

    public Date getUploadTimestamp()
    {
        return this.upload_timestamp;
    }
}
