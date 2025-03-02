package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    public static List<User> getUsers() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "19martie2004";

        try(Connection connection = DriverManager.getConnection(url,user,password)) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM \"User\" ";
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("score")
                ));
            }
            return users;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(User addedUser) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "19martie2004";

        try(Connection connection = DriverManager.getConnection(url,user,password)) {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO \"User\" (username,password,score) VALUES ('" + addedUser.getUserName() + "','" + addedUser.getPassword() + "',0);";
            statement.executeUpdate(sql);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateMaxScore(User addedUser,int maxScore) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "19martie2004";

        try(Connection connection = DriverManager.getConnection(url,user,password)) {
            Statement statement = connection.createStatement();
            String sql = "UPDATE \"User\" SET score = " + maxScore + " WHERE \"username\" = '" + addedUser.getUserName() + "';";
            statement.executeUpdate(sql);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean findUser(String username) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "19martie2004";

        try(Connection connection = DriverManager.getConnection(url,user,password)) {
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) from \"User\" where username = '" + username + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int count = resultSet.getInt("count");
            if(count > 0)
                return true;
            return false;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User getUser(String username) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "19martie2004";

        try(Connection connection = DriverManager.getConnection(url,user,password)) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from \"User\" where username = '" + username + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            User userObj = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getInt("score"));
            return userObj;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
