package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.Major;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.List;

@Controller
public class MajorController
{
    @RequestMapping(path = "/admin/view-majors", method = RequestMethod.GET)
    public String viewMajors(Model model) throws Exception
    {
        List<Major> majors = DB_Helper.getAllMajors();
        model.addAttribute("majors", majors);
        return "view-majors";
    }

    @RequestMapping(path = "/admin/add-major", method = RequestMethod.GET)
    public String createMajor(Model model) throws IOException
    {
        model.addAttribute("major", new Major());
        return "add-major";
    }

    @RequestMapping(path = "/admin/add-major", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createMajorConfirm(Major major) throws Exception
    {
        DB_Helper.createMajor(major.getMajor());
        return "redirect:/admin/view-majors";
    }

    @RequestMapping(path = "/admin/delete-major/{name}", method = RequestMethod.GET)
    public String deleteMajor(@PathVariable String name,  Model model)
    {
        model.addAttribute("name", name);
        return "delete-major";
    }

    @RequestMapping(path = "/admin/delete-major/{name}", method = RequestMethod.POST)
    public String confirmDeleteMajor(@PathVariable String name) throws Exception
    {
        DB_Helper.deleteMajor(name);
        return "redirect:/admin/view-majors";
    }
}
