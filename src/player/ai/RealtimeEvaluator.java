package player.ai;

import game.BoardHelper;

import java.awt.*;
import java.util.ArrayList;

import static game.BoardHelper.getAllPossibleMoves;

public class RealtimeEvaluator implements Evaluator {
    int[][] weightSetForDiscCount;


    public int eval(int[][] board , int player){
        int score = 0;

        int[] weights = weightSetForDiscCount[BoardHelper.getTotalStoneCount(board)];

        if(weights[0] != 0) {
            score += weights[0] * mobility(board,player);
        }
        if(weights[1] != 0) {
            score += weights[1] * coinParity(board,player);
        }
        if(weights[2] != 0) {
            score += weights[2] * placement(board,player);
        }
        if(weights[3] != 0) {
            score += weights[3] * stability(board,player);
        }
        if(weights[4] != 0) {
            score += weights[4] * cornerCaptured(board,player);
        }

        return score;
    }


    public RealtimeEvaluator(int[][] weightSet , int[] timingSet){
        weightSetForDiscCount = new int[65][weightSet[0].length];
        for(int dc = 0; dc <= 64; dc++) {
            // determine which set of weights to use
            int w = 0;
            for(int i = 0; i < timingSet.length; i++) {
                if(dc <= timingSet[i]) {
                    w = i;
                    break;
                }
            }

            if(w == 0) {
                weightSetForDiscCount[dc] = weightSet[0];
                continue;
            }

            // linearly interpolate between the set of weights given for the
            // current number of moves and the previous set of weights
            double factor = ((double)dc - timingSet[w - 1]) / (timingSet[w] - timingSet[w - 1]);
            for(int i = 0; i < weightSet[w].length; i++) {
                weightSetForDiscCount[dc][i] = (int)Math.rint(factor * weightSet[w][i] + (1 - factor) * weightSet[w - 1][i]);
            }
        }
    }

    public static int coinParity(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myCoin = BoardHelper.getPlayerStoneCount(board,player);
        int opponentCoin = BoardHelper.getPlayerStoneCount(board,oplayer);

        return 100 * (myCoin - opponentCoin) / (myCoin + opponentCoin + 1);
    }

    static int[][] SQUARE_SCORE = {
            {100 , -10 , 15  ,  10 ,  10 , 15  , -10 ,  100},
            {-10 , -25 ,  -4, -4 , -4 , -4 , -25 , -10 },
            {15   ,  -4 ,   6,   4,   4,   6,  -4 ,  15  },
            {10   ,  -4 ,   4,   0,   0,   4,  -4 ,  10  },
            {10   ,  -4 ,   4,   0,   0,   4,  -4 ,  10  },
            {15  ,  -4 ,   6,   4,   4,   6,  -4 ,  15  },
            {-10 , -25 ,  -4, -4 , -4 , -4 , -25 , -10 },
            {100 , -10 , 15 ,  10 ,  10, 15 , -10 ,  100}
    };

    public static int placement(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myW = 0;
        int opW = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j]==player) myW += SQUARE_SCORE[i][j];
                if(board[i][j]==oplayer) opW += SQUARE_SCORE[i][j];
            }
        }

        return myW - opW;
    }

    public static int stability(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myS = 0;
        int opS = 0;


        if(board[0][0] == player) myS += BoardHelper.getStableDisks(board,player,0,0).size();

        if(board[0][7] == player) myS += BoardHelper.getStableDisks(board,player,0,7).size();

        if(board[7][0] == player) myS += BoardHelper.getStableDisks(board,player,7,0).size();

        if(board[7][7] == player) myS += BoardHelper.getStableDisks(board,player,7,7).size();



        if(board[0][0] == oplayer) opS += BoardHelper.getStableDisks(board,oplayer,0,0).size();
        if(board[0][7] == oplayer) opS += BoardHelper.getStableDisks(board,oplayer,0,7).size();
        if(board[7][0] == oplayer) opS += BoardHelper.getStableDisks(board,oplayer,7,0).size();
        if(board[7][7] == oplayer) opS += BoardHelper.getStableDisks(board,oplayer,7,7).size();

        return 100 * (myS - opS) / (myS + opS + 1);
    }
    public static int mobility(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myMoveCount = getAllPossibleMoves(board,player).size();
        int opponentMoveCount = getAllPossibleMoves(board,oplayer).size();
        if(opponentMoveCount == 0)
            return 500;

        return 100 * (myMoveCount - opponentMoveCount) / (myMoveCount + opponentMoveCount + 1);
    }

//    public static int frontier(int[][] board , int player){
//        int oplayer = (player==1) ? 2 : 1;
//
//        int myF = BoardHelper.getFrontierSquares(board,player).size();
//        int opF = BoardHelper.getFrontierSquares(board,oplayer).size();
//
//        return 100 * (myF - opF) / (myF + opF + 1);
//    }

    public static int cornerCaptured(int[][] board , int player){
        int oplayer = (player==1) ? 2 : 1;

        int myCorners = 0;
        int opCorners = 0;

        if(board[0][0]==player) myCorners++;
        if(board[7][0]==player) myCorners++;
        if(board[0][7]==player) myCorners++;
        if(board[7][7]==player) myCorners++;

        if(board[0][0]==oplayer) opCorners++;
        if(board[7][0]==oplayer) opCorners++;
        if(board[0][7]==oplayer) opCorners++;
        if(board[7][7]==oplayer) opCorners++;

        return 100 * (myCorners - opCorners) / (myCorners + opCorners + 1);
    }

}
