package it.brt.school.course.parentstudents.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class StudentJustificationMessage
{
    @Getter
    @Setter
    private String studentName;
    @Getter
    @Setter
    private String studentLastName;
    @Getter
    @Setter
    private String parentSignature;
    @Getter
    @Setter
    private Date absentFrom;
    @Getter
    @Setter
    private Date absentTo;


    @Override
    public String toString() {
        return String.format ("Justification of :%s, parent %s ",studentName, parentSignature);
    }


}
