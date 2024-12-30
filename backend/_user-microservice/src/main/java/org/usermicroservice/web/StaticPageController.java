package org.usermicroservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "forward:/static/reset-password.html";
    }
}
