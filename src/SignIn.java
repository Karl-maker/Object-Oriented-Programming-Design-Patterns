import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignIn extends JFrame{
    private JPanel mainPanel;
    private JPanel topHalf;
    private JPanel bottomHalf;
    private JLabel headerLabel;
    private JPanel doctorPanel;
    private JPanel patientPanel;
    private JButton patientButton;
    private JButton doctorButton;
    private JLabel promptLabel;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JButton loginButton;
    private JButton adminButton;
    private JPanel adminPanel;
    private ImageIcon doctor_icon, patient_icon, admin_icon;
    private String accountType;

    API api;

    public SignIn(){

        add(mainPanel);
        setTitle("Sign In");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(SignIn.EXIT_ON_CLOSE);

        api = API.getInstance();

        doctor_icon = new ImageIcon("resources/doctor_icon.png");
        patient_icon = new ImageIcon("resources/patient_icon.png");
        admin_icon = new ImageIcon("resources/admin_icon.png");

        doctorButton.setIcon(doctor_icon);
        patientButton.setIcon(patient_icon);
        adminButton.setIcon(admin_icon);

        idField.setBorder(new LineBorder(Color.GRAY,0, true));
        passwordField.setBorder(new LineBorder(Color.GRAY,0, true));

        doctorPanel.setBorder(new LineBorder(Color.GRAY,0, true));
        patientPanel.setBorder(new LineBorder(Color.GRAY,0, true));
        adminPanel.setBorder(new LineBorder(Color.GRAY,0, true));

        promptLabel.setText("Welcome!");
        promptLabel.setForeground(Color.GRAY);

        patientPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                selectPatient();
            }
        });
        doctorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                selectDoctor();
            }
        });
        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPatient();
            }
        });
        doctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDoctor();
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAdmin();
            }
        });
        adminPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                selectAdmin();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String error;
                try{
                    Home home = new Home(accountType, api.authenticateUser(accountType, Integer.parseInt(idField.getText()), passwordField.getPassword()));

                    home.setVisible(true);
                    setDefaultCloseOperation(SignIn.HIDE_ON_CLOSE);
                    dispose();
                }catch(Exception error){
                    //promptLabel.setText("Credentials Do Not Match Our Database");
                    promptLabel.setText(error.toString());
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registration register = new Registration();
                register.setVisible(true);
                setDefaultCloseOperation(SignIn.HIDE_ON_CLOSE);
                dispose();
            }
        });
    }
    public void selectDoctor(){
        patientPanel.setBorder(new LineBorder(Color.green,0));
        doctorPanel.setBorder(new LineBorder(Color.green,1));
        adminPanel.setBorder(new LineBorder(Color.blue,0));
        promptLabel.setText("Hello Doctor");
        this.accountType = "Specialist";
    }
    public void selectPatient(){
        patientPanel.setBorder(new LineBorder(Color.green,1));
        doctorPanel.setBorder(new LineBorder(Color.green,0));
        adminPanel.setBorder(new LineBorder(Color.blue,0));
        promptLabel.setText("Hello Patient");
        this.accountType = "Patient";
    }

    public void selectAdmin(){
        patientPanel.setBorder(new LineBorder(Color.green,0));
        doctorPanel.setBorder(new LineBorder(Color.green,0));
        adminPanel.setBorder(new LineBorder(Color.yellow,1));
        promptLabel.setText("Hello Administrator");
        this.accountType = "Administrator";
    }
}
