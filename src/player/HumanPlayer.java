package player;

import game.GamePlayer;

import java.awt.*;

public class  HumanPlayer extends GamePlayer {


    public HumanPlayer(int mark) {
        super(mark);
        this.name="HumanPlayer";
    }

    public HumanPlayer(){
        this.name="HumanPlayer";
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }


    public String playerName() {
        return "User" ;
    }

    @Override
    public Point play(int[][] board) {
        return null;
    }

    @Override
    public void setSearchDepth(int depth) {

    }
}
