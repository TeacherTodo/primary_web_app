package edu.nau.coe_stic_app.controllers;

import com.google.common.hash.Hashing;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @RequestMapping(path = {"/admin", "/admin/home", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDashboard(Model model) throws Exception {
        List<Student> students = DB_Helper.getAllStudents();
        List<Requirement> requirements = DB_Helper.getAllRequirements();
        Map<Student, List<RequirementAndInstance>> map = new HashMap<>();
        HashMap<String, Document > docuMap = new HashMap<>();


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

            for( int indice = 0; indice < requirementsInstances.size(); indice++ )
            {
                if( requirementsInstances.get(indice).getDocGUID() != null )
                {
                    docuMap.put(requirementsInstances.get(indice).getDocGUID(), DB_Helper.getDocumentByGUID(requirementsInstances.get(indice).getDocGUID(), requirementsInstances.get(indice).getStudentUID()));
                }
            }

            map.put(student, studentRequirementAndInstances);

            for( int indice = 0; indice < studentRequirements.size(); indice++ )
            {
                if( studentRequirements.get(indice).getDocGUID() != null )
                {
                    docuMap.put(studentRequirements.get(indice).getDocGUID(), DB_Helper.getDocumentByGUID(studentRequirements.get(indice).getDocGUID(), studentRequirements.get(indice).getStudentUID()));
                }
            }
        }



        model.addAttribute("map", map);
        model.addAttribute("docuMap", docuMap);


        return "admin";
    }

    /*
     * Filter by Major
     */
    @GetMapping(value = "admin", params = "filter=major")
    public String filterByMajor(Model model) throws IOException {
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
