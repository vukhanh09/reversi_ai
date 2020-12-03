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
    public ResultSet getWinRate(String option,int level) throws SQLException {
        var query = "Select Win,AllPoint from PruningWinRate Where Name=? and Level=?";
        var conn = MConnection.getInstance().getConnection();
        PreparedStatement pre= conn.prepareStatement(query);
        pre.setString(1,option);
        pre.setInt(2,level);
        ResultSet result= pre.executeQuery();
        return result;

    }

    public void insertWinRate(String option,int level,boolean win) throws SQLException {
        int max = 0, all = 0;
        ResultSet resultSet = getWinRate(option, level);
        while (resultSet.next()) {
            max = resultSet.getInt("Win");
            all = resultSet.getInt("AllPoint");

        }
        if (win) {
            max++;
            all++;
        } else {
            all++;
        }

        var query = "UPDATE PruningWinRate SET Win=?,AllPoint=? WHERE Name=? and Level=?";
        var conn = MConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, max);
        preparedStatement.setInt(2, all);
        preparedStatement.setString(3, option);
        preparedStatement.setInt(4, level);
        preparedStatement.executeUpdate();
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
