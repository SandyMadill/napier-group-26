package com.napier.semga;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;


public class MainIntegrationTest {
    static Main main;
    @BeforeAll
    static void init(){
        main = new Main();
        Main.connect("localhost:33060", 30000);
    }

    @Test
    void getAllCountries(){
        ArrayList<Country> countries = main.getAllCountries();
        assertFalse(countries.isEmpty());
        assertEquals(countries.size(), 239);
    }

    @Test
    void getAllCities(){
        ArrayList<City> cities = main.getAllCities();
        assertFalse(cities.isEmpty());
        assertEquals(cities.size(), 4079);
    }

    @Test
    void getAllCapitalCities(){
        ArrayList<City> cities = main.getAllCapitalCities();
        assertFalse(cities.isEmpty());
        assertEquals(cities.size(), 232);
    }

    @Test
    void getAllCountriesByContinent(){
        ArrayList<Country> countries = main.getCountriesByContinent("Africa");
        assertFalse(countries.isEmpty());
        for(Country country : countries){
            assertEquals(country.continent, "Africa");
        }
    }

    @Test
    void getAllCountriesByRegion(){
        ArrayList<Country> countries = main.getCountriesByRegion("Central America");
        assertFalse(countries.isEmpty());
        for(Country country : countries){
            assertEquals(country.region, "Central America");
        }
    }

    @Test
    void getAllCitiesByContinent(){
        ArrayList<City> cities = main.getCitiesByContinent("Asia");
        assertFalse(cities.isEmpty());
        ArrayList<Country> countries = main.getAllCountries();
        HashMap<String, String> countryHashMap = new HashMap<>();
        for(Country country : countries){
            countryHashMap.put(country.code, country.continent);
        }
        for(City city : cities){
            assertEquals(countryHashMap.get(city.countryCode), "Asia");
        }
    }

    @Test
    void getAllCitiesByRegion(){
        ArrayList<City> cities = main.getCitiesByRegion("Eastern Europe");
        assertFalse(cities.isEmpty());
        ArrayList<Country> countries = main.getAllCountries();
        HashMap<String, String> countryHashMap = new HashMap<>();
        for(Country country : countries){
            countryHashMap.put(country.code, country.region);
        }
        for(City city : cities){
            assertEquals(countryHashMap.get(city.countryCode), "Eastern Europe");
        }
    }

    @Test
    void getAllCitiesByCountry(){
        ArrayList<City> cities = main.getCitiesByCountry("CHN");
        assertFalse(cities.isEmpty());
        for(City city : cities){
            assertEquals(city.countryCode, "CHN");
        }
    }

    @Test
    void getAllCitiesByDistrict(){
        ArrayList<City> cities = main.getCitiesByDistrict("California");
        assertFalse(cities.isEmpty());
        for(City city : cities){
            assertEquals(city.district, "California");
        }
    }

    @Test
    void getAllCapitalCitiesByContinent(){
        ArrayList<City> cities = main.getCapitalCitiesByContinent("South America");
        assertFalse(cities.isEmpty());
        ArrayList<Country> countries = main.getAllCountries();
        HashMap<String, String> countryHashMap = new HashMap<>();
        for(Country country : countries){
            countryHashMap.put(country.code, country.continent);
        }
        for(City city : cities){
            assertEquals(countryHashMap.get(city.countryCode), "South America");
        }
    }

    @Test
    void getAllCapitalCitiesByRegion(){
        ArrayList<City> cities = main.getCapitalCitiesByRegion("Southern Europe");
        assertFalse(cities.isEmpty());
        ArrayList<Country> countries = main.getAllCountries();
        HashMap<String, String> countryHashMap = new HashMap<>();
        for(Country country : countries){
            countryHashMap.put(country.code, country.region);
        }
        for(City city : cities){
            assertEquals(countryHashMap.get(city.countryCode), "Southern Europe");
        }
    }
}
