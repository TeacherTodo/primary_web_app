package edu.nau.coe_stic_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CreateDocumentRequest;
import edu.nau.coe_stic_app.models.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
public class DocumentController
{
    @RequestMapping(path = "/admin/all-docs", method = RequestMethod.GET)
    public String allDocs(Model model) throws Exception
    {
        List<Document> docs = DB_Helper.getAllDocs();
        model.addAttribute("docs", docs);
        return "view-docs"; //TODO: Create HTML page
    }

    @RequestMapping(path = "/admin/pending-docs",method = RequestMethod.GET)
    public String pendingDocs(Model model) throws Exception
    {
        List<Document> docs = DB_Helper.getPendingDocs();
        model.addAttribute("docs", docs);
        return "view-docs";
    }

    @RequestMapping(path = "/create-document", method = RequestMethod.POST)
    public String createDoc(@RequestBody String jsonString) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        CreateDocumentRequest req = mapper.readValue(jsonString, CreateDocumentRequest.class);
        DB_Helper.createDocument(req);
        return "redirect:/dashboard";
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
}
