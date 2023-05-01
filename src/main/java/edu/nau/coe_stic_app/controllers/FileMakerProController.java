package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import okhttp3.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class FileMakerProController {

    @GetMapping("fileMakerPro/import")
    public String importFileMakerPro(HttpServletRequest req) {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        return "fileMakerPro-import";
    }

    @GetMapping("fileMakerPro/export")
    public String exportFileMakerPro(HttpServletRequest req) {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        return "fileMakerPro-export";
    }

    @GetMapping(path="fileMakerPro-import")
    public String fileMakerProImport(HttpServletRequest req) {
        try {
            DB_Helper.importFileMakerPro();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/admin";
    }

}
