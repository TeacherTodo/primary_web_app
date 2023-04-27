package edu.nau.coe_stic_app.models;

public class CreateDocumentRequest {
    public String fileExtension;
    public int requirementInstanceId;
    public String studentGuid;
    public String fileGuid;
    public String studentName;

    @Override
    public String toString() {
        return "CreateDocumentRequest{" +
                "fileExtension='" + fileExtension + '\'' +
                ", requirementInstanceId=" + requirementInstanceId +
                ", studentGuid='" + studentGuid + '\'' +
                ", fileGuid='" + fileGuid + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
