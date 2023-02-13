package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.MySQL_Helper;
import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController
{
    @RequestMapping(path = {"/admin", "/admin/home", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboard(Model model)
    {
        Connection con = MySQL_Helper.createSqlConnection();
        List<String> users = MySQL_Helper.getAllApprovedUsers(con);
        HashMap<String, List<RequirementInstance>> userRequirements = new HashMap<String, List<RequirementInstance>>();

        for(String user : users)
        {
            List<RequirementInstance> requirements = MySQL_Helper.getRequirementInstances(con, user);
            userRequirements.put(user, requirements);
        }

        model.addAttribute("users", users.toArray());
        model.addAttribute("userRequirements", userRequirements);

        return "admin";
    }
}
