import javax.swing.*;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

public abstract class User {

    private String firstname, lastname;
    private char[] password;
    private int id;
    public boolean isActive = false;
    private ArrayList<String> notifications = new ArrayList<String>();
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    //Constructor

    User(){
    }

    //Overloaded

    User(int id, String firstname, String lastname, char[] password){

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public void refreshUser(){
        API api = API.getInstance();

        int i = 0;

        while(i < appointments.size()){
            appointments.set(i, api.searchAppointments(appointments.get(i).getBookingCode()));
            i++;
        }

    }


    public void notificationCheck(){
        if(notifications.size() != 0)
        showMessageDialog(null, notifications.get(notifications.size()-1)); //Call the last notification
    }

    public void appointmentSubscribed(Appointment appointment){
        appointments.add(appointment);
    }

    public void appointmentUpdated(String bookingCode, String status, int slotNumber){ //Test this method

        //Return data to notify user

        int i = 0;

        while(appointments.size() > i){
            if(appointments.get(i).getBookingCode().equalsIgnoreCase(bookingCode)){
                appointments.get(i).setStatus(status);
                appointments.get(i).setSlotNumber(slotNumber);
                //return appointments.get(i);

                break;
            }
            i++;
        }
       // return null;
        if(isActive){
            showMessageDialog(null, "Appointment With Dr." + appointments.get(i).getSpecialist().getFirstname() + " " + appointments.get(i).getSpecialist().getLastname() + " and " + appointments.get(i).getPatient().getFirstname() + " " + appointments.get(i).getPatient().getLastname() + " is now " + appointments.get(i).getStatus());
        }
        else{
            notifications.add( "Appointment With Dr." + appointments.get(i).getSpecialist().getFirstname() + " " + appointments.get(i).getSpecialist().getLastname() + " and " + appointments.get(i).getPatient().getFirstname() + " " + appointments.get(i).getPatient().getLastname() + " is now " + appointments.get(i).getStatus());
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
