package edu.nau.coe_stic_app.controllers;

import com.google.common.hash.Hashing;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.*;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @RequestMapping(path = "/admin/unauthorized", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "You are not authorized to access this page.")
    public String unauthorizedAccess()
    {
        return "";
    }

    @RequestMapping(path = {"/admin", "/admin/home", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboard(Model model) throws IOException {
        System.out.println("AdminController adminDashboard()"); //TODO: debugging
        System.out.flush(); //TODO: debugging

//        HashMap<Student, RequirementInstance[]> map = new HashMap<Student, RequirementInstance[]>();
        HashMap<Student, List<RequirementInstance>> map = new HashMap<>();
        List<Requirement> requirements = DB_Helper.getAllRequirements();
        List<Student> students = DB_Helper.getAllStudents();

        //TODO: debugging
//        System.out.println("AdminController students: " + students);
//        students.forEach(System.out::println);
        //TODO: end debugging

        for (int index = 0; index < students.size(); index++) {
            List<RequirementInstance> requirementsInstances = DB_Helper.getStudentRequirements(students.get(index)
                                                                                                       .getUid());
            //TODO: debugging
//            System.out.println("AdminController requirements: " + requirements);
            //TODO: end debugging

//            map.put(students.get(index), (RequirementInstance[]) requirements.toArray());
            map.put(students.get(index), requirementsInstances);

            //TODO: debugging
//            System.out.println("AdminController END OF LOOP");
            //TODO: end debugging
        }

        //TODO: debugging
//        System.out.println("AdminController OUT OF LOOP");
        //TODO: end debugging

        //TODO: debugging
//        System.out.println("AdminController map: " + map);
        //TODO: end debugging

        model.addAttribute("map", map);


        return "admin";
    }

    /*
     * Filter by Major
     */
    @GetMapping(value = "admin", params = "filter=major")
    public String filterByMajor(HttpServletRequest req, Model model) throws IOException {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if(!cookie.getRole().equals("admin"))
        {
            return "redirect:/admin/unauthorized";
        }

        List<Student> students = DB_Helper.getAllStudents();
        List<Requirement> requirements = DB_Helper.getAllRequirements();
        Map<String, Map<Student, List<RequirementAndInstance>>> studentsAndRequirementByMajor = new HashMap<>();

        for (Student student : students) {
            List<RequirementInstance> studentRequirements = DB_Helper.getStudentRequirements(student.getUid());
            List<RequirementAndInstance> studentRequirementAndInstances = RequirementAndInstance.create(studentRequirements, requirements);

            String major = student.getMajor();

            if (!studentsAndRequirementByMajor.containsKey(major)) {
                studentsAndRequirementByMajor.put(major, new HashMap<>());
            }

            studentsAndRequirementByMajor.get(major)
                    .put(student, studentRequirementAndInstances);
        }

        studentsAndRequirementByMajor.forEach((major, mapOfStudents) -> {
            System.out.println("Major: " + major);
            mapOfStudents.forEach((student, listOfReq) -> {
                System.out.println("\tStudent: " + student);
                System.out.println("\tlistOfReq: " + listOfReq);
                System.out.println("\t===================================");
            });
            System.out.println("+++++++++++++++++++++++++++++++");
        });

        model.addAttribute("map", studentsAndRequirementByMajor);

        return "admin-filter";
    }

    /*
     * Filter students with uncompleted requirements
     */
    public String filterByUncompletedRequirements(Model model) throws IOException {
        List<Student> students = DB_Helper.getAllStudents();
        Map<Student, List<RequirementInstance>> studentRequirementMap = new HashMap<>();
        for (Student student : students) {
            List<RequirementInstance> requirementInstances = DB_Helper.getStudentRequirements(student.getUid());
            for (RequirementInstance requirementInstance : requirementInstances) {
                if (requirementInstance.getStatus() != "Complete") {
                    studentRequirementMap.put(student, requirementInstances);
                }

                break;
            }
        }

        //TODO: probably want to add an attribute telling the model what we are filtering by.
        model.addAttribute("studentRequirementMap", studentRequirementMap);

        //TODO: finish creating the template to display the filtered students
        return "admin-filter";
    }

    /*
     * Filter students with all requirements completed
     */
    public String filterByCompletedRequirements(Model model) throws IOException {
        List<Student> students = DB_Helper.getAllStudents();
        Map<Student, List<RequirementInstance>> studentRequirementMap = new HashMap<>();
        for (Student student : students) {
            List<RequirementInstance> requirementInstances = DB_Helper.getStudentRequirements(student.getUid());
            for (RequirementInstance requirementInstance : requirementInstances) {
                if (requirementInstance.getStatus() == "Complete") {
                    studentRequirementMap.put(student, requirementInstances);
                }

                break;
            }
        }

        //TODO: probably want to add an attribute telling the model what we are filtering by.
        model.addAttribute("studentRequirementMap", studentRequirementMap);

        return "admin-filter";
    }


    /*
     * Filter Students by email aka studentUid
     */
    @GetMapping(value = "admin", params = "id")
    public String findStudent(@RequestParam(name = "id") String studentId, Model model) throws IOException {
        model.addAttribute("value", studentId);
        return "test";

//        //TODO: create a form to get the student's email that will be passed into the controller
//        String studentEmail = "someEmail"; //TODO change
//        String studentUid = Hashing.sha256().hashString(studentEmail, StandardCharsets.UTF_8).toString();
//        Student student = DB_Helper.getStudent(studentUid);
//        List<RequirementInstance> requirementInstances = DB_Helper.getStudentRequirements(studentUid);
//        //TODO might not want to use a map, but it might be easier if we keep the interface uniform
//        Map<Student, List<RequirementInstance>> studentRequirementMap = new HashMap<>();
//        studentRequirementMap.put(student, requirementInstances);
//
//        //TODO: probably want to add an attribute telling the model what we are filtering by.
//        model.addAttribute("studentRequirementMap", studentRequirementMap);
//
//        //TODO: finish creating the template to display the filtered students
//        return "admin-filter";
    }

    /*
     * TODO: it would be nice to later filter students that have documents that
     *       are pending approval, this will likely require that the api be
     *       modified.
     */
}
