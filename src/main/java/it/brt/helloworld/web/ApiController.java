package it.brt.helloworld.web;


import it.brt.helloworld.models.Classe;
import it.brt.helloworld.models.Studente;
import it.brt.helloworld.services.ClasseService;
import it.brt.helloworld.services.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/demo")
public class ApiController {

    @Autowired
    StudenteService studenteService;
    @Autowired
    ClasseService classeService;

    @GetMapping(value = {"/hello", "/hello/{name}"})
    public String Hello(@PathVariable(name = "name") Optional<String> name) {
        return "Hello :" + ((name.isEmpty()) ? "Anonymous" : name.get()) + ", I'm Spring Boot";
    }

    @GetMapping(value = "/studenti")
    public ResponseEntity<List<Studente>> getStudenti(@RequestParam(name = "nome", required = false) Optional<String> nome,
                                                      @RequestParam(name = "query", required = false) Optional<String> query) {
        List<Studente> studenti;
        try {

            studenti = new ArrayList<Studente>();

            if (!nome.isPresent() && !query.isPresent()) {
                studenteService.getStudenti().forEach(studenti::add);
            } else if (!query.isPresent())  {
                studenteService.getStudentiStartWith(nome.get()).forEach(studenti::add);
            } else
                studenteService.searchStudente(query.get()).forEach(studenti::add);

            if (studenti.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(studenti, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/studenti/{id}")
    public ResponseEntity<Studente> getStudente(@PathVariable(name = "id", required = true) long id) {
        Optional<Studente> studente;
        try {
            studente = studenteService.getStudenteById(id);

            if (studente.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(studente.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/studenti")
    public ResponseEntity<Studente> creaStudente(@RequestBody(required = true) Studente studente) {
        try {


            if (studenteService.existsByNomeAndCognome(studente.getNome(), studente.getCognome()))
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            if (studente.getClasse() == null)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            Optional<Classe> _classe = classeService.getClasse(studente.getClasse().getClasse_id());

            if (_classe.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            studente.setClasse(_classe.get());
            studente.setStudente_id(null);
            Studente _studente = studenteService.createStudente(studente);
            return new ResponseEntity<>(_studente, HttpStatus.CREATED);

        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(path = "/studenti/{id}")
    public ResponseEntity<Studente> updateStudente(@PathVariable(name = "id", required = true) long id, @RequestBody(required = true) Studente studente) {
        try {
            Optional<Studente> _studente = studenteService.getStudenteById(id);
            if (_studente.isPresent() && !_studente.isEmpty()) {

                Studente studenteUPD = _studente.get();
                studenteUPD.setEmail(studente.getEmail());
                studenteUPD.setDataDiNascita(studente.getDataDiNascita());
                studenteUPD = studenteService.updateStudente(studenteUPD);
                return new ResponseEntity<>(_studente.get(), HttpStatus.OK);
            } else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/studenti/{id}")
    public ResponseEntity<Studente> deleteStudente(@PathVariable(name = "id", required = true) long id) {
        try {
            if (studenteService.exists(id)) {
                studenteService.deleteStudente(id);

                return new ResponseEntity<>(null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/classi")
    public ResponseEntity<List<Classe>> getClassi(@RequestParam(value = "query", required = false) Optional<String> query) {
        List<Classe> classi;
        try {
             if (query.isPresent())
                classi= classeService.findOwnStudentClass(query.get());
            else
                classi= classeService.getClassi();


            if (classi.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);


            return new ResponseEntity<>(classi, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
