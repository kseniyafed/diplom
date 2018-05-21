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
import testsystem.models.UserGroup;
import testsystem.services.ResultService;
import testsystem.services.SubjectService;
import testsystem.services.UserGroupService;
import testsystem.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ResultService resultService;

    @RequestMapping
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("groups", userGroupService.getGroups());
        return "controlGroupsPage";
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public String createGroup(Model model) {
        return "createGroupPage";
    }

    @RequestMapping(value = "/deleteGroup/**", method = RequestMethod.POST)
    public void deleteGroup(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String[] parseRequest = request.getServletPath().split("/");
        userGroupService.deleteGroup(Long.valueOf(parseRequest[3]));
        response.sendRedirect("/groups");

    }

    @RequestMapping(value = "/editGroup/**")
    public String editGroup(Model model, HttpServletRequest request) throws IOException {

        String[] parseRequest = request.getServletPath().split("/");
        UserGroup userGroup = userGroupService.getById(Long.valueOf(parseRequest[3]));

        model.addAttribute("groupName", userGroup.getName());
        model.addAttribute("groupId", userGroup.getId());
        model.addAttribute("students", userGroup.getUsers());


        return "editGroupPage";

    }

    @RequestMapping(value = "/**/deleteStudent/**", method = RequestMethod.POST)
    public void deleteStudent(HttpServletResponse response,HttpServletRequest request) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");

        userService.deleteStudent(Long.valueOf(parseRequest[4]));
        response.sendRedirect("/groups/editGroup/" + Long.valueOf(parseRequest[2]));
    }

    @RequestMapping(value = "/**/editStudent/**", method = RequestMethod.POST)
    public String editStudent( Model model, HttpServletRequest request) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        UserGroup userGroup = userGroupService.getById(Long.valueOf(parseRequest[2]));
        User user = userService.getById(Long.valueOf(parseRequest[4]));
        model.addAttribute("groupName", userGroup.getName());
        model.addAttribute("groupId", userGroup.getId());
        model.addAttribute("userId", user.getId());
        model.addAttribute("userName", user.getName());
        model.addAttribute("userLogin", user.getLogin());
        model.addAttribute("userPassword", user.getPassword());

        return "editStudentPage";
    }

    @RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
    public String saveGroup(@RequestParam("groupName") String groupName) {
        userGroupService.createGroup(groupName);
        return "createGroupPage";
    }

    @RequestMapping(value = "/**/updateStudent/**", method = RequestMethod.POST)
    public void updateStudent(HttpServletResponse response, HttpServletRequest request,
                              @RequestParam("name") String name,
                              @RequestParam("login") String login,
                              @RequestParam("password") String password) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        userService.updateUser(Long.valueOf(parseRequest[4]), name, login, password);
        response.sendRedirect("/groups/editGroup/" + Long.valueOf(parseRequest[2]));

    }

    @RequestMapping(value = "/saveChanges/**", method = RequestMethod.POST)
    public void saveChanges(HttpServletResponse response,
                            @RequestParam("groupName") String groupName,
                            HttpServletRequest request) throws IOException {

        String[] parseRequest = request.getServletPath().split("/");
        userGroupService.updateGroup(Long.valueOf(parseRequest[3]), groupName);
        response.sendRedirect("/groups");

    }

    @RequestMapping(value = "/addStudent/**", method = RequestMethod.POST)
    public String addStudent(Model model, HttpServletRequest request) {
        String[] parseRequest = request.getServletPath().split("/");
        UserGroup userGroup = userGroupService.getById(Long.valueOf(parseRequest[3]));
        model.addAttribute("groupName", userGroup.getName());
        model.addAttribute("groupId", userGroup.getId());

        return "addStudentPage";
    }

    @RequestMapping(value = "/saveStudent/**", method = RequestMethod.POST)
    public void saveStudent(HttpServletResponse response, Model model,
                            HttpServletRequest request,
                            @RequestParam("name") String name,
                            @RequestParam("login") String login,
                            @RequestParam("password") String password) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        UserGroup userGroup = userGroupService.getById(Long.valueOf(parseRequest[3]));
        userService.createUser(userGroup, login, password, name);
        model.addAttribute("groupName", userGroup.getName());
        model.addAttribute("groupId", userGroup.getId());
        response.sendRedirect("/groups/editGroup/" + userGroup.getId());

    }

    @RequestMapping(value = "/groupPage/**", method = RequestMethod.GET)
    public String showGroupResults(Model model, HttpServletRequest request) throws SQLException, IOException {
        String[] parseRequest = request.getServletPath().split("/");
        UserGroup userGroup = userGroupService.getById(Long.valueOf(parseRequest[3]));
        model.addAttribute("groupName", userGroup.getName());
        model.addAttribute("subjects", subjectService.getSubjects());
        model.addAttribute("students", userGroup.getUsers());
        model.addAttribute("results", resultService.getGroupResults(userGroup));
        return "groupPage";
    }

}
