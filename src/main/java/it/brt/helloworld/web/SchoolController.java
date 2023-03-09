package it.brt.helloworld.web;

import it.brt.helloworld.models.Student;
import it.brt.helloworld.models.exception.InvalidArgException;
import it.brt.helloworld.models.exception.NotFoundException;
import it.brt.helloworld.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/school/students")
public class SchoolController {

    @Autowired
    StudentService studentService;

    @GetMapping
    public List<Student> students(@RequestParam(required = false) Map<String, String> requestParams) {
       return studentService.find(requestParams);
    }

    @GetMapping("/{taxCode}")
    public Student getStudent(@PathVariable String taxCode) throws NotFoundException {
        return studentService.get(taxCode);
    }

    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student) throws InvalidArgException {
        return studentService.insert(student);
    }

    @PutMapping
    public Student updateStudent(@Valid @RequestBody Student student) throws NotFoundException {
        return studentService.update(student);
    }

    @DeleteMapping("/{taxCode}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable String taxCode) throws NotFoundException {
        studentService.delete(taxCode);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
