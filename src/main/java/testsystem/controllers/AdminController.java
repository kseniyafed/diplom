package testsystem.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/controlGroups", method = RequestMethod.POST)
    public void controlLists(HttpServletResponse response)
                            throws SQLException, IOException {
        response.sendRedirect("/groups");
    }

    @RequestMapping(value = "/controlTests", method = RequestMethod.POST)
    public void controlTest(HttpServletResponse response) throws SQLException, IOException {
        response.sendRedirect("/tests");
    }

    @RequestMapping
    public String index() {
        return "adminPage";
    }

}
