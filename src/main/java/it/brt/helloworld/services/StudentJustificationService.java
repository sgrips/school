package it.brt.helloworld.services;

import it.brt.helloworld.models.StudentJustification;
import it.brt.helloworld.repositories.StudentJustificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentJustificationService {
    @Autowired
    private StudentJustificationRepository studentJustificationRepository;

    public StudentJustification createStudentJustification(StudentJustification studentJustification) {
        return studentJustificationRepository.save(studentJustification);
    }

}
