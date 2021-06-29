package com.company;

import static com.company.Account.allBookedTreatments;
import static com.company.Account.bookButton;
import static com.company.Account.treatmentTable;
import static com.company.Account.loggedInPatient;
import static com.company.Login.persons;
import static com.company.Account.message;
import static com.company.Account.panel;
import static com.company.Login.app;
import static com.company.Login.treatments;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author danzlo
 */

public class Account extends JFrame implements java.util.Observer {

    public static JPanel panel;
    public static JTable treatmentTable;
    public static JLabel message;
    public static Set<Treatment> allBookedTreatments = new HashSet<>();
    public static Patient loggedInPatient;
    public static BookingsPanel bookingsPanel;
    public static MainPanel mainPanel;
    public static JButton bookButton;
    public static CardLayout cl;


    public Account (Patient loggedInUser){
        super("Welcome " + loggedInUser.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                app.setVisible(true);
                app.setEnabled(true);
            }
        });

        setSize(1000, 500);

        for (Treatment treatment : treatments) {
            treatment.addObserver(this);
        }


        loggedInPatient = loggedInUser;
        loggedInPatient.addObserver(this);

        panel = new JPanel();
        cl = new CardLayout();
        panel.setLayout(cl);

        mainPanel = new MainPanel();
        bookingsPanel = new BookingsPanel();
        AppointmentsPanel apptsPanel = new AppointmentsPanel();

        panel.add(mainPanel, "Look up");
        panel.add(bookingsPanel, "Bookings");
        panel.add(apptsPanel, "Appointments");

        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        JMenu lookup = new JMenu("Look up");
        JMenuItem mainItem = new JMenuItem ("Find treatments");
        mainItem.addActionListener(e -> cl.show(panel,"Look up"));
        lookup.add(mainItem);
        mb.add(lookup);

        JMenu bookings = new JMenu("Bookings");
        JMenuItem bookingItem = new JMenuItem ("My Bookings");
        bookingItem.addActionListener(e -> cl.show(panel,"Bookings"));
        bookings.add(bookingItem);
        mb.add(bookings);

        JMenu appointments = new JMenu("Appointments");
        JMenuItem apptsItem = new JMenuItem ("Appointments with physicians");
        apptsItem.addActionListener(e -> cl.show(panel,"Appointments"));
        appointments.add(apptsItem);
        mb.add(appointments);

        cl.show(panel, "1");

        add(panel);
    }

    @Override
    public void update(Observable o, Object arg) {
        mainPanel = new MainPanel();
        panel.add(mainPanel, "Look up");
        bookingsPanel = new BookingsPanel();
        panel.add(bookingsPanel, "Bookings");
        cl.show(panel, "Look up");
    }
}

class MainPanel extends JPanel {
    JComboBox dateList;


