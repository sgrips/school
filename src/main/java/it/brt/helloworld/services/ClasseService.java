package it.brt.helloworld.services;

import it.brt.helloworld.models.Classe;
import it.brt.helloworld.repositories.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> getClassi(){
        return classeRepository.findAll();
    }

    public Optional<Classe> getClasse(Long id){
        return classeRepository.findById(id);
    }


    public List<Classe> findOwnStudentClass(String query) {
        return classeRepository.findOwnStudentClass(query);

    }
}
