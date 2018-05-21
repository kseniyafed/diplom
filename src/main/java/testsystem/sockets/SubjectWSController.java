package testsystem.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import testsystem.models.State;
import testsystem.models.Subject;
import testsystem.services.StateService;
import testsystem.services.SubjectService;

@Controller
@EnableScheduling
class SubjectWSController {

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
        Subject subject = subjectService.getById(id);

        StringBuilder str = new StringBuilder();
        int[] positions = new int[subject.getQuestions().size()];

        Iterable<State> statesBySubject = this.states.getStateBySubject(subject);
        for (State state : statesBySubject) {
            positions[state.getPosition()]++;

        }
        str.append("[");
        for (int i=0; i<positions.length; i++ ){
            str.append(positions[i])
                    .append(", ");
        }
        str.delete(str.length()-2, str.length()-1 );
        str.append("]");

       return str.toString();


    }


}


