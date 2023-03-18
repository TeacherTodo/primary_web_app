package edu.nau.coe_stic_app.security;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SecurityHelper
{
   public static String hashUID(String uid)
   {
      return Hashing.sha256().hashString(uid, StandardCharsets.UTF_8).toString();
   }
}
