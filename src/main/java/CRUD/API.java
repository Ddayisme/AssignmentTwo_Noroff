package CRUD;

import models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class API{

    private String url = "jdbc:postgresql://localhost:5432/Noroff_AssignmentTwo_Db";
    private String username= "postgres";

    private String password ="1234";
    public API(){

    }

    public API(String url, String username, String password){
        this.url= url;
        this.username=username;
        this.password=password;
    }

    public void test(){
        try(Connection conn= DriverManager.getConnection(url, username, password)){
            System.out.println("Noe skjer");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public Customer getCustomerByName(String name){
        String sql="Select * from customer where first_name LIKE ?";
        Customer customer=null;

        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,name);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                        customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return customer;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();

        String sql="Select * from customer";

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement= conn.prepareStatement(sql);
            ResultSet result= statement.executeQuery();

            while(result.next()){
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

        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> getCustomerById(int id){
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        List<Customer> customers = new ArrayList<>();

        try(Connection conn= DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()){
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

        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }
}
