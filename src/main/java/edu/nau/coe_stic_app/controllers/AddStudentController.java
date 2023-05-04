package edu.nau.coe_stic_app.controllers;

import com.google.common.hash.Hashing;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.models.Student;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class AddStudentController
{
   @RequestMapping(path = "/admin/add-student", method = RequestMethod.GET)
   public String addStudent(HttpServletRequest req, Model model) throws IOException
   {
      CookieValues cookie = SecurityHelper.getCookieValues(req);
      if (!cookie.getRole().equals("admin")) {
         // return "redirect:/admin/unauthorized";
      }

      model.addAttribute("student", new Student());
      model.addAttribute("majors", DB_Helper.getAllMajors());
      model.addAttribute("terms", DB_Helper.getAllTerms());
      return "add-student";
   }

   @RequestMapping(path = "/admin/add-student", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
   public String confirmAddStudent(HttpServletRequest req, Student student) throws Exception
   {
      CookieValues cookie = SecurityHelper.getCookieValues(req);
      if (!cookie.getRole().equals("admin")) {
         // return "redirect:/admin/unauthorized";
      }

      student.setUid(Hashing.sha256().hashString(student.getUid(), StandardCharsets.UTF_8).toString());
      DB_Helper.createStudent(student.getUid(), student.getMajor(), student.getGrad_term(), student.getGrad_year(),
              student.getFirst_name(), student.getLast_name());
      return "redirect:/admin";
   }
}
