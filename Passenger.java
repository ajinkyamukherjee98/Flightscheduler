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
public class Passenger {
     private String passengerName;
    private PreparedStatement insertPassenger;
    private static PreparedStatement selectAllPassengers;
    private static DBConnection dbConnection;
    private static Connection connection;
    
    public Passenger(String apassengerName)
    {
        passengerName = apassengerName;
    }
    
    public String getPassenger()
    {
        return passengerName;
    }
    
    public void setPassenger(String apassengerName)
    {
        passengerName = apassengerName;
    }
    
    public String toString()
    {
        return passengerName;
    }
    
    public Passenger()
    {
        
    }
    
    public int addPassenger(String aname)
    {
        int result = 0;
        try
        {
            connection = dbConnection.getDBConnection();
            insertPassenger = connection.prepareStatement("INSERT INTO PASSENGER"+"(PASSENGERNAME)"+"VALUES (?)");
            insertPassenger.setString(1, aname);
            result = insertPassenger.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            dbConnection.close();
        }
        return result;
    }
        
    public static List< Passenger > getAllPassenger() throws SQLException 
    {
        connection = dbConnection.getDBConnection();
        selectAllPassengers = connection.prepareStatement("SELECT * FROM PASSENGER");
        List<Passenger> results = null;
        ResultSet resultSet = null;
        try
        {
            resultSet = selectAllPassengers.executeQuery();
            results = new ArrayList< Passenger >();
            while(resultSet.next())
            {
                results.add(new Passenger(resultSet.getString(1)));
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
}
