import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class BookingLogin extends JFrame {
    private JLabel Header;
    private JTextField booking_codeField;
    private JButton promptButton;
    private JPanel mainPanel;

    API api;
    User user;
    int selection;

    public BookingLogin(String accountType, User user) {

        add(mainPanel);
        setTitle("Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(BookingLogin.DISPOSE_ON_CLOSE);

        api = API.getInstance();

        booking_codeField.setBorder(new LineBorder(Color.GRAY,0, true));

        this.user = user;

        if(accountType.equals("Specialist")){
            promptButton.setText("Start/Finish Appointment");
            selection = 1;
        }
        else if(accountType.equals("Patient")){
            promptButton.setText("Check In");
            selection = 2;
        }
        else if(accountType.equals("Administrator")){
            promptButton.setText("Check In Patient");
            selection = 3;
        }

        promptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;

                user.refreshUser();

                if(selection == 1){//Specialist

                    try{
                        if(api.searchAppointments(booking_codeField.getText()).getSpecialist().getId() == user.getId()){
                            if(api.searchAppointments(booking_codeField.getText()).getStatus().equals("WAITING")){
                                api.searchAppointments(booking_codeField.getText()).setStatus("IN-PROGRESS");
                                api.searchAppointments(booking_codeField.getText()).notifyUsers();
                                showMessageDialog(null, "Appointment is In-PROGRESS");
                            }
                            else if(api.searchAppointments(booking_codeField.getText()).getStatus().equals("IN-PROGRESS")){
                                api.searchAppointments(booking_codeField.getText()).setStatus("FINISHED");
                                api.searchAppointments(booking_codeField.getText()).notifyUsers();
                                showMessageDialog(null, "Appointment is Finished");
                            }
                            else{
                                showMessageDialog(null, "Unable to change status unless patient Checked In");
                            }
                        }
                    }catch(Exception error){
                        showMessageDialog(null, "Cannot Find Appointment, Please Check Booking Code");
                    }
                }
                else if(selection == 2){ //Patient
                    try{
                        if(api.searchAppointments(booking_codeField.getText()).getPatient().getId() == user.getId()){
                            if(api.searchAppointments(booking_codeField.getText()).getStatus().equals("WAITING")){
                                showMessageDialog(null, "Wait On Specialist To Attend To You");
                            }
                            else if(api.searchAppointments(booking_codeField.getText()).getStatus().equals("NEW APPOINTMENT")){
                                api.searchAppointments(booking_codeField.getText()).setStatus("WAITING");
                                showMessageDialog(null, "We Will Notify The Specialist That You Are Here");

                                api.searchAppointments(booking_codeField.getText()).notifyUsers();
                            }
                            else{
                                showMessageDialog(null, "Unable to change status");
                            }
                        }
                    }catch(Exception error){
                        showMessageDialog(null, "Cannot Find Appointment, Please Check Booking Code");
                    }
                }
                else if(selection == 3){
                    if(api.searchAppointments(booking_codeField.getText()).getStatus().equals("NEW APPOINTMENT")){
                        api.searchAppointments(booking_codeField.getText()).setStatus("WAITING");
                        api.searchAppointments(booking_codeField.getText()).notifyUsers();
                    }else{
                        showMessageDialog(null, "Unable to change status");
                        api.searchAppointments(booking_codeField.getText()).notifyUsers();
                    }
                }
                //...
            }
        });
    }
}
