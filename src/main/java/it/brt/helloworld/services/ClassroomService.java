package it.brt.helloworld.services;

import it.brt.helloworld.models.Classroom;
import it.brt.helloworld.models.exception.InvalidArgException;
import it.brt.helloworld.models.exception.NotFoundException;
import it.brt.helloworld.repositories.ClassroomRepository;
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
public class ClassroomService {

    @Autowired
    ClassroomRepository classroomRepository;

    private static final int DEFAULT_LIMIT = 5;

    public Classroom get(Integer grade, String section) throws NotFoundException {
        return classroomRepository.findById(Classroom.getId(grade, section)).orElseThrow(NotFoundException::new);
    }

    public Classroom insert(Classroom classroom) throws InvalidArgException {

        if (classroomRepository.existsById(classroom.getId())) {
            throw new InvalidArgException();
        }

        return classroomRepository.save(classroom);
    }

    public Classroom update(Classroom classroom) throws NotFoundException {

        if (!classroomRepository.existsById(classroom.getId())) {
            throw new NotFoundException();
        }

        return classroomRepository.save(classroom);
    }

    public void delete(Integer grade, String section) throws NotFoundException {
        Classroom.ClassroomId cId = Classroom.getId(grade, section);
        if (!classroomRepository.existsById(cId)) {
            throw new NotFoundException();
        }

        classroomRepository.deleteById(cId);
    }

    public List<Classroom> find(Map<String, String> filters) {

        if (filters == null || filters.isEmpty()) {
            return Collections.emptyList();
        }

        Classroom classroom = new Classroom();
        ExampleMatcher em = SearchUtils.loadMatcher(filters, classroom, Classroom.class);
        Example<Classroom> ex = Example.of(classroom, em);
        Integer limit = SearchUtils.loadLimit(filters);
        Integer offset = SearchUtils.loadOffset(filters);
        Pageable p = PageRequest.of(offset > 0 ? offset : 0, limit > 0 ? limit : DEFAULT_LIMIT);
        return classroomRepository.findAll(ex, p).stream().toList();
    }

}
