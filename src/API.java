import java.time.LocalDate;
import java.util.ArrayList;

/*

    This class works as a server for all data to and from the database

 */

public class API { //Singleton Pattern makes all program work from this one class

    private ArrayList<Patient> patients = new ArrayList<Patient>();
    private ArrayList<Specialist> specialists = new ArrayList<Specialist>();
    private ArrayList<Administrator> administrators = new ArrayList<Administrator>();
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    private LocalDate date = LocalDate.now();

    char[] allPassword = {'a','d','m','i','n','1','2','3'};
    private Patient Dave = new Patient(1000, "Dave", "Johnson", allPassword);

    private Specialist Timothy = new Specialist(1000, "Timothy", "Ali", allPassword, "Cardiac");
    private Specialist Ryan = new Specialist(1001, "Ryan", "Queens", allPassword, "Paediatric");
    private Specialist Luis = new Specialist(1002, "Luis", "Jackson", allPassword, "Renal");
    private Specialist Hillary = new Specialist(1003, "Hillary", "Yorke", allPassword, "Dermatologists");
    private Specialist Ethan = new Specialist(1004, "Ethan", "Ellis", allPassword, "Endocrinologists");

    private Administrator Gabby = new Administrator(100, "Gabby", "Williams", allPassword);
    private Appointment appointment1  = new Appointment(2, date, Dave, Timothy);


    //Singleton

    static API api = new API();
    private API(){

        appointment1.setStatus("WAITING");
        appointment1.setBookingCode("201");

        appointment1.subscribeAdministrator(Gabby);
        Dave.appointmentSubscribed(appointment1);
        Timothy.appointmentSubscribed(appointment1);
        Gabby.appointmentSubscribed(appointment1);

        //appointment1.notifyUsers();

        //Pre-made
        patients.add(Dave);

        specialists.add(Timothy);
        specialists.add(Ryan);
        specialists.add(Luis);
        specialists.add(Hillary);
        specialists.add(Ethan);

        administrators.add(Gabby);

        appointments.add(appointment1);


    }
    public static API getInstance(){
        return api;
    }

    //Each method should take object and search for it in this class then edit it

    //Factory Pattern in use to authenticate and use User
    public User authenticateUser(String userType, int id, char[] password){ //Test Method

        User newUser = null;

        if(userType.equalsIgnoreCase("Patient")){
            //Search Database

            int i = 0;
            while(patients.size() > i){
                if(patients.get(i).getId() == id && String.valueOf(patients.get(i).getPassword()).equals(String.valueOf(password))){
                    return patients.get(i);
                }
                i++;
            }
        }
        else if(userType.equalsIgnoreCase("Specialist")){
            //Search Database
            int i = 0;
            while(specialists.size() > i){
                if(specialists.get(i).getId() == id && String.valueOf(specialists.get(i).getPassword()).equals(String.valueOf(password))){
                    return specialists.get(i);
                }
                i++;
            }
        }
        else if(userType.equalsIgnoreCase("Administrator")){
            //Search Database
            int i = 0;
            while(administrators.size() > i){
                if(administrators.get(i).getId() == id && String.valueOf(administrators.get(i).getPassword()).equals(String.valueOf(password))){
                    return administrators.get(i);
                }
                i++;
            }
        }
        return newUser;
    }

    public void registerUser(String userType){

        if(userType.equalsIgnoreCase("Patient")){

        }
        else if(userType.equalsIgnoreCase("Specialist")){

        }
        else if(userType.equalsIgnoreCase("Administrator")){

        }
    }

    public Appointment searchAppointments(String code){
        int i = 0;
        while(i < appointments.size()){
            if(appointments.get(i).getBookingCode().equals(code)){
                return appointments.get(i);
            }
            i++;
        }
        return null;
    }

    Specialist getSpecialist(int i){
        return specialists.get(i);
    }

    Patient getPatient(int id){
        int i = 0;
        while(i < patients.size()){
            if(patients.get(i).getId() == id){
                return patients.get(i);
            }
            i++;
        }
        return null;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public ArrayList<Specialist> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(ArrayList<Specialist> specialists) {
        this.specialists = specialists;
    }

    public ArrayList<Administrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(ArrayList<Administrator> administrators) {
        this.administrators = administrators;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public char[] getAllPassword() {
        return allPassword;
    }

    public void setAllPassword(char[] allPassword) {
        this.allPassword = allPassword;
    }

    public static API getApi() {
        return api;
    }

    public static void setApi(API api) {
        API.api = api;
    }
}
