package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.Requirement;
import edu.nau.coe_stic_app.models.RequirementAndInstance;
import edu.nau.coe_stic_app.models.RequirementInstance;
import edu.nau.coe_stic_app.models.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class StudentController {
    @GetMapping({"/", "/home", "/dashboard", "/student"})
    public String studentDashboard(Model model) throws IOException {
        String studentUID = "459c857678de7fd622295c13208a416929a11cfa80e53c27973ea4e048dcb9f2";
        Student student = DB_Helper.getStudent(studentUID);
        model.addAttribute("student", student);

        List<Requirement> requirements = DB_Helper.getAllRequirements();
        List<RequirementInstance> requirementInstances = DB_Helper.getStudentRequirements(studentUID);

        List<RequirementAndInstance> requirementAndInstances = RequirementAndInstance.create(requirementInstances, requirements);

        model.addAttribute("student", student);
        model.addAttribute("requirementAndInstances", requirementAndInstances);

        return "student";
    }
//    @RequestMapping(path = {"/", "/home", "/dashboard", "/student"}, method = RequestMethod.GET)
//    public String studentDashboard(Model model) throws IOException
//    {
//        //TODO: begin debugging
//        Requirement myReq = new Requirement(1, "my major", "my title", "my description", false);
//        String myJson = new ObjectMapper().writeValueAsString(myReq);
//        System.out.println("StudentController myJson: " + myJson);
//
//        Requirement myReqDecoded = new ObjectMapper().readValue(myJson, Requirement.class);
//        System.out.println("StudentController myReqDecoded: " + myReqDecoded);
//
//        String myJson2 = "{\"id\":1,\"major\":\"Computer Science\",\"title\":\"Submit Score of Arizona Teacher Proficiency Assessment\",\"description\":\"You must upload a passing score for administrator review on the required subject knowledge portion of the Arizona Teacher Proficiency Assessment that corresponds to the teaching certificate that you are pursuing.\",\"documentationRequired\":true}";
//
//        Requirement myReq2Decoded = new ObjectMapper().readValue(myJson2, Requirement.class);
//        System.out.println("StudentController myReq2Decoded: " + myReq2Decoded);
//        //TODO: end debugging
//
//        List<RequirementInstance> instances = DB_Helper.getStudentRequirements("6e8d6c69af58e73c7248364aa59b0c257f6ba1d19782eb9e38890a61ada948ef");//TODO: Update call to use actual UID of logged in user
//        List<Requirement> requirements = new ArrayList<Requirement>();
//
//        for(RequirementInstance instance : instances)
//        {https://stackoverflow.com/questions/20636456/using-thymeleaf-when-the-value-is-null
//            System.out.println("StudentController Requirement Instance: " + instance); //TODO: debugging
//            Requirement req = DB_Helper.getRequirementByID(instance.getRequirementID());
//            System.out.println("StudentController Requirement: " + req); //TODO: debugging
//            requirements.add(req);
//        }
//
//        System.out.println("StudentController Requirements: " + requirements);
//
//        model.addAttribute("TaskArray", instances.toArray());
//        model.addAttribute("requirements", requirements);
//        model.addAttribute("studentName", "John Smith"); //TODO: Get student name from cookie
//
//        return "student";
//    }
}
