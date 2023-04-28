package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileMakerProController {

    @GetMapping("fileMakerPro/import")
    public String importFileMakerPro(HttpServletRequest req) {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        return "fileMakerPro-import";
    }

    @GetMapping("fileMakerPro/export")
    public String exportFileMakerPro(HttpServletRequest req) {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        return "fileMakerPro-export";
    }

}
