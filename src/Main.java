import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Session;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (user_id IDENTITY, username VARCHAR, password VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS jobs (job_id IDENTITY, company_name VARCHAR, salary VARCHAR, location VARCHAR, contact_name VARCHAR, contact_number VARCHAR, " +
                "contact_email VARCHAR, have_applied BOOLEAN, rating INT, comments VARCHAR, user_id INT)");
    }

    public static void insertUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, user.username);
        stmt.setString(2, user.password);
        stmt.execute();
    }

    static User selectUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            int id = results.getInt("user_id");
            String password = results.getString("password");
            return new User(id, username, password);
        }
        return null;
    }

    public static void insertJob(Connection conn, Job job) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO jobs VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, job.companyName);
        stmt.setString(2, job.location);
        stmt.setString(3, job.salary);
        stmt.setString(4, job.contactName);
        stmt.setString(5, job.contactNumber);
        stmt.setString(6, job.contactEmail);
        stmt.setBoolean(7, job.haveApplied);
        stmt.setInt(8, job.rating);
        stmt.setString(9, job.comments);
        stmt.setInt(10, job.userId);
        stmt.execute();
    }

    public static ArrayList<Job> selectJobs(Connection conn, Integer userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM jobs INNER JOIN users ON jobs.user_id = users.user_id WHERE users.user_id = ?");
        stmt.setInt(1, userId);
        ResultSet results = stmt.executeQuery();
        ArrayList<Job> jobs = new ArrayList<>();
        while (results.next()) {
            Integer jobId = results.getInt("jobs.job_id");
            String companyName = results.getString("jobs.company_name");
            String location = results.getString("jobs.location");
            String salary = results.getString("jobs.salary");
            String contactName = results.getString("jobs.contact_name");
            String contactNumber = results.getString("jobs.contact_number");
            String contactEmail = results.getString("jobs.contact_email");
            Boolean haveApplied = results.getBoolean("jobs.have_applied");
            Integer rating = results.getInt("jobs.rating");
            String comments = results.getString("jobs.comments");
            Job job = new Job(jobId, companyName, location, salary, contactName, contactNumber, contactEmail, haveApplied, rating, comments, userId);
            jobs.add(job);
        }
        return jobs;
    }

    public static void updateJobs(Connection conn, Job job) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE jobs SET company_name = ?, location = ?, salary = ?, contact_name = ?, contact_number = ?, contact_email = ?, have_applied = ?, rating = ?, comments = ? WHERE job_id = ?");
        stmt.setString(1, job.companyName);
        stmt.setString(2, job.location);
        stmt.setString(3, job.salary);
        stmt.setString(4, job.contactName);
        stmt.setString(5, job.contactNumber);
        stmt.setString(6, job.contactEmail);
        stmt.setBoolean(7, job.haveApplied);
        stmt.setInt(8, job.rating);
        stmt.setString(9, job.comments);
        stmt.setInt(10, job.jobId);
        stmt.execute();
    }

    public static void deleteJobs(Connection conn, Integer jobId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM jobs WHERE job_id = ?");
        stmt.setInt(1, jobId);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {

        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        Spark.externalStaticFileLocation("public");
        Spark.init();

        Spark.get(
                "/jobs",
                (request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    User user = selectUser(conn, username);
                    ArrayList<Job> jobs = selectJobs(conn, user.userId);
                    JsonSerializer serializer = new JsonSerializer();
                    return serializer.serialize(jobs);
                }
        );
        Spark.post(
                "/login",
                (request, response) -> {
                    String body = request.body();
                    JsonParser parser = new JsonParser();
                    User user = parser.parse(body, User.class);
                    if (user.username == null || user.password == null) {
                        throw new Exception("Name or password not sent");
                    }
                    User validUser = selectUser(conn, user.username);
                    if (validUser == null) {
                        insertUser(conn, user);
                    }
                    else if (!validUser.password.equals(user.password)) {
                        throw new Exception("Wrong password.");
                    }
                    Session session = request.session();
                    session.attribute("username", validUser);
                    return "Success.";
                }
        );
        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    return "";
                }
        );

        Spark.post(
                "/jobs",
                (request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    String body = request.body();
                    JsonParser parser = new JsonParser();
                    Job job = parser.parse(body, Job.class);
                    insertJob(conn, job);
                    return "";
                }
        );

        Spark.put(
                "/jobs",
                (request, response) -> {
                    Session session = request.session();
                    session.attribute("username");
                    String body = request.body();
                    JsonParser parser = new JsonParser();
                    Job job = parser.parse(body, Job.class);
                    updateJobs(conn, job);
                    return "";
                }
        );

        Spark.delete(
                "/jobs/:job_id",
                (request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");
                    if (username == null) {
                        throw new Exception("You must log in to delete!");
                    }
                    int id = Integer.valueOf(request.params(":job_id"));
                    deleteJobs(conn, id);
                    return "";
                }
        );
    }
}
