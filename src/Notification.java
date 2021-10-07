import javax.swing.*;

public class Notification extends JFrame{
    private JLabel notificationLabel;
    private JPanel main;

    Notification(){
        add(main);
        setTitle("Notification");
        setSize(800, 100);
    }

    public JLabel getNotificationLabel(){
        return notificationLabel;
    }
}
