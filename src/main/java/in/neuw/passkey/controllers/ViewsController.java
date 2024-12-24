package in.neuw.passkey.controllers;

import in.neuw.passkey.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController {

    private final ProfileService profileService;

    public ViewsController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model, SecurityContext securityContext) {
        final String username = securityContext.getAuthentication().getName();
        model.addAttribute("username", username);
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        setCsrfToModelAttributes(request, model);
        model.addAttribute("contextPath", request.getContextPath());
        return "pages/login";
    }

    @GetMapping("/logout")
    public String logout(Model model, SecurityContext securityContext, HttpServletRequest request) {
        setCsrfToModelAttributes(request, model);
        model.addAttribute("username", securityContext.getAuthentication().getName());
        return "pages/logout";
    }

    @GetMapping("/profile")
    public String profile(Model model, SecurityContext securityContext, HttpServletRequest request) {
        final String username = securityContext.getAuthentication().getName();
        var passkeys = profileService.resolvedCredentialRecords(username);
        setCsrfToModelAttributes(request, model);
        model.addAttribute("username", username);
        model.addAttribute("passkeys", passkeys);
        return "pages/profile";
    }

    @GetMapping("/passkeys")
    public String passkeys(Model model, SecurityContext securityContext, HttpServletRequest request) {
        final String username = securityContext.getAuthentication().getName();
        var passkeys = profileService.resolvedCredentialRecords(username);
        setCsrfToModelAttributes(request, model);
        model.addAttribute("username", username);
        model.addAttribute("passkeys", passkeys);
        return "pages/passkeys";
    }

    private void setCsrfToModelAttributes(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        var csrfHeader = renderCsrfHeader(csrfToken);
        model.addAttribute("csrfHeader", csrfHeader);
        model.addAttribute("csrfToken", csrfToken);
    }

    private String renderCsrfHeader(CsrfToken csrfToken) {
        return CSRF_HEADERS
                .replace("headerName", csrfToken.getHeaderName())
                .replace("headerValue", csrfToken.getToken());
    }

    private static final String CSRF_HEADERS = """
			{"headerName" : "headerValue"}""";

}
