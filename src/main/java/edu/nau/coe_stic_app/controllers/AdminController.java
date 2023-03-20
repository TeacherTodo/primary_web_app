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
        System.out.println("AdminController adminDashboard()"); //TODO: debugging
        System.out.flush(); //TODO: debugging

//        HashMap<Student, RequirementInstance[]> map = new HashMap<Student, RequirementInstance[]>();
        HashMap<Student, List<RequirementInstance>> map = new HashMap<>();

        List<Student> students = DB_Helper.getAllStudents();

        //TODO: debugging
        System.out.println("AdminController students: " + students);
        students.forEach(System.out::println);
        //TODO: end debugging

        for(int index = 0; index < students.size(); index++)
        {
            List<RequirementInstance> requirements = DB_Helper.getStudentRequirements(students.get(index).getUid());
            //TODO: debugging
            System.out.println("AdminController requirements: " + requirements);
            //TODO: end debugging

//            map.put(students.get(index), (RequirementInstance[]) requirements.toArray());
            map.put(students.get(index), requirements);

            //TODO: debugging
            System.out.println("AdminController END OF LOOP");
            //TODO: end debugging
        }

        //TODO: debugging
        System.out.println("AdminController OUT OF LOOP");
        //TODO: end debugging

        //TODO: debugging
        System.out.println("AdminController map: " + map);
        //TODO: end debugging

        model.addAttribute("map", map);
        return "admin";
    }
}
