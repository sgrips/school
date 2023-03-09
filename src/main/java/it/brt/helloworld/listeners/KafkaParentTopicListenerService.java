package it.brt.helloworld.listeners;

import it.brt.helloworld.models.StudentJustification;
import it.brt.helloworld.services.StudentJustificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaParentTopicListenerService {

    @Autowired
    StudentJustificationService studentJustificationService;

    private static final Logger log = LoggerFactory.getLogger(KafkaParentTopicListenerService.class);

    @KafkaListener(
            topics =  "#{'${spring.kafka.topics}'.split(',')}",
            groupId = "groupid",
            containerFactory = "studentJustificationKafkaListenerContainerFactory")
    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "5",
            autoCreateTopics = "true",
            exclude = NullPointerException.class)
    public void studentJustificationListener(StudentJustification studentJustification) {
        log.info("Received justification: {}", studentJustification);
        studentJustificationService.createStudentJustification(studentJustification) ;
    }

}
