package edu.nau.coe_stic_app.controllers;

import com.google.common.hash.Hashing;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Requirement> requirements = DB_Helper.getAllRequirements();
        List<Student> students = DB_Helper.getAllStudents();

        //TODO: debugging
//        System.out.println("AdminController students: " + students);
//        students.forEach(System.out::println);
        //TODO: end debugging

        for(int index = 0; index < students.size(); index++)
        {
            List<RequirementInstance> requirementsInstances = DB_Helper.getStudentRequirements(students.get(index).getUid());
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
    public String filterByMajor(Model model) throws IOException {
        List<Student> students = DB_Helper.getAllStudents();
        Map<String, List<Student>> studentsByMajor = new HashMap<>();
        for (Student student : students) {
            String major = student.getMajor();
            if (!studentsByMajor.containsKey(major)) {
                studentsByMajor.put(major, new ArrayList<>());
            }
            studentsByMajor.get(major).add(student);
        }

        model.addAttribute("studentsByMajor", studentsByMajor);

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

        //TODO: finish creating the template to display the filtered students
        return "admin-filter";
    }

    public String findStudent(Model model) throws IOException {
        //TODO: create a form to get the student's email that will be passed into the controller
        String studentEmail = "someEmail"; //TODO change
        String studentUid = Hashing.sha256().hashString(studentEmail, StandardCharsets.UTF_8).toString();
        Student student = DB_Helper.getStudent(studentUid);
        List<RequirementInstance> requirementInstances = DB_Helper.getStudentRequirements(studentUid);
        //TODO might not want to use a map, but it might be easier if we keep the interface uniform
        Map<Student, List<RequirementInstance>> studentRequirementMap = new HashMap<>();
        studentRequirementMap.put(student, requirementInstances);

        //TODO: probably want to add an attribute telling the model what we are filtering by.
        model.addAttribute("studentRequirementMap", studentRequirementMap);

        //TODO: finish creating the template to display the filtered students
        return "admin-filter";
    }

    /*
     * TODO: it would be nice to later filter students that have documents that
     *       are pending approval, this will likely require that the api be
     *       modified.
     */
}
