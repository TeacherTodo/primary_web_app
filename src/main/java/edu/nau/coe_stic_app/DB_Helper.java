package edu.nau.coe_stic_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.exceptions.Malformed_API_URL_Exception;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import edu.nau.DataModel.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

   public static Document getDocument(String guid, String uid) throws IOException
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
}
