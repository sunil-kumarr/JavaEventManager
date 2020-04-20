import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String currentDate(){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = java.util.Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }
    public  static Timestamp getTimeStamp(String eventTime){
        SimpleDateFormat datetimeFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date date = null;
        try {
            date = datetimeFormatter.parse(eventTime);
            Timestamp eventTimeStamp = new Timestamp(date.getTime());
            return eventTimeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
}

