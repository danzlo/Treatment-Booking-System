package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author danzlo
 */

    public class Login extends JFrame {

        public static Set<Person> persons = new HashSet<Person>();
        public static Set<Treatment> treatments = new HashSet<Treatment>();

        JTextField idText;
        JLabel loginMsg;

        JTextField fullNameText;
        JTextField fullAddressText;
        JTextField phoneNumberText;
        JLabel registrationMsg;
        JCheckBox checkbox;
        JComboBox areaCombo;
        JLabel areaLabel;

        public static Login app;


        public Login (){
            super("Login");
            app = this;

            JPanel panel = new JPanel();

            /** Switching between the following 2 panels */

            JPanel panelLogin = new JPanel();
            JPanel panelRegister = new JPanel();
            JButton switchToRegister = new JButton("Switch to Register");
            JButton switchToLogin = new JButton("Switch to Login");

            JButton registerButton = new JButton("Register");
            JButton loginButton = new JButton("Log in");

            CardLayout cl = new CardLayout();
            panel.setLayout(cl);

            /** LOGIN PANEL */

            JPanel innerLoginPanel = new JPanel();
            innerLoginPanel.setLayout(new BoxLayout(innerLoginPanel, BoxLayout.PAGE_AXIS));

            JLabel nameLabel = new JLabel("Id:");
            innerLoginPanel.add(nameLabel);

            idText = new JTextField();
            innerLoginPanel.add(idText);

            loginMsg = new JLabel("");
            loginMsg.setForeground(Color.RED);
            innerLoginPanel.add(loginMsg);


            loginButton.addActionListener(new ClickListener("Log in"));
            innerLoginPanel.add(loginButton);

            panelLogin.setLayout(new BorderLayout());
            panelLogin.add(innerLoginPanel, BorderLayout.NORTH);

            panelLogin.add(switchToRegister, BorderLayout.SOUTH);
            /**End of login panel*/


            /**REGISTER PANEL*/
            JPanel innerRegisterPanel = new JPanel();
            innerRegisterPanel.setLayout(new BoxLayout(innerRegisterPanel, BoxLayout.PAGE_AXIS));

            JPanel textFieldPanel = new JPanel();
            textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));

            JLabel fullNameLabel = new JLabel("Full Name:");
            textFieldPanel.add(fullNameLabel);

            fullNameText = new JTextField();
            textFieldPanel.add(fullNameText);

            JLabel fullAddressNameLabel = new JLabel("Full Address:");
            textFieldPanel.add(fullAddressNameLabel);

            fullAddressText = new JTextField();
            textFieldPanel.add(fullAddressText);

            JLabel phoneNumberLabel = new JLabel("Phone number:");
            textFieldPanel.add(phoneNumberLabel);

            phoneNumberText = new JTextField();
            textFieldPanel.add(phoneNumberText);

            checkbox = new JCheckBox("I'm a physician");
            checkbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){

                        areaLabel = new JLabel("Area of expertise: ");
                        Area areas[]= {Area.Physiotherapy, Area.Osteopathy, Area.Rehabilitation};
                        areaCombo = new JComboBox(areas);
                        areaCombo.setSelectedIndex(2);
                        areaCombo.setAlignmentX(0);

                        innerRegisterPanel.remove(registerButton);
                        innerRegisterPanel.add(areaLabel);
                        innerRegisterPanel.add(areaCombo);
                        innerRegisterPanel.add(registerButton);

                        innerRegisterPanel.revalidate();
                        innerRegisterPanel.repaint();
                    }
                    else {
                        innerRegisterPanel.remove(areaCombo);
                        innerRegisterPanel.remove(areaLabel);
                        innerRegisterPanel.revalidate();
                        innerRegisterPanel.repaint();
                    }
                }
            });

            textFieldPanel.add(checkbox);

            registrationMsg = new JLabel("");
            registrationMsg.setForeground(Color.RED);
            textFieldPanel.add(registrationMsg);

            innerRegisterPanel.add(textFieldPanel);


            registerButton.addActionListener(new ClickListener("Register"));
            innerRegisterPanel.add(registerButton);

            panelRegister.setLayout(new BorderLayout());
            panelRegister.add(innerRegisterPanel, BorderLayout.NORTH);

            panelRegister.add(switchToLogin, BorderLayout.SOUTH);

            /**adding login and register panels to the main  panel*/

            panel.add(panelLogin, "Login");
            panel.add(panelRegister, "Register");

            /**switching between login and register*/

            switchToRegister.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panel, "Register");
                }
            });

            switchToLogin.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panel, "Login");
                }
            });

            cl.show(panel, "1");

            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(panel);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.ENGLISH);
            try {

                /**HARD CODED DATA*/

                Patient patient1 = new Patient("Jose D Watson", "597 N. Gregory Street Merrimack, NH 03054", "0075895423");
                persons.add(patient1);

                persons.add(new Patient("Travis M Swank", "9106 La Sierra Avenue Fernandina Beach, FL 32034", "0075876589"));
                persons.add(new Patient("Sandra S Lawton", "7527 School Ave. Edison, NJ 08817", "0075894758"));
                persons.add(new Patient("Monica F Anderson", "44 Peachtree Street Wheaton, IL 60187",  "0075982493"));
                persons.add(new Patient("Nick D Jonas", "62 Buttonwood Ave. Unit C Reading, MA 01867", "0073255877"));
                persons.add(new Patient("Jim F Francis", "1 E. Fairfield Circle La Verne, TN 37086",  "0071236578"));
                persons.add(new Patient("Hank D Monet", "8201 Joy Ridge Dr. Roselle, IL 60172",  "0073658521"));
                persons.add(new Patient("Julia E Vance", "42 Windfall Ave. Front Royal, VA 22630",  "0072571346"));
                persons.add(new Patient("Anne F Silver", "7128 Oakland Ave. Hobart, IN 46342",  "0078784157"));
                persons.add(new Patient("Simone C Geller", "99 Center St. Miamisburg, OH 45342",  "0072366549"));
                persons.add(new Patient("Marin S Hammond", "1 Bohemia Rd. Clarksburg, WV 26301",  "0071236578"));
                persons.add(new Patient("Rick T Grover", "9193 Lyme Dr. Old Bridge, NJ 08857",  "0073658521"));
                persons.add(new Patient("Gabriel I Flores", "961 Hickory Drive Woodbridge, VA 22191",  "0072571346"));
                persons.add(new Patient("Clara E Dimitri", "856 Walnutwood Street Lake Villa, IL 60046",  "0078784157"));
                persons.add(new Patient("Lara D Cortana", "9157 Bear Hill Street Haines City, FL 33844",  "0072366549"));


                persons.add(new Physician("Tim Sawyer","7342 Sherwood Ave. Winter Springs, FL 32708", "0072458475", Area.Physiotherapy));
                Physician physician2 = new Physician("Lucas Mayor", "8089 Trenton Dr. Mason City, IA 50401", "0074566541", Area.Osteopathy);
                physician2.appointmentDay = DayOfWeek.Wednesday;
                physician2.appointmentHr = 14;
                persons.add(physician2);
                Physician physician3 = new Physician("Anna Summer", "840 Albany Dr. New Bern, NC 28560", "0071112365", Area.Rehabilitation);
                physician3.appointmentDay = DayOfWeek.Monday;
                physician3.appointmentHr = 19;
                persons.add(physician3);
                Physician physician4 = new Physician("Liz Taylor", "772 West Bradford Dr. New Baltimore, MI 48047","0074548899", Area.Rehabilitation);
                physician4.appointmentDay = DayOfWeek.Friday;
                physician4.appointmentHr = 20;
                persons.add(physician4);
                Physician physician5 = new Physician("Adam Lever", "396 Cypress Ave. Southington, CT 06489", "0071311332", Area.Physiotherapy);
                physician5.appointmentDay = DayOfWeek.Monday;
                physician5.appointmentHr = 17;
                persons.add(physician5);
                Physician physician6 = new Physician("Tony Stanton", "421 Toytown Rd. Monopolis, MS 45276", "0078977456", Area.Osteopathy);
                physician6.appointmentDay = DayOfWeek.Tuesday;
                physician6.appointmentHr = 18;
                persons.add(physician6);


                Treatment treatment1 = new Treatment(1, "Massage", "Rehabilitation", "Liz Taylor", "Medical consulting suite A", formatter.parse("03-06-2021 10:00"), 1);
                treatment1.timeTable.put(DayOfWeek.Monday, 10);
                Treatment treatment2 = new Treatment(2, "Neural mobilisation", "Physiotherapy", "Adam Lever", "Medical consulting suite B", formatter.parse("03-06-2021 13:00"), 1);
                treatment2.timeTable.put(DayOfWeek.Monday, 13);
                Treatment treatment3 =new Treatment(3, "Acupuncture", "Osteopathy", "Tony Stanton", "Medical consulting suite C", formatter.parse("04-06-2021 11:00"), 1);
                treatment3.timeTable.put(DayOfWeek.Tuesday, 11);
                Treatment treatment4 = new Treatment(4, "Mobilisation of the spine and joints", "Physiotherapy", "Tim Sawyer", "Medical consulting suite B", formatter.parse("04-06-2021 14:00"), 1);
                treatment4.timeTable.put(DayOfWeek.Tuesday, 14);
                Treatment treatment5 = new Treatment(5, "Pool rehabilitation”).", "Rehabilitation", "Anna Summer", "Pool", formatter.parse("05-06-2021 15:00"), 1);
                treatment5.timeTable.put(DayOfWeek.Wednesday, 15);
                Treatment treatment6 = new Treatment(6, "Gym rehabilitation", "Rehabilitation", "Liz Taylor", "Gym", formatter.parse("05-06-2021 15:00"), 1);
                treatment6.timeTable.put(DayOfWeek.Wednesday, 15);
                Treatment treatment7 = new Treatment(7, "Dance movement psychotherapy", "Physiotherapy", "Adam Lever", "Medical consulting suite B", formatter.parse("05-06-2021 16:00"), 1);
                treatment7.timeTable.put(DayOfWeek.Wednesday, 16);
                Treatment treatment8 = new Treatment(8, "Muscle treatment", "Osteopathy", "Lucas Mayor", "Medical consulting suite C", formatter.parse("07-06-2021 12:00"), 1);
                treatment8.timeTable.put(DayOfWeek.Friday, 12);
                Treatment treatment9 = new Treatment(9, "Knee injury", "Rehabilitation", "Anna Summer", "Medical consulting suite A", formatter.parse("11-06-2021 11:00"), 1);
                treatment9.timeTable.put(DayOfWeek.Monday, 11);
                Treatment treatment10 = new Treatment(10, "Back pain", "Physiotherapy", "Tim Sawyer", "Gym", formatter.parse("04-06-2021 11:00"), 1);
                treatment10.timeTable.put(DayOfWeek.Tuesday, 11);
                Treatment treatment11 = new Treatment(11, "Spine correction", "Osteopathy", "Tony Stanton", "Medical consulting suite C", formatter.parse("07-07-2021 16:00"), 1);
                treatment11.timeTable.put(DayOfWeek.Friday, 16);
                Treatment treatment12 = new Treatment(12, "Neck correction", "Osteopathy", "Lucas Mayor", "Medical consulting suite C", formatter.parse("06-07-2021 12:00"), 1);
                treatment12.timeTable.put(DayOfWeek.Thursday, 12);
                Treatment treatment13 = new Treatment(13, "Shoulder injury", "Rehabilitation", "Anna Summer", "Medical consulting suite A", formatter.parse("04-07-2021 11:00"), 1);
                treatment13.timeTable.put(DayOfWeek.Tuesday, 11);
                Treatment treatment14 = new Treatment(14, "Ankle injury", "Rehabilitation", "Liz Taylor", "Medical consulting suite A", formatter.parse("04-07-2021 12:00"), 1);
                treatment14.timeTable.put(DayOfWeek.Tuesday, 12);
                Treatment treatment15 = new Treatment(15, "Disc herniation", "Physiotherapy", "Adam Lever", "Medical consulting suite B", formatter.parse("04-07-2021 13:00"), 1);
                treatment15.timeTable.put(DayOfWeek.Tuesday, 13);
                Treatment treatment16 = new Treatment(16, "Groin strain", "Physiotherapy", "Tim Sawyer", "Medical consulting suite B", formatter.parse("07-07-2021 12:00"), 1);
                treatment16.timeTable.put(DayOfWeek.Friday, 12);
                Treatment treatment17 = new Treatment(17, "Joint correction", "Osteopathy", "Lucas Mayor", "Medical consulting suite C", formatter.parse("07-07-2021 11:00"), 1);
                treatment17.timeTable.put(DayOfWeek.Friday, 13);
                Treatment treatment18 = new Treatment(18, "Tennis elbow", "Rehabilitation", "Anna Summer", "Gym", formatter.parse("03-07-2021 11:00"), 1);
                treatment18.timeTable.put(DayOfWeek.Monday, 11);
                Treatment treatment19 = new Treatment(19, "Shoulder mobility", "Rehabilitation", "Liz Taylor", "Pool", formatter.parse("03-07-2021 11:00"), 1);
                treatment19.timeTable.put(DayOfWeek.Monday, 12);
                Treatment treatment20 = new Treatment(20, "Runners knee", "Physiotherapy", "Adam Lever", "Medical consulting suite B", formatter.parse("07-07-2021 11:00"), 1);
                treatment20.timeTable.put(DayOfWeek.Friday, 11);
                treatments.add(treatment1);
                treatments.add(treatment2);
                treatments.add(treatment3);
                treatments.add(treatment4);
                treatments.add(treatment5);
                treatments.add(treatment6);
                treatments.add(treatment7);
                treatments.add(treatment8);
                treatments.add(treatment9);
                treatments.add(treatment10);
                treatments.add(treatment11);
                treatments.add(treatment12);
                treatments.add(treatment13);
                treatments.add(treatment14);
                treatments.add(treatment15);
                treatments.add(treatment16);
                treatments.add(treatment17);
                treatments.add(treatment18);
                treatments.add(treatment19);
                treatments.add(treatment20);
            } catch (ParseException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        class ClickListener implements ActionListener {
            private String text;

            public ClickListener(String text) {
                this.text = text;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if(this.text.equals("Log in")){
                    try{
                        int id = Integer.parseInt(idText.getText());
                        Person loggedInPerson = findPersonById(id);
                        if(loggedInPerson != null){
                            loggedInPerson.signIn();
                            setEnabled(false);
                            return;
                        }

                        loginMsg.setText("Id is not found");

                    } catch(NumberFormatException ex) {
                        loginMsg.setText("Id is in the incorrect format");
                    }
                }
                else {
                    String fullName = fullNameText.getText();
                    String fullAddress = fullAddressText.getText();

                    if(fullName.isEmpty()){
                        registrationMsg.setText("Full name is required");
                    }else if(fullAddress.isEmpty()){
                        registrationMsg.setText("Address is required");
                    } else{
                        try{
                            Long.parseLong(phoneNumberText.getText());
                            if(checkbox.isSelected()){
                                Area area = (Area)areaCombo.getSelectedItem();
                                Physician physician = registerPhysician(fullName, fullAddress, phoneNumberText.getText(), area);
                                registrationMsg.setForeground(Color.green);
                                registrationMsg.setText("Success. Your physician id is " + physician.getId());
                            } else {
                                Patient patient = registerPatient(fullName, fullAddress, phoneNumberText.getText());
                                registrationMsg.setForeground(Color.green);
                                registrationMsg.setText("Success. Your patient id is " + patient.getId());
                            }

                        }catch(NumberFormatException ex){
                            registrationMsg.setText("Phone number is incorrect");
                        }
                    }
                }
            }
        }

        Person findPersonById(int id) {
            for (Iterator<Person> iter = persons.iterator(); iter.hasNext(); ) {
                Person loggedInPerson = iter.next();
                if(loggedInPerson.getId() == id){
                    return loggedInPerson;
                }
            }
            return null;
        }

        Patient registerPatient(String fullName, String fullAddress, String phoneNumber){
            Patient patient = new Patient(fullName, fullAddress, phoneNumber);
            persons.add(patient);
            return patient;
        }

        Physician registerPhysician(String fullName, String fullAddress, String phoneNumber, Area area){
            Physician physician = new Physician(fullName, fullAddress, phoneNumber, area );
            persons.add(physician);
            return physician;
        }
    }



