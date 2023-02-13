package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController
{
    @RequestMapping(path = {"/admin", "/admin/home", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboard(Model model)
    {
        //TODO: Insert call to doc API to get a list of all the approved student users
        HashMap<String, List<RequirementInstance>> userRequirements = new HashMap<String, List<RequirementInstance>>();

        for(String user : users)
        {
            //TODO: Insert call to doc API to get a list of all req instances for specific user
            userRequirements.put(user, requirements);
        }

        model.addAttribute("users", users.toArray());
        model.addAttribute("userRequirements", userRequirements);

        return "admin";
    }
}
