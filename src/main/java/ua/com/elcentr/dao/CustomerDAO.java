package ua.com.elcentr.dao;

import ua.com.elcentr.model.Customer;
import ua.com.elcentr.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

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
}
