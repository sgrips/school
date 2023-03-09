package it.brt.helloworld.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "StudentJustifications")
public class StudentJustification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String studentId;

    @Column
    private Date createTime;

    @Column
    private Date absentFrom;

    @Column
    private Date absentTo;

    @Column
    private String description;

}

