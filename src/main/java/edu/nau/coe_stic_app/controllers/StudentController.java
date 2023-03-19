package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.Requirement;
import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController
{
    @RequestMapping(path = {"/", "/home", "/dashboard", "/student"}, method = RequestMethod.GET)
    public String studentDashboard(Model model) throws IOException
    {
        List<RequirementInstance> instances = DB_Helper.getStudentRequirements("6e8d6c69af58e73c7248364aa59b0c257f6ba1d19782eb9e38890a61ada948ef");//TODO: Update call to use actual UID of logged in user
        List<Requirement> requirements = new ArrayList<Requirement>();

        for(RequirementInstance instance : instances)
        {
            requirements.add(DB_Helper.getRequirementByID(instance.getRequirementID()));
        }

        model.addAttribute("TaskArray", instances.toArray());
        model.addAttribute("requirements", requirements);
        model.addAttribute("studentName", "CHANGE ME"); //TODO: Get student name from cookie

        return "student";
    }
}
