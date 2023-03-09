package it.brt.helloworld.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.brt.helloworld.utils.Searchable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@Entity
@Table(name = "Students")
@NoArgsConstructor
public class Student {

    @Id
    @Column(name = "taxCode", length = 16, nullable = false)
    @NonNull
    @Searchable
    private String taxCode;

    @Column(name = "firstName", length = 50, nullable = false)
    @NonNull
    @Searchable
    private String firstName;

    @Column(name = "lastName", length = 50, nullable = false)
    @NonNull
    @Searchable
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    @Temporal(TemporalType.DATE)
    @NonNull
    private Date birthDate;

    @Column(name = "email")
    @Nullable
    @Email
    private String email;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "grade", referencedColumnName = "grade"),
            @JoinColumn(name = "sec", referencedColumnName = "sec")
    })
    @JsonBackReference
    private Classroom classroom;

}
