import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventManager extends JFrame {
    private JPanel contentPane;
    private JTextField eventName = null;
    private JTextArea eventDetails = null;
    private JButton saveEvent;
    private JButton deleteEvent;
    private JComboBox<String> startTimePicker=null;
    private JTextField startDate=null;
    private JLabel errorLabel=null;

    public EventManager() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName("Add Event");
        setBounds(450, 190, 500, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        addFields();
        saveToDatabase();
    }

    private void addFields() {
        JLabel eName = new JLabel("Event Name");
        eName.setBounds(50,10,250,50);
        eName.setFont(new Font("Times New Roman",Font.PLAIN,20));
        contentPane.add(eName);

        eventName = new JTextField();
        eventName.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        eventName.setBounds(50, 65, 350, 50);
        contentPane.add(eventName);

        JLabel eventDate = new JLabel("Event Date");
        eventDate.setBounds(50,120,100,50);
        eventDate.setFont(new Font("Times New Roman",Font.PLAIN,20));
        contentPane.add(eventDate);

        String currentDate = Utils.currentDate();
        startDate = new JTextField(currentDate, 20);
        startDate.setBounds(160,120,150,50);
        startDate.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        contentPane.add(startDate);

        JButton selectDate = new JButton("Select");
        selectDate.setBounds(310,120,100,50);
        contentPane.add(selectDate);

        selectDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                startDate.setText(new DatePicker(contentPane).setPickedDate());
            }
        });

        JLabel eventTime = new JLabel("Event Time");
        eventTime.setBounds(50,180,100,50);
        eventTime.setFont(new Font("Times New Roman",Font.PLAIN,20));
        contentPane.add(eventTime);

        String[] timings = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
                "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        startTimePicker = new JComboBox<String>(timings);
        startTimePicker.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        startTimePicker.setBounds(160,180,100,50);
        contentPane.add(startTimePicker);

        JLabel eventDetailsLabel = new JLabel("Event Details");
        eventDetailsLabel.setBounds(50,240,150,50);
        eventDetailsLabel.setFont(new Font("Times New Roman",Font.PLAIN,20));
        contentPane.add(eventDetailsLabel);

        eventDetails = new JTextArea();
        eventDetails.setBounds(50, 300, 350, 100);
        eventDetails.setFont(new Font("Times New Roman",Font.PLAIN,25));
        eventDetails.setFocusable(true);
        contentPane.add(eventDetails);

        saveEvent = new JButton("Save");
        saveEvent.setBounds(50,410,100,50);
        contentPane.add(saveEvent);


        errorLabel = new JLabel();
        errorLabel.setBounds(50,470,400,100);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        contentPane.add(errorLabel);
    }

    private boolean checkText(String text){
        if(text==null || text.isEmpty() || text.equals(""))
            return false;
        return true;
    }
    public void saveToDatabase() {
        saveEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = eventName.getText();
                if(!checkText(name)){
                    errorLabel.setText("Enter Event Name!!");
                    return;
                }
                String details = eventDetails.getText();
                if(!checkText(details)){
                    errorLabel.setText("Enter Event Details!!");
                    return;
                }
                String sDate = startDate.getText();
                if(!checkText(sDate)){
                    errorLabel.setText("Enter valid date!");
                    return;
                }
                String sTime = String.valueOf(startTimePicker.getSelectedItem());
                DatabaseHandler.insertEvent(name,details,sDate,sTime,saveEvent);
            }
        });
    }
}
