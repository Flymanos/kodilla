package com.kodilla.jdbc;

import org.junit.Assert;
import org.junit.Test;
import sun.jvm.hotspot.utilities.AssertionFailure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManagerTestSuite {
    @Test
    public void testConnect() throws SQLException {
        //Given
        //When
        DbManager dbManager = DbManager.getInstance();
        //Then
        Assert.assertNotNull(dbManager.getConnection());
    }

    @Test
    public void testSelectUsers() throws SQLException {
        //Given
        DbManager dbManager = DbManager.getInstance();

        //When
        String sqlQuery = "SELECT * FROM USERS";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);

        //Then
        int counter = 0;
        while(rs.next()) {
            System.out.println(rs.getInt("ID") + ", " +
                    rs.getString("FIRSTNAME") + ", " +
                    rs.getString("LASTNAME"));
            counter++;
        }
        rs.close();
        statement.close();
        Assert.assertEquals(5, counter);
    }

    @Test
    public void testSelectUsersAndPosts() throws SQLException{
        //Given
        DbManager dbManager = DbManager.getInstance();
        //When
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT users.FIRSTNAME, users.LASTNAME FROM users, posts " +
                "WHERE users.ID = posts.user_id GROUP BY users.id HAVING COUNT(*) > 1");
        int counter = 0;
        while(rs.next()){
            System.out.println(rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME"));
            counter++;
        }
        //then
        Assert.assertEquals(2, counter);
    }
}
