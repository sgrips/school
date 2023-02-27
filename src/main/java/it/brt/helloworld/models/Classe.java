package it.brt.helloworld.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Reference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Classi")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "classe_id")
public class Classe {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long classe_id;

    @Getter
    @Setter
    @Column(name = "sezione", length = 1)
    private String sezione;

    @Getter
    @Setter
    @OneToMany(mappedBy = "classe" )
    @JsonManagedReference
    private List<Studente> studenti=new ArrayList<Studente>();



}
