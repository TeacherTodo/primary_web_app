package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentController
{
    @RequestMapping(path = {"/", "/home", "/dashboard", "/student"}, method = RequestMethod.GET)
    public String studentDashboard(Model model) throws IOException
    {
        List<RequirementInstance> instances = DB_Helper.getStudentRequirements("uid");//TODO: Update call to use actual UID of logged in user
        model.addAttribute("TaskArray", instances.toArray());
        //TODO: Pass list of requirements for each instance
        //TODO: Pass student name

        return "student";
    }
}
