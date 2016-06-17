import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Dan on 6/17/16.
 */
public class MainTest {
    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testUser() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "pw");
        Main.insertUser(conn, user);
        user = Main.selectUser(conn, user.username);
        conn.close();
        assertTrue(user != null);
    }

    @Test
    public void testJobs() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "pw");
        Main.insertUser(conn, user);
        Job job1 = new Job(1, "company", "place", "contact", "555", "email", true, 3, "comments", user.userId);
        Main.insertJob(conn, job1);
        Job job2 = new Job(2, "iron yard", "chs", "contact", "555", "email", false, 5, "comments", user.userId);
        Main.insertJob(conn, job2);
        Job job3 = new Job(3, "mcdonalds", "nyc", "contact", "555", "email", true, 1, "comments", user.userId);
        Main.insertJob(conn, job3);
        ArrayList<Job> jobs = Main.selectJobs(conn, 1);
        conn.close();
        assertTrue(jobs.size() == 3);
    }
}