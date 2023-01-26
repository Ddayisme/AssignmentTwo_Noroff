package com.example.assignmenttwo_noroff.Repositories;

import com.example.assignmenttwo_noroff.models.Customer;
import com.example.assignmenttwo_noroff.models.CustomerCountry;
import com.example.assignmenttwo_noroff.models.CustomerGenre;
import com.example.assignmenttwo_noroff.models.CustomerSpender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Our implementation of the CustomerRepository with all the innhereted methods
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepository{

    private final String url;
    private final String username;
    private final String password;

    /**
     * Our constructor with the connection data to the DB, fetched from Recources, application.properties
     *
     * @param url
     * @param username
     * @param password
     */
    public CustomerRepositoryImpl(@Value("${spring.datasource.url}") String url,
                @Value("${spring.datasource.username}") String username,
                @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Our implenentation to get a List of all customers
     *
     * @return
     */
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

    /**
     * Implementation to get a single customer by Id from the DB
     *
     * @param id
     * @return
     */
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

    /**
     * Implementation to get a single customer by firs_name
     *
     * @param name
     * @return
     */
    @Override
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

    /**
     * Helping method to get a mapped customer object returned in a list
     *
     * @param result
     * Resultset result, is the result of an SQL-Query
     *
     * @return
     * @throws SQLException
     */
    private List<Customer> fetchCustomers(ResultSet result) throws SQLException {
        List<com.example.assignmenttwo_noroff.models.Customer> customers = new ArrayList<>();
        while (result.next()) {
            com.example.assignmenttwo_noroff.models.Customer customer = new com.example.assignmenttwo_noroff.models.Customer(
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

    /**
     * Implementation to insert a new Customer object to the DB as a SQL-Query
     *
     * @param object
     * @return
     */
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

    /**
     * Get a page/subset, with a specified amount, from the Customer table in the DB
     *
     * @param limit
     * The limit, number of Customer objects
     *
     * @param offset
     * The index starting point in the DB
     *
     * @return
     */
    @Override
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

    /**
     * Implementation to update the last name of a specified customer in the DB
     *
     * @param object
     * @return
     */
    @Override
    public int updateCustomer(Customer object) {

        String sql = "UPDATE customer set last_name=?, " +
                "first_name=?, country=?, postal_code=?, phone=?, email=? where customer_id=?";
        int result = 0;


        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(7, object.customer_id());
            statement.setString(1, object.last_name());
            statement.setString(2, object.first_name());
            statement.setString(3, object.country());
            statement.setString(4, object.postal_code());
            statement.setString(5, object.phone());
            statement.setString(6, object.email());

            result = statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Implementation of a SQL-Query to get the highest spending customer form the DB
     *
     * @return
     */
    @Override
    public CustomerSpender findHighestSpendingCustomer() {

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

    /**
     * Implementation of a SQL-Query to find which country which has the most customers
     *
     * @return
     */
    @Override
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

    /**
     * Which genre is most popular for a specified customer by Id
     *
     * @param id
     * @return
     */
    @Override
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
                "ORDER BY 2 desc " +
                "FETCH FIRST 1 ROW WITH TIES";

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
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    /**
     * Test uotput from some methods descibed above
     */
    public void test(){
        System.out.println(getAllCustomers());
        System.out.println(findHighestSpendingCustomer());
        System.out.println(getPageOfCustomers(20,11));
        System.out.println(findHighestGenreForACustomer(12));

        Customer a_testCustomer= new Customer(
            4, "Bjørn", "Bjørnsen", "Sweden", "0561", "+46 55778811" , "Bjørn@bjørnsen.sve"
        );

        updateCustomer(a_testCustomer);
    }
}
