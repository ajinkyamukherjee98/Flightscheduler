/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerdbAjinkyaMukherjeeaam5802;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */
public class Flight {
     private String flightNumber;
    private int seats;
    
    private static PreparedStatement selectAllFlights;
    private static PreparedStatement getFlightSeats;
    private static DBConnection dbConnection;
    private static Connection connection;
    
    public String toString()
    {
        return flightNumber;
    }
    
    public Flight()
    {
        
    }
    
    public int getSeatsAvailable(String flightName)
    {
        int seatsAvailable = 0;
        ResultSet resultSet = null;
        connection = dbConnection.getDBConnection();
        try
        {
            
            getFlightSeats = connection.prepareStatement("SELECT SEATS FROM FLIGHT WHERE Number = ? "); 
            getFlightSeats.setString(1, flightName); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            seatsAvailable = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatsAvailable;
    }
    
    public static List< Flight > getAllFlights() throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectAllFlights = connection.prepareStatement("SELECT * FROM FLIGHT");
        List<Flight> results = null;
        ResultSet resultSet = null;
        try
        {
            resultSet = selectAllFlights.executeQuery();
            results = new ArrayList< Flight >();
            while(resultSet.next())
            {
                results.add(new Flight(resultSet.getString(1),resultSet.getInt(2)));
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
    
    public Flight(String aflightNumber, int aseats)
    {
        flightNumber = aflightNumber;
        seats = aseats;
    }
    
    public void addFlight(String aflightNumber, int aseats)
    {
        flightNumber = aflightNumber;
        seats = aseats;
    }
    
    public String getFlightNumber()
    {
        return flightNumber;
    }
    
    public int getSeats()
    {
        return seats;
    }
}
