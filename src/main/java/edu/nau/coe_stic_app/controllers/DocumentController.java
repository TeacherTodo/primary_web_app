package edu.nau.coe_stic_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CreateDocumentRequest;
import edu.nau.coe_stic_app.models.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class DocumentController
{
    @RequestMapping(path = "/get-document/{guid}/{fileExtension}", method = RequestMethod.GET)
    public byte[] getFileContent(@PathVariable String guid, @PathVariable String fileExtension) throws IOException
    {
        return DB_Helper.getFileContent(guid, fileExtension);
    }

    @RequestMapping(path = "/upload-document/{guid}/{fileExtension}", method = RequestMethod.POST)
    public String postFile(@PathVariable String guid, @PathVariable String fileExtension, @RequestBody byte data[]) throws IOException
    {
        DB_Helper.uploadFileContent(guid, fileExtension, data);
        return "redirect:/dashboard";
    }

    @RequestMapping(path = "/admin/all-docs", method = RequestMethod.GET)
    public String allDocs(Model model) throws Exception
    {
        List<Document> docs = DB_Helper.getAllDocs();
        //TODO: Pass requirement name to model
        model.addAttribute("docs", docs);
        return "view-docs"; //TODO: Create HTML page
    }

    @RequestMapping(path = "/admin/pending-docs",method = RequestMethod.GET)
    public String pendingDocs(Model model) throws Exception
    {
        List<Document> docs = DB_Helper.getPendingDocs();
        //TODO: Pass requirement name to model
        model.addAttribute("docs", docs);
        return "view-docs";
    }

    /*
     * Used to render a form to test /create-document
     */
    @GetMapping("/test-upload")
    public String testUpload()
    {
        return "test-upload";
    }

    @RequestMapping(path = "/create-document", method = RequestMethod.POST)
    public String createDoc(@RequestPart MultipartFile file, @RequestPart String student_uid, @RequestPart String requirement_uid) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String guid = UUID.randomUUID().toString();
        CreateDocumentRequest req = new CreateDocumentRequest();
        req.student_uid = student_uid;
        req.file_extension = Files.getFileExtension(file.getOriginalFilename());
        System.out.println("createDoc() requirement_uid: " + requirement_uid + " " + requirement_uid.getClass());
        req.requirement_instance_id = Integer.parseInt(requirement_uid);
        req.file_guid = guid;

        //TODO: i need to fix this
        System.out.println("req: " + req);
        DB_Helper.createDocument(req);
        DB_Helper.uploadFileContent(req.file_guid, req.file_extension, file.getBytes());
//        return "redirect:/dashboard";
        return "redirect:/";
    }

    //Edit
    @RequestMapping(path = "/edit-document", method = RequestMethod.POST)
    public String editDoc(@RequestBody String jsonString) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Document doc = mapper.readValue(jsonString, Document.class);
        DB_Helper.editDocument(doc);
        return "redirect:/admin/dashboard";
    }

    //Delete
    @RequestMapping(path = "/delete-document/{guid}", method = RequestMethod.POST)
    public String deleteDoc(@PathVariable String guid) throws Exception
    {
        DB_Helper.deleteDocument(guid);
        return "redirect:/dashboard";
    }

    //TODO: Add approve/deny endpoints
}
