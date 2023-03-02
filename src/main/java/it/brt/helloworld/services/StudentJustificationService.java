package it.brt.helloworld.services;

import it.brt.helloworld.models.StudentJustification;
import it.brt.helloworld.repositories.StudentJustificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentJustificationService {
    @Autowired
    private StudentJustificationRepository studentJustificationRepository;

    public List<StudentJustification> getStudentJustifications(){
        return studentJustificationRepository.findAll();
    }

    public StudentJustification createStudentJustification(StudentJustification studentJustification) {
        StudentJustification _studentJustificatione = studentJustificationRepository.save(studentJustification);
        return _studentJustificatione;
    }

}
