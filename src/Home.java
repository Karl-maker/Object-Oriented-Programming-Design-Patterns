import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {

    private ImageIcon doctor_icon, patient_icon, admin_icon;
    private String accountType;
    private User user;
    private JLabel usernameLabel;
    private JPanel mainPanel;
    private JLabel iconLabel;
    private JLabel appointmentNewsFeed;
    private JButton viewAllAppointmentsButton;
    private JButton createAppointmentButton;
    private JButton signOutButton;
    private JLabel appointmentTime;
    private JLabel appointmentStatus;
    private JButton AppointmentButton;
    AppointmentViewer viewer;

    Home(String accountType, User user){

        this.accountType = accountType;
        //user.refreshUser();
        this.user = user;

        add(mainPanel);
        setTitle("Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(Home.EXIT_ON_CLOSE);

        doctor_icon = new ImageIcon("resources/doctor_icon.png");
        patient_icon = new ImageIcon("resources/patient_icon.png");
        admin_icon = new ImageIcon("resources/admin_icon.png");

        displayHome();
        displayAppointment();

        user.isActive = true; //Set User Active ONLINE
        user.notificationCheck(); //Get Notifications

        //User Type Restrictions

        if(accountType.equals("Specialist") || accountType.equals("Administrator")){
            createAppointmentButton.setEnabled(false);
        }

        if(accountType.equals("Patient")){
            AppointmentButton.setText("Check In");
        }
        else if(accountType.equals("Specialist")){
            AppointmentButton.setText("Start/Finish Appointment");
        }
        else if(accountType.equals("Administrator")){
            AppointmentButton.setText("Check In Patient");
        }

        viewAllAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewer = new AppointmentViewer(user, accountType);
                viewer.setVisible(true);
                //setDefaultCloseOperation(Home.HIDE_ON_CLOSE);
                //dispose();
            }
        });
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignIn signIn = new SignIn();
                signIn.setVisible(true);
                user.isActive = false;
                setDefaultCloseOperation(Home.EXIT_ON_CLOSE);
                viewer.dispose();
                dispose();
            }
        });

        createAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAppointment createAppointment = new CreateAppointment(user);
                createAppointment.setVisible(true);
            }
        });
        AppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Send to page to enter Booking Info
                BookingLogin booking = new BookingLogin(accountType, user);
                booking.setVisible(true);
            }
        });
    }

    void displayAppointment(){
        //Show different colours for status

        if(accountType.equalsIgnoreCase("Patient")){
            try{
                int i = user.getAppointments().size();
                Appointment appointment = user.getAppointments().get(i - 1);
                appointmentTime.setText(appointment.getDate().toString());
                appointmentNewsFeed.setText("Dr." + appointment.getSpecialist().getFirstname() + " " + appointment.getSpecialist().getLastname());
                appointmentStatus.setText(appointment.getStatus());

                if(appointment.getStatus().equalsIgnoreCase("CANCEL")){
                    appointmentStatus.setForeground(Color.RED);
                }
                else if(appointment.getStatus().equalsIgnoreCase("WAITING")){
                    appointmentStatus.setForeground(Color.YELLOW);
                }
                else if(appointment.getStatus().equalsIgnoreCase("IN-PROGRESS")){
                    appointmentStatus.setForeground(Color.GREEN);
                }
                else if(appointment.getStatus().equalsIgnoreCase("FINISHED")){
                    appointmentStatus.setForeground(Color.BLUE);
                }
                else if(appointment.getStatus().equalsIgnoreCase("NEW APPOINTMENT")){
                    appointmentStatus.setForeground(Color.cyan);
                }

            }catch(Exception e){
                appointmentNewsFeed.setText("No Upcoming Appointments");
            }
        }else if(accountType.equalsIgnoreCase("Specialist")){
            try{
                int i = user.getAppointments().size();
                Appointment appointment = user.getAppointments().get(i - 1);
                appointmentTime.setText(appointment.getDate().toString());
                appointmentNewsFeed.setText(appointment.getPatient().getFirstname() + " " + appointment.getPatient().getLastname());
                appointmentStatus.setText(appointment.getStatus());

                if(appointment.getStatus().equalsIgnoreCase("CANCEL")){
                    appointmentStatus.setForeground(Color.RED);
                }
                else if(appointment.getStatus().equalsIgnoreCase("WAITING")){
                    appointmentStatus.setForeground(Color.YELLOW);
                }
                else if(appointment.getStatus().equalsIgnoreCase("IN-PROGRESS")){
                    appointmentStatus.setForeground(Color.GREEN);
                }
                else if(appointment.getStatus().equalsIgnoreCase("FINISHED")){
                    appointmentStatus.setForeground(Color.BLUE);
                }
                else if(appointment.getStatus().equalsIgnoreCase("NEW APPOINTMENT")){
                    appointmentStatus.setForeground(Color.cyan);
                }


            }catch(Exception e){
                appointmentNewsFeed.setText("No Upcoming Appointments");
            }

        }else if(accountType.equalsIgnoreCase("Administrator")){
            try{
                int i = user.getAppointments().size();
                Appointment appointment = user.getAppointments().get(i - 1);
                appointmentTime.setText(appointment.getDate().toString());
                appointmentNewsFeed.setText("Dr." + appointment.getSpecialist().getFirstname() + " " + appointment.getSpecialist().getLastname() + " and " + appointment.getPatient().getFirstname() + " " + appointment.getPatient().getLastname());
                appointmentStatus.setText(appointment.getStatus());

                if(appointment.getStatus().equalsIgnoreCase("CANCEL")){
                    appointmentStatus.setForeground(Color.RED);
                }
                else if(appointment.getStatus().equalsIgnoreCase("WAITING")){
                    appointmentStatus.setForeground(Color.YELLOW);
                }
                else if(appointment.getStatus().equalsIgnoreCase("IN-PROGRESS")){
                    appointmentStatus.setForeground(Color.GREEN);
                }
                else if(appointment.getStatus().equalsIgnoreCase("FINISHED")){
                    appointmentStatus.setForeground(Color.BLUE);
                }
                else if(appointment.getStatus().equalsIgnoreCase("NEW APPOINTMENT")){
                    appointmentStatus.setForeground(Color.cyan);
                }

            }catch(Exception e){
                appointmentNewsFeed.setText("No Upcoming Appointments");
            }
        }
    }

    void displayHome(){

        if(accountType.equalsIgnoreCase("Patient")){
            //Set Icon Image
            iconLabel.setIcon(patient_icon);
        }else if(accountType.equalsIgnoreCase("Specialist")){
            //Set Icon Image
            iconLabel.setIcon(doctor_icon);
        }else if(accountType.equalsIgnoreCase("Administrator")){
            //Set Icon Image
            iconLabel.setIcon(admin_icon);
        }

        //Display Info
        usernameLabel.setText(user.getFirstname() + " " + user.getLastname());
    }
}


