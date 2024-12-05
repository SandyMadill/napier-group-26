package com.napier.semga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

/***
 * the main class of the application
 */
@SpringBootApplication
@RestController
public class Main
{
    /***
     * functionality changes frequently, depending on what's being implemented at the time
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length < 1) {
            connect("localhost:33060", 10000);
        } else {
            connect(args[0], Integer.parseInt(args[1]));
        }
        SpringApplication.run(Main.class, args);
    }



    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;
    /**
     * Connect to the MySQL database.
     */
    public static void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        boolean shouldWait = false;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                if (shouldWait) {
                    // Wait a bit for db to start
                    Thread.sleep(delay);
                }


                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());

                // Let's wait before attempting to reconnect
                shouldWait = true;
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect()
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
    @RequestMapping("city")
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
    @RequestMapping("country")
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
    @RequestMapping("capital-city")
    public ArrayList<City> getAllCapitalCities() {

        try {
            ArrayList<City> capitialCities = new ArrayList<>();
            Statement stmt = con.createStatement();

            String strSelectCapitial = "SELECT * FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Capital = city.ID ORDER BY city.Population DESC";
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
    @RequestMapping("population-country")
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
                population.totalPopulation = rslt.getLong("totalPopulation");
                population.cityPopulation = rslt.getLong("cityPopulation");
                population.nonCityPopulation = rslt.getLong("nonCityPopulation");
                population.name = rslt.getString("country.name");

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
            System.out.println(String.format(" %-20s %-20s %-20s %-20s", "Name", "CityPopulation", "NonCitiesPopulation,TotalPopulation"));
        }
        // print countries Population
        assert populations != null;
        for (Population population : populations){
            if (population == null){
                continue;
            }
            else {
                System.out.println(String.format(" %-20s %-20s %-20s %-20s",population.name,population.cityPopulation,  population.nonCityPopulation, population.totalPopulation));
            }
        }
    }

    /***
     * Get all cities and non cities population from the database
     * All Regions population
     */
    @RequestMapping("population-region")
    public ArrayList<Population> getAllRegionsPopulation() {

        try {
            ArrayList<Population> RegionPopulation = new ArrayList<Population>();
            Statement stmt = con.createStatement();

            String strSelectPopulation = "SELECT country.Region as Region, sum(totalPopulation) as totalPopulation, sum(cityPopulation) as cityPopulation , sum((totalPopulation - cityPopulation)) as nonCityPopulation " +
                    "from country,(select country.code as code, Population as totalPopulation, Region as Region from country) as r1 join (select countryCode as code,sum(city.Population) " +
                    "as cityPopulation from city group by countryCode) as r2 on r1.code=r2.code " +
                    "where country.code=r1.code GROUP BY Region; ";
            ResultSet rslt = stmt.executeQuery(strSelectPopulation);
            while (rslt.next()) {
                Population population = new Population();
                population.name = rslt.getString("Region");
                population.cityPopulation = rslt.getLong("cityPopulation");
                population.nonCityPopulation = rslt.getLong("nonCityPopulation");
                population.totalPopulation = rslt.getLong("totalPopulation");
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
            System.out.println(String.format("%-20s %-20s %-20s %-20s","Name", "CityPopulation", "NonCitiesPopulation,TotalPopulation"));
        }
        // print countries Population
        assert populations != null;
        for (Population population : populations){
            if (population == null){
                continue;
            }
            else {
                System.out.println(String.format("%-20s %-20s %-20s %-20s",population.name,population.cityPopulation,  population.nonCityPopulation, population.totalPopulation));
            }
        }
    }

    /***
     * Get all cities and non cities population from the database
     * All Regions population
     */
    @RequestMapping("population-continent")
    public ArrayList<Population> getAllContinentPopulation() {

        try {
            ArrayList<Population> continentPopulations = new ArrayList<Population>();
            Statement stmt = con.createStatement();

            String strSelectPopulation = "SELECT country.Continent as Continent, sum(totalPopulation) as totalPopulation, sum(cityPopulation) as cityPopulation , sum((totalPopulation - cityPopulation)) as nonCityPopulation " +
                    "from country,(select country.code as code, Population as totalPopulation, Continent as Continent from country) as r1 join (select countryCode as code,sum(city.Population) " +
                    "as cityPopulation from city group by countryCode) as r2 on r1.code=r2.code " +
                    "where country.code=r1.code GROUP BY Continent; ";
            ResultSet rslt = stmt.executeQuery(strSelectPopulation);

            while (rslt.next()) {
                Population population = new Population();
                System.out.println(rslt.getLong("totalPopulation"));
                population.name = rslt.getString("Continent");
                population.cityPopulation = rslt.getLong("cityPopulation");
                population.nonCityPopulation = rslt.getLong("nonCityPopulation");
                population.totalPopulation = rslt.getLong("totalPopulation");
                continentPopulations.add(population);
            }
            System.out.println(continentPopulations.size());
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
                System.out.println(String.format("%s %s %s %d %d %d", population.name, population.cityPopulation,  population.nonCityPopulation, population.totalPopulation));
            }
        }
    }

     /****
     * Makes a request for a list of countries using a filter provided in the parameter
     * @param field The field that will be used to filter the query
     * @param filter The filter
     * @return array list of the results of the query
     */
    public ArrayList<Country> getCountriesByFilter(String field, String filter){
        try {
            ArrayList<Country> countries = new ArrayList<Country>();

            String strSelect = "SELECT * FROM country WHERE Continent = ? ORDER BY Population DESC";

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

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Capital = city.ID AND " + field + " = ? ORDER BY city.Population DESC");
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

    /**
     * Retrieves a list of all countries from a continent that is provided in the parameter
     * @param continent the continent
     * @return array list of all the countries of the given continent
     */
    @RequestMapping("country-continent")
    public ArrayList<Country> getCountriesByContinent(@RequestParam(value = "continent") String continent){
        return getCountriesByFilter("country.Continent", continent);
    }

    /**
     * Retrieves a list of all countries from a region that is provided in the parameter
     * @param region the continent
     * @return array list of all the countries of the given region
     */
    @RequestMapping("country-region")
    public ArrayList<Country> getCountriesByRegion(@RequestParam(value = "region") String region){
        return getCountriesByFilter("country.Region", region);
    }

    /**
     * Retrieves a list of all cities from a continent that is provided in the parameter
     * @param continent the continent
     * @return array list of all the cities of the given continent
     */
    @RequestMapping("city-continent")
    public ArrayList<City> getCitiesByContinent(@RequestParam(value = "continent") String continent){
        return getCitiesByFilters("country.Continent", continent);
    }

    /**
     * Retrieves a list of all cities from a region that is provided in the parameter
     * @param region the region
     * @return array list of all the cities of the given region
     */
    @RequestMapping("city-region")
    public ArrayList<City> getCitiesByRegion(@RequestParam(value = "region") String region){
        return getCitiesByFilters("country.Region", region);
    }

    /**
     * Retrieves a list of all cities from a country that is provided in the parameter using it's country code
     * @param countryCode the country requested
     * @return array list of all the cities of the given country
     */
    @RequestMapping("city-country")
    public ArrayList<City> getCitiesByCountry(@RequestParam(value = "countryCode") String countryCode){
        return getCitiesByFilters("city.countryCode", countryCode);
    }

    /**
     * Retrieves a list of all cities from a district that is provided in the parameter
     * @param district the district requested
     * @return array list of all the cities of the given country
     */
    @RequestMapping("city-district")
    public ArrayList<City> getCitiesByDistrict(@RequestParam(value = "district") String district){
        return getCitiesByFilters("city.District", district);
    }

    /**
     * Retrieves a list of all capital cities from a continent that is provided in the parameter
     * @param continent the continent
     * @return array list of all the capital cities of the given continent
     */
    @RequestMapping("capital-city-continent")
    public ArrayList<City> getCapitalCitiesByContinent(@RequestParam(value = "continent") String continent){
        return getCapitalCitiesByFilters("country.Continent", continent);
    }

    /**
     * Retrieves a list of all capital cities from a region that is provided in the parameter
     * @param region the region
     * @return array list of all the capital cities of the given region
     */
    @RequestMapping("capital-city-region")
    public ArrayList<City> getCapitalCitiesByRegion(@RequestParam(value="region") String region){
        return getCapitalCitiesByFilters("country.Region", region);
    }

    /***
     * Retrieves the percentage of the population that speaks the language provided in the parameter
     * @param language
     * @return
     */
    public double getLanguagePercentage(String language){
        try{
            double population = 0;
            double worldPopulation = 0;
            ArrayList<City> cities = new ArrayList<City>();
            PreparedStatement stmt = con.prepareStatement("SELECT l.Percentage, c.Population FROM countrylanguage l INNER JOIN country c ON l.CountryCode = c.Code WHERE Language = ?");
            stmt.setString(1, language);
            ResultSet rslt = stmt.executeQuery();
            while (rslt.next()) {
                population += (rslt.getDouble("Percentage")/100)*rslt.getInt("Population");
            }
            Statement stmt2 = con.createStatement();
            ResultSet rslt2 = stmt.executeQuery("SELECT c.Population FROM country c");
            while (rslt2.next()) {
                worldPopulation += rslt2.getInt("Population");
            }
            return ((population/worldPopulation)*100);
        } catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }


    /***
     *
     * Gets top N user input and filters the country arraylist
     *
     * ***/
    @RequestMapping("country-top")
    public ArrayList<Country> getTopNCountries (@RequestParam(value="N") int topN) {
        try {
            return new ArrayList<Country>(getAllCountries().subList(0, topN));
        } catch (IndexOutOfBoundsException e) {
            return getAllCountries();
        }
    }

    /***
     * Get top N user input to filter contries by continent then display results
     */
    @RequestMapping("country-top-continent")
    public ArrayList<Country> getTopNContinentsCountries (@RequestParam(value="N") int topN, @RequestParam(value="continent") String continent) {
        try{
            return new ArrayList<Country>(getCountriesByContinent(continent).subList(0, topN));
        } catch (IndexOutOfBoundsException e) {
            return getCountriesByContinent(continent);
        }
    }

    /***
     * Get top N user input to filter contries by region then display results
     */
    @RequestMapping("country-top-region")
    public ArrayList<Country> getTopNCountriesByRegion (@RequestParam(value="N") int topN, @RequestParam(value="region") String region) {
        try {
            return new ArrayList<Country>(getCountriesByRegion(region).subList(0, topN));
        } catch (IndexOutOfBoundsException e) {
            return getCountriesByRegion(region);
        }
    }

    /***
     * Gets top N user input and filters the city arraylist
     */
    @RequestMapping("city-top")
    public ArrayList<City> getTopNCities (@RequestParam(value="N") int topN) {
            try {
                return new ArrayList<City>(getAllCities().subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getAllCities();
            }
    }

    /***
     * Get top N user input to filter cities by continent then display results
     */
    @RequestMapping("city-top-continent")
    public ArrayList<City> getTopNCitiesByContinent (@RequestParam(value="N") int topN, @RequestParam(value="continent") String continent) {
            try {
                return new ArrayList<City>(getCitiesByContinent(continent).subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getCitiesByContinent(continent);
            }
    }

    /***
     * Get top N user input to filter cities by region then display results
     */
    @RequestMapping("city-top-region")
    public ArrayList<City> getTopNCitiesByRegion (@RequestParam(value="N") int topN, @RequestParam(value="region") String region) {
            try {
                return new ArrayList<City>(getCitiesByRegion(region).subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getCitiesByRegion(region);
            }
    }

    /***
     * Get top N user input to filter cities by country code then display results
     */
    @RequestMapping("city-top-country")
    public ArrayList<City> getTopNCitiesByCountry (@RequestParam(value="N") int topN, @RequestParam(value="countryCode") String countryCode) {
            try {
                return new ArrayList<City>(getCitiesByCountry(countryCode).subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getCitiesByCountry(countryCode);
            }
    }

    /***
     * Get top N user input to filter cities by district then display results
     */
    @RequestMapping("city-top-district")
    public ArrayList<City> getTopNCitiesByDistrict (@RequestParam(value="N") int topN, @RequestParam(value="district") String district) {
            try {
                return new ArrayList<City>(getCitiesByDistrict(district).subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getCitiesByDistrict(district);
            }
    }

    /***
     * Gets top N user input and filters the capital city arraylist
     */
    @RequestMapping("capital-city-top")
    public ArrayList<City> getTopNCapitalCities (@RequestParam(value="N") int topN) {
            try {
                return new ArrayList<City>(getAllCapitalCities().subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getAllCapitalCities();
            }
    }

    /***
     * Get top N user input to filter  capital cities by continent then display results
     */
    @RequestMapping("capital-city-top-continent")
    public ArrayList<City> getTopNCapitalCitiesByContinent (@RequestParam(value="N") int topN, @RequestParam(value="continent") String continent) {
            try {
                return new ArrayList<City>(getCapitalCitiesByContinent(continent).subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getCapitalCitiesByContinent(continent);
            }
    }

    /***
     * Get top N user input to filter capital cities by region then display results
     */
    @RequestMapping("capital-city-top-region")
    public ArrayList<City> getTopNCapitalCitiesByRegion (@RequestParam(value="N") int topN, @RequestParam(value="region") String region) {
            try {
                return new ArrayList<City>(getCapitalCitiesByRegion(region).subList(0, topN));
            } catch (IndexOutOfBoundsException e) {
                return getCapitalCitiesByRegion(region);
            }
    }




}