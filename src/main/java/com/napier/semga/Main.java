package com.napier.semga;

import java.sql.*;
import java.util.ArrayList;

public class Main
{
    /***
     * currently just connects to the mysql database
     * @param args
     */
    public static void main(String[] args)
    {
        Main m = new Main();

        m.connect();

        ArrayList<Country> countries = m.getAllCountries();

        m.printCountries(countries);
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;
    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }
        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }
    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /***
     * Prints a list of cities
     * @param cities the list of cities to be printed
     */
    public  void printCities(ArrayList<City> cities){
        //  check if cities is null
        if (cities == null){
            System.out.println("No cities found");
        }
        else{
            //  print header
            System.out.println(String.format("%-10s %-20s %-10s %-20s", "Id", "Name", "Country Code", "Population"));

            //  print cities
            for (City city : cities){
                if (city == null){
                    continue;
                }
                else {
                    System.out.println(String.format("%-10s %-20s %-10s %-20s", city.id, city.name, city.countryCode, city.population));
                }
            }
        }
    }

    /***
     * gets all the countries from the database
     * @return array list containing countries
     */
    public ArrayList<Country> getAllCountries(){
        try {
            ArrayList<Country> countries = new ArrayList<Country>();
            Statement stmt = con.createStatement();

            String strSelect = "SELECT * FROM country " +
                    "ORDER BY country.Population DESC";

            ResultSet rslt = stmt.executeQuery(strSelect);

            while (rslt.next()) {
                Country country = new Country();
                country.code = rslt.getString("country.Code");
                countries.add(country);
            }



            return countries;
        }
        catch (SQLException sqle) {
            System.out.println("Error getting countries from DB");
            System.out.println(sqle.getMessage());
            return null;
        }
    }

    /***
     * Prints a list of countries
     * @param countries the list of countries to be printed
     */
    public  void printCountries(ArrayList<Country> countries){
        //  check if countries is null
        if (countries == null){
            System.out.println("No countries found");
        }
        else{
            //  print header
            System.out.println(String.format("%-8s %-10s %-10s %-10s %-10s %-5s %-3s %-3s %-7s %-13s %-5s %-3s %-2s", "Code", "Name", "Continent", "Region", "Population", "LifeExpectancy", "GNP", "GNP(old)", "localName", "Government Form", "Head Of State", "Capital", "Code 2"));

            //  print countries
            for (Country country : countries){
                if (country == null){
                    continue;
                }
                else {
                    System.out.println(String.format("%-8s %-10s %-10s %-10s %-10s %-5s %-3s %-3s %-7s %-13s %-5s %-3s %-2s", country.code, country.name, country.continent, country.region, country.population, country.lifeExpectancy, country.gnp, country.gnpOld, country.localName, country.governmentForm, country.headOfState, country.capital, country.code2));
                }
            }
        }
    }
}