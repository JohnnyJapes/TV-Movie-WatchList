package main;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sql {

    @Test
    void selectRank() throws SQLException {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:local.db");
            ResultSet rs = connection.createStatement().executeQuery("Select * from listentries  where rank>=2 and list_id=0");
            while(rs.next()){
                System.out.println("id: " + rs.getInt("id") + ", rank: " + rs.getInt("rank"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally{
            connection.close();
        }

    }



}
