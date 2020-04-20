import javafx.geometry.HorizontalDirection;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class DatePicker {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    String day = "";
    JLabel monthName;
    JDialog datePickerDialog;
    JButton[] calenderButtons ;

    public DatePicker(JPanel parentFrame) {
        calenderButtons= new JButton[49];
        datePickerDialog = new JDialog();
        datePickerDialog.setModal(true);
        String[] dayHeaders = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel dateShowPanel = new JPanel(new GridLayout(7, 7));
        for(int button = 0; button <49;button++) {
            final int selection = button;
            calenderButtons[button]=new JButton();
            calenderButtons[button].setSize(25,25);
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
                        datePickerDialog.dispose();
                    }
                });
            }
            dateShowPanel.add(calenderButtons[button]);
        }
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        JButton previousMonth = new JButton("<<Prev");
        monthName = new JLabel("Month",JLabel.CENTER);
        JButton nextMonth = new JButton("Next>>");
        previousMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                month--;
                displayDate();
            }
        });
        nextMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        bottomPanel.add(previousMonth);
        bottomPanel.add(monthName);
        bottomPanel.add(nextMonth);
        datePickerDialog.add(dateShowPanel, BorderLayout.CENTER);
        datePickerDialog.add(bottomPanel, BorderLayout.NORTH);
        datePickerDialog.pack();
        datePickerDialog.setLocationRelativeTo(parentFrame);
        displayDate();
        datePickerDialog.setVisible(true);
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
        monthName.setText(sdf.format(calendar.getTime()));
        datePickerDialog.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals(""))return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}