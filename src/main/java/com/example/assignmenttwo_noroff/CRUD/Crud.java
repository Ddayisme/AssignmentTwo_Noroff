package com.example.assignmenttwo_noroff.CRUD;

import com.example.assignmenttwo_noroff.Repositories.CustomerRepository;
import com.example.assignmenttwo_noroff.models.Customer;
import com.example.assignmenttwo_noroff.models.CustomerCountry;
import com.example.assignmenttwo_noroff.models.CustomerGenre;
import com.example.assignmenttwo_noroff.models.CustomerSpender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Crud implements CustomerRepository {

    private final String url;
    private final String username;
    private final String password;

    public Crud(@Value("${spring.datasource.url}") String url,
                @Value("${spring.datasource.username}") String username,
                @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void test() {
        System.out.println(getAllCustomers());
        System.out.println(getCustomerById(2));
        System.out.println(getCustomerByName("Leonie"));
        System.out.println(getPageOfCustomers(10,10));
        System.out.println(findHigestSpendingCustomer());
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

    @Override
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

    @Override
    public Customer getCustomerById(Integer id) {
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
        return customers.get(0);
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

    @Override
    public int insertCustomer(Customer object) {
        String sql = "INSERT INTO customer (first_name, last_name, country, postal_code, phone, email) VALUES (?,?,?,?,?,?)";
        int result = 0;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, object.first_name());
            statement.setString(2, object.last_name());
            statement.setString(3, object.country());
            statement.setString(4, object.postal_code());
            statement.setString(5, object.phone());
            statement.setString(6, object.email());

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

    @Override
    public int updateCustomerLastName(Customer object) {

        String sql = "UPDATE customer set last_name=? where customer_id=?";
        int result = 0;


        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, object.last_name());
            statement.setInt(2, object.customer_id());

            result = statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public CustomerCountry mostCustomersCountry() {

        String sql = "SELECT country , count(*) from customer GROUP BY country ORDER BY 2 DESC Limit 1";


        CustomerCountry mostPopularCountry;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            result.next();

            mostPopularCountry = new CustomerCountry(
                    result.getString(1),
                    result.getInt(2)
            );
            return mostPopularCountry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerSpender findHigestSpendingCustomer() {

        String sql = "SELECT customer_id, SUM(total) \n" +
                "FROM invoice\n" +
                "GROUP BY customer_id\n" +
                "ORDER BY 2 desc LIMIT 1;";


        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            result.next();
            CustomerSpender customerSpender = new CustomerSpender(
                    result.getInt("customer_id"),
                    result.getDouble(2)
            );

            return customerSpender;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CustomerGenre> findHighestGenreForACustomer(int id) {
        String sql = "SELECT genre.name, count(*)\n" +
                "FROM customer\n" +
                "INNER JOIN invoice\n" +
                "ON customer.customer_id = invoice.customer_id\n" +
                "INNER JOIN invoice_line\n" +
                "ON invoice.invoice_id = invoice_line.invoice_id\n" +
                "INNER JOIN track\n" +
                "ON track.track_id = invoice_line.track_id\n" +
                "INNER JOIN genre\n" +
                "ON genre.genre_id = track.genre_id\n" +
                "WHERE customer.customer_id = ?\n" +
                "GROUP BY genre.name\n" +
                "ORDER BY 2 desc";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();

            List<CustomerGenre> customer = new ArrayList<>();
            while (result.next()) {
                CustomerGenre customerGenre = new CustomerGenre(
                        result.getString(1),
                        result.getInt(2)
                );
                customer.add(customerGenre);
            }
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(Customer object){
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }
}