    public MainPanel(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panelnr1 =new JPanel();

        /** areas of expertise */
        JLabel label = new JLabel("Area of expertise: ");
        String areas[] = { "Physiotherapy","Osteopathy","Rehabilitation"};
        

        JList areasList = new JList(areas);
        panelnr1.add(label);
        panelnr1.add(areasList);

        /** physicians by name */
        
        JPanel panelnr2 =new JPanel();
        JLabel label2 = new JLabel("Physicians: ");

        Map<String, Physician> physicians = new HashMap<>();

        for (Person physician : persons) {
            if (physician instanceof Physician) {
                physicians.put(physician.getFullName(), (Physician) physician);
            }
        }
        JList physiciansList = new JList(physicians.keySet().toArray());

        panelnr2.add(label2);
        panelnr2.add(physiciansList);

        JPanel lookupPanel = new JPanel();
        lookupPanel.setLayout(new BoxLayout(lookupPanel, BoxLayout.X_AXIS));
        lookupPanel.add(panelnr1);
        lookupPanel.add(panelnr2);

        Object[][] rows = new String[treatments.size()][6];
        String[] columns ={"Code", "Treatment", "Area", "Volume", "Physician", "Schedule"};

        Iterator<Treatment> iter = treatments.iterator();
        int index = 0;

        while(iter.hasNext()){
            Treatment  treatment = iter.next();

            rows[index][0] = Integer.toString(treatment.id);
            rows[index][1] = treatment.name;
            rows[index][2] = treatment.area;
            rows[index][3] = String.valueOf(treatment.volume);
            rows[index][4] = treatment.physician;
            rows[index][5] = treatment.timeTable.keySet().toArray()[0] + " " + treatment.timeTable.values().toArray()[0].toString()+ ":00";
            index++;
        }

        TableModel model = new DefaultTableModel(rows, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        treatmentTable = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        treatmentTable.setRowSorter(sorter);

        JScrollPane sp = new JScrollPane(treatmentTable);


        /** Filter by physician */

        physiciansList.addListSelectionListener(arg0 -> {
            if (!arg0.getValueIsAdjusting()) {
                String selectedPhysician = physiciansList.getSelectedValue().toString();
                treatmentTable.setRowSorter(sorter);
                RowFilter rf;
                try {
                    rf = RowFilter.regexFilter(selectedPhysician);
                } catch (java.util.regex.PatternSyntaxException e) {
                    return;
                }
                sorter.setRowFilter(rf);
            }
        });

        /** filter by area of expertise */
        areasList.addListSelectionListener(arg0 -> {
            if (!arg0.getValueIsAdjusting()) {
                String selectedArea = areasList.getSelectedValue().toString();
                treatmentTable.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(selectedArea));
            }
        });

        add(lookupPanel);
        add(sp);


        message = new JLabel("");
        add(message);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        String[] dates = {
                "01-06-2021", "02-06-2021", "03-06-2021", "04-06-2021", "05-06-2021", "06-06-2021", "07-06-2021",
                "08-06-2021", "09-06-2021", "10-06-2021", "11-06-2021", "12-06-2021", "13-06-2021", "14-06-2021",
                "15-06-2021", "16-06-2021", "17-06-2021", "18-06-2021", "19-06-2021", "20-06-2021", "21-06-2021",
                "22-06-2021", "23-06-2021", "24-06-2021", "25-06-2021", "26-06-2021", "27-06-2021", "28-06-2021",
                "29-06-2021", "30-06-2021",
                
                "01-07-2021", "02-07-2021", "03-07-2021", "04-07-2021", "05-07-2021", "06-07-2021", "07-07-2021", 
                "08-07-2021", "09-07-2021", "10-07-2021", "11-07-2021", "12-07-2021", "13-07-2021", "14-07-2021",
                "15-07-2021", "16-07-2021", "17-07-2021", "18-07-2021", "19-07-2021", "20-07-2021", "21-07-2021",
                "22-07-2021", "23-07-2021", "24-07-2021", "25-07-2021", "26-07-2021", "27-07-2021", "28-07-2021",
                "29-07-2021", "30-07-2021", "31-07-2021"

        };
        dateList = new JComboBox(dates);
        add(dateList);

        bookButton = new JButton("Book treatment");

        /** booking a treatment */
        bookButton.addActionListener(e -> {
            try {
                int selectedIndex = treatmentTable.getSelectedRow();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);

                if(selectedIndex == -1){
                    message.setForeground(Color.RED);
                    message.setText("You have not selected a treatment");
                } else {
                    Treatment bookedTreatment = null;
                    int treatmentId = Integer.parseInt(treatmentTable.getValueAt(selectedIndex, 0).toString());
                    boolean treatmentExists = false;
                    boolean timeSlotBusy = false;

                    for (Treatment treatment : treatments) {
                        if(treatment.id == treatmentId){
                            Date newTreatmentDate = formatter.parse(Objects.requireNonNull(dateList.getSelectedItem()).toString() + " " + treatment.timeTable.values().toArray()[0] + ":00");
                            if(treatment.date.getDay() !=  newTreatmentDate.getDay()){
                                message.setForeground(Color.RED);
                                message.setText("This treatment is not available on this day");
                                return;
                            }
                            else {
                                if(!treatment.date.toString().equals(newTreatmentDate.toString())){
                                    bookedTreatment = new Treatment(
                                            treatments.size()+ allBookedTreatments.size() + 1,
                                            treatment.name,
                                            treatment.area,
                                            treatment.physician,
                                            treatment.place,
                                            newTreatmentDate,
                                            treatment.volume);
                                } else {
                                    bookedTreatment = treatment;
                                }
                            }
                        }
                    }

                    for(Treatment treatment: allBookedTreatments){
                        assert bookedTreatment != null;
                        if (treatment.id == bookedTreatment.id) {
                            treatmentExists = true;
                            break;
                        }
                    }

                    for (Treatment patientTreatment : loggedInPatient.registeredTreatments) {
                        assert bookedTreatment != null;
                        if (patientTreatment.id == bookedTreatment.id) {
                            message.setForeground(Color.red);
                            message.setText("You already booked this treatment on this date. Please choose a different date.");
                            return;
                        }
                        if (patientTreatment.date.toString().equals(bookedTreatment.date.toString())) {
                            timeSlotBusy = true;
                        }
                    }
                    if(timeSlotBusy){
                        message.setForeground(Color.red);
                        message.setText("You have another treatment booked at this time.");
                        return;
                    }
                    else if(!treatmentExists){
                        allBookedTreatments.add(bookedTreatment);
                    }

                    assert bookedTreatment != null;
                    String notification = bookedTreatment.registerPatient(loggedInPatient);

                    if(notification.equals("Treatment booked!")){
                        loggedInPatient.bookTreatment(bookedTreatment);
                        message.setForeground(Color.green);
                    } else{
                        message.setForeground(Color.red);
                    }
                    message.setText(notification);
                    panel.revalidate();
                    panel.repaint();
                }
            } catch (ParseException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        add(bookButton);
    }
}


class BookingsPanel extends JPanel {
    public BookingsPanel (){
        super();
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Booked Treatments: ");
        add(label, BorderLayout.NORTH);

        JPanel bookedTreatments = new JPanel();
        bookedTreatments.setLayout(new BoxLayout(bookedTreatments, BoxLayout.Y_AXIS));


        for (Treatment treatment : loggedInPatient.registeredTreatments) {
            JPanel bookedItem = new JPanel();

            bookedItem.setLayout(new BoxLayout(bookedItem, BoxLayout.X_AXIS));
            bookedItem.setLayout(new BoxLayout(bookedItem, BoxLayout.LINE_AXIS));
            bookedItem.setBorder(BorderFactory.createEmptyBorder(0, 13, 13, 13));

            JLabel treatmentId = new JLabel(String.valueOf(treatment.id));
            bookedItem.add(treatmentId);

            bookedItem.add(Box.createHorizontalGlue());

            JLabel treatmentName = new JLabel(treatment.name);
            bookedItem.add(treatmentName);

            bookedItem.add(Box.createHorizontalGlue());

            JLabel treatmentDate = new JLabel(treatment.date.toString());
            bookedItem.add(treatmentDate);

            bookedItem.add(Box.createHorizontalGlue());
            JLabel attendedLabel = new JLabel("Attended");
            attendedLabel.setForeground(Color.green);

            JButton attendButton = new JButton("Attend");
            JButton cancelButton = new JButton("Cancel");


            /** Attend treatment */
            attendButton.addActionListener(e -> {
                JButton button = (JButton)e.getSource();
                JPanel panel = (JPanel)button.getParent();
                Component[] components = panel.getComponents();
                JLabel l = (JLabel)components[0];
                int treatmentId1 = Integer.parseInt(l.getText());

                for(Treatment treatment1 : allBookedTreatments){
                    if(treatment1.id == treatmentId1){
                        loggedInPatient.attendTreatment(treatment1);
                    }
                }
                panel.remove(8);
                panel.remove(8);
                panel.add(attendedLabel);
                panel.revalidate();
                panel.repaint();

            });

            /** Cancel treatment */
            cancelButton.addActionListener(e -> {

                JButton button = (JButton)e.getSource();
                JPanel panel = (JPanel)button.getParent();
                Component[] components = panel.getComponents();
                JLabel l = (JLabel)components[0];
                int treatmentId12 = Integer.parseInt(l.getText());

                for(Treatment treatment12 : allBookedTreatments){
                    if(treatment12.id == treatmentId12){
                        loggedInPatient.cancelTreatment(treatment12);
                        treatment12.deletePatient(loggedInPatient);
                        if(treatment12.registeredPatients.size() == 0){
                            allBookedTreatments.remove(treatment12);
                        }
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();
                    }
                }
            });
            
            bookedItem.add(attendButton);
            bookedItem.add(cancelButton);
            
            bookedItem.add(Box.createRigidArea(new Dimension(13, 0)));
            
            bookedTreatments.add(bookedItem);
        }

        add(bookedTreatments, BorderLayout.CENTER);
    }
}

class AppointmentsPanel extends JPanel {
    JTable table;
    JLabel appointmentMsg;
    Map<String, Physician> physicians;
    JTextField visitorText;
    JComboBox datesList;

    public AppointmentsPanel (){
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panelnr1 =new JPanel();
        panelnr1.setLayout(new BoxLayout(panelnr1, BoxLayout.Y_AXIS));
        JLabel label1 = new JLabel("Area of expertise: ");
        String[] areas = { "Physiotherapy","Osteopathy","Rehabilitation"};

        JList areasList = new JList(areas);

        panelnr1.add(label1);
        panelnr1.add(areasList);

        JLabel timeLabel = new JLabel("Choose time: ");
        panelnr1.add(timeLabel);

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
        String[] hrs = { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        JComboBox hrsList = new JComboBox(hrs);
        String[] min = {"00", "15", "30", "45", "60"};
        JComboBox minList = new JComboBox(min);
        timePanel.add(hrsList);
        timePanel.add(minList);
        panelnr1.add(timePanel);

        JLabel dateLabel = new JLabel("Select date: ");
        panelnr1.add(dateLabel);
        String[] dates = {
                "01-06-2021", "02-06-2021", "03-06-2021", "04-06-2021", "05-06-2021", "06-06-2021", "07-06-2021",
                "08-06-2021", "09-06-2021", "10-06-2021", "11-06-2021", "12-06-2021", "13-06-2021", "14-06-2021",
                "15-06-2021", "16-06-2021", "17-06-2021", "18-06-2021", "19-06-2021", "20-06-2021", "21-06-2021",
                "22-06-2021", "23-06-2021", "24-06-2021", "25-06-2021", "26-06-2021", "27-06-2021", "28-06-2021",
                "29-06-2021", "30-06-2021",

                "01-07-2021", "02-07-2021", "03-07-2021", "04-07-2021", "05-07-2021", "06-07-2021", "07-07-2021",
                "08-07-2021", "09-07-2021", "10-07-2021", "11-07-2021", "12-07-2021", "13-07-2021", "14-07-2021",
                "15-07-2021", "16-07-2021", "17-07-2021", "18-07-2021", "19-07-2021", "20-07-2021", "21-07-2021",
                "22-07-2021", "23-07-2021", "24-07-2021", "25-07-2021", "26-07-2021", "27-07-2021", "28-07-2021",
                "29-07-2021", "30-07-2021", "31-07-2021"
        };
        datesList = new JComboBox(dates);
        panelnr1.add(datesList);
        
        JLabel visitorLabel = new JLabel("Enter your name: ");
        panelnr1.add(visitorLabel);

        visitorText = new JTextField();
        panelnr1.add(visitorText);

        appointmentMsg = new JLabel(" ");
        panelnr1.add(appointmentMsg);

        JButton bookAptBtn = new JButton("Book appointment");
        bookAptBtn.addActionListener(e -> {

            Integer hrs1 = Integer.parseInt(Objects.requireNonNull(hrsList.getSelectedItem()).toString());
            Integer min1 = Integer.parseInt(Objects.requireNonNull(minList.getSelectedItem()).toString());
            String date = Objects.requireNonNull(datesList.getSelectedItem()).toString();
            int selectedIndex = table.getSelectedRow();
            
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date apptDate = formatter.parse(date + " 10:00");

                if(selectedIndex == -1){
                    appointmentMsg.setText("You haven't selected a physician");
                } else if(visitorText.getText().isEmpty()){
                    appointmentMsg.setText("You haven't entered your name");
                }
                else {
                    String physicianName = table.getValueAt(selectedIndex, 1).toString();

                    for(Physician physician: physicians.values()){
                        if(physician.getFullName() == physicianName){
                            appointmentMsg.setText(physician.bookAppointment(apptDate, hrs1, min1, visitorText.getText()));
                        }
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(AppointmentsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


        panelnr1.add(bookAptBtn);

        JPanel panelnr2 =new JPanel();

        physicians = new HashMap<>();

        for (Person physician : persons) {
            if (physician instanceof Physician) {
                physicians.put(physician.getFullName(), (Physician) physician);
            }
        }

        Object[][] rows = new String[physicians.size()][3];
        String[] columns ={"Area", "Physician", "Available"};
        int index = 0;

        for(Physician physician: physicians.values()){
            rows[index][0] = physician.area.toString();
            rows[index][1] = physician.getFullName();
            rows[index][2] = physician.appointmentDay.toString() + " " + physician.appointmentHr + ":00";
            index++;
        }
        TableModel model = new DefaultTableModel(rows, columns);
        table = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        JScrollPane sp = new JScrollPane(table);


        areasList.addListSelectionListener(arg0 -> {
            if (!arg0.getValueIsAdjusting()) {
                String filter = areasList.getSelectedValue().toString();
                table.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(filter));
            }
        });

        add(sp);
        add(panelnr1);
        add(panelnr2);
    }
}

