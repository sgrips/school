package it.brt.helloworld.models;

import it.brt.helloworld.utils.Searchable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Classroom")
@IdClass(Classroom.ClassroomId.class)
public class Classroom {

    @Id
    @Column(name = "grade", length = 1)
    @NonNull
    @Max(5)
    @Min(1)
    @Searchable
    private Integer grade;

    @Id
    @Column(name = "sec", length = 1)
    @NonNull
    @Pattern(regexp = "[A-Z]")
    @Searchable
    private String section;

    public ClassroomId getId() {
        return getId(this.grade, this.section);
    }

    public static ClassroomId getId(Integer grade, String section) {
        return new ClassroomId(grade, section);
    }

    @Data
    public static class ClassroomId implements Serializable {
        private Integer grade;
        private String section;

        public ClassroomId(Integer grade, String section) {
            this.grade = grade;
            this.section = section;
        }
    }

}
