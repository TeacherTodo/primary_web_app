package edu.nau.coe_stic_app.controllers;

import edu.nau.coe_stic_app.DB_Helper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class FMP_Controller
{
    @RequestMapping(path = "/admin/fmp_upload", method = RequestMethod.GET)
    public String displayPage(Model model)
    {
        return "fmp-upload";
    }

    @RequestMapping(path = "/admin/fmp_upload", method = RequestMethod.POST)
    public String uploadConfirm(@RequestParam("file") MultipartFile file, Model model) throws IOException
    {
        DB_Helper.uploadFmpExport(file);
        return "upload-confirm";
    }
}
