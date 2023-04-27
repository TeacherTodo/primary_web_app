package edu.nau.coe_stic_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Document

        /*
         * "guid":"bbc61998-80f4-4723-8620-4868ffa062de",
         * "fileExtension":"txt",
         * "approvalStatus":"Pending Approval",
         * "requirementInstanceId":674137664,
         * "studentGuid":"459c857678de7fd622295c13208a416929a11cfa80e53c27973ea4e048dcb9f2",
         * "studentName":null,
         * "uploadTimestamp":1682497934308
         */ {
    private String guid;
    private String fileExtension;
    private String approvalStatus;
    private int requirementInstanceId;
    private String studentGuid;
    private String studentName;
    @JsonIgnore
    private Date uploadTimestamp;

    protected Document() {
    }

    public Document(String guid, String fileExtension, String approvalStatus, int requirementInstanceId, String studentGuid, String studentName, Date uploadTimestamp) {
        this.guid = guid;
        this.fileExtension = fileExtension;
        this.approvalStatus = approvalStatus;
        this.requirementInstanceId = requirementInstanceId;
        this.studentGuid = studentGuid;
        this.studentName = studentName;
        this.uploadTimestamp = uploadTimestamp;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String file_extension) {
        this.fileExtension = file_extension;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approval_status) {
        this.approvalStatus = approval_status;
    }

    public int getRequirementInstanceId() {
        return requirementInstanceId;
    }

    public void setRequirementInstanceId(int requirement_instance_id) {
        this.requirementInstanceId = requirement_instance_id;
    }

    public String getStudentGuid() {
        return studentGuid;
    }

    public void setStudentGuid(String student_guid) {
        this.studentGuid = student_guid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String student_name) {
        this.studentName = student_name;
    }

    @JsonIgnore
    public Date getUploadTimestamp() {
        return uploadTimestamp;
    }

    @JsonIgnore
    public void setUploadTimestamp(Date upload_timestamp) {
        this.uploadTimestamp = upload_timestamp;
    }

    @Override
    public String toString() {
        return "Document{" +
                "guid='" + guid + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", requirementInstanceId=" + requirementInstanceId +
                ", studentGuid='" + studentGuid + '\'' +
                ", studentName='" + studentName + '\'' +
                ", uploadTimestamp=" + uploadTimestamp +
                '}';
    }
}