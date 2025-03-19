package se.lexicon;

import se.lexicon.dao.CityDaoImpl;
import se.lexicon.db.DBConnection;
import se.lexicon.model.City;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Represents the entry point of the application.
 */
public class Main {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection();) {
            CityDaoImpl cityDao = new CityDaoImpl(connection);
            //findById
//            Optional<City> city1;
//            city1 = cityDao.findById(1);
//            System.out.println(city1);
            //findByCode
//            List<City> cityList = cityDao.findByCode("NLD");
//            cityList.forEach(city -> System.out.println(city));
            //findByName
            //SELECT name, COUNT(*) FROM city GROUP BY name HAVING COUNT(*) > 1; to see whether there are multiple cities sharing the same name
//            List<City> cityList = cityDao.findByName("Ede");
//            cityList.forEach(city -> System.out.println(city));
            //findAll
//              List<City> cityList = cityDao.findAll();
//              cityList.forEach(city -> System.out.println(city));
            //save
//            City moheda = cityDao.save(new City("Moheda", "SWE", "Kronoberg", 3000));
//            System.out.println(moheda);
            //update
            cityDao.findByName("moheda").forEach(city -> System.out.println(city));
            City moheda = cityDao.findById(4080).get();
            moheda.setPopulation(3400);
            cityDao.update(moheda);
            cityDao.findByName("moheda").forEach(city -> System.out.println(city));
            //delete
            cityDao.deleteById(4081);
            cityDao.findByName("moheda").forEach(city -> System.out.println(city));


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
