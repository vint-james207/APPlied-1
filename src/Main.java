import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jamesyburr on 6/16/16.
 */
public class Main {
    public static void updateJobs(Connection conn, Integer jobId, User user, Jobs job) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE jobs SET companyName = ?, location = ?, contactName = ?, contactNumber = ?, contactEmail = ?, haveApplied = ?, rating = ?, comments = ? WHERE id = ?");
        stmt.setString(1, job.companyName);
        stmt.setString(2, job.location);
        stmt.setString(3, job.contactName);
        stmt.setString(4, job.contactNumber);
        stmt.setString(5, job.contactEmail);
        stmt.setBoolean(6, job.haveApplied);
        stmt.setInt(7, job.rating);
        stmt.setString(8, job.comments);
        stmt.execute();
    }

    public static void deleteJobs(Connection conn, Integer jobId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM jobs WHERE id = ?");
        stmt.setInt(1, jobId);
        stmt.execute();
    }


    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
    }
}