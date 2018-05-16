package testsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import testsystem.models.User;
import testsystem.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping(method = RequestMethod.GET)
//    public String index(Model model) {
//        model.addAttribute("users", userService.getUsers());
//        return "users";
//    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String startCreate() {
        return "addUser";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model,
                         @RequestParam("app_name") String name,
                         @RequestParam("app_surname") String surname) {
        userService.createUser(name, surname);
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @RequestMapping(method = RequestMethod.GET)
    public void index(HttpServletResponse response, Model model) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(auth.getName());
        model.addAttribute("user", user);

        if (user.getUserGroup().getName().equals("ADMIN")) {

            response.sendRedirect("/admin");

        } else {
            response.sendRedirect("/student");
        }


    }

}
