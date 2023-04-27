package edu.nau.coe_stic_app.security;

import com.google.common.hash.Hashing;
import edu.nau.coe_stic_app.models.CookieValues;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;

public class SecurityHelper {
    public static String hashUID(String uid)
    {
        return Hashing.sha256().hashString(uid, StandardCharsets.UTF_8).toString();
    }

    public static CookieValues getCookieValues(HttpServletRequest request)
    {
        Cookie cookies[] = request.getCookies();
        Cookie appCookie = null;
        int index;
        String cookieValue, parts[], uid, name, role, token;
        boolean isRegisteredStudent;

        //Find application cookie
        for(index = 0; index < cookies.length; index++)
        {
            if(cookies[index].getName().equals("SticWebApp"))
            {
                appCookie = cookies[index];
                break;
            }
        }

        //Extract cookie values
        cookieValue = appCookie.getValue();
        parts = cookieValue.split("&"); //UID=sbg75, Name=Sam:Gerstner, Role=student, RegisteredStudent=false, Token=xxxxx

        uid = parts[0].substring(parts[0].indexOf("=") + 1);
        name = parts[1].substring(parts[1].indexOf("=") + 1).replace(":", " ");
        role = parts[2].substring(parts[2].indexOf("=") + 1);
        isRegisteredStudent = Boolean.parseBoolean(parts[3].substring(parts[3].indexOf("=") + 1));
        token = parts[4].substring(parts[4].indexOf("=") + 1);
        CookieValues cookieVals = new CookieValues(uid, name, role, isRegisteredStudent, token);

        return cookieVals;
    }
}
