package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.AdminUser;
import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminUserController {
    @RequestMapping(path = "/admin/view-admins", method = RequestMethod.GET)
    public String viewAdmins(HttpServletRequest req, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        List<AdminUser> adminList = DB_Helper.getAllAdmins();
        model.addAttribute("adminList", adminList);
        return "view-admins";
    }

    @RequestMapping(path = "/admin/add-admin", method = RequestMethod.GET)
    public String createAdmin(HttpServletRequest req, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        model.addAttribute("admin", new AdminUser());
        return "add-admin";
    }

    @RequestMapping(path = "/admin/add-admin", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String confirmCreateAdmin(HttpServletRequest req, AdminUser admin) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        DB_Helper.createAdminUser(admin.getUID());
//        return "redirect:/admin/view-admins";
        return "redirect:/admin"; //TODO: change

    }

    @RequestMapping(path = "/admin/delete-admin/{uid}", method = RequestMethod.GET)
    public String deleteAdmin(HttpServletRequest req, @PathVariable String uid, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        model.addAttribute("uid", uid);
        return "delete-admin";
    }

    @RequestMapping(path = "/admin/delete-admin/{uid}", method = RequestMethod.POST)
    public String confirmDeleteAdmin(HttpServletRequest req, @PathVariable String uid) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            return "redirect:/admin/unauthorized";
        }

        DB_Helper.deleteAdmin(uid);
        return "redirect:/admin/view-admins";
    }
}
