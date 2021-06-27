package com.company;

import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author danzlo
 */
class LoginTest {
    
    public LoginTest() {
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
     * Test of findPersonById method, of class Login.
     */

    @Test
    public void testFindPersonById() {
        int id = 1;
        Login instance = new Login();
        Person result = instance.findPersonById(id);
        assertNotNull(result);
    }

    @Test
    public void testRegisterPatient() {
        Login instance = new Login();
        Patient patient = instance.registerPatient(
                "Sandra S Lawton",
                "7527 School Ave. Edison, NJ 08817",
                "0075894758"
        );
        assertThat(patient, instanceOf(Patient.class));
    }

    @Test
    public void testRegisterPhysician() {
        Login instance = new Login();
        Physician physician = instance.registerPhysician(
                "Sandra S Lawton",
                "7527 School Ave. Edison, NJ 08817",
                "0075894758",
                Area.Physiotherapy
        );
        assertThat(physician, instanceOf(Physician.class));
    }
}
