package edu.nau.coe_stic_app.controllers;

import edu.nau.DataModel.Student;
import edu.nau.coe_stic_app.DB_Helper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController
{
    @RequestMapping(path = {"/admin", "/admin/home", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboard(Model model) throws IOException
    {
        List<Student> students = DB_Helper.getAllStudents();
        model.addAttribute("students", students.toArray());
        return "admin";
    }
}
