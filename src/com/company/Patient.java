package com.company;

import java.util.ArrayList;

/**
 *
 * @author danzlo
 */

public class Patient extends Person {
    ArrayList<Treatment> registeredTreatments;
    ArrayList<Treatment> attendedTreatments;

    public Patient (String fullName, String fullAddress, String phoneNumber){
        super( fullName, fullAddress, phoneNumber);
        registeredTreatments = new ArrayList<>();
        attendedTreatments = new ArrayList<>();
    }

    void bookTreatment(Treatment bookedTreatment) {
        for(Treatment treatment : registeredTreatments){
            if(treatment.id == bookedTreatment.id){
                return;
            }
        }
        this.registeredTreatments.add(bookedTreatment);
        setChanged();
        notifyObservers();
    }

    void attendTreatment(Treatment bookedTreatment){
        for(int i = 0; i < registeredTreatments.size(); i++){
            Treatment treatment = registeredTreatments.get(i);
            if (treatment.id == bookedTreatment.id){
                attendedTreatments.add(bookedTreatment);
                registeredTreatments.remove(bookedTreatment);
            }
        }
    }

    void cancelTreatment(Treatment bookedTreatment){
        for(int i = 0; i < registeredTreatments.size(); i++){
            Treatment treatment = registeredTreatments.get(i);
            if (treatment.id == bookedTreatment.id){
                registeredTreatments.remove(bookedTreatment);
            }
        }
    }


    @Override
    public void signIn(){
        Account account = new Account(this);
        account.setVisible(true);
        account.setLocationRelativeTo(null);
    }
}
