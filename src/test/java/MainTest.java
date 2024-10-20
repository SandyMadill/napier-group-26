import com.napier.semga.City;
import com.napier.semga.Country;
import com.napier.semga.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MainTest {
    static Main main;
    @BeforeAll
    static void init(){
        main = new Main();
    }

    @Test
    void printCitiesNull(){
        main.printCities(null);
    }

    @Test
    void printCitiesEmpty(){
        ArrayList<City> cities = null;
        main.printCities(cities);
    }

    @Test
    void printCitiesContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        main.printCities(cities);
    }

    @Test
    void printCities(){
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.id = 1;
        city.name = "Kabul";
        city.countryCode = "AFG";
        city.district = "Kabul";
        city.population = 1780000;
        cities.add(city);
        main.printCities(cities);

    }

    @Test
    void printCountriesNull(){
        main.printCountries(null);
    }

    @Test
    void printCountriesEmpty(){
        ArrayList<Country> countries = null;
        main.printCountries(countries);
    }

    @Test
    void printCountriesContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        main.printCountries(countries);
    }

    @Test
    void printCountries(){
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.code = "ABW";
        country.name = "Aruba";
        country.continent = "North America";
        country.region = "Caribbean";
        country.surfaceArea = 193.00;
        country.indepYear = 1974;
        country.population = 103000;
        country.lifeExpectancy = 78.4;
        country.gnp = 828.00;
        country.gnpOld = 793.00;
        country.localName = "Aruba";
        country.governmentForm = "Nonmetropolitan Territory of The Netherlands";
        country.headOfState = "Beatrix";
        country.capital = 129;
        country.code2= "AW";

        countries.add(country);

        main.printCountries(countries);

    }
}
