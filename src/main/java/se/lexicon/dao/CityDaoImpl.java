package se.lexicon.dao;

import se.lexicon.model.City;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the implementation of CityDao for interacting with the 'city' table in the database.
 */
public class CityDaoImpl implements CityDao {

    private final Connection connection;

    public CityDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<City> findById(int id) {
        String sql = "SELECT * FROM city WHERE id = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    City city = new City(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("countrycode"),
                            rs.getString("district"),
                            rs.getInt("population"));
                    return Optional.of(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findById(): " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<City> findByCode(String code) {
        String sql = "SELECT * FROM city WHERE countrycode = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                List<City> resultList = new ArrayList<>();
                while (rs.next()) {
                    resultList.add(new City(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("countrycode"),
                            rs.getString("district"),
                            rs.getInt("population")));
                }
                return resultList;
            }
        } catch (SQLException e) {
            System.out.println("Error findByCode(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<City> findByName(String name) {
        String sql = "SELECT * FROM city WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                List<City> resultList = new ArrayList<>();
                while (rs.next()) {
                    resultList.add(new City(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("countrycode"),
                            rs.getString("district"),
                            rs.getInt("population")));
                }
                return resultList;
            }
        } catch (SQLException e) {
            System.out.println("Error findByName(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<City> findAll() {
        String sql = "SELECT * FROM city";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery();) {
            List<City> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(new City(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("countrycode"),
                        rs.getString("district"),
                        rs.getInt("population")));
            }
            return resultList;

        } catch (SQLException e) {
            System.out.println("Error findAll(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public City save(City city) {
        String sql = "INSERT INTO city (name, countrycode, district, population)  VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Successfully saved " + city.getName() + " to DB.");
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int firstId = keys.getInt(1);
                        city.setId(firstId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        return city;
    }

    @Override
    public void update(City city) {
        if (city.getId() <= 0) {
            throw new IllegalArgumentException("City ID must be set for update.");
        }

        String sql = "UPDATE city SET name = ?, countrycode = ?, district = ?, population = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.setInt(5, city.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Successfully updated city with ID " + city.getId());
            } else {
                System.out.println("Couldn't find city with ID " + city.getId());
            }

        } catch (SQLException e) {
            System.out.println("Error update(): " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM city WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Successfully deleted city with ID " + id);
            } else {
                System.out.println("Couldn't find city with ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error deleteById(): " + e.getMessage());
        }

    }
    // TODO: Needs completion
}
