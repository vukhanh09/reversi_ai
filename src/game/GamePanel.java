package game;

import Database.ActionDatabase;
import player.HumanPlayer;
import player.RandomPlayer;
import player.ai.AIPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class GamePanel extends JPanel implements GameEngine {

    //reversi board
    int[][] board = null;
    int[][][] undo = new int[60][8][8];
    int temp = 0;

    public static boolean CvsC = false;
    //player turn
    //black plays first
    int turn = 1;

    //swing elements
    BoardCell[][] cells = null;
    JLabel score1 = null;
    JLabel tab = null;
    JLabel score2 = null;

    int p1score = 0;
    int p2score = 0;

    GamePlayer player1 = new HumanPlayer(1);
    GamePlayer player2 = null;
    Timer player1HandlerTimer;
    Timer player2HandlerTimer;


    @Override
    public int getBoardValue(int i,int j){
        return board[i][j];
    }

    @Override
    public void setBoardValue(int i,int j,int value){
        board[i][j] = value;
    }

    public GamePanel(){

        if(CvsC){
            this.player1 = new AIPlayer(1,5,true);//Alpha - Beta
            this.player2 = new AIPlayer(2,5,false);//MiniMax
        }
        else{
            player2 = new AIPlayer(2,1,true);
        }

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        JPanel reversiBoard = new JPanel();
        reversiBoard.setLayout(new GridLayout(8,8));
        reversiBoard.setPreferredSize(new Dimension(500,500));
        reversiBoard.setBackground(new Color(41,100, 59));

        //init board
        resetBoard();

        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this,reversiBoard,i,j);
                reversiBoard.add(cells[i][j]);
            }
        }


        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(110,150,230));
        score1 = new JLabel();
        score1.setFont(new Font(" ",Font.CENTER_BASELINE,15));
        score1.setForeground(Color.black);
        score2 = new JLabel();
        score2.setFont(new Font(" ",Font.CENTER_BASELINE,15));
        score2.setForeground(Color.white);
        tab = new JLabel("     --:--     ");
        tab.setForeground(Color.green);
        tab.setFont(new Font(" ",Font.CENTER_BASELINE,15));

        sidebar.add(score1);
        sidebar.add(tab);
        sidebar.add(score2);


        this.add(sidebar,BorderLayout.NORTH);
        this.add(reversiBoard);

        //
        updateBoardInfo();
        //updateTotalScore();

        //AI Handler Timer (to unfreeze gui)
        player1HandlerTimer = new Timer(100,(ActionEvent e) -> {
            handleAI(player1);
            player1HandlerTimer.stop();
            manageTurn();
        });

        player2HandlerTimer = new Timer(100,(ActionEvent e) -> {
            handleAI(player2);
            player2HandlerTimer.stop();
            manageTurn();
        });

        manageTurn();

    }

    private boolean awaitForClick = false;

    public void manageTurn(){
        if(BoardHelper.hasAnyMoves(board,1) || BoardHelper.hasAnyMoves(board,2)) {
            updateBoardInfo();
            if (turn == 1) {
                if(BoardHelper.hasAnyMoves(board,1)) {
                    if (player1.isUserPlayer()) {
                        awaitForClick = true;
                        //after click this function should be call backed
                    } else {
                        player1HandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 1 has no legal moves !");
                    turn = 2;
                    manageTurn();
                }
            } else {
                if(BoardHelper.hasAnyMoves(board,2)) {
                    if (player2.isUserPlayer()) {
                        awaitForClick = true;
                        //after click this function should be call backed
                    } else {
                        player2HandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 2 has no legal moves !");
                    turn = 1;
                    manageTurn();
                }
            }
        }else{
            //game finished
            try {
                new ActionDatabase().averageNode(player1, player2);
            } catch (Exception e) {
                System.out.println("...");
            }
            System.out.println("Game Finished !");

            int winner = BoardHelper.getWinner(board);
            //JOptionPane.setDefaultLocale();
            if(CvsC){
                if(winner==1)
                    JOptionPane.showMessageDialog(null,"                           Alpha-Beta WIN","Reversi",JOptionPane.PLAIN_MESSAGE);
                else if(winner==2)
                    JOptionPane.showMessageDialog(null,"                            MiniMax WIN","Reversi",JOptionPane.PLAIN_MESSAGE);

            }
            else{
                if(winner==1)
                    JOptionPane.showMessageDialog(null,"                             YOU WIN\n"+"               YOU : " + p1score+"     --:--     "+p2score+" : CPU  ","Reversi",JOptionPane.PLAIN_MESSAGE);
                else if(winner==2)
                    JOptionPane.showMessageDialog(null,"                            YOU LOSE\n"+"               YOU : " + p1score+"     --:--     "+p2score+" : CPU  ","Reversi",JOptionPane.PLAIN_MESSAGE);

                reset();
                player1HandlerTimer = new Timer(100,(ActionEvent e) -> {
                    handleAI(player1);
                    player1HandlerTimer.stop();
                    manageTurn();
                });

                player2HandlerTimer = new Timer(100,(ActionEvent e) -> {
                    handleAI(player2);
                    player2HandlerTimer.stop();
                    manageTurn();
                });

                manageTurn();
            }
            //updateTotalScore();
            //restart
            //resetBoard();
            //turn=1;
            //manageTurn();
        }
    }

    public void resetBoard(){
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j]=0;
            }
        }
        //initial board state
        setBoardValue(3,3,2);
        setBoardValue(3,4,1);
        setBoardValue(4,3,1);
        setBoardValue(4,4,2);
        undo[0] = board;
    }

    //update highlights on possible moves and scores
    public void updateBoardInfo(){
         p1score = 0;
         p2score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == 1) p1score++;
                if(board[i][j] == 2) p2score++;

                if(BoardHelper.canPlay(board,turn,i,j)){
                    if(turn==1)
                        cells[i][j].highlight = 1;
                    else
                        cells[i][j].highlight = 2;
                }else{
                    cells[i][j].highlight = 0;
                }
            }
        }
        if(turn==1){
            score1.setOpaque(true);
            score1.setBackground(Color.lightGray);
            score2.setOpaque(true);
            score2.setBackground(new Color(110,150,230));
        }
        else{
            score2.setOpaque(true);
            score2.setBackground(Color.lightGray);
            score1.setOpaque(true);
            score1.setBackground(new Color(110,150,230));
        }

        if(CvsC){
            score1.setText("  Alpha-Beta : " + p1score+"  ");
            score2.setText("  "+p2score+" : MiniMax  ");
        }
        else{
            score1.setText("  YOU : " + p1score+"  ");
            score2.setText("  "+p2score+" : CPU  ");
        }

       // score2.setText(player2.playerName() + " : " + p2score);
    }

