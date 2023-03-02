package it.brt.helloworld.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "StudentJustifications")
public class StudentJustification
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Long justification_id;

    @Getter
    @Setter
    @Column
    private String studentName;
    @Getter
    @Setter
    @Column
    private String studentLastName;
    @Getter
    @Setter
    @Column
    private String parentSignature;
    @Getter
    @Setter
    @Column
    private Date createTime;
    @Getter
    @Setter
    @Column
    private Date absentFrom;
    @Getter
    @Setter
    @Column
    private Date absentTo;

    @Override
    public String toString() {
        return String.format ("Justification of :%s, parent %s ",studentName, parentSignature);
    }


}

