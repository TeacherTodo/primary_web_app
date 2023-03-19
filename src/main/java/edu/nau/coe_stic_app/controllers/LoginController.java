package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController
{
   private static String authorizationRequestBaseUri = "oauth2/authorization";
   Map<String, String> oauthAuthenticationUrls = new HashMap<>();

   @Autowired
   private ClientRegistrationRepository clientRegistrationRepository;

   @Autowired
   private OAuth2AuthorizedClientService authorizedClientService;

   @GetMapping("/login")
   public String getLoginPage(Model model)
   {
      Iterable<ClientRegistration> clientRegistrations = null;
      ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
              .as(Iterable.class);
      if (type != ResolvableType.NONE &&
              ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0]))
      {
         clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
      }

      clientRegistrations.forEach(registration ->
              oauthAuthenticationUrls.put(registration.getClientName(),
                      authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
      model.addAttribute("urls", oauthAuthenticationUrls);

      return "login";
   }

   @GetMapping("/login-success")
   public String setupAppCookie(OAuth2AuthenticationToken auth, HttpServletResponse webResponse) throws IOException
   {
      OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());
      String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
      String email, name = "", uid = "", role = "";
      boolean registeredStudent = false;

      if (!StringUtils.isEmpty(userInfoEndpointUri))
      {
         RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
         HttpEntity entity = new HttpEntity("", headers);
         ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
         Map userAttributes = response.getBody();
         email = (String) userAttributes.get("email");
         name = (String) userAttributes.get("name");
         uid = SecurityHelper.hashUID(email.substring(0, email.indexOf("@")));
         role = DB_Helper.getUserRole(uid);
         registeredStudent = (role.equals("admin")) ? false : DB_Helper.isRegisteredStudent(uid);
      }

      String regStudentString = registeredStudent ? "true" : "false";
      String cookieValue = "UID=" + uid + "&Name=" + name + "&Role=" + role + "&RegisteredStudent=" + regStudentString
              + "&Token=" + client.getAccessToken().getTokenValue();
      Cookie cookie = new Cookie("SticWebApp", cookieValue);
      cookie.setMaxAge(1800);
      cookie.setPath("/");

      try
      {
         webResponse.addCookie(cookie);
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }

      return cookieValue;
   }
}
