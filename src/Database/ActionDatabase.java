package Database;

import game.GamePanel;
import game.GamePlayer;
import player.ai.AIPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void averageNode (GamePlayer p1, GamePlayer p2) throws SQLException{
        var conn = MConnection.getInstance().getConnection();
        var sql= "insert into averageNode values('"+p1.name+"','"+p2.name+"',"+((AIPlayer) p2).getSearchDepth()+", "+((AIPlayer) p2).sum+")";
        PreparedStatement preparedStatement  = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
        System.out.println("Số node duyệt là: "+((AIPlayer) p2).sum);
       // System.out.println(average("Alpha-Beta", 1));
    }

    public int average(String name, int level) throws SQLException {
        int avg=0;
        var conn = MConnection.getInstance().getConnection();
        var sql= "select AVG(node) as average from averageNode where Level="+level+" and AI='"+name+"'";
        PreparedStatement preparedStatement  = conn.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            avg=rs.getInt("average");
        }
        preparedStatement.close();
        conn.close();
        return avg;
    }
}
