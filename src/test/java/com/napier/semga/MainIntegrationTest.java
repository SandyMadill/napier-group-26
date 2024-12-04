package com.napier.semga;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Test
    void getTopNCountries () {
        int maxSize = main.getAllCountries().size();
        ArrayList<Country> countries = main.getTopNCountries(10);
        assertFalse(countries.isEmpty());
        if (maxSize > 10) {
            assertEquals(countries.size(), 10);
        }
        else{
            assertEquals(countries.size(), maxSize);
        }

    }


    @Test
    void getTopNContinentsCountries () {
        int maxSize = main.getCountriesByContinent("Asia").size();
        ArrayList<Country> countries = main.getTopNContinentsCountries(10, "Asia");
        assertFalse(countries.isEmpty());
        if (maxSize > 10) {
            assertEquals(countries.size(), 10);
        }
        else{
            assertEquals(countries.size(), maxSize);
        }
    }


    @Test
    void getTopNCountriesByRegion () {
        int maxSize = main.getCountriesByRegion("Eastern Asia").size();
        ArrayList<Country> countries = main.getTopNCountriesByRegion(4, "Eastern Asia");
        assertFalse(countries.isEmpty());
        if (maxSize > 4) {
            assertEquals(countries.size(), 4);
        }
        else{
            assertEquals(countries.size(), maxSize);
        }
    }


    @Test
    void getTopNCities () {
        int maxSize = main.getAllCities().size();
        ArrayList<City> cities = main.getTopNCities(10);
        assertFalse(cities.isEmpty());
        if (maxSize > 10) {
            assertEquals(cities.size(), 10);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }
    }


    @Test
    void getTopNCitiesByContinent () {
        int maxSize = main.getCitiesByContinent("Asia").size();
        ArrayList<City> cities = main.getTopNCitiesByContinent(10, "Asia");
        assertFalse(cities.isEmpty());
        if (maxSize > 10) {
            assertEquals(cities.size(), 10);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }

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
    void getTopNCitiesByRegion () {
        int maxSize = main.getCitiesByRegion("South America").size();
        ArrayList<City> cities = main.getTopNCitiesByRegion(10, "South America");
        assertFalse(cities.isEmpty());
        if (maxSize > 10) {
            assertEquals(cities.size(), 10);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }

        ArrayList<Country> countries = main.getAllCountries();
        HashMap<String, String> countryHashMap = new HashMap<>();
        for(Country country : countries){
            countryHashMap.put(country.code, country.region);
        }
        for(City city : cities){
            assertEquals(countryHashMap.get(city.countryCode), "South America");
        }


    }


    @Test
    void getTopNCitiesByCountry () {
        int maxSize = main.getCitiesByCountry("CHN").size();
        ArrayList<City> cities = main.getTopNCitiesByCountry(7, "CHN");
        assertFalse(cities.isEmpty());
        if (maxSize > 7) {
            assertEquals(cities.size(), 7);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }

        for(City city : cities){
            assertEquals(city.countryCode, "CHN");
        }
    }


    @Test
    void getTopNCitiesByDistrict () {
        int maxSize = main.getCitiesByDistrict("California").size();
        ArrayList<City> cities = main.getTopNCitiesByDistrict(4, "California");
        assertFalse(cities.isEmpty());
        if (maxSize > 4) {
            assertEquals(cities.size(), 4);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }

        for(City city : cities){
            assertEquals(city.district, "California");
        }
    }

    @Test
    void getTopNCapitalCities () {
        int maxSize = main.getAllCities().size();
        ArrayList<City> cities = main.getTopNCities(10);
        assertFalse(cities.isEmpty());
        if (maxSize > 10) {
            assertEquals(cities.size(), 10);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }

    }

    @Test
    void getTopNCapitalCitiesByContinent () {
        int maxSize = main.getCapitalCitiesByContinent("Asia").size();
        ArrayList<City> cities = main.getTopNCapitalCitiesByContinent(10, "Asia");
        assertFalse(cities.isEmpty());
        if (maxSize > 10) {
            assertEquals(cities.size(), 10);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }

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
    void getTopNCapitalCitiesByRegion () {
        int maxSize = main.getCitiesByRegion("Western Africa").size();
        ArrayList<City> cities = main.getTopNCitiesByRegion(5, "Western Africa");
        assertFalse(cities.isEmpty());
        if (maxSize > 5) {
            assertEquals(cities.size(), 5);
        }
        else{
            assertEquals(cities.size(), maxSize);
        }
        ArrayList<Country> countries = main.getAllCountries();
        HashMap<String, String> countryHashMap = new HashMap<>();
        for(Country country : countries){
            countryHashMap.put(country.code, country.region);
        }
        for(City city : cities){
            assertEquals(countryHashMap.get(city.countryCode), "Western Africa");
        }
    }
}
