package it.brt.helloworld.web;

import it.brt.helloworld.models.Classroom;
import it.brt.helloworld.models.exception.InvalidArgException;
import it.brt.helloworld.models.exception.NotFoundException;
import it.brt.helloworld.services.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/school/classroom")
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @GetMapping
    public List<Classroom> classroom(@RequestParam(required = false) Map<String, String> requestParams) {
        return classroomService.find(requestParams);
    }

    @GetMapping("/{grade}/{section}")
    public Classroom get(@PathVariable Integer grade, @PathVariable String section) throws NotFoundException {
        return classroomService.get(grade, section);
    }

    @PostMapping
    public Classroom create(@Valid @RequestBody Classroom classroom) throws InvalidArgException {
        return classroomService.insert(classroom);
    }

    @PutMapping
    public Classroom update(@Valid @RequestBody Classroom classroom) throws NotFoundException {
        return classroomService.update(classroom);
    }

    @DeleteMapping("/{grade}/{section}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer grade, @PathVariable String section) throws NotFoundException {
        classroomService.delete(grade, section);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
