package it.brt.helloworld.services;

import it.brt.helloworld.models.Student;
import it.brt.helloworld.models.exception.InvalidArgException;
import it.brt.helloworld.models.exception.NotFoundException;
import it.brt.helloworld.repositories.StudentRepository;
import it.brt.helloworld.utils.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    private static final int DEFAULT_LIMIT = 5;

    public Student get(String taxCode) throws NotFoundException {
        return studentRepository.findById(taxCode).orElseThrow(NotFoundException::new);
    }

    public Student insert(Student student) throws InvalidArgException {

        if (studentRepository.existsById(student.getTaxCode())) {
            throw new InvalidArgException();
        }

        return studentRepository.save(student);
    }

    public Student update(Student student) throws NotFoundException {

        if (!studentRepository.existsById(student.getTaxCode())) {
            throw new NotFoundException();
        }

        return studentRepository.save(student);
    }

    public void delete(String taxCode) throws NotFoundException {

        if (!studentRepository.existsById(taxCode)) {
            throw new NotFoundException();
        }

        studentRepository.deleteById(taxCode);
    }

    public List<Student> find(Map<String, String> filters) {

        if (filters == null || filters.isEmpty()) {
            return Collections.emptyList();
        }

        Student student = new Student();
        ExampleMatcher em = SearchUtils.loadMatcher(filters, student, Student.class);
        Example<Student> ex = Example.of(student, em);
        Integer limit = SearchUtils.loadLimit(filters);
        Integer offset = SearchUtils.loadOffset(filters);
        Pageable p = PageRequest.of(offset > 0 ? offset : 0, limit > 0 ? limit : DEFAULT_LIMIT);
        return studentRepository.findAll(ex, p).stream().toList();
    }

}
