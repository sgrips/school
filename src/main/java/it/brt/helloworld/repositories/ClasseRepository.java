package it.brt.helloworld.repositories;

import it.brt.helloworld.models.Classe;
import it.brt.helloworld.models.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClasseRepository extends JpaRepository<Classe,Long> {

    @Query(value = "SELECT c FROM Classe c INNER JOIN " +
            "c.studenti s  WHERE s.nome=:query ",nativeQuery = false)
    List<Classe> findOwnStudentClass (String query) ;

}
