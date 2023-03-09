package it.brt.helloworld.repositories;

import it.brt.helloworld.models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Classroom.ClassroomId> {
    
}
