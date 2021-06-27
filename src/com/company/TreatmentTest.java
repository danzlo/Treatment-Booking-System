package com.company;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author danzlo
 */
class TreatmentTest {

    public TreatmentTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of registerPatient method, of class Treatment.
     */
    @Test
    public void testRegisterPatient() {
        Patient patient = new Patient("Lara D Cortana","9157 Bear Hill Street Haines City, FL 33844","0072366549");
        Treatment instance = new Treatment(
                1,
                "Massage",
                "Rehabilitation",
                "Liz Taylor",
                "Medical consulting suite A",
                new Date(),
                1);
        String expResult = "Treatment booked!";
        String result = instance.registerPatient(patient);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegisterPatientNoVacanciesLeft() {
        Patient patient1=new Patient("Lara D Cortana","9157 Bear Hill Street Haines City, FL 33844","0072366549");
        Patient patient2= new Patient("Linus Minus","9157 Bear Hill Street Haines City, FL 33844","0072366549");
        Treatment instance = new Treatment(
                1,
                "Massage",
                "Rehabilitation",
                "Liz Taylor",
                "Medical consulting suite A",
                new Date(),
                0);
        String expResult = "Sorry, no more vacancies left";
        instance.registerPatient(patient1);
        String result = instance.registerPatient(patient2);
        assertEquals(expResult, result);
    }

    @Test
    public void testDeletePatient() {
        Patient patient = new Patient("Lara D Cortana","9157 Bear Hill Street Haines City, FL 33844","0072366549");
        Treatment instance = new Treatment(
                1,
                "Massage",
                "Rehabilitation",
                "Liz Taylor",
                "Medical consulting suite A",
                new Date(),
                1);
        instance.registerPatient(patient);
        assertEquals(1, instance.registeredPatients.size());
        instance.deletePatient(patient);
        assertEquals(0, instance.registeredPatients.size());
    }

}