import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import static javax.swing.JOptionPane.showMessageDialog;

public class CreateAppointment extends JFrame{

    API api;
    LocalDate date;
    Appointment appointment;
    int specialistButton = 0;
    int slotButton = 0;
    int slotNum = 0;
    int max = 100, min = 0;
    String times[] = {"9:00am - 10:00am", "10:00am - 11:00am", "11:00am - 12:00pm", "12:00pm - 1:00pm", "1:00pm - 2:00pm", "2:00pm - 3:00pm", "3:00pm - 4:00pm"};

    private JLabel DoctorLabel;
    private JButton SpecialistNextButton;
    private JButton SpecialistBackButton;
    private JTextField MonthField;
    private JTextField YearField;
    private JTextField DayField;
    private JButton CONFIRMButton;
    private JLabel SlotLabel;
    private JButton SlotBackButton;
    private JButton SlotNextButton;
    private JPanel mainPanel;
    private User user;

    CreateAppointment(User user){
        add(mainPanel);
        setTitle("Create Appointments");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(CreateAppointment.DISPOSE_ON_CLOSE);

        this.user = user;
        api = API.getInstance();

        YearField.setBorder(new LineBorder(Color.GRAY,0, true));
        MonthField.setBorder(new LineBorder(Color.GRAY,0, true));
        DayField.setBorder(new LineBorder(Color.GRAY,0, true));

        displayDrInfo(0);
        displaySlotInfo(1);

        SpecialistNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    displayDrInfo(specialistButton + 1);
                    specialistButton++;
                }catch(Exception error){
                    specialistButton = 0;
                    displayDrInfo(specialistButton);
                }
            }
        });
        SpecialistBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayDrInfo(specialistButton - 1);
                    specialistButton--;
                }catch(Exception error){
                    specialistButton = api.getSpecialists().size() - 1;
                    displayDrInfo(specialistButton);
                }
            }
        });
        SlotNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    displaySlotInfo(slotButton + 1);
                    slotButton++;
                }catch(Exception error){
                    slotButton = 0;
                    displaySlotInfo(slotButton);
                }
            }
        });
        SlotBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displaySlotInfo(slotButton - 1);
                    slotButton--;
                }catch(Exception error){
                    slotButton = 6;
                    displaySlotInfo(slotButton);
                }
            }
        });
        CONFIRMButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                confirmAppointment();
            }
        });
    }

    void displayDrInfo(int i){
        DoctorLabel.setText("Dr." + api.getSpecialists().get(i).getFirstname() + " " + api.getSpecialists().get(i).getLastname() + " Specialist In: " + api.getSpecialists().get(i).getSpecialistType());
    }

    void displaySlotInfo(int i){
        SlotLabel.setText(times[i]);
    }

    void confirmAppointment(){

        boolean run = true;

        try{
            int day = Integer.parseInt(DayField.getText());
            int month = Integer.parseInt(MonthField.getText());
            int year = Integer.parseInt(YearField.getText());
            appointment = new Appointment(slotButton + 1, setDate(month, day, year), api.getPatient(user.getId()), api.getSpecialist(specialistButton));
            appointment.setStatus("NEW APPOINTMENT");
        }catch(Exception error){
            showMessageDialog(null, "Enter Valid Dates");
            return;
        }

        int random_int = 0;

        //Search if another appointment is on that day and slot time

        int k = 0;

        while(k < api.getAppointments().size()){
            if(api.getAppointments().get(k).getSpecialist().getId() == appointment.getSpecialist().getId() && appointment.getDate().equals(api.getAppointments().get(k).getDate()) && appointment.getSlotNumber() == api.getAppointments().get(k).getSlotNumber()){
                showMessageDialog(null, "Sorry, That Time Slot Is Taken");
                System.out.println(appointment.getSlotNumber() + " = " + api.getAppointments().get(k).getSlotNumber());
                return; //Leave that process
            }
            k++;
        }

        while(run){
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            if(api.searchAppointments(String.valueOf(random_int)) == null){
                appointment.setBookingCode(String.valueOf(random_int));
            }
            run = false;
        }

        api.getAppointments().add(appointment);

        int i = 0;

        while(i < api.getAdministrators().size()){
            appointment.subscribeAdministrator(api.getAdministrators().get(i));
            api.getAdministrators().get(i).appointmentSubscribed(appointment);
            i++;
        }

        api.getPatient(user.getId()).appointmentSubscribed(appointment);
        api.getSpecialist(specialistButton).appointmentSubscribed(appointment);

        api.searchAppointments(appointment.getBookingCode()).notifyUsers(); //String is NULL
        showMessageDialog(null, "Booking Code Is " + random_int);


    }

    LocalDate setDate(int month, int day, int year){
       return LocalDate.of(year, month, day);
    }



    /*
    Specialist getSpecialist(int i){
    return api.getSpecialists().get(i);
    }
     */



}
