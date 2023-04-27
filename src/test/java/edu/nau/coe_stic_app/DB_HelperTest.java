package edu.nau.coe_stic_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.models.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DB_HelperTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getFileContent() {

    }

    @Test
    void uploadFileContent() {

    }

    @Test
    void getUserRole() throws IOException {
        String case1 = DB_Helper.getUserRole("005c90a835f9fd4415591d3d0c8dbbfd32f3416d8d9cd8cafa65e92cb63669ff");
        String case2 = DB_Helper.getUserRole("1f38f63f48e236e4cc11e33b1e44ef19bd1a56215800b22da89dfbe5288888e7");
        String case3 = DB_Helper.getUserRole("sbg75");

        assertEquals("student", case1);
        assertEquals("student", case2);
        assertEquals("admin", case3);
    }

    @Test
    void isRegisteredStudent() throws IOException {
        boolean case1 = DB_Helper.isRegisteredStudent("005c90a835f9fd4415591d3d0c8dbbfd32f3416d8d9cd8cafa65e92cb63669ff");
        boolean case2 = DB_Helper.isRegisteredStudent("1f38f63f48e236e4cc11e33b1e44ef19bd1a56215800b22da89dfbe5288888e7");
        boolean case3 = DB_Helper.isRegisteredStudent("somerandomstring");

        assertTrue(case1);
        assertTrue(case2);
        assertFalse(case3);
    }

    @Test
    void getStudent() throws IOException {
        Student case1Expected = new Student("09d2aa8d87369380faa0479f7b3c1bd676135653518030454fb421afa114690e", "Computer Science", "Fall", 2024);
        Student case2Expected = new Student("1530639ef97e9ce2f6ccaae402b35c685b72254a82b0caf21a404f351f627a36", "Computer Engineering", "Spring", 2023);
        Student case1 = DB_Helper.getStudent("09d2aa8d87369380faa0479f7b3c1bd676135653518030454fb421afa114690e");
        Student case2 = DB_Helper.getStudent("1530639ef97e9ce2f6ccaae402b35c685b72254a82b0caf21a404f351f627a36");

        assertEquals(case1Expected, case1);
        assertEquals(case2Expected, case2);
    }

    @Test
    void getStudentRequirements() {

    }

    @Test
    void getAllTerms() throws IOException {
        List<Term> terms = DB_Helper.getAllTerms();
        Term expected[] = {new Term("Fall"), new Term("Spring"), new Term("Summer"), new Term("Winter")};
        assertArrayEquals(expected, terms.toArray());
    }

    @Test
    void getAllRequirementStatuses() throws IOException {
        List<RequirementStatus> statuses = DB_Helper.getAllRequirementStatuses();
        RequirementStatus expected[] = {new RequirementStatus("Complete"), new RequirementStatus("In Progress"), new RequirementStatus("Incomplete")};
        assertArrayEquals(expected, statuses.toArray());
    }

    @Test
    void getAllApprovalStatuses() throws IOException {
        List<ApprovalStatus> statuses = DB_Helper.getAllApprovalStatuses();
        ApprovalStatus expected[] = {new ApprovalStatus("Approved"), new ApprovalStatus("Denied"), new ApprovalStatus("Pending Approval")};
        assertArrayEquals(expected, statuses.toArray());
    }

    @Test
    void getRequirementByID() throws IOException {

        Requirement case1Expected = new Requirement(-2039212213, "Computer Science", "Computer Science Requirement 1 Title",
                "Computer Science Requirement 1 Description", false);
        Requirement case1 = DB_Helper.getRequirementByID(-2039212213);

        assertEquals(case1Expected, case1);
    }

    @Test
    void getMajorByName() throws IOException {
        Major case1Expected = new Major("Computer Science");
        Major case2Expected = new Major("Computer Engineering");
        Major case1 = DB_Helper.getMajorByName("Computer Science");
        Major case2 = DB_Helper.getMajorByName("Computer Engineering");
    }

    @Test
    void getTermByName() throws IOException {
        Term case1Expected = new Term("Fall");
        Term case2Expected = new Term("Spring");
        Term case1 = DB_Helper.getTermByName("Fall");
        Term case2 = DB_Helper.getTermByName("Spring");

        assertEquals(case1Expected, case1);
        assertEquals(case2Expected, case2);
    }

    @Test
    void createAdminUser() {

    }

    @Test
    void createStudent() {

    }

    @Test
    void createRequirement() {

    }

    @Test
    void createRequirementInstance() {

    }

    @Test
    void testCreateRequirementInstance() {

    }

    @Test
    void testCreateRequirementInstance1() {

    }

    @Test
    void testCreateRequirementInstance2() {

    }

    @Test
    void createMajor() {

    }

    @Test
    void createDocument() {

    }

    @Test
    void editRequirement() {

    }

    @Test
    void editStudent() {

    }

    @Test
    void editDocument() {

    }

    @Test
    void editRequirementInstance() {

    }

    @Test
    void deleteRequirement() {

    }

    @Test
    void deleteMajor() {

    }

    @Test
    void deleteAdmin() {

    }

    @Test
    void deleteDocument() {

    }
}