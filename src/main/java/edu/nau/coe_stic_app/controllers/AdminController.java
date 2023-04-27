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
        HashMap<String, Document> docuMap = new HashMap<>();

        for (Student student : students) {
            List<RequirementInstance> studentRequirements = DB_Helper.getStudentRequirements(student.getUid());
            List<RequirementAndInstance> studentRequirementAndInstances = RequirementAndInstance.create(studentRequirements, requirements);

            map.put(student, studentRequirementAndInstances);

            for (int indice = 0; indice < studentRequirements.size(); indice++) {
                if (studentRequirements.get(indice).getDocGUID() != null) {
                    System.out.println("adminDashboard(Model model) DocGUID: " + studentRequirements.get(indice).getDocGUID());
                    String docGUID = studentRequirements.get(indice).getDocGUID();
                    // TODO: doc is null, fix this
                    Document doc = DB_Helper.getDocumentByGUID(docGUID, studentRequirements.get(indice).getStudentUID());
                    System.out.println("adminDashboard(Model model) doc: " + doc.toString());
                    docuMap.put(docGUID, doc);
                }
            }
        }

        docuMap.forEach((k, v) -> {
            System.out.println("adminDashboard(Model model) docuMap: " + k + " " + v);
        });

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

        // TODO: debugging
        studentsAndRequirementByMajor.forEach((major, mapOfStudents) -> {
            System.out.println("Major: " + major);
            mapOfStudents.forEach((student, listOfReq) -> {
                System.out.println("\tStudent: " + student);
                System.out.println("\tlistOfReq: " + listOfReq);
                System.out.println("\t===================================");
            });
            System.out.println("+++++++++++++++++++++++++++++++");
        });
        // TODO: end debugging

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
    @GetMapping(value = "admin", params = "email")
    public String findStudent(@RequestParam(name = "email") String studentEmail, Model model) throws IOException {
//        @GetMapping
//        @RequestMapping("/")
//        public ResponseEntity<JsonNode> get() throws JsonProcessingException {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode json = mapper.readTree("{\"id\": \"132\", \"name\": \"Alice\"}");
//            return ResponseEntity.ok(json);
//        }

        // Todo: we have the email, and code should be modified to allow us to getStudentByEmail
        //       currently doing this by hashing the email and using that as the studentUid
        String studentUid = Hashing.sha256().hashString(studentEmail, StandardCharsets.UTF_8).toString();
        Student student = DB_Helper.getStudent(studentUid);

        List<Requirement> requirements = DB_Helper.getAllRequirements();
        Map<Student, List<RequirementAndInstance>> map = new HashMap<>();
        List<RequirementInstance> studentRequirements = DB_Helper.getStudentRequirements(student.getUid());
        List<RequirementAndInstance> studentRequirementAndInstances = RequirementAndInstance.create(studentRequirements, requirements);

        map.put(student, studentRequirementAndInstances);


        model.addAttribute("map", map);

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

        //TODO: finish creating the template to display the filtered students
        return "admin";
    }

    /*
     * TODO: it would be nice to later filter students that have documents that
     *       are pending approval, this will likely require that the api be
     *       modified.
     */
}
