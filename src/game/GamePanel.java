package game;

import player.*;
import player.ai.AIPlayerRealtime;
import player.ai.Ai2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements GameEngine {

    //reversi board
    int[][] board;

    //player turn
    //black plays first
    int turn = 1;

    //swing elements
    BoardCell[][] cells;
    JLabel score1;
    JLabel tab;
    JLabel score2;

    int p1score = 0;
    int p2score = 0;



    GamePlayer player1 = new Ai2(1,6);
    GamePlayer player2 = new AIPlayerRealtime(2,6);
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
        player1HandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(player1);
            player1HandlerTimer.stop();
            manageTurn();
        });

        player2HandlerTimer = new Timer(1000,(ActionEvent e) -> {
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
            System.out.println("Game Finished !");

            int winner = BoardHelper.getWinner(board);
            //JOptionPane.setDefaultLocale();
            if(winner==1)
                JOptionPane.showMessageDialog(null,"                             YOU WIN\n"+"               YOU : " + p1score+"     --:--     "+p2score+" : CPU  ","Reversi",JOptionPane.PLAIN_MESSAGE);
            else if(winner==2)
                JOptionPane.showMessageDialog(null,"                            YOU LOSE\n"+"               YOU : " + p1score+"     --:--     "+p2score+" : CPU  ","Reversi",JOptionPane.PLAIN_MESSAGE);

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
                    cells[i][j].highlight = 1;
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

        score1.setText("  YOU : " + p1score+"  ");
        score2.setText("  "+p2score+" : CPU  ");
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
        int i = aiPlayPoint.x;
        int j = aiPlayPoint.y;
        if(!BoardHelper.canPlay(board,ai.myMark,i,j)) System.err.println("FATAL : AI Invalid Move !");
        System.out.println(ai.playerName() + " Played in : "+ i + " , " + j);

        //update board
        board = BoardHelper.getNewBoardAfterMove(board,aiPlayPoint,turn);

        //advance turn
        turn = (turn == 1) ? 2 : 1;

        repaint();
    }

}
