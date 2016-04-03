package com.focuszz.mkfs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focuszz.mkfs.web.form.IndexForm;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(@ModelAttribute("form") IndexForm form) {
        form.setName("hello world!");
        return "index";
    }
}
