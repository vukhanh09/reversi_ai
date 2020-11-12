package Database;

import java.sql.SQLException;

public class ActionDatabase {
    public void printInfo() throws SQLException {
        var conn = MConnection.getInstance().getConnection();
        var sql= "Select * FROM PruningWinRate";
        var result = conn.prepareStatement(sql);
        var resultSet = result.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString("Name"));
            System.out.println(resultSet.getInt("Win"));
            System.out.println(resultSet.getInt("All"));
            System.out.println(resultSet.getInt("Level"));
        }
    }
}
