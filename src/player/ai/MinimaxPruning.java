package player.ai;

import game.BoardHelper;

import java.awt.*;

public class MinimaxPruning {
    static int nodesExplored = 0;
    public static Point solve(int[][] board, int player, int depth, Evaluator e){
        nodesExplored = 0;
        Point bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for(Point move : BoardHelper.getAllPossibleMoves(board, player)){
            int[][] newBord = BoardHelper.getNewBoardAfterMove(board,move,player);
            int childScore = MinValue(newBord,player,depth-1,e,Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(childScore>bestScore){
                bestScore = childScore;
                bestMove = move;
            }
        }
        System.out.println("Nodes Explored : " + nodesExplored);
        return bestMove;
    }

    public static int MaxMin(int[][] board, int player, int depth, Evaluator e,int alpha,int beta,boolean max){
        nodesExplored++;
        if(depth == 0 || BoardHelper.isGameFinished(board)){
            return e.eval(board,player);
        }
        int opPlayer = (player==1)? 2 : 1;

        if((max && !BoardHelper.hasAnyMoves(board,player)) || (!max && !BoardHelper.hasAnyMoves(board,opPlayer))){
            //System.out.println("Forfeit State Reached !");
            return MaxMin(board,player,depth-1,e,alpha,beta,!max);
        }
        if(max){
            int bestScore = Integer.MIN_VALUE;
            for(Point move : BoardHelper.getAllPossibleMoves(board,player)){
                int[][] newBord = BoardHelper.getNewBoardAfterMove(board,move,player);
                bestScore = Math.max(bestScore,MaxMin(newBord,player,depth-1,e,alpha,beta,false));
                if(bestScore>=beta){
                    return bestScore;
                }
                alpha = Math.max(bestScore,alpha);
            }
            return bestScore;
        }
        else{
            int bestScore = Integer.MAX_VALUE;
            for(Point move : BoardHelper.getAllPossibleMoves(board,opPlayer)){
                int[][] newBord = BoardHelper.getNewBoardAfterMove(board,move,opPlayer);
                bestScore = Math.min(bestScore,MaxMin(newBord,player,depth-1,e,alpha,beta,true));
                if(bestScore<=alpha){
                    return bestScore;
                }
                beta = Math.min(beta,bestScore);
            }
            return bestScore;
        }

    }

    public static int MaxValue(int[][] board, int player, int depth, Evaluator e,int alpha,int beta){
        nodesExplored++;
        if(depth == 0 || BoardHelper.isGameFinished(board)){
            return e.eval(board,player);
        }
        int opPlayer = (player==1)? 2 : 1;
        if(!BoardHelper.hasAnyMoves(board,player)){
            //System.out.println("Forfeit State Reached !");
            return MinValue(board,player,depth-1,e,alpha,beta);
        }

        int bestScore = Integer.MIN_VALUE;
        for(Point move : BoardHelper.getAllPossibleMoves(board,player)){
            int[][] newBord = BoardHelper.getNewBoardAfterMove(board,move,player);
            bestScore = Math.max(bestScore,MinValue(newBord,player,depth-1,e,alpha,beta));
            if(bestScore>=beta){
                return bestScore;
            }
            alpha = Math.max(bestScore,alpha);
        }
        return bestScore;

    }

    public static int MinValue(int[][] board, int player, int depth, Evaluator e,int alpha,int beta){
        nodesExplored++;
        if(depth == 0 || BoardHelper.isGameFinished(board)){
            return e.eval(board,player);
        }
        int opPlayer = (player==1)? 2 : 1;
        if(!BoardHelper.hasAnyMoves(board,opPlayer)){
            //System.out.println("Forfeit State Reached !");
            return MaxValue(board,player,depth-1,e,alpha,beta);
        }
        int bestScore = Integer.MAX_VALUE;
        for(Point move : BoardHelper.getAllPossibleMoves(board,opPlayer)){
            int[][] newBord = BoardHelper.getNewBoardAfterMove(board,move,opPlayer);
            bestScore = Math.min(bestScore,MaxValue(newBord,player,depth-1,e,alpha,beta));
            if(bestScore<=alpha){
                return bestScore;
            }
            beta = Math.min(beta,bestScore);
        }
        return bestScore;

    }


}
