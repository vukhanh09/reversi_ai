package player;

import game.BoardHelper;
import game.GamePlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends GamePlayer {

    public RandomPlayer(int mark){
        super(mark);
        this.name="RandomPlayer";
    }

    public RandomPlayer(){
        this.name="RandomPlayer";
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return null;
    }

    @Override
    public Point play(int[][] board) {
        ArrayList<Point> alMove = BoardHelper.getAllPossibleMoves(board,myMark);
        Random rand = new Random();
        if(alMove.size()>0){
            return alMove.get(rand.nextInt(alMove.size()));
        }
        else{
            return null;
        }

    }

    @Override
    public void setSearchDepth(int depth) {

    }
}
