/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerdbAjinkyaMukherjeeaam5802;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */
public class Waitlist {
    private String passengerName;
    private Date date;
    private String flightNumber;
    private PreparedStatement insertWaitlist;
    private PreparedStatement getPosition;
    private int position;
    private static DBConnection dbConnection;
    private static PreparedStatement selectCustomersBooked;
    private static Connection connection;
    
    
    public void addWaitList(String apassengerName, Date adate, String aflightNumber )
    {
        passengerName = apassengerName;
        date = adate;
        flightNumber = aflightNumber;
    }
    
    public Date getDay()
    {
        return date;
    }
      
    
    public String getFlightNumber()
    {
        return flightNumber;
    }
    
    public int getWaitlistPosition(String flightName, Date adate)
    {
        int position = 1;
        ResultSet resultSet;
        connection = dbConnection.getDBConnection();
        try
        {
            connection = dbConnection.getDBConnection();
        getPosition = connection.prepareStatement("SELECT COUNT(Number) from WAITLIST where Number = ? and date = ?");
             
            getPosition.setString(1, flightName); getPosition.setDate(2, adate); 
            resultSet = getPosition.executeQuery(); 
            resultSet.next(); 
            position = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return position+1;
    }
    
    public int addBooking(String aname, String aflightNumber, Date adate)
    {
        int result = 0;
        int r=0;
        try
        {
            connection = dbConnection.getDBConnection();
            insertWaitlist = connection.prepareStatement("INSERT INTO WAITLIST "+"(Name,Date,Number,Position)"+"VALUES (?,?,?,?)");
            r = getWaitlistPosition(aflightNumber,adate);
            insertWaitlist.setString(1, aname);
            insertWaitlist.setString(3, aflightNumber);
            insertWaitlist.setDate(2, adate);
            insertWaitlist.setInt(4, r);
            result = insertWaitlist.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return r;
    }
    
    public Waitlist(String aname, String aflight, Date adate, int aposition)
    {
        passengerName=aname;
    date=adate;
    flightNumber=aflight;
    position=aposition;
    }
    
    public String ToString()
    {
        return ""+passengerName+","+flightNumber+","+position;
    }
    
    public static List< Waitlist > getCustomersBooked(Date adate) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomersBooked = connection.prepareStatement("SELECT * FROM WAITLIST WHERE DATE=?");
        List<Waitlist> results = null;
        ResultSet resultSet = null;
        try
        {
            
            
            selectCustomersBooked.setDate(1, adate);
            resultSet = selectCustomersBooked.executeQuery();
            results = new ArrayList< Waitlist >();
            while(resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString(1),resultSet.getString(3),resultSet.getDate(2),resultSet.getInt(4)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                dbConnection.close();
            }
        }
        return results;        
    }
    
    public Waitlist()
    {
        
    }
    
    public String getPassenger()
    {
        return passengerName;
    }
}
