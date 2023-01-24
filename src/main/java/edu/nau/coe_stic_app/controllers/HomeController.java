package edu.nau.coe_stic_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController
{
    @RequestMapping(path = {"/home", "/index", "/"}, method = RequestMethod.GET)
    public String serverHomePage(Model model)
    {
        return "index";
    }
}
