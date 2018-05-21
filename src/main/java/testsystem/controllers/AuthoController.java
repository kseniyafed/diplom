package testsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import testsystem.models.User;
import testsystem.services.StateService;
import testsystem.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class AuthoController {
    @Autowired
    private StateService stateService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "authorization";
    }

    @RequestMapping(value = "/login-error", method = RequestMethod.GET)
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "authorization";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user = userService.getByLogin(auth.getName());
            stateService.deleteStateByUser(user);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.sendRedirect("/login");

    }
//
//    @RequestMapping(value = "/client", method = RequestMethod.GET)
//    public String client() {
//        return "ws-client";
//    }

}