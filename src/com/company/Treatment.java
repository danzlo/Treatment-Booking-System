package com.company;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author danzlo
 */

public class Treatment extends java.util.Observable {
    int id;
    String name;
    String area;
    String physician;
    String place;
    Map timeTable;
    Date date;
    int volume;
    Set<Patient> registeredPatients = new HashSet();
    Set<Patient> attendedPatients = new HashSet();

    public Treatment(
            int id,
            String name,
            String area,
            String physician,
            String place,
            Date date,
            int volume
    ){
        this.id = id;
        this.name = name;
        this.area = area;
        this.physician = physician;
        this.place = place;
        this.date = date;
        this.volume = volume;
        timeTable = new HashMap<DayOfWeek, Integer>();
    }

    String registerPatient(Patient patient){
        if((this.volume - this.registeredPatients.size()) <= 0) {
            return "Sorry, there are no spaces left";
        } else {
            this.registeredPatients.add(patient);
            setChanged();
            notifyObservers();
            return "Treatment booked!";
        }
    }

    void deletePatient(Patient patient){
        for(Patient enrolledPatient: this.registeredPatients){
            if(enrolledPatient.getId() == patient.getId()){
                this.registeredPatients.remove(patient);
            }
        }
    }
}