//    public void updateTotalScore(){
//        tscore1.setText(player1.playerName() + " : " + totalscore1);
//        tscore2.setText(player2.playerName() + " : " + totalscore2);
//    }

    @Override
    public void handleClick(int i,int j){
        if(awaitForClick && BoardHelper.canPlay(board,turn,i,j)){
            System.out.println("User Played in : "+ i + " , " + j);

            undo[temp] = board;
            temp++;

            //update board
            board = BoardHelper.getNewBoardAfterMove(board,new Point(i,j),turn);


            //advance turn
            turn = (turn == 1) ? 2 : 1;

            repaint();

            awaitForClick = false;

            //callback
            manageTurn();
        }
    }

    public void handleAI(GamePlayer ai){
        Point aiPlayPoint = ai.play(board);
        try{
            int i = aiPlayPoint.x;
            int j = aiPlayPoint.y;
            if(!BoardHelper.canPlay(board,ai.myMark,i,j)) System.err.println("FATAL : AI Invalid Move !");
            System.out.println(ai.playerName() + " Played in : "+ i + " , " + j);
            //update board
            board = BoardHelper.getNewBoardAfterMove(board,aiPlayPoint,turn);
            //advance turn
            turn = (turn == 1) ? 2 : 1;
        }
        catch (Exception e){
            System.out.println("...");
        }


        repaint();
    }

    public void setLevel(int lv){
        if(CvsC){
            player1.setSearchDepth(lv);
            player2.setSearchDepth(lv);
        }
        else{
            player1.setSearchDepth(lv);
        }

    }

    public void setAI(GamePlayer ai){
        this.player2 = ai;
    }

//    public void setCpu(){
//
//    }

    public void Undo(){
        if(turn == 1 && temp>0){
            temp--;
            board = undo[temp];
            updateBoardInfo();
            repaint();
        }
    }

    public void reset(){
        resetBoard();
        updateBoardInfo();
        if(!CvsC){
            player1 = new HumanPlayer();
        }
        turn = 1;
        temp = 0;
        repaint();
    }

    public void finalize(){
        System.out.println("gc");
    }

}
