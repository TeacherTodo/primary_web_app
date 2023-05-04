package edu.nau.coe_stic_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CookieValues;
import edu.nau.coe_stic_app.models.CreateDocumentRequest;
import edu.nau.coe_stic_app.models.Document;
import edu.nau.coe_stic_app.security.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Controller
public class DocumentController {
    @RequestMapping(path = "/get-document/{guid}/{fileExtension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFileContent(@PathVariable String guid, @PathVariable String fileExtension) throws IOException {
        System.out.println("/get-document/{guid}/{fileExtension} guid: " + guid + " fileExtension: " + fileExtension);
        byte[] bytes = DB_Helper.getFileContent(guid, fileExtension);

        System.out.println("bytes: " + new String(bytes, StandardCharsets.UTF_8));

        // Content-Disposition: attachment; filename="cool.html"

        return bytes;
    }

    @RequestMapping(path = "/upload-document/{guid}/{fileExtension}", method = RequestMethod.POST)
    public String postFile(@PathVariable String guid, @PathVariable String fileExtension, @RequestBody byte data[]) throws IOException {
        DB_Helper.uploadFileContent(guid, fileExtension, data);
        return "redirect:/dashboard";
    }

    @RequestMapping(path = "/admin/all-docs", method = RequestMethod.GET)
    public String allDocs(HttpServletRequest req, Model model) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        List<Document> docs = DB_Helper.getAllDocs();
        //TODO: Pass requirement name to model
        model.addAttribute("docs", docs);

        return "view-docs";
    }

    @RequestMapping(path = "/admin/pending-docs", method = RequestMethod.GET)
    public String pendingDocs(HttpServletRequest req, Model model) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        List<Document> docs = DB_Helper.getPendingDocs();
        //TODO: Pass requirement name to model
        model.addAttribute("docs", docs);
        return "view-docs";
    }

    /*
     * Used to render a form to test /create-document
     */
    @GetMapping("/test-upload")
    public String testUpload() {
        return "test-upload";
    }

    @RequestMapping(path = "/create-document", method = RequestMethod.POST)
    public String createDoc(@RequestPart MultipartFile file, @RequestPart String student_uid, @RequestPart String requirement_uid) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String guid = UUID.randomUUID().toString();
        CreateDocumentRequest req = new CreateDocumentRequest();
        req.studentGuid = student_uid;
        req.fileExtension = Files.getFileExtension(file.getOriginalFilename());
        System.out.println("createDoc() requirement_uid: " + requirement_uid + " " + requirement_uid.getClass());
        req.requirementInstanceId = Integer.parseInt(requirement_uid);
        req.fileGuid = guid;

        //TODO: i need to fix this
        System.out.println("/create-document endpoint req: " + req);
        DB_Helper.createDocument(req);
        DB_Helper.uploadFileContent(req.fileGuid, req.fileExtension, file.getBytes());
        DB_Helper.changeRequirementInstanceStatus(req.fileGuid, "In Progress");

        return "redirect:/";
    }

    //Edit
    @RequestMapping(path = "/edit-document", method = RequestMethod.POST)
    public String editDoc(@RequestBody String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Document doc = mapper.readValue(jsonString, Document.class);
        DB_Helper.editDocument(doc);
        return "redirect:/admin";
    }

    //Delete
    @RequestMapping(path = "/delete-document/{guid}", method = RequestMethod.POST)
    public String deleteDoc(@PathVariable String guid) throws Exception {
        DB_Helper.deleteDocument(guid);
        return "redirect:/dashboard";
    }

    //TODO: Add approve/deny endpoints
    @GetMapping("/approve-document/{guid}")
    public String approveDoc(HttpServletRequest req, @PathVariable String guid) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

        DB_Helper.approveDocument(guid);
        DB_Helper.changeRequirementInstanceStatus(guid, "Completed");
        return "redirect:/admin";
    }

    @GetMapping("/deny-document/{guid}")
    public String denyDoc(HttpServletRequest req, @PathVariable String guid) throws Exception {
        CookieValues cookie = SecurityHelper.getCookieValues(req);
        if (!cookie.getRole().equals("admin")) {
            // return "redirect:/admin/unauthorized";
        }

//        DB_Helper.denyDocument(guid);
        DB_Helper.deleteDocument(guid); // TODO: should we delete it?
        DB_Helper.changeRequirementInstanceStatus(guid, "Incomplete");
        return "redirect:/admin";
    }
}
