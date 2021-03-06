package ua.com.elcentr.dao;

import ua.com.elcentr.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class CustomerDAO {

    private static final Logger LOG = Logger.getLogger(CustomerDAO.class.getName());

    public static Customer save(String name, String notes) {
        String sql = "" +
                "INSERT INTO customers(name, notes) " +
                "VALUES(?,?)";
        String sequenceSql = "" +
                "SELECT currval('customer_id_seq')";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSql)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, notes);
            preparedStatement.executeUpdate();
            ResultSet resultSet = sequenceStatement.executeQuery();
            Integer id = null;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return Customer.builder()
                    .id(id)
                    .name(name)
                    .notes(notes)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("Customer with name %s was not created", name));
    }

    public static Customer update(Integer id, String name, String notes) {
        if (isNull(id)) {
            throw new RuntimeException("id is null, update is impossible");
        }
        String sql = "" +
                "UPDATE customers " +
                "SET name=?, notes=? " +
                "WHERE id=?";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, notes);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return Customer.builder()
                    .id(id)
                    .name(name)
                    .notes(notes)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("User with id %d was not updated", id));
    }

    public static Optional<Customer> findById(Integer id) {
        String sql = "" +
                "SELECT * FROM customers " +
                "WHERE id=?";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .notes(resultSet.getString("notes"))
                        .build();
                return Optional.of(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void delete(Integer id) {
        if (isNull(id)) {
            throw new RuntimeException("id is null, delete is impossible");
        }
        String sql = "DELETE " +
                "FROM customers " +
                "WHERE id=?";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
