package testsystem.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import testsystem.models.State;
import testsystem.models.Subject;
import testsystem.services.StateService;
import testsystem.services.SubjectService;

import javax.servlet.http.HttpServletRequest;


@Controller
@EnableScheduling
class SubjectWSController {
    @Autowired
    private SubjectService subjects;

    @Autowired
    private StateService states;

    @Autowired
    private SubjectService subjectService;

    /**
     *
     * @param id
     * @return "1: 4, 2: 9, 3: 0, 4: 2"
     */
    @MessageMapping("/subject")
    @SendTo("/topic/subject")
    public String getSubject(long id) {
//        String[] parseRequest = request.getServletPath().split("/");
//        Subject subject = subjectService.getById(Long.valueOf(parseRequest[3]));
        Subject subject = subjectService.getById(id);

        StringBuilder str = new StringBuilder();
        int[] positions = new int[subject.getQuestions().size()];

        Iterable<State> statesBySubject = this.states.getStateBySubject(subject);
        for (State state : statesBySubject) {
            positions[state.getPosition()]++;
           // state.getUser();
        }
        str.append("[");
        for (int i=0; i<positions.length; i++ ){
            str.append(positions[i])
                    .append(", ");
        }
//        System.out.print(subject.);
        str.delete(str.length()-2, str.length()-1 );
        str.append("]");
        //model.addAttribute("str", str);
       return str.toString();
       /// return "mmm";

    }

//    @RequestMapping("/start")
//    public String start() {
//        return "ws-client";
//    }
}


//@Controller
//@EnableScheduling
//class SubjectWSController {
//
//    @Autowired
//    SimpMessagingTemplate template;
//
//    @Scheduled(fixedDelay = 20000L)
//    @SendTo("/topic/pingpong")
//    public void  sendPong() {
//        template.convertAndSend("/topic/pingpong", "pong (periodic)");
//    }
//
//    @MessageMapping("/ping")
//    @SendTo("/topic/pingpong")
//    public String  sendPingResponse() {
//        return "pong (response)";
//    }
//}