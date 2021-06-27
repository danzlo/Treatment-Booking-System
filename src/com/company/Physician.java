package com.company;

/**
 *
 * @author danzlo
 */

import java.util.ArrayList;
import java.util.Date;


enum Area {
    Physiotherapy,
    Osteopathy,
    Rehabilitation
}
enum DayOfWeek {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
}

public class Physician extends Person{
    Area area;
    ArrayList<Appointment> appointments;
    DayOfWeek appointmentDay;
    int appointmentHr;

    public Physician(String fullName, String fullAddress, String phoneNumber, Area area) {
        super(fullName, fullAddress, phoneNumber);
        this.area = area;
        appointmentDay = DayOfWeek.Monday;
        appointmentHr = 19;
        appointments = new ArrayList<>();
    }

    String bookAppointment(Date date, int hrs, int mins, String visitor){
        int day = date.getDay()-1;

        if(DayOfWeek.values()[day] != appointmentDay){
            return "Sorry, there are no appointments available on this day";
        }

        if(appointmentHr != hrs){
            return "Sorry, there no appointments available at this hour";
        }

        date.setHours(hrs);
        date.setMinutes(mins);
        date.setSeconds(0);

        for(Appointment appt: appointments){
            if(appt.date.compareTo(date) == 0){
                return "Sorry, this time slot is occupied";
            }
        }

        Appointment appt = new Appointment(appointments.size() + 1, date, this.getFullName(), visitor);
        appointments.add(appt);
        return "Appointment booked!";
    }

    void cancelAppointment(int id){
        for(int i = 0; i < appointments.size(); i++){
            Appointment appt = appointments.get(i);
            if(appt.id == id){
                appointments.remove(appt);
            }
        }
    }

    @Override
    public void signIn(){
        PhysicianAccount account = new PhysicianAccount(this);
        account.setVisible(true);
        account.setLocationRelativeTo(null);
        account.setVisible(true);
    }

}
