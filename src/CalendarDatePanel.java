import javafx.geometry.HorizontalDirection;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class CalendarDatePanel {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    String day = "";
    JLabel monthName;
    JButton[] calenderButtons ;
    private DataListener myListener = null;

    public CalendarDatePanel(JPanel parentPanel,DataListener myListener) {
        this.myListener = myListener;

        calenderButtons= new JButton[49];
        String[] dayHeaders = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };
        JPanel dateShowPanel = new JPanel(new GridLayout(7, 7));
        dateShowPanel.setBounds(20,30,400,200);
        for(int button = 0; button <49;button++) {
            final int selection = button;
            calenderButtons[button]=new JButton();
            calenderButtons[button].setSize(20,20);
            calenderButtons[button].setFocusPainted(true);
            calenderButtons[button].setBackground(Color.WHITE);
            if (button < 7) {
                calenderButtons[button].setText(dayHeaders[button]);
                calenderButtons[button].setForeground(Color.BLACK);
            }
            else {
                calenderButtons[button].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = calenderButtons[selection].getActionCommand();
                        for(int i=7;i<calenderButtons.length;i++){
                            calenderButtons[i].setBackground(Color.WHITE);
                        }
                        calenderButtons[selection].setBackground(Color.RED);
                       setPickedDate();
                    }
                });
            }
            dateShowPanel.add(calenderButtons[button]);
        }

        JButton previousMonth = new JButton("<");
        previousMonth.setBounds(50,10,45,20);
        previousMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                month--;
                displayDate();
            }
        });
        parentPanel.add(previousMonth);

        monthName = new JLabel("Month",JLabel.CENTER);
        monthName.setBounds(95,10,150,20);
        parentPanel.add(monthName);

        JButton nextMonth = new JButton(">");
        nextMonth.setBounds(245,10,45,20);
        nextMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        parentPanel.add(nextMonth);

        parentPanel.add(dateShowPanel);
        displayDate();
    }

    public void displayDate() {
        for (int x = 7; x < calenderButtons.length; x++)
            calenderButtons[x].setText("");
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, 1);
        int dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            calenderButtons[x].setText("" + day);
        String currentdate = sdf.format(calendar.getTime());
        monthName.setText(currentdate);
    }

    public void setPickedDate() {
        String currentDate = Utils.currentDate();
        if (! day.equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(year, month, Integer.parseInt(day));
            currentDate = sdf.format(cal.getTime());
        }
        myListener.setCurrentDate(currentDate);
    }
}