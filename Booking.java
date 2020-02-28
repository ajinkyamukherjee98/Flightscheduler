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
public class Booking {
    private String passengerName;
    private Date date;
    private String flightNumber;
    private PreparedStatement insertBooking;
    private static PreparedStatement selectCustomersBooked;
    private PreparedStatement getFlightSeats;
    private static DBConnection dbConnection;
    private static Connection connection;
    
    
    
    public Date getDay()
    {
        return date;
    }
      
    
    public String getFlightNumber()
    {
        return flightNumber;
    }
    
    public String getPassenger()
    {
        return passengerName;
    }
    public Booking() 
    {
        
        
    }
    
    public Booking(String aname, String aflightNo, Date adate) 
    {
        passengerName = aname;
        date = adate;
        flightNumber =aflightNo;
        
    }
    
    public int getSeatsBooked(String flightName, Date adate)
    {
        int seatsBooked = 0;
        ResultSet resultSet;
        connection = dbConnection.getDBConnection();
        try
        {
            connection = dbConnection.getDBConnection();
        getFlightSeats = connection.prepareStatement("select count(Number) from booking where Number = ? and date = ?");
             
            getFlightSeats.setString(1, flightName); getFlightSeats.setDate(2, adate); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            seatsBooked = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatsBooked;
    }
    
    public String ToString()
    {
        return passengerName;
    }
    
    public static List< Booking > getCustomersBooked(String flightName, Date adate) throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectCustomersBooked = connection.prepareStatement("SELECT * FROM BOOKING WHERE NUMBER=? AND DATE=?");
        List<Booking> results = null;
        ResultSet resultSet = null;
        try
        {
            
            selectCustomersBooked.setString(1, flightName);
            selectCustomersBooked.setDate(2, adate);
            resultSet = selectCustomersBooked.executeQuery();
            results = new ArrayList< Booking >();
            while(resultSet.next())
            {
                results.add(new Booking(resultSet.getString(1),resultSet.getString(3),resultSet.getDate(2)));
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
    
    public int addBooking(String aname, String aflightNumber, Date adate)
    {
        int result = 0;
        
        try
        {
            connection = dbConnection.getDBConnection();
            insertBooking = connection.prepareStatement("INSERT INTO BOOKING "+"(Name,Date,Number)"+"VALUES (?,?,?)");
            
            insertBooking.setString(1, aname);
            insertBooking.setString(3, aflightNumber);
            insertBooking.setDate(2, adate);
            result = insertBooking.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return result;
    }
}
