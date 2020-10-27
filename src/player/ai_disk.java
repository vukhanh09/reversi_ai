package player;

import game.BoardHelper;

public class ai_disk {

    public static int valHeuristicDisk(int[][] board ){

        int max = BoardHelper.getPlayerStoneCount(board,1);
        int min = BoardHelper.getPlayerStoneCount(board,2);

        return 100 * (max - min) / (max + min);
    }

}
