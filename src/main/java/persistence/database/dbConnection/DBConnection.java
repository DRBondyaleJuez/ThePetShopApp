package persistence.database.dbConnection;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class DBConnection {

    private String database;
    private final String url;
    private final String user = "postgres";
    private final String password;
    private final Connection currentConnection;

    public DBConnection(String currentDatabase) {

        database = currentDatabase;
        url = "jdbc:postgresql://localhost/" + database;
        password = new DBPasswordHandler().getPassword();
        currentConnection = connect();

    }

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public Connection getConnection() {
        return currentConnection;
    }

    public boolean addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp){

        //TODO: Explore properly the returning to usethe information of the returned clase from the postgreSQL

        String sql = "INSERT INTO users (user_id,username,password,email,date_created) " +
                     "VALUES ('" + newUserUUID + "','" + newUsername + "','" + newUserPassword + "','" + newUserEmail + "','" + newUserCreationTimeStamp + "') " +
                     "RETURNING *";
        try (
                PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Amount of actors: " + resultSet.getFetchSize());

            //This is not working properly. Investigate the returned resultset when inserting----------------------------------
            System.out.println(resultSet);
            while(resultSet.next()) {
                String returnedUsername = resultSet.getString("username");
                System.out.println(returnedUsername);

                if (newUsername == returnedUsername) {
                    System.out.println("EVERYTHING WAS CORRECT");
                } else {
                    System.out.println("SOMETHING WAS WRONG");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public String getUsernameIfInTable(String newUsername) {

        String sql = "SELECT * " +
                "FROM users " +
                "WHERE username = '" + newUsername + "'";
        String returnedUsername = "";
        try (
                PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Amount of actors: " + resultSet.getFetchSize());

            System.out.println(resultSet);
            while(resultSet.next()) {
                returnedUsername = resultSet.getString("username");
                System.out.println(returnedUsername);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnedUsername;
    }

    public String getEmailIfInTable(String newEmail) {

        String sql = "SELECT * " +
                "FROM users " +
                "WHERE email = '" + newEmail + "'";
        String returnedEmail = "";
        try (
                PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Amount of actors: " + resultSet.getFetchSize());

            System.out.println(resultSet);
            while(resultSet.next()) {
                returnedEmail = resultSet.getString("email");
                System.out.println(returnedEmail);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return returnedEmail;
    }

    public String getCorrespondingEncryptedPassword(String username) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE username = '" + username + "'";
        String returnedPassword = "";
        try (
                PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Amount of actors: " + resultSet.getFetchSize());

            System.out.println(resultSet);
            while(resultSet.next()) {
                returnedPassword = resultSet.getString("password");
                System.out.println(returnedPassword);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnedPassword;
    }
    public String properCase(String s) {
        String result = s;
        try (
             CallableStatement properCase = currentConnection.prepareCall("{ ? = call initcap( ? ) }")) {
            properCase.registerOutParameter(1, Types.VARCHAR);
            properCase.setString(2, s);
            properCase.execute();
            result = properCase.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public void getAllColumnFormActorsTable(){
        String sql = "SELECT * FROM actor";
        try (
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Amount of actors: " + resultSet.getFetchSize());

            while (resultSet.next()) {
                int actorId = resultSet.getInt("actor_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date lastUpdate = resultSet.getDate("last_update");

                System.out.println(actorId + " " + firstName + " " + lastName + " " + lastUpdate);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getFilms(String pattern, int releaseYear) {
        String SQL = "SELECT * FROM get_film (?, ?)";
        try (
            PreparedStatement pstmt = currentConnection.prepareStatement(SQL)) {

            pstmt.setString(1,pattern);
            pstmt.setInt(2,releaseYear);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%s %d",
                        rs.getString("film_title"),
                        rs.getInt("film_release_year")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
