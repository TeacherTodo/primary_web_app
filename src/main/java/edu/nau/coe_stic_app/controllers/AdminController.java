package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController
{
    @RequestMapping(path = {"/admin", "/admin/home", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboard(Model model) throws IOException
    {
        HashMap<Student, RequirementInstance[]> map = new HashMap<Student, RequirementInstance[]>();
        List<Student> students = DB_Helper.getAllStudents();

        for(int index = 0; index < students.size(); index++)
        {
            List<RequirementInstance> requirements = DB_Helper.getStudentRequirements(students.get(index).getUID());
            map.put(students.get(index), (RequirementInstance[]) requirements.toArray());
        }

        model.addAttribute("StudentArray", map);
        return "admin";
    }
}
