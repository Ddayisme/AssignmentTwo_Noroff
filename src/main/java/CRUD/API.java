package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
