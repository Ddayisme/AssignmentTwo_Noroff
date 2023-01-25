package CRUD;

import models.Customer;
import models.CustomerCountry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crud {

    private String url = "jdbc:postgresql://localhost:5432/Noroff_AssignmentTwo_Db";
    private String username = "postgres";

    private String password = "1234";

    public Crud() {

    }

    public Crud(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void test() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Noe skjer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Customer getCustomerByName(String name) {
        String sql = "Select * from customer where first_name LIKE ?";
        Customer customer = null;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            customer = fetchCustomers(result).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        String sql = "Select * from customer";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            customers = fetchCustomers(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            customers = fetchCustomers(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> getPageOfCustomers(int limit, int offset) {
        String sql = "SELECT * FROM customer LIMIT ? OFFSET ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet result = statement.executeQuery();
            customers = fetchCustomers(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public int addCustomer(String firstName, String lastName, String country, String postalCode, String phone, String email) {
        String sql = "INSERT INTO customer (first_name, last_name, country, postal_code, phone, email) VALUES (?,?,?,?,?,?)";
        int result = 0;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, country);
            statement.setString(4, postalCode);
            statement.setString(5, phone);
            statement.setString(6, email);

            result = statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Customer> fetchCustomers(ResultSet result) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (result.next()) {
            Customer customer = new Customer(
                    result.getInt("customer_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("country"),
                    result.getString("postal_code"),
                    result.getString("phone"),
                    result.getString("email")
            );
            customers.add(customer);
        }
        return customers;
    }

    public int editCustomerLastName(int id, String lastname) {

        String sql = "UPDATE customer set last_name=? where customer_id=?";
        int result = 0;


        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, lastname);
            statement.setInt(2, id);

            result = statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String mostCustomersCountry() {

        String sql = "SELECT country , count(*) from customer GROUP BY country ORDER BY 2 DESC Limit 1";


        CustomerCountry mostPopularCountry;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            result.next();


            return result.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}