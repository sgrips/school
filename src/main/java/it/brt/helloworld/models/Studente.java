package it.brt.helloworld.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Studenti")
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long studente_id;

    @Column(name = "nome", length = 50, nullable = false)
    @Getter
    @Setter
    private String nome;

    @Column(name = "cognome", length = 50, nullable = false)
    @Getter
    @Setter
    private String cognome;

    @Column(name = "dataDiNascita", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonProperty("datadinascita")
    @Getter
    @Setter
    private Date dataDiNascita;

    @Column(name = "email", length = 255, nullable = false)
    @Getter
    @Setter
    private String email;

    @OneToOne()
    @JoinColumn(name = "classe_id")
    @JsonBackReference
    @Getter
    @Setter
    private Classe classe;


/*    public long getStudente_id() {
        return studente_id;
    }

    public void setStudente_id(Long studente_id) {
        this.studente_id = studente_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }*/

    @Override
    public String toString() {
        return "Studente [id=" + studente_id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + "]";
    }
}
