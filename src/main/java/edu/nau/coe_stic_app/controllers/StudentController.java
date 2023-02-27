package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.DataModel.*;
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
        List<RequirementInstance> instances = DB_Helper.getRequirementInstances("uid"); //TODO: Update call to use actual UID of logged in user
        model.addAttribute("req_instances", instances.toArray());

        return "student";
    }
}
