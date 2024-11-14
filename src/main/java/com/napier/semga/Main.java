package com.napier.semga;

import java.sql.*;
import java.util.ArrayList;

/***
 * the main class of the application
 */
public class Main
{
    /***
     * functionality changes frequently, depending on what's being implemented at the time
     * @param args
     */
    public static void main(String[] args)
    {
        Main m = new Main();

        m.connect();

        ArrayList<City> cities = m.getCitiesByDistrict("California");

        m.printCities(cities);
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
            System.out.println(String.format("%-10s %-20s %-10s %-20s %-20s", "Id", "Name", "Country Code", "Population", "District"));

            //  print cities
            for (City city : cities){
                if (city == null){
                    continue;
                }
                else {
                    System.out.println(String.format("%-10s %-20s %-10s %-20s %-20s", city.id, city.name, city.countryCode, city.population, city.district));
                }
            }
        }
    }

    /****
     * gets all cities from the database
     * @return array list containing all cities
     */
    public ArrayList<City> getAllCities(){
        try{
            ArrayList<City> cities = new ArrayList<City>();

            Statement stmt = con.createStatement();

            String strSelect = "SELECT * FROM city " +
                    "ORDER BY city.Population DESC";

            ResultSet rslt = stmt.executeQuery(strSelect);

            while (rslt.next()) {
                City city = new City();
                city.id = rslt.getInt("ID");
                city.name = rslt.getString("Name");
                city.countryCode = rslt.getString("CountryCode");
                city.district = rslt.getString("District");
                city.population = rslt.getInt("Population");
                cities.add(city);
            }

            return cities;
        }
        catch (Exception e){
            System.out.println("Error getting cities from DB");
            System.out.println(e.getMessage());
            return null;
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
                country.name = rslt.getString("country.Name");
                country.continent = rslt.getString("country.Continent");
                country.region = rslt.getString("country.Region");
                country.surfaceArea = rslt.getDouble("country.SurfaceArea");
                country.indepYear = rslt.getInt("country.IndepYear");
                country.population = rslt.getInt("country.Population");
                country.lifeExpectancy = rslt.getDouble("country.LifeExpectancy");
                country.gnp = rslt.getDouble("country.GNP");
                country.gnpOld = rslt.getDouble("country.GNPOld");
                country.localName = rslt.getString("country.LocalName");
                country.governmentForm = rslt.getString("country.GovernmentForm");
                country.headOfState = rslt.getString("country.HeadOfState");
                country.capital = rslt.getInt("country.Capital");
                country.code2 = rslt.getString("country.Code2");
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

    /***
     *
     * Gets all capital Cities from city table
     */

    public ArrayList<City> getAllCapitalCities() {

        try {
            ArrayList<City> capitialCities = new ArrayList<>();
            Statement stmt = con.createStatement();

            String strSelectCapitial = "SELECT * FROM city WHERE District='Capital Region'" + " ORDER BY Population DESC";
            ResultSet rslt = stmt.executeQuery(strSelectCapitial);

            while (rslt.next()) {
                City city = new City();
                city.id = rslt.getInt("city.Id");
                city.name = rslt.getString("city.Name");
                city.countryCode = rslt.getString("city.CountryCode");
                city.population = rslt.getInt("city.Population");
                capitialCities.add(city);

            }
            return capitialCities;
        }
        catch (SQLException sqle) {
            System.out.println("Error getting Capital cities from DB");
            System.out.println(sqle.getMessage());
            return null;
        }

    }

    /***
     *
     * Prints capitialCities
     */
    public void printCapitalCities(ArrayList<City> capitialCities){
        if (capitialCities == null){
            System.out.println("No Capital cities found");
        }
        // print header
        else{
            System.out.println(String.format("%-8s %-10s %-10s %-10s %-10s %-5s %-3s %-3s %-7s %-13s %-5s %-3s %-2s", "city.ID", "Name", "CountryCode", "District", "Population"));
        }
        // print Capital Cities
        for (City city : capitialCities){
            if (city == null){
                continue;
            }
            else {
                System.out.println(String.format("%-8s %-10s %-10s %-10s %-10s %-5s %-3s %-3s %-7s %-13s %-5s %-3s %-2s", city.id, city.name, city.countryCode, city.district, city.population));
            }
        }
    }

    /***
     * Makes a request for a list of countries using a filter provided in the parameter
     * @param field The field that will be used to filter the query
     * @param filter The filter
     * @return array list of the results of the query
     */
    public ArrayList<Country> getCountriesByFilter(String field, String filter){
        try {
            ArrayList<Country> countries = new ArrayList<Country>();

            String strSelect = "SELECT * FROM country WHERE Continent = ? ORDER BY Population DESC";
            System.out.println(strSelect);

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM country WHERE " + field + " = ? ORDER BY country.Population DESC");
            stmt.setString(1, filter);

            ResultSet rslt = stmt.executeQuery();

            while (rslt.next()) {
                Country country = new Country();
                country.code = rslt.getString("country.Code");
                country.name = rslt.getString("country.Name");
                country.continent = rslt.getString("country.Continent");
                country.region = rslt.getString("country.Region");
                country.surfaceArea = rslt.getDouble("country.SurfaceArea");
                country.indepYear = rslt.getInt("country.IndepYear");
                country.population = rslt.getInt("country.Population");
                country.lifeExpectancy = rslt.getDouble("country.LifeExpectancy");
                country.gnp = rslt.getDouble("country.GNP");
                country.gnpOld = rslt.getDouble("country.GNPOld");
                country.localName = rslt.getString("country.LocalName");
                country.governmentForm = rslt.getString("country.GovernmentForm");
                country.headOfState = rslt.getString("country.HeadOfState");
                country.capital = rslt.getInt("country.Capital");
                country.code2 = rslt.getString("country.Code2");
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
     * Makes a request for a list of cities using a filter provided in the parameter
     * @param field The field that will be used to filter the query
     * @param filter The filter
     * @return array list of the results of the query
     */
    public ArrayList<City> getCitiesByFilters(String field, String filter){
        try{
            ArrayList<City> cities = new ArrayList<City>();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE " + field + " = ? ORDER BY city.Population DESC");
            stmt.setString(1, filter);

            ResultSet rslt = stmt.executeQuery();

            while (rslt.next()) {
                City city = new City();
                city.id = rslt.getInt("ID");
                city.name = rslt.getString("Name");
                city.countryCode = rslt.getString("CountryCode");
                city.district = rslt.getString("District");
                city.population = rslt.getInt("Population");
                cities.add(city);
            }

            return cities;
        }
        catch (Exception e){
            System.out.println("Error getting cities from DB");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /***
     * Makes a request for a list of capital cities using a filter provided in the parameter
     * @param field The field that will be used to filter the query
     * @param filter The filter
     * @return array list of the results of the query
     */
    public ArrayList<City> getCapitalCitiesByFilters(String field, String filter){
        try{
            ArrayList<City> cities = new ArrayList<City>();

            Statement stmt = con.createStatement();

            String strSelect = "SELECT * FROM city " +
                    "WHERE " + field + " = " + filter + " " +
                    "ORDER BY city.Population DESC";

            ResultSet rslt = stmt.executeQuery(strSelect);

            while (rslt.next()) {
                City city = new City();
                city.id = rslt.getInt("ID");
                city.name = rslt.getString("Name");
                city.countryCode = rslt.getString("CountryCode");
                city.district = rslt.getString("District");
                city.population = rslt.getInt("Population");
                cities.add(city);
            }

            return cities;
        }
        catch (Exception e){
            System.out.println("Error getting cities from DB");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a list of all countries from a continent that is provided in the parameter
     * @param continent the continent
     * @return array list of all the countries of the given continent
     */
    public ArrayList<Country> getCountriesByContinent(String continent){
        return getCountriesByFilter("country.Continent", continent);
    }

    /**
     * Retrieves a list of all countries from a region that is provided in the parameter
     * @param region the continent
     * @return array list of all the countries of the given region
     */
    public ArrayList<Country> getCountriesByRegion(String region){
        return getCountriesByFilter("country.Region", region);
    }

    /**
     * Retrieves a list of all cities from a continent that is provided in the parameter
     * @param continent the continent
     * @return array list of all the cities of the given continent
     */
    public ArrayList<City> getCitiesByContinent(String continent){
        return getCitiesByFilters("country.Continent", continent);
    }

    /**
     * Retrieves a list of all cities from a region that is provided in the parameter
     * @param region the region
     * @return array list of all the cities of the given region
     */
    public ArrayList<City> getCitiesByRegion(String region){
        return getCitiesByFilters("country.Region", region);
    }

    /**
     * Retrieves a list of all cities from a country that is provided in the parameter using it's country code
     * @param countryCode the country requested
     * @return array list of all the cities of the given country
     */
    public ArrayList<City> getCitiesByCountry(String countryCode){
        return getCitiesByFilters("city.countryCode", countryCode);
    }

    /**
     * Retrieves a list of all cities from a district that is provided in the parameter
     * @param district the district requested
     * @return array list of all the cities of the given country
     */
    public ArrayList<City> getCitiesByDistrict(String district){
        return getCitiesByFilters("city.District", district);
    }

}