package edu.nau.coe_stic_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nau.coe_stic_app.DB_Helper;
import edu.nau.coe_stic_app.models.CreateRequirementInstanceRequest;
import edu.nau.coe_stic_app.models.RequirementInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequirementInstanceController {
    @RequestMapping(path = "/create-instance", method = RequestMethod.POST)
    public String createInstance(@RequestBody String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CreateRequirementInstanceRequest req = mapper.readValue(jsonString, CreateRequirementInstanceRequest.class);

        if (req.doc_guid == null && req.retake_date == null) {
            DB_Helper.createRequirementInstance(req.requirement_id, req.student_uid, req.status);
        } else if (req.doc_guid == null) {
            DB_Helper.createRequirementInstance(req.requirement_id, req.student_uid, req.status, req.retake_date);
        } else if (req.retake_date == null) {
            DB_Helper.createRequirementInstance(req.requirement_id, req.student_uid, req.status, req.doc_guid);
        } else {
            DB_Helper.createRequirementInstance(req.requirement_id, req.student_uid, req.status, req.doc_guid, req.retake_date);
        }

        return "redirect:/dashboard";
    }

    @RequestMapping(path = "/edit-instance", method = RequestMethod.POST)
    public String editInstance(@RequestBody String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RequirementInstance instance = mapper.readValue(jsonString, RequirementInstance.class);
        DB_Helper.editRequirementInstance(instance);
        return "redirect:/dashboard";
    }
}
