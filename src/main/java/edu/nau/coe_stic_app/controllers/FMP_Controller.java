package edu.nau.coe_stic_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FMP_Controller
{
    @RequestMapping(path = "/admin/fmp_upload", method = RequestMethod.GET)
    public String displayPage(Model model)
    {
        return "fmp-upload";
    }

    @RequestMapping(path = "/admin/fmp_upload", method = RequestMethod.POST)
    public String uploadConfirm(Model model)
    {
        //TODO: Add operations to upload file to database

        return "upload-confirm";
    }
}
