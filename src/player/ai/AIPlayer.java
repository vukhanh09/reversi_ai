package player.ai;

import game.GamePlayer;

import java.awt.*;

public class AIPlayer extends GamePlayer {

    private int searchDepth;
    private Evaluator evaluator;
    private boolean option;


    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    public void setSearchDepth(int depth){
        this.searchDepth = depth;
    }

    public AIPlayer(int mark, int depth,boolean option) {
        super(mark);
        searchDepth = depth;
        this.option = option;
        if(mark==1) {
            evaluator = new RealtimeEvaluator(new int[][] {
                    {8, 85, -40, 10, 210, 520},
                    {8, 85, -40, 10, 210, 520},
                    {33, -50, -15, 4, 416, 2153},
                    {46, -50, -1, 3, 612, 4141},
                    {51, -50, 62, 3, 595, 3184},
                    {33, -5,  66, 2, 384, 2777},
                    {44, 50, 163, 0, 443, 2568},
                    {13, 50, 66, 0, 121, 986},
                    {4, 50, 31, 0, 27, 192},
                    {8, 500, 77, 0, 36, 299}},
                    new int[] {0, 55, 56, 57, 58, 59, 60, 61, 62, 63});
        }else{
            evaluator = new RealtimeEvaluator(new int[][] {
                    {8, 85, -40, 10, 210, 520},
                    {8, 85, -40, 10, 210, 520},
                    {33, -50, -15, 4, 416, 2153},
                    {46, -50, -1, 3, 612, 4141},
                    {51, -50, 62, 3, 595, 3184},
                    {33, -5,  66, 2, 384, 2777},
                    {44, 50, 163, 0, 443, 2568},
                    {13, 50, 66, 0, 121, 986},
                    {4, 50, 31, 0, 27, 192},
                    {8, 500, 77, 0, 36, 299}},
                    new int[] {0, 55, 56, 57, 58, 59, 60, 61, 62, 63});
        }
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        if(option){
            return "AI Pruning (Depth " + searchDepth + ")";
        }
        else
            return "AI (Depth " + searchDepth + ")";
    }

    @Override
    public Point play(int[][] board) {
        if(option){
            return MinimaxPruning.solve(board,myMark,searchDepth,evaluator);
        }
        else
            return Minimax.solve(board,myMark,searchDepth,evaluator);
    }
}
