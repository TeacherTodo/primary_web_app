package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.models.Major;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class MajorController {
    @RequestMapping(path = "/admin/view-majors", method = RequestMethod.GET)
    public String viewMajors(HttpServletRequest req, Model model) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if(!cookie.getRole().equals("admin"))
        {
            return "redirect:/admin/unauthorized";
        }

        List<Major> majors = DB_Helper.getAllMajors();
        model.addAttribute("majors", majors);
        return "view-majors";
    }

    @RequestMapping(path = "/admin/add-major", method = RequestMethod.GET)
    public String createMajor(HttpServletRequest req, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if(!cookie.getRole().equals("admin"))
        {
            return "redirect:/admin/unauthorized";
        }

        model.addAttribute("major", new Major());
        return "add-major";
    }

    @RequestMapping(path = "/admin/add-major", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createMajorConfirm(@RequestParam MultiValueMap paramMap) throws Exception {
        System.out.println("createMajorConfirm(MultiValueMap paramMap) keySet(): " + paramMap.keySet()); //TODO: debugging
        System.out.println("createMajorConfirm(MultiValueMap paramMap) major: " + paramMap.getFirst("major")); //TODO: debugging
        DB_Helper.createMajor((String) paramMap.getFirst("major"));
        return "redirect:/admin/view-majors";
    }

    @RequestMapping(path = "/admin/delete-major/{name}", method = RequestMethod.GET)
    public String deleteMajor(HttpServletRequest req, @PathVariable String name, Model model) {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if(!cookie.getRole().equals("admin"))
        {
            return "redirect:/admin/unauthorized";
        }

        System.out.println("deleteMajor(name): " + name);
        model.addAttribute("major", name);
        return "delete-major";
    }

    @RequestMapping(path = "/admin/delete-major/{name}", method = RequestMethod.POST)
    public String confirmDeleteMajor(@PathVariable String name) throws Exception {
        System.out.println("confirmDeleteMajor(name): " + name);
        DB_Helper.deleteMajor(name);
        return "redirect:/admin/view-majors";
    }
}
