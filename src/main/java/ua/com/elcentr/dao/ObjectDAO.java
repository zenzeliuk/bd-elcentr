package ua.com.elcentr.dao;

import ua.com.elcentr.model.Customer;
import ua.com.elcentr.model.Object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class ObjectDAO {

    private static final Logger LOG = Logger.getLogger(Object.class.getName());

    public static Object save(String name, String notes) {
        String sql = "" +
                "INSERT INTO objects(name, notes) " +
                "VALUES(?,?)";
        String sequenceSql = "" +
                "SELECT currval('object_id_seq')";

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
            return Object.builder()
                    .id(id)
                    .name(name)
                    .notes(notes)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("Customer with name %s was not created", name));
    }

    public static Object update(Integer id, String name, String notes) {
        if (isNull(id)) {
            throw new RuntimeException("id is null, update is impossible");
        }
        String sql = "" +
                "UPDATE objects " +
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
            return Object.builder()
                    .id(id)
                    .name(name)
                    .notes(notes)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("User with id %d was not updated", id));
    }

    public static Optional<Object> findById(Integer id) {
        String sql = "" +
                "SELECT * FROM objects " +
                "WHERE id=?";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object object = Object.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .notes(resultSet.getString("notes"))
                        .build();
                return Optional.of(object);
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
                "FROM objects " +
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
