package it.brt.helloworld.listeners;

import it.brt.helloworld.models.StudentJustification;
import it.brt.helloworld.repositories.ClasseRepository;
import it.brt.helloworld.services.ClasseService;
import it.brt.helloworld.services.StudentJustificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import it.brt.school.course.parentstudents.models.StudentJustificationMessage;

import java.util.Date;
import java.util.List;

@Component
public class KafkaParentTopicListenerService
{
//
//    @KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "groupid")
//    public void listenMessage(String message) {
//        System.out.println("Received Message in group foo: " + message);
//    }
//
    @Autowired
    StudentJustificationService studentJustificationService;

    @Autowired
    ClasseService classeService;


    @KafkaListener(
            topics =  "#{'${spring.kafka.topics}'.split(',')}",
            groupId = "groupid",
            containerFactory = "studentJustificationKafkaListenerContainerFactory")
    public void studentJustificationListener(StudentJustificationMessage studentJustificationMessage) {

        try{
            studentJustificationService.createStudentJustification(new StudentJustification()
                    {{
                    setAbsentFrom(studentJustificationMessage.getAbsentFrom());
            setAbsentTo(studentJustificationMessage.getAbsentTo());
            setParentSignature(studentJustificationMessage.getParentSignature());
            setStudentName(studentJustificationMessage.getStudentName());
            setStudentLastName(studentJustificationMessage.getStudentLastName());
            setCreateTime(new Date());
        }})
            ;

        }   catch (Exception e) {
            System.err.println(e );

            e.printStackTrace();
        }


    }

}
