package edu.nau.coe_stic_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.models.*;
import okhttp3.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class DB_Helper {
    //@Value("${api.url}:http://localhost:8888")
    private static String apiUrl = "http://localhost:8888/api";

    public static byte[] getFileContent(String guid, String fileExtension) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/document-content/" + guid + "/" + fileExtension).build();
        Response response = client.newCall(request).execute();
        return response.body().bytes();
    }

    public static void uploadFileContent(String guid, String fileExtension, byte data[]) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(data);
        Request request = new Request.Builder().url(apiUrl + "/document-content/" + guid + "/" + fileExtension).post(body).build();

        //TODO: testing
        System.out.println("uploadFileContent() request: " + request);
        System.out.println("uploadFileContent() body: " + body.contentType());

        Response response = client.newCall(request).execute();
    }

    public static String getUserRole(String uid) throws IOException {
        List<AdminUser> admins = getAllAdmins();

        for (AdminUser admin : admins) {
            if (admin.getUID().equals(uid)) {
                return "admin";
            }
        }

        return "student";
    }

    public static boolean isRegisteredStudent(String uid) throws IOException {
        List<Student> students = getAllStudents();

        for (Student student : students) {
            if (student.getUid().equals(uid)) {
                return true;
            }
        }

        return false;
    }

    public static Student getStudent(String uid) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/student/" + uid).build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Student student = mapper.readValue(response.body().byteStream(), Student.class);
            return student;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<RequirementInstance> getStudentRequirements(String uid) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/student/requirements/" + uid).build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String responseString = response.body().string();
            //responseString = removeExtraField(responseString);
            System.out.printf("getStudentRequirements(String uid) Response String: %s\n", responseString);
            List<RequirementInstance> requirements = mapper.readValue(responseString, new TypeReference<List<RequirementInstance>>() {
            });
            return requirements;
        } catch (Exception e) {
            System.out.println("Failed to parse JSON response.");
            e.printStackTrace();
            return null;
        }
    }

    private static String removeExtraField(String string) {
        while (string.contains("studentUID")) {
            int startIndex = string.indexOf("studentUID") - 1;
            int endIndex = startIndex + 81;

            if (string.charAt(endIndex + 1) != '}') {
                endIndex++;
            }

            string = string.substring(0, startIndex) + string.substring(endIndex, string.length());
        }

        return string;
    }

    public static List<Student> getAllStudents() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/students").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();


        try {
            //TODO: debugging testing the pasing of one student object
            String studentJson = "{\n" +
                    "  \"uid\" : \"6e8d6c69af58e73c7248364aa59b0c257f6ba1d19782eb9e38890a61ada948ef\",\n" +
                    "  \"major\" : \"Computer Science\",\n" +
                    "  \"gradTerm\" : \"Spring\",\n" +
                    "  \"gradYear\" : 2023\n" +
                    "}";
            Student student = mapper.readValue(studentJson, Student.class);
            System.out.println("getAllStudents(): student.toString(): " + student.toString());
            //TODO: end debugging

            String responseString = response.body().string();
            System.out.printf("getAllStudents(): responseString: %s\n", responseString); //TODO: debugging
            List<Student> students = mapper.readValue(responseString, new TypeReference<List<Student>>() {
            });
            System.out.println("getAllStudents(): I am going to print a list of the java objects"); //TODO: debugging
            students.forEach(System.out::println); //TODO: debugging
            System.out.println("END getAllStudents()"); //TODO: debugging

            return students;
        } catch (Exception e) {
            System.out.println("Failed to parse JSON response.");
            return null;
        }
    }

    //TODO: Add functions for documents

    public static List<Major> getAllMajors() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/majors").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Major> majors = mapper.readValue(response.body().byteStream(), new TypeReference<List<Major>>() {
            });
            return majors;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Term> getAllTerms() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/terms").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Term> terms = mapper.readValue(response.body().byteStream(), new TypeReference<List<Term>>() {
            });
            return terms;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<RequirementStatus> getAllRequirementStatuses() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/requirement-status").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<RequirementStatus> statuses = mapper.readValue(response.body().byteStream(), new TypeReference<List<RequirementStatus>>() {
            });
            return statuses;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<ApprovalStatus> getAllApprovalStatuses() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/approval-status").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<ApprovalStatus> statuses = mapper.readValue(response.body().byteStream(), new TypeReference<List<ApprovalStatus>>() {
            });
            return statuses;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Requirement> getAllRequirements() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/requirements").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Requirement> requirements = mapper.readValue(response.body().byteStream(), new TypeReference<List<Requirement>>() {
            });
            return requirements;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<AdminUser> getAllAdmins() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/admins").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<AdminUser> admins = mapper.readValue(response.body().byteStream(), new TypeReference<List<AdminUser>>() {
            });
            return admins;
        } catch (Exception e) {
            return null;
        }
    }

    public static Requirement getRequirementByID(int id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/requirement/" + id).build();
        Response response = client.newCall(request).execute();

        System.out.println("getRequirementByID() response.body().string(): " + response.body().toString()); //TODO: Remove this

        try {
            // TODO: i added this, works in student controller
            String myReqJson = response.body().string();
            System.out.println("    Works: getRequirementByID() response.body().string(): " + myReqJson);
            Requirement myReqDecoded = new ObjectMapper().readValue(myReqJson, Requirement.class);
            System.out.println("    Works: getRequirementByID() response.body().string(): " + myReqDecoded.toString());
            return myReqDecoded;
            // TODO: end of my code


//         ObjectMapper mapper = new ObjectMapper();
//         Requirement req = mapper.readValue(response.body().byteStream(), Requirement.class);
//         System.out.println("    getRequirementByID() req.toString(): " + req.toString()); //TODO: Remove this
//         return req;
        } catch (Exception e) {
            System.out.println("    getRequirementByID(): Failed to parse JSON response."); //TODO: Remove this
            return null;
        }
    }

    public static Major getMajorByName(String name) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/major/" + name).build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Major major = mapper.readValue(response.body().byteStream(), Major.class);
            return major;
        } catch (Exception e) {
            return null;
        }
    }

    public static Term getTermByName(String name) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/term/" + name).build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Term term = mapper.readValue(response.body().byteStream(), Term.class);
            return term;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Document> getAllDocs() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/docs-all").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Document> docs = mapper.readValue(response.body().byteStream(), new TypeReference<List<Document>>() {
            });
            return docs;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Document> getPendingDocs() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/docs-pending").build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Document> docs = mapper.readValue(response.body().byteStream(), new TypeReference<List<Document>>() {
            });
            return docs;
        } catch (Exception e) {
            return null;
        }
    }

    public static void createAdminUser(String uid) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(apiUrl + "/admins/" + uid).post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createStudent(String uid, String major, String grad_term, int grad_year, String first_name, String last_name) throws Exception {
        CreateStudentRequest req = new CreateStudentRequest(uid, major, grad_term, grad_year, first_name, last_name);
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/students").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createRequirement(String major, String title, String description, boolean documentation_required) throws Exception {
        CreateRequirementRequest req = new CreateRequirementRequest(major, title, description, documentation_required);
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirements").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createRequirementInstance(int requirement_id, String student_uid, String status) throws Exception {
        CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status);
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createRequirementInstance(int requirement_id, String student_uid, String status, String doc_guid) throws Exception {
        CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status, doc_guid);
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createRequirementInstance(int requirement_id, String student_uid, String status, Date retake_date) throws Exception {
        CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status, retake_date);
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createRequirementInstance(int requirement_id, String student_uid, String status, String doc_guid, Date retake_date) throws Exception {
        CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status, doc_guid, retake_date);
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createMajor(String name) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(apiUrl + "/majors/" + name).post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void createDocument(CreateDocumentRequest req) throws Exception {
        System.out.println("DB_Helper.createDocument()");
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        //TODO: testing
        System.out.println("DB_Helper.createDocument() req: " + mapper.writeValueAsString(req));

        // create an okHttp request with content type application/json
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, mapper.writeValueAsString(req));
        Request request = new Request.Builder().url(apiUrl + "/documents").post(body).build();
        Response response = client.newCall(request).execute();
        System.out.println("createDocument(CreateDocumentRequest req) response.code(): " + response.code());
        System.out.println("createDocument(CreateDocumentRequest req) response.body(): " + response.body().toString());
    }

    public static void editRequirement(Requirement req) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirement").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void editStudent(Student student) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(student)).build();
        Request request = new Request.Builder().url(apiUrl + "/student").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void editDocument(Document doc) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(doc)).build();
        Request request = new Request.Builder().url(apiUrl + "/document").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void editRequirementInstance(RequirementInstance instance) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(instance)).build();
        Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void deleteRequirement(int id) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(apiUrl + "/requirement/" + id).delete(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void deleteMajor(String name) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(apiUrl + "/major/" + name).delete(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void deleteAdmin(String uid) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(apiUrl + "/admin/" + uid).delete(body).build();
        Response response = client.newCall(request).execute();
    }

    public static void deleteDocument(String guid) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(apiUrl + "/document/" + guid).delete(body).build();
        Response response = client.newCall(request).execute();
    }

    public static Document getDocumentByGUID(String guid, String studentUID) throws Exception {
        System.out.println("DB_Helper.getDocumentByGUID():");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl + "/document/" + guid + "/" + studentUID).build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Document doc = mapper.readValue(response.body().byteStream(), Document.class);
            System.out.println("DB_Helper.getDocumentByGUID() doc: " + doc);
            return doc;
        } catch (Exception e) {
            return null;
        }
    }
}
