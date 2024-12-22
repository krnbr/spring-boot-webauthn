package in.neuw.passkey.controllers;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController {

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model, SecurityContext securityContext) {
        final String username = securityContext.getAuthentication().getName();
        model.addAttribute("username", username);
        return "pages/index";
    }

}
