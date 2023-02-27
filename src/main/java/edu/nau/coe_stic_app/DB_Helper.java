package edu.nau.coe_stic_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.DataModel.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public class DB_Helper
{
   @Value("${api.url}")
   private static String apiUrl;

   public static List<Student> getAllStudents() throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
              .url(apiUrl + "/students")
              .build();
      Response response = client.newCall(request).execute();

      ObjectMapper mapper = new ObjectMapper();
      List<Student> students = mapper.readValue(response.body().byteStream(), new TypeReference<List<Student>>(){});
      return students;
   }

   public static List<RequirementInstance> getRequirementInstances(String uid) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
              .url(apiUrl + "/students/" + uid + "/requirements")
              .build();
      Response response = client.newCall(request).execute();

      ObjectMapper mapper = new ObjectMapper();
      List<RequirementInstance> requirements = mapper.readValue(response.body().byteStream(), new TypeReference<List<RequirementInstance>>(){});
      return requirements;
   }

   public static Document getStudentDocument(String guid, String uid) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
              .url(apiUrl + "/documents/" + uid + "/documents/" + guid)
              .build();
      Response response = client.newCall(request).execute();

      ObjectMapper mapper = new ObjectMapper();
      Document doc = mapper.readValue(response.body().byteStream(), Document.class);
      return doc;
   }

   public static void uploadStudentDocument(MultipartFile file, String uid) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody requestBody = new MultipartBody.Builder()
              .setType(MultipartBody.FORM)
              .addFormDataPart("file", file.getName(), RequestBody.create(
                      MediaType.parse("application/pdf"), file.getBytes()))
              .build();

      Request request = new Request.Builder()
              .url(apiUrl + "/students/" + uid + "/documents")
              .post(requestBody)
              .build();

      Response response = client.newCall(request).execute();
   }

   public static void uploadFmpExport(MultipartFile file) throws IOException
   {
      OkHttpClient client = new OkHttpClient();
      RequestBody requestBody = new MultipartBody.Builder()
              .setType(MultipartBody.FORM)
              .addFormDataPart("file", file.getName(), RequestBody.create(
                      MediaType.parse("application/vnd.ms-excel"), file.getBytes()))
              .build();

      Request request = new Request.Builder()
              .url(apiUrl + "/documents")
              .post(requestBody)
              .build();

      Response response = client.newCall(request).execute();
   }
}
