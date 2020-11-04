package player.ai;

import game.BoardHelper;

import java.awt.*;
import java.util.ArrayList;

public class Heuristic {
    public int corner(int [][]board,int player){
        ArrayList<Point> allMove = BoardHelper.getAllPossibleMoves(board,player);
        for(Point move:allMove){
            if(move.x == 0 && move.y==0){
                return 100;
            }
            else if(move.x == 0 && move.y==7)
            {
                return 100;
            }
            else if(move.x == 7 && move.y==0)
            {
                return 100;
            }else if(move.x == 7 && move.y==7)
            {
                return 100;
            }
        }
        return 0;

    }

}
