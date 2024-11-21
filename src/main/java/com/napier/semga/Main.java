package com.napier.semga;

import javax.swing.plaf.synth.Region;
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

        ArrayList<Country> countries = m.getAllCountries();
        ArrayList<Population> populations = m.getAllCountryPopulation();


        m.printCountries(countries);
        m.printPopulation(populations);

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
     *
     * Gets all Population from continent, region and country table
     * All Countries Population
     */

    public ArrayList<Population> getAllCountryPopulation() {

        try {
            ArrayList<Population> CountriesPopulation = new ArrayList<>();
            Statement stmt = con.createStatement();

            String strSelectPopulation = "SELECT country.name,totalPopulation,cityPopulation,totalPopulation- cityPopulation as nonCityPopulation " +
                    "from country,(select country.code as code, Population as totalPopulation from country) as r1 join (select countryCode as code,sum(city.Population) " +
                    "as cityPopulation from city group by countryCode) as r2 on r1.code=r2.code " +
                    "where country.code=r1.code; ";
            ResultSet rslt = stmt.executeQuery(strSelectPopulation);

            while (rslt.next()) {
                Population population = new Population();
                population.totalPopulation = rslt.getInt("totalPopulation");
                population.cityPopulation = rslt.getInt("cityPopulation");
                population.nonCityPopulation = rslt.getInt("nonCityPopulation");
                population.country = rslt.getString("country.name");
                population.regions="";
                population.continents="";

                CountriesPopulation.add(population);

            }
            return CountriesPopulation;
        }
        catch (SQLException sqle) {
            System.out.println("Error getting Country population from DB");
            System.out.println(sqle.getMessage());
            return null;
        }

    }

    /***
     *
     * Prints Countries Population
     */
    public void printCountriesPopulation(ArrayList<Population> populations){
        if (populations == null){
            System.out.println("Sorry, No Population found");
        }
        // print header
        else{
            System.out.println(String.format(" %-20s %-20s %-20s %-20s", "Countries", "CityPopulation", "NonCitiesPopulation,TotalPopulation"));
        }
        // print countries Population
        assert populations != null;
        for (Population population : populations){
            if (population == null){
                continue;
            }
            else {
                System.out.println(String.format(" %-20s %-20s %-20s %-20s",population.country,population.cityPopulation,  population.nonCityPopulation, population.totalPopulation));
            }
        }
    }

    /***
     * Get all cities and non cities population from the database
     * All Regions population
     */


    public ArrayList<Population> getAllRegionsPopulation() {

        try {
            ArrayList<Population> RegionPopulation = new ArrayList<Population>();
            Statement stmt = con.createStatement();

            String strSelectPopulation = "SELECT country.name,totalPopulation,cityPopulation,totalPopulation- cityPopulation as nonCityPopulation" +
                    "from country,(select country.code as code, Population as totalPopulation from country) as r1 join (select countryCode as code,sum(city.Population)" +
                    "as cityPopulation from city group by countryCode) as r2 on r1.code=r2.code" +
                    "where country.code=r1.code";
            ResultSet rslt = stmt.executeQuery(strSelectPopulation);

            while (rslt.next()) {
                Population population = new Population();
                population.country = rslt.getString("country");
                population.cityPopulation = rslt.getInt("cityPopulation");
                population.nonCityPopulation = rslt.getInt("nonCityPopulation");
                population.totalPopulation = rslt.getInt("totalPopulation");

                RegionPopulation.add(population);

            }
            return RegionPopulation;
        }
        catch (SQLException sqle) {
            System.out.println("Error getting on country Population from DB");
            System.out.println(sqle.getMessage());
            return null;
        }

    }

    /***
     *
     * Prints All Region Population
     */
    public void printRegionPopulation(ArrayList<Population> populations){
        if (populations == null){
            System.out.println("Sorry, No Population found in this Region");
        }
        // print header
        else{
            System.out.println(String.format(" %-20s %-20s %-20s %-20s %-20s","Region", "Countries", "CityPopulation", "NonCitiesPopulation,TotalPopulation"));
        }
        // print countries Population
        assert populations != null;
        for (Population population : populations){
            if (population == null){
                continue;
            }
            else {
                System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s",population.regions,population.country,population.cityPopulation,  population.nonCityPopulation, population.totalPopulation));
            }
        }
    }

    /***
     * Get all cities and non cities population from the database
     * All Regions population
     */


    public ArrayList<Population> getAllContinentPopulation() {

        try {
            ArrayList<Population> continentPopulations = new ArrayList<Population>();
            Statement stmt = con.createStatement();

            String strSelectPopulation = "SELECT country.name,totalPopulation,cityPopulation,totalPopulation- cityPopulation as nonCityPopulation" +
                    "from country,(select country.code as code, Population as totalPopulation from country) as r1 join (select countryCode as code,sum(city.Population)" +
                    "as cityPopulation from city group by countryCode) as r2 on r1.code=r2.code" +
                    "where country.code=r1.code; ";
            ResultSet rslt = stmt.executeQuery(strSelectPopulation);
            System.out.println(rslt.next());

            while (rslt.next()) {
                Population population = new Population();
                population.regions = rslt.getString("AllRegionsPopulation");
                population.cityPopulation = rslt.getInt("cityPopulation");
                population.nonCityPopulation = rslt.getInt("nonCityPopulation");
                population.totalPopulation = rslt.getInt("totalPopulation");


                continentPopulations.add(population);

            }
            return continentPopulations;
        }
        catch (SQLException sqle) {
            System.out.println("Error getting on Region Population from DB");
            System.out.println(sqle.getMessage());
            return null;
        }

    }

    /***
     *
     * Prints All Continent Population
     */
    public void printPopulation(ArrayList<Population> populations){
        if (populations == null){
            System.out.println("Sorry, No Population found");
        }
        // print header
        else
            System.out.println(String.format(" %-20s %-20s %-20s %-20s", "Continent/Region/Country", "CityPopulation", "NonCitiesPopulation", "TotalPopulation"));
        // print countries Population
        assert populations != null;
        for (Population population : populations){
            if (population == null){
                continue;
            }
            else {
                System.out.println(String.format("%s %s %s %d %d %d",population.country, population.continents, population.regions, population.cityPopulation,  population.nonCityPopulation, population.totalPopulation));
            }
        }
    }
}