package edu.nau.coe_stic_app.models;

public class CreateDocumentRequest
{
    public String file_extension;
    public int requirement_instance_id;
    public String student_uid;
    public String file_guid;
    public String student_name;

    @Override
    public String toString()
    {
        return "CreateDocumentRequest{" +
                "file_extension='" + file_extension + '\'' +
                ", requirement_instance_id=" + requirement_instance_id +
                ", student_uid='" + student_uid + '\'' +
                ", file_guid='" + file_guid + '\'' +
                ", student_name='" + student_name + '\'' +
                '}';
    }
}
