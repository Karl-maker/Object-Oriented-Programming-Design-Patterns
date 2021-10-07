import org.w3c.dom.events.EventException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class AppointmentViewer extends JFrame {
    private JPanel mainPanel;
    private JLabel Date;
    private JLabel Participant;
    private JLabel Status;
    private JButton ChangeStatus;
    private JButton Next;
    private JButton Back;
    private JLabel Slot;
    private JLabel Code;
    private JLabel patientIDLabel;
    private LocalDate date;
    private API api;
    private ArrayList<Appointment> appointments= new ArrayList<Appointment>();
    private int displayNum = 0;

    AppointmentViewer(User user, String accountType){
        add(mainPanel);
        setTitle("Appointments");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(AppointmentViewer.DISPOSE_ON_CLOSE);

        date = LocalDate.now(); //Get today's Date

        //Show appointments based on days

        //Collect all appointments == to today and 7 days later
        int appointmentSize = user.getAppointments().size();
        int i = 0;
        int j = 0;
        LocalDate currentDate;
        int viewerDays;
        viewerDays = 1;

        if(accountType.equals("Administrator")){
            viewerDays = 30;
        }
        else if(accountType.equals("Patient")){
            viewerDays = 365;
        }

        while(i < viewerDays){
            currentDate = this.date.plusDays(i);
            j = 0;
            while(j < appointmentSize){
                if(user.getAppointments().get(j).getDate().equals(currentDate)){
                    //Sort with slot number
                    this.appointments.add(user.getAppointments().get(j));
                }
                j++;
            }
            i++;
        }

        try{
            displayContent(displayNum);
        }catch(Exception e){
            showMessageDialog(null, "No Appointments Created");
            dispose();
        }

        Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayContent(displayNum + 1);
                    displayNum++;
                }catch(Exception error){
                    displayNum = 0;
                    displayContent(displayNum);
                }
            }
        });
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayContent(displayNum - 1);
                    displayNum--;
                }catch(Exception error){
                    displayNum = appointments.size() - 1;
                    displayContent(displayNum);
                }
            }
        });
        ChangeStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                api = API.getInstance();

                try{
                    api.searchAppointments(appointments.get(displayNum).getBookingCode()).setStatus("CANCEL");
                    api.searchAppointments(appointments.get(displayNum).getBookingCode()).setSlotNumber(8); //ERASE FROM SLOT
                    api.searchAppointments(appointments.get(displayNum).getBookingCode()).notifyAll();

                    appointments.get(displayNum).setStatus("CANCEL");
                    displayContent(displayNum);
                }catch(Exception error){
                    //Cannot FIND
                    System.out.println(error);
                }

            }
        });
    }

    void displayContent(int i){
        Date.setText(appointments.get(i).getDate().toString());
        Participant.setText("Dr." + appointments.get(i).getSpecialist().getFirstname()+ " " +appointments.get(i).getSpecialist().getLastname() + " is seeing " + appointments.get(i).getPatient().getFirstname() + " " + appointments.get(i).getPatient().getLastname());
        Status.setText(appointments.get(i).getStatus());
        Slot.setText(displaySlot(appointments.get(i).getSlotNumber()));
        Code.setText("Booking Code: "+appointments.get(i).getBookingCode());
        patientIDLabel.setText("| Patient ID: " + appointments.get(i).getPatient().getId());
    }

    String displaySlot(int i){
        String times[] = {"9:00am - 10:00am", "10:00am - 11:00am", "11:00am - 12:00pm", "12:00pm - 1:00pm", "1:00pm - 2:00pm", "2:00pm - 3:00pm", "3:00pm - 4:00pm", "N/A"};
        return times[i - 1];
    }
}
