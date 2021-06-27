package com.company;

import static com.company.PhysicianAccount.allTreatments;
import static com.company.PhysicianAccount.allTreatmentsTable;
import static com.company.PhysicianAccount.loggedInPhysician;
import static com.company.PhysicianAccount.mainPanel;
import static com.company.PhysicianAccount.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
  * @author danzlo
 */


public class PhysicianAccount extends JFrame implements java.util.Observer {

    public static JPanel panel;
    public static JTable allTreatmentsTable;
    public static Set<Treatment> allTreatments = new HashSet<>();
    public static Physician loggedInPhysician;
    public static MyTreatmentsPanel myTreatmentsPanel;
    public static PhysicianMainPanel mainPanel;
    CardLayout cl;


    public PhysicianAccount(Physician loggedInUser){
        super("Welcome " + loggedInUser.getFullName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                Login.app.setVisible(true);
                Login.app.setEnabled(true);
            }
        });

        setSize(800, 600);

        this.allTreatments = Account.allBookedTreatments;

        this.loggedInPhysician = loggedInUser;

        panel = new JPanel();
        cl = new CardLayout();
        panel.setLayout(cl);

        mainPanel = new PhysicianMainPanel();

        myTreatmentsPanel = new MyTreatmentsPanel();
        PhysicianAppointmentsPanel allapptsPanel = new PhysicianAppointmentsPanel();
        MyAppointmentsPanel myapptsPanel = new MyAppointmentsPanel();

        panel.add(mainPanel, "lookup");
        panel.add(myTreatmentsPanel, "myTreatments");
        panel.add(allapptsPanel, "appointments");
        panel.add(myapptsPanel, "my-Appointments");

        /**Menu*/

        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        JMenu lookup = new JMenu("All Treatments");
        JMenuItem mainItem = new JMenuItem ("Browse treatments");
        mainItem.addActionListener(e -> cl.show(panel,"lookup"));
        lookup.add(mainItem);
        mb.add(lookup);

        JMenu bookings = new JMenu("My Treatments");
        JMenuItem bookingItem = new JMenuItem ("Treatments with registered students");
        bookingItem.addActionListener(e -> cl.show(panel,"myTreatments"));
        bookings.add(bookingItem);
        mb.add(bookings);

        JMenu appointments = new JMenu("Appointments");
        JMenuItem apptsItem = new JMenuItem ("All appointments");
        apptsItem.addActionListener(e -> cl.show(panel,"appointments"));

        JMenuItem myapptsItem = new JMenuItem ("My appointments");
        myapptsItem.addActionListener(e -> cl.show(panel,"My-appointments"));
        appointments.add(apptsItem);
        appointments.add(myapptsItem);
        mb.add(appointments);

        cl.show(panel, "1");

        add(panel);

    }

    @Override
    public void update(Observable o, Object arg) {
        mainPanel = new PhysicianMainPanel();
        panel.add(mainPanel, "lookup");

    }

}

class PhysicianMainPanel extends JPanel {

