package it.brt.school.course.parentstudents.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class StudentJustificationMessage
{

    private String studentName;
    private String studentLastName;
    private String parentSignature;
    private Date absentFrom;
    private Date absentTo;


}
