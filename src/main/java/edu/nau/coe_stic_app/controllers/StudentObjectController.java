package edu.nau.coe_stic_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CreateStudentRequest;
import edu.nau.coe_stic_app.models.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentObjectController
{
    @RequestMapping(path = "/create-student", method = RequestMethod.POST)
    public String createStudent(@RequestBody String jsonString) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        CreateStudentRequest req = mapper.readValue(jsonString, CreateStudentRequest.class);
        DB_Helper.createStudent(req.uid, req.major, req.grad_term, req.grad_year);
        return "redirect:/dashboard";
    }

    @RequestMapping(path = "/edit-student", method = RequestMethod.POST)
    public String editStudent(@RequestBody String jsonString) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(jsonString, Student.class);
        DB_Helper.editStudent(student);
        return "redirect:/admin/dashboard";
    }
}
