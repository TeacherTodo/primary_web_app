package edu.nau.coe_stic_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.models.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class DB_Helper
{
   @Value("${api.url}")
   private static String apiUrl;

   public static Student getStudent(String uid) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/student/" + uid).build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         Student student = mapper.readValue(response.body().byteStream(), Student.class);
         return student;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<RequirementInstance> getStudentRequirements(String uid) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/student/requirements/" + uid).build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<RequirementInstance> requirements = mapper.readValue(response.body().byteStream(), new TypeReference<List<RequirementInstance>>(){});
         return requirements;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<Student> getAllStudents() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/students").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<Student> students = mapper.readValue(response.body().byteStream(), new TypeReference<List<Student>>(){});
         return students;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   //TODO: Add functions for documents

   public static List<Major> getAllMajors() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/majors").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<Major> majors = mapper.readValue(response.body().byteStream(), new TypeReference<List<Major>>(){});
         return majors;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<Term> getAllTerms() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/terms").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<Term> terms = mapper.readValue(response.body().byteStream(), new TypeReference<List<Term>>(){});
         return terms;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<RequirementStatus> getAllRequirementStatuses() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/requirement-status").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<RequirementStatus> statuses = mapper.readValue(response.body().byteStream(), new TypeReference<List<RequirementStatus>>(){});
         return statuses;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<ApprovalStatus> getAllApprovalStatuses() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/approval-status").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<ApprovalStatus> statuses = mapper.readValue(response.body().byteStream(), new TypeReference<List<ApprovalStatus>>(){});
         return statuses;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<Requirement> getAllRequirements() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/requirements").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<Requirement> requirements = mapper.readValue(response.body().byteStream(), new TypeReference<List<Requirement>>(){});
         return requirements;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static List<AdminUser> getAllAdmins() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/admins").build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         List<AdminUser> admins = mapper.readValue(response.body().byteStream(), new TypeReference<List<AdminUser>>(){});
         return admins;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static Requirement getRequirementByID(int id) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/requirement/" + id).build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         Requirement req = mapper.readValue(response.body().byteStream(), Requirement.class);
         return req;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static Major getMajorByName(String name) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/major/" + name).build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         Major major = mapper.readValue(response.body().byteStream(), Major.class);
         return major;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static Term getTermByName(String name) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder().url(apiUrl + "/term/" + name).build();
      Response response = client.newCall(request).execute();
      ObjectMapper mapper = new ObjectMapper();

      try
      {
         Term term = mapper.readValue(response.body().byteStream(), Term.class);
         return term;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   public static void createAdminUser(String uid) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().build();
      Request request = new Request.Builder().url(apiUrl + "/admins/" + uid).post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createStudent(String uid, String major, String grad_term, int grad_year) throws Exception
   {
      CreateStudentRequest req = new CreateStudentRequest(uid, major, grad_term, grad_year);
      ObjectMapper mapper = new ObjectMapper();
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/students").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createRequirement(String major, String title, String description, boolean documentation_required) throws Exception
   {
      CreateRequirementRequest req = new CreateRequirementRequest(major, title, description, documentation_required);
      ObjectMapper mapper = new ObjectMapper();
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/requirements").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createRequirementInstance(int requirement_id, String student_uid, String status) throws Exception
   {
      CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status);
      ObjectMapper mapper = new ObjectMapper();
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createRequirementInstance(int requirement_id, String student_uid, String status, String doc_guid) throws Exception
   {
      CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status, doc_guid);
      ObjectMapper mapper = new ObjectMapper();
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createRequirementInstance(int requirement_id, String student_uid, String status, Date retake_date) throws Exception
   {
      CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status, retake_date);
      ObjectMapper mapper = new ObjectMapper();
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createRequirementInstance(int requirement_id, String student_uid, String status, String doc_guid, Date retake_date) throws Exception
   {
      CreateRequirementInstanceRequest req = new CreateRequirementInstanceRequest(requirement_id, student_uid, status, doc_guid, retake_date);
      ObjectMapper mapper = new ObjectMapper();
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/requirement-instance").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void createMajor(String name) throws Exception
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().build();
      Request request = new Request.Builder().url(apiUrl + "/majors/" + name).post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void editRequirement(Requirement req) throws Exception
   {
      OkHttpClient client = new OkHttpClient();
      ObjectMapper mapper = new ObjectMapper();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(req)).build();
      Request request = new Request.Builder().url(apiUrl + "/requirement").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void editStudent(Student student) throws Exception
   {
      OkHttpClient client = new OkHttpClient();
      ObjectMapper mapper = new ObjectMapper();
      RequestBody body = new FormBody.Builder().add("", mapper.writeValueAsString(student)).build();
      Request request = new Request.Builder().url(apiUrl + "/student").post(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void deleteRequirement(int id) throws Exception
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().build();
      Request request = new Request.Builder().url(apiUrl + "/requirement/" + id).delete(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void deleteMajor(String name) throws Exception
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().build();
      Request request = new Request.Builder().url(apiUrl + "/major/" + name).delete(body).build();
      Response response = client.newCall(request).execute();
   }

   public static void deleteAdmin(String uid) throws Exception
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody body = new FormBody.Builder().build();
      Request request = new Request.Builder().url(apiUrl + "/admin/" + uid).delete(body).build();
      Response response = client.newCall(request).execute();
   }
}
