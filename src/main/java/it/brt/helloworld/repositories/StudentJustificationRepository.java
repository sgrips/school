package it.brt.helloworld.repositories;

import it.brt.helloworld.models.StudentJustification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJustificationRepository  extends JpaRepository<StudentJustification,Long> {
}
