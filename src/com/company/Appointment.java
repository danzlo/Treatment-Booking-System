package com.company;

import java.util.Date;

/**
 * @author danzlo
 */

public class Appointment {

    int id;

    Date date;

    String physician;

    String visitor;

    public Appointment(int id, Date date, String physician, String parent){
        this.id = id;
        this.date = date;
        this.physician = physician;
        this.visitor = parent;
    }
}
