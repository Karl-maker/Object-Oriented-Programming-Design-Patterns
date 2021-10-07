import java.time.LocalDate;
import java.util.ArrayList;

//Will be A Subject to Observers

public class Appointment {

    private Specialist specialist;
    private Patient patient;
    private ArrayList<Administrator> administrators = new ArrayList<Administrator>();

    private String status;
    private String description;
    private String bookingCode;
    private LocalDate date;
    private int day; // 1 - 31
    private int month; // 1 - 12
    private int year;
    private int dayOfWeek; // Monday = 1, etc..
    private int slotNumber; // 1 - 6

    public Appointment(int slotNumber, LocalDate date, Patient patient, Specialist specialist){

        this.slotNumber = slotNumber;
        this.date = date;
        this.patient = patient;
        this.specialist = specialist;

    }

    public void subscribeAdministrator(Administrator administrator){ //Everytime it is created loop the list for this
        administrators.add(administrator);
    }

    public void notifyUsers(){ //Do this everytime change is made to appointment

        //Notify the users who are linked to this appointment

        for(Administrator administrator : administrators)
        {
            administrator.appointmentUpdated(this.bookingCode, this.status, this.slotNumber);
        }
        specialist.appointmentUpdated(this.bookingCode, this.status, this.slotNumber);
        patient.appointmentUpdated(this.bookingCode, this.status, this.slotNumber);

        API api = API.getInstance();

        api.searchAppointments(this.bookingCode).setStatus(this.status);
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ArrayList<Administrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(ArrayList<Administrator> administrators) {
        this.administrators = administrators;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    @Override
    public String toString() {
        return date.getMonth().name() + " " + date.getDayOfMonth() + ", " + date.getYear();
    }
}
