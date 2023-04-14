package edu.nau.coe_stic_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileMakerProController {

    @GetMapping("fileMakerPro/import")
    public String importFileMakerPro() {
        return "fileMakerPro-import";
    }

    @GetMapping("fileMakerPro/export")
    public String exportFileMakerPro() {
        return "fileMakerPro-export";
    }

}
