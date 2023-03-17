package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.AdminUser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminUserController
{
    @RequestMapping(path = "/admin/view-admins", method = RequestMethod.GET)
    public String viewAdmins(Model model) throws IOException
    {
        List<AdminUser> adminList = DB_Helper.getAllAdmins();
        model.addAttribute("adminList", adminList);
        return "view-admins";
    }

    @RequestMapping(path = "/admin/add-admin", method = RequestMethod.GET)
    public String createAdmin(Model model) throws IOException
    {
        model.addAttribute("admin", new AdminUser());
        return "add-admin";
    }

    @RequestMapping(path = "/admin/add-admin", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String confirmCreateAdmin(AdminUser admin) throws Exception
    {
        DB_Helper.createAdminUser(admin.getUID());
        return "redirect:/admin/view-admins";
    }

    @RequestMapping(path = "/admin/delete-admin/{uid}", method = RequestMethod.GET)
    public String deleteAdmin(@PathVariable String uid,  Model model) throws IOException
    {
        model.addAttribute("uid", uid);
        return "delete-admin";
    }

    @RequestMapping(path = "/admin/delete-admin/{uid}", method = RequestMethod.POST)
    public String confirmDeleteAdmin(@PathVariable String uid) throws Exception
    {
        DB_Helper.deleteAdmin(uid);
        return "redirect:/admin/view-admins";
    }
}
