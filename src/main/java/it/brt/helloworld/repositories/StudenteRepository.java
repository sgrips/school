package it.brt.helloworld.repositories;

import it.brt.helloworld.models.Classe;
import it.brt.helloworld.models.Studente;
import it.brt.helloworld.services.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudenteRepository extends JpaRepository<Studente ,Long> {

    public List<Studente> findByNomeStartsWith(String nome);
    public Optional<Studente> findByNomeAndCognome(String Nome , String Cognome);
    public boolean existsByNomeAndCognome(String Nome , String Cognome);

    @Query("SELECT s FROM Studente s WHERE s.nome=:query or s.cognome=:query")
    public List<Studente> seachStudente(String query);

}
