import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class Registration extends JFrame{
    private JButton PatientButton;
    private JButton SpecialistButton;
    private JButton AdministratorButton;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JPasswordField passwordField;
    private JTextField specialistField;
    private JLabel passwordLabel;
    private JButton registerButton;
    private JPanel mainPanel;

    int selection = 0;

    API api;

    Registration(){

        add(mainPanel);
        setTitle("Register");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(Registration.EXIT_ON_CLOSE);

        api = API.getInstance();

        firstnameField.setBorder(new LineBorder(Color.GRAY,0, true));
        lastnameField.setBorder(new LineBorder(Color.GRAY,0, true));
        passwordField.setBorder(new LineBorder(Color.GRAY,0, true));
        specialistField.setBorder(new LineBorder(Color.GRAY,0, true));

        PatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = 1; //For Patient
                specialistField.setEnabled(false);
            }
        });
        AdministratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = 2; //For Administrator
                specialistField.setEnabled(false);
            }
        });
        SpecialistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = 3; //For Specialist
                specialistField.setEnabled(true);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstname, lastname, specialistType = "";
                char[] password;
                int medID;

                try{
                    firstname = firstnameField.getText();
                    lastname = lastnameField.getText();
                    password = passwordField.getPassword();
                }catch(Exception error){
                    showMessageDialog(null, "Enter Information");
                    return;
                }

                if(selection == 0){ //NOTHING
                    showMessageDialog(null, "Select Account Type!");
                }
                else if(selection == 1){ //Patient
                    medID = 1000 + api.getPatients().size();
                    Patient patient = new Patient(1000 + api.getPatients().size(), firstname, lastname, password);
                    api.getPatients().add(patient);
                    showMessageDialog(null, "Your MedID is " + medID);
                }
                else if(selection == 2){
                    medID = 100 + api.getAdministrators().size();
                    Administrator admin = new Administrator(100 + api.getAdministrators().size(), firstname, lastname, password);
                    api.getAdministrators().add(admin);
                    showMessageDialog(null, "Your MedID is " + medID);
                }
                else if(selection == 3){

                    try{
                        specialistType = specialistField.getText();
                        medID = 1000 + api.getSpecialists().size();
                        Specialist specialist = new Specialist(1000 + api.getSpecialists().size(), firstname, lastname, password, specialistType);
                        api.getSpecialists().add(specialist);
                        showMessageDialog(null, "Your MedID is " + medID);
                    }catch(Exception error){
                        showMessageDialog(null, "Enter Specialist Type");
                        return;
                    }
                }

                SignIn signIn = new SignIn();
                signIn.setVisible(true);
                setDefaultCloseOperation(Registration.HIDE_ON_CLOSE);
                dispose();
            }
        });
    }
}

//Disable Specialist Type If Specialist NOT Selected
