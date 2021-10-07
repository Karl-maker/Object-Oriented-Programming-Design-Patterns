import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void isNotificationStatusWorking(){
        LocalDate date = LocalDate.now();
        Patient patient = new Patient();
        patient.setFirstname("Dave");
        patient.setLastname("William");
        Administrator administrator = new Administrator();
        Specialist specialist = new Specialist();
        specialist.setLastname("Luis");
        specialist.setFirstname("Jeff");
        Appointment appointment = new Appointment(3, date, patient, specialist);
        appointment.setBookingCode("WBOD"); //Would be null without
        appointment.subscribeAdministrator(administrator);

        patient.isActive = true;

        administrator.appointmentSubscribed(appointment);
        patient.appointmentSubscribed(appointment);
        specialist.appointmentSubscribed(appointment);

        appointment.setStatus("Cancelled");
        appointment.notifyUsers();
        patient.notificationCheck();
        assertTrue(patient.getAppointments().get(0).getStatus().equals("Cancelled"));
        assertTrue(specialist.getAppointments().get(0).getStatus().equals("Cancelled"));
        assertTrue(administrator.getAppointments().get(0).getStatus().equals("Cancelled"));

    }

    @Test
    void isNotificationBookingCodeWorking(){
        LocalDate date = LocalDate.now();
        Patient patient = new Patient();
        Administrator administrator = new Administrator();
        Specialist specialist = new Specialist();
        Appointment appointment = new Appointment(3, date, patient, specialist);
        appointment.setBookingCode("WBOD");
        appointment.subscribeAdministrator(administrator);

        administrator.appointmentSubscribed(appointment);
        patient.appointmentSubscribed(appointment);
        specialist.appointmentSubscribed(appointment);

        appointment.setStatus("Cancelled");
        appointment.notifyUsers();

        assertTrue(patient.getAppointments().get(0).getBookingCode().equals("WBOD"));
        assertTrue(specialist.getAppointments().get(0).getBookingCode().equals("WBOD"));
        assertTrue(administrator.getAppointments().get(0).getBookingCode().equals("WBOD"));
    }


}