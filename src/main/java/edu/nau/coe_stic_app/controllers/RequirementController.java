package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.models.Major;
import edu.nau.coe_stic_app.models.Requirement;
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
public class RequirementController {
    @RequestMapping(path = "/admin/add-requirement", method = RequestMethod.GET)
    public String requirementGet(HttpServletRequest req, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        List<Major> majors = DB_Helper.getAllMajors();
        model.addAttribute("allMajors", majors);
        model.addAttribute("requirement", new Requirement());
        return "add-requirement";
    }

    @RequestMapping(path = "/admin/add-requirement", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String requirementPost(Requirement req) throws Exception {
        // NOTE: testing
        System.out.println("requirementPost(Requirement req): ");
        System.out.println(req);
        // NOTE: end testing

        DB_Helper.createRequirement(req.getMajor(), req.getTitle(), req.getDescription(), req.isDocumentationRequired());
        return "redirect:/admin/view-requirements";
    }

    @RequestMapping(path = "/admin/view-requirements", method = RequestMethod.GET)
    public String viewRequirements(HttpServletRequest req, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        List<Requirement> requirements = DB_Helper.getAllRequirements();
        model.addAttribute("requirementList", requirements);
        return "view-requirements";
    }

    @RequestMapping(path = "/admin/edit-requirement/{id}", method = RequestMethod.GET)
    public String editRequirement(HttpServletRequest req, @PathVariable int id, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        Requirement requ = DB_Helper.getRequirementByID(id);
        List<Major> majors = DB_Helper.getAllMajors();
        model.addAttribute("allMajors", majors);
        model.addAttribute("requirement", requ);
        model.addAttribute("selectedMajor", requ.getMajor());
        return "edit-requirement";
    }

    @RequestMapping(path = "/admin/edit-requirement/{id}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String editRequirementConfirm(Requirement req) throws Exception {
        DB_Helper.editRequirement(req);
        return "redirect:/admin/view-requirements";
    }

    @RequestMapping(path = "/admin/delete-requirement/{id}", method = RequestMethod.GET)
    public String deleteRequirement(HttpServletRequest req, @PathVariable int id, Model model) {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        model.addAttribute("id", id);
        return "delete-requirement";
    }

    @RequestMapping(path = "/admin/delete-requirement/{id}", method = RequestMethod.POST)
    public String deleteRequirementConfirm(@PathVariable int id) throws Exception {
        DB_Helper.deleteRequirement(id);
        return "redirect:/admin/view-requirements";
    }
}
