package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.MySQL_Helper;
import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.sql.Connection;
import java.util.List;

@Controller
public class StudentController
{
    @RequestMapping(path = {"/", "/home", "/dashboard", "/student"}, method = RequestMethod.GET)
    public String studentDashboard(Model model)
    {

        Connection con = MySQL_Helper.createSqlConnection();
        List<RequirementInstance> instances = MySQL_Helper.getRequirementInstances(con, "changeme");
        model.addAttribute("req_instances", instances.toArray());

        return "student";
    }
}
