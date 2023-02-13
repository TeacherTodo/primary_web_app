package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentController
{
    @RequestMapping(path = {"/", "/home", "/dashboard", "/student"}, method = RequestMethod.GET)
    public String studentDashboard(Model model)
    {

        //TODO: Insert call to doc API to get all req instances for specific UID
        model.addAttribute("req_instances", instances.toArray());

        return "student";
    }
}
