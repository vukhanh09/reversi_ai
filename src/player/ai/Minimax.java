package player.ai;

import game.BoardHelper;
import game.BoardPrinter;

import java.awt.*;
import java.util.ArrayList;

public class Minimax {
    //public static int sum=0;
    static int nodesExplored = 0;

    public static Point solve(int[][] board, int player,int depth,Evaluator e, AIPlayer ai){
        nodesExplored = 0;
        int bestScore = Integer.MIN_VALUE;
        Point bestMove = null;
        for(Point move : BoardHelper.getAllPossibleMoves(board,player)){
            //create new node
            int[][] newNode = BoardHelper.getNewBoardAfterMove(board,move,player);
            //recursive call
            int childScore = MIN_minimax(newNode,player,depth-1,e);
            if(childScore > bestScore) {
                bestScore = childScore;
                bestMove = move;
            }
        }
        System.out.println("Nodes Explored : " + nodesExplored);
        ai.sum+=nodesExplored;
        return bestMove;
    }


    private static int MAX_minimax(int[][] node,int player,int depth,Evaluator e){
        nodesExplored++;
        if(depth == 0 || BoardHelper.isGameFinished(node)){
            return e.eval(node,player);
        }
        int oplayer = (player==1) ? 2 : 1;

        if(!BoardHelper.hasAnyMoves(node,player)){
            return MIN_minimax(node,player,depth-1,e);
        }

        int score = Integer.MIN_VALUE;
        for(Point move : BoardHelper.getAllPossibleMoves(node,player)){ //my turn
            //create new node
            int[][] newNode = BoardHelper.getNewBoardAfterMove(node,move,player);
            //recursive call
            int childScore = MIN_minimax(newNode,player,depth-1,e);
            if(childScore > score) score = childScore;
        }

        return score;
    }


    private static int MIN_minimax(int[][] node,int player,int depth,Evaluator e){
        nodesExplored++;

        if(depth == 0 || BoardHelper.isGameFinished(node)){
            //BoardPrinter bpe = new BoardPrinter(node,"Depth : " + depth);
            return e.eval(node,player);
        }
        int oplayer = (player==1) ? 2 : 1;

        if(!BoardHelper.hasAnyMoves(node,oplayer)){
            //System.out.println("Forfeit State Reached !");
            return MAX_minimax(node,player,depth-1,e);
        }

        int score = Integer.MAX_VALUE;
        for(Point move : BoardHelper.getAllPossibleMoves(node,oplayer)){ //opponent turn
            //create new node
            int[][] newNode = BoardHelper.getNewBoardAfterMove(node,move,oplayer);
            //recursive call
            int childScore = MAX_minimax(newNode,player,depth-1,e);
            if(childScore < score) score = childScore;
        }

        return score;
    }
}
