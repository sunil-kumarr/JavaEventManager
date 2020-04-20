import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainCalendar extends JFrame implements DataListener {
    private static JPanel contentPane;
    private String currentDate;
    private JPanel panel;
    private JList<String> list;
    private JLabel EventList=null;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DatabaseHandler.createDatabase();
                    DatabaseHandler.createTable();
                    MainCalendar frame = new MainCalendar();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public MainCalendar() {
        currentDate = Utils.currentDate();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(screenSize.width / 3, screenSize.height);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        CalendarDatePanel myCalendar = new CalendarDatePanel(contentPane,this);


        EventList = new JLabel("Event List");
        EventList.setBounds(50, 240, 300, 40);
        EventList.setFont(new Font("Times New Roman", Font.BOLD, 30));
        contentPane.add(EventList);

        JButton addEvent = new JButton("+");
        addEvent.setBounds(350, 240, 45, 40);
        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventManager manager = new EventManager();
                manager.setVisible(true);
            }
        });
        contentPane.add(addEvent);

        panel = new JPanel(new BorderLayout());
        panel.setBounds(50, 300, 350, 300);

        list = new JList<String>();
        list.setFont(new Font("Times New Roman",Font.PLAIN,25));

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        panel.add(scrollPane);
        contentPane.add(panel);
        updateEventList();
    }

    private void updateEventList() {
        List<EventModel> myEventList = DatabaseHandler.fetchEvents(currentDate);
        if (!myEventList.isEmpty()) {
            int len = myEventList.size();
            String[] myList = new String[len];
            for (int i = 0; i < len; i++) {
                EventModel model = myEventList.get(i);
                myList[i] =" (" + model.getEventName() + "@ "+model.getEventTime() +") " + model.getEventDetails();
            }
            list.setListData(myList);
            scrollPane.setVisible(true);
        }else {
            scrollPane.setVisible(false);
            JLabel NoEvent = new JLabel("NO Events Today");
            NoEvent.setFont(new Font("Times New Roman", Font.BOLD, 45));
            panel.add(NoEvent);
        }
    }

    @Override
    public void setCurrentDate(String date) {
        currentDate = date;
        EventList.setText("Events@ "+currentDate);
        updateEventList();
    }
}
