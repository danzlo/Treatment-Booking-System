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

public class PatientTest {

    public PatientTest() {
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
     * Test bookTreatment method, of class Patient.
     */
    @Test
    public void testBookATreatment() {
        Patient patient = new Patient(
                "Marin S Hammond",
                "1 Bohemia Rd. Clarksburg, WV 26301",
                "0071236578"
        );
        Treatment treatment = new Treatment(
                3,
                "Acupuncture",
                "Osteopathy",
                "Tony Stanton",
                "Medical consulting suite C",
                new Date(),
                1

        );
        patient.bookTreatment(treatment);
        assertEquals(patient.registeredTreatments.size(), 1);
    }
    /**
     * Test of attendTreatment method, of class Patient.
     */
    @Test
    public void testAttendATreatment() {
        Patient patient = new Patient(
                "Marin S Hammond",
                "1 Bohemia Rd. Clarksburg, WV 26301",
                "0071236578"
        );
        Treatment treatment = new Treatment(
                3,
                "Acupuncture",
                "Osteopathy",
                "Tony Stanton",
                "Medical consulting suite C",
                new Date(),
                3
        );
        patient.bookTreatment(treatment);
        patient.attendTreatment(treatment);
        assertEquals(patient.attendedTreatments.size(), 1);
    }

    @Test
    public void testCancelATreatment() {
        Patient patient = new Patient(
                "Marin S Hammond",
                "1 Bohemia Rd. Clarksburg, WV 26301",
                "0071236578"
        );
        Treatment treatment = new Treatment(
                3,
                "Acupuncture",
                "Osteopathy",
                "Tony Stanton",
                "Medical consulting suite C",
                new Date(),
                3
        );
        patient.bookTreatment(treatment);
        patient.cancelTreatment(treatment);
        assertEquals(patient.registeredTreatments.size(), 0);
    }

}