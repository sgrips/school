package it.brt.helloworld.services;

import it.brt.helloworld.models.Studente;
import it.brt.helloworld.repositories.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;
import java.util.Optional;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;

    public List<Studente> getStudenti() {
        return studenteRepository.findAll();
    }

    public Optional<Studente> getStudenteById(long id) {
        return studenteRepository.findById(id);
    }

    public boolean exists(Long id) {
        return studenteRepository.existsById(id);
    }

    public boolean existsByNomeAndCognome(String nome, String cognome) {
        return studenteRepository.existsByNomeAndCognome(nome, cognome);
    }

    public List<Studente> searchStudente(String query) {
        return studenteRepository.seachStudente(query);
    }

    public List<Studente> getStudentiStartWith(String nome) {
        return studenteRepository.findByNomeStartsWith(nome);
    }

    public Optional<Studente> getStudentiByNomeCognome(String nome, String cognome) {
        return studenteRepository.findByNomeAndCognome(nome, cognome);
    }

    public Studente createStudente(Studente studente) {
        Studente _studente = studenteRepository.save(studente);
        return _studente;
    }

    public Studente updateStudente(Studente studente) {

        return studenteRepository.save(studente);
    }

    public void deleteStudente(Long id) {
        studenteRepository.deleteById(id);
    }
}
