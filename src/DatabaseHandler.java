
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASS = "root";
    static final String DB_NAME="event_database";

    public static void createDatabase(){
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS event_database";
            stmt.executeUpdate(sql);
        }catch (SQLException exp){

        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            } catch(SQLException se2){ }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static void createTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection(DB_URL+DB_NAME, USER, PASS);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS events " +
                    "( name VARCHAR(255), " +
                    "  details VARCHAR(1000), " +
                    "  date VARCHAR(255), " +
                    "  time VARCHAR(255) " +
                    " )";
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static void insertEvent(String eventName,String eventDetails,String eventDate,String eventTime,JButton saveEvent){
        try {
            Connection connection = DriverManager.getConnection(DB_URL+DB_NAME, USER, PASS);
            String query = "INSERT INTO events values('" + eventName + "','" + eventDetails + "','" + eventDate + "','" +
                            eventTime + "')";
            Statement sta = connection.createStatement();
            int x = sta.executeUpdate(query);
            if (x == 0) {
                JOptionPane.showMessageDialog(saveEvent, "Event exists already!!");
            } else {
                JOptionPane.showMessageDialog(saveEvent, "Event Saved Successfully");
            }
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static List<EventModel> fetchEvents(String selectedDate){
        Connection conn = null;
        Statement stmt = null;
        List<EventModel> eventList = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(DB_URL+DB_NAME, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM events where date = '"+selectedDate+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String eventName  = rs.getString("name");
                String eventDetails = rs.getString("details");
                String eventDate = rs.getString("date");
                String eventTime = rs.getString("time");
                eventList.add(new EventModel(eventName,eventDetails,eventDate,eventTime));
            }
            rs.close();
        }catch(SQLException se){

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se) {
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return eventList;
    }
}