    public PhysicianMainPanel(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Object[][] rows = new String[allTreatments.size()][6];
        String columns[]={"Code", "Treatment","Area","Patients registered","Physician", "Date"};


        Iterator<Treatment> iter = allTreatments.iterator();
        int index = 0;

        while(iter.hasNext()){
            Treatment  lesson = iter.next();

            rows[index][0] = Integer.toString(lesson.id);
            rows[index][1] = lesson.name;
            rows[index][2] = lesson.area;
            rows[index][3] = String.valueOf(lesson.registeredPatients.size());
            rows[index][4] = lesson.physician;
            rows[index][5] = lesson.date.toString();
            index++;
        }

        TableModel model = new DefaultTableModel(rows, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        allTreatmentsTable = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        allTreatmentsTable.setRowSorter(sorter);

        JScrollPane sp = new JScrollPane(allTreatmentsTable);

        add(sp);
    }

}

class MyTreatmentsPanel extends JPanel {
    public MyTreatmentsPanel(){
        super();
        setLayout(new BorderLayout());

        JLabel label = new JLabel("My bookings: ");
        add(label, BorderLayout.NORTH);

        JPanel bookedTreatments = new JPanel();
        bookedTreatments.setLayout(new BoxLayout(bookedTreatments, BoxLayout.Y_AXIS));


        for (Treatment lesson : Account.allBookedTreatments) {
            if(lesson.physician == loggedInPhysician.getFullName()){
                JPanel bookedItem = new JPanel();

                bookedItem.setLayout(new BoxLayout(bookedItem, BoxLayout.X_AXIS));
                bookedItem.setLayout(new BoxLayout(bookedItem, BoxLayout.LINE_AXIS));
                bookedItem.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

                JLabel lessonId = new JLabel(String.valueOf(lesson.id));
                bookedItem.add(lessonId);

                bookedItem.add(Box.createHorizontalGlue());

                JLabel lessonName = new JLabel(lesson.name);
                bookedItem.add(lessonName);

                bookedItem.add(Box.createHorizontalGlue());

                JLabel lessonDate = new JLabel(lesson.date.toString());
                bookedItem.add(lessonDate);

                bookedItem.add(Box.createHorizontalGlue());

                JButton cancelButton = new JButton("cancel");
                bookedItem.add(cancelButton);

                cancelButton.addActionListener(e -> {
                    JButton button = (JButton)e.getSource();
                    JPanel visitorPanel = (JPanel)button.getParent();
                    Component[] components = visitorPanel.getComponents();
                    JLabel l = (JLabel)components[0];
                    int lessonId1 = Integer.parseInt(l.getText());
                    Treatment lessonToCancel = null;

                    for (Treatment lesson1 : Account.allBookedTreatments) {
                        if(lesson1.id == lessonId1){
                            lessonToCancel = lesson1;
                        }
                    }
                    Account.allBookedTreatments.remove(lessonToCancel);
                    for(Patient s: lesson.registeredPatients){
                        s.cancelTreatment(lessonToCancel);
                    }
                    visitorPanel.removeAll();
                    visitorPanel.revalidate();
                    visitorPanel.repaint();
                    mainPanel = new PhysicianMainPanel();
                    panel.add(mainPanel, "lookup");
                });


                bookedItem.add(Box.createRigidArea(new Dimension(10, 0)));


                bookedTreatments.add(bookedItem);
            }
        }

        add(bookedTreatments, BorderLayout.CENTER);
    }
}

class PhysicianAppointmentsPanel extends JPanel {
    JTable table;
    Map<String, Physician> physicians;

    public PhysicianAppointmentsPanel (){
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel2 =new JPanel();

        physicians = new HashMap<>();

        for (Iterator<Person> iter = Login.persons.iterator(); iter.hasNext(); ) {
            Person physician = iter.next();
            if(Physician.class.isInstance(physician)){
                physicians.put(physician.getFullName(), (Physician)physician);
            }
        }

        Object[][] rows = new String[physicians.size()][3];
        String columns[]={"Area", "Physician", "Available"};
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


        add(sp);
        add(panel2);
    }
}

class MyAppointmentsPanel extends JPanel {
    public MyAppointmentsPanel() {
        super();
        setLayout(new BorderLayout());

        JLabel label = new JLabel("My appointments: ");
        add(label, BorderLayout.NORTH);

        JPanel bookedTreatments = new JPanel();
        bookedTreatments.setLayout(new BoxLayout(bookedTreatments, BoxLayout.Y_AXIS));


        for (Appointment appointment : loggedInPhysician.appointments) {

            JPanel appointmentItem = new JPanel();

            appointmentItem.setLayout(new BoxLayout(appointmentItem, BoxLayout.X_AXIS));
            appointmentItem.setLayout(new BoxLayout(appointmentItem, BoxLayout.LINE_AXIS));
            appointmentItem.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            JLabel appointmentId = new JLabel(Integer.toString(appointment.id));
            appointmentItem.add(appointmentId);

            appointmentItem.add(Box.createHorizontalGlue());

            JLabel appointmentDate = new JLabel(appointment.date.toString());
            appointmentItem.add(appointmentDate);

            appointmentItem.add(Box.createHorizontalGlue());

            JLabel visitorName = new JLabel(appointment.visitor);
            appointmentItem.add(visitorName);

            appointmentItem.add(Box.createHorizontalGlue());

            JButton cancelButton = new JButton("cancel");
            appointmentItem.add(cancelButton);

            cancelButton.addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                JPanel panel = (JPanel) button.getParent();
                Component[] components = panel.getComponents();
                JLabel l = (JLabel) components[0];
                int id = Integer.parseInt(l.getText());
                loggedInPhysician.cancelAppointment(id);

                panel.removeAll();
                panel.revalidate();
                panel.repaint();

            });

            bookedTreatments.add(appointmentItem);

        }

        add(bookedTreatments, BorderLayout.CENTER);
    }
}

