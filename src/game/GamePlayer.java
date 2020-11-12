package game;

import java.awt.*;

public abstract class GamePlayer {

    protected int myMark;
    public GamePlayer(int mark){
        myMark = mark;
    }
    public GamePlayer(){}

    abstract public boolean isUserPlayer();

    abstract public String playerName();

    abstract public Point play(int[][] board);

    abstract public void setSearchDepth(int depth);

}
