package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 * @author danzlo
 */


public class PhysicianTest {


        public PhysicianTest() {
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
         * Test of bookAppointment method, of class Physician.
         */
        @Test
        public void testBookAnAppointment() {
            Date date = new Date();
            int hrs = 9;
            int mins = 30;
            String visitor = "John Courtney";
            Physician physician = new Physician(
                    "Lucas Mayor",
                    "8089 Trenton Dr. Mason City, IA 50401",
                    "0074566541",
                    Area.Osteopathy
            );
            physician.bookAppointment(date, hrs, mins, visitor);
            assertEquals(0, physician.appointments.size());
        }

        @Test
        public void testBookAppointmentIncorrectDay() {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-M-yyyy hh:mm",
                    Locale.ENGLISH
            );
            Date date = null;
            try {
                date = formatter.parse("04-06-2021 11:00");
            } catch (ParseException ex) {
                Logger.getLogger(PhysicianTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            int hrs = 9;
            int mins = 30;
            String visitor = "John Courtney";
            Physician physician = new Physician(
                    "Lucas Mayor",
                    "8089 Trenton Dr. Mason City, IA 50401",
                    "0074566541",
                    Area.Osteopathy
            );
            String message = physician.bookAppointment(date, hrs, mins, visitor);
            assertEquals("Sorry, there are no appointments available on this day", message);
        }

        @Test
        public void testCancelAnAppointment() {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-M-yyyy hh:mm",
                    Locale.ENGLISH
            );
            Date date = null;
            try {
                date = formatter.parse("06-05-2021 10:00");
            } catch (ParseException ex) {
                Logger.getLogger(PhysicianTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Physician physician = new Physician(
                    "Lucas Mayor",
                    "8089 Trenton Dr. Mason City, IA 50401",
                    "0074566541",
                    Area.Osteopathy
            );

            physician.bookAppointment(date, 19, 30, "John");
            assertEquals(1, physician.appointments.size());
            physician.cancelAppointment(1);
            assertEquals(0, physician.appointments.size());
        }

    }
