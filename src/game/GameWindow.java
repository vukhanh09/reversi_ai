package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow(){
        GamePanel gp = new GamePanel();
        this.add(gp);

        //this.setSize(500,500);
        // set menu
        JMenuBar menuBar = new JMenuBar(); // Tao cac menu
        JMenu levelMenu = new JMenu("Level");
        JMenu undo = new JMenu("Undo");
        JMenu reset = new JMenu("Reset");
        JMenu ai = new JMenu("Choose AI");
        JMenuItem minimax = new JMenuItem("MiniMax");
        JMenuItem alphaBeta = new JMenuItem("Alpha - Beta");
        ai.add(minimax);
        ai.add(alphaBeta);

        // tao cac item
        JMenuItem level1 = new JMenuItem("Level 1");
        //newMenuItem.setActionCommand("New");
        JMenuItem level2 = new JMenuItem("Level 2");
        //openMenuItem.setActionCommand("Open");
        JMenuItem level3 = new JMenuItem("Level 3");
        //saveMenuItem.setActionCommand("Save");
        JMenuItem level4 = new JMenuItem("Level 4");
        //exitMenuItem.setActionCommand("Exit");
        JMenuItem level5 = new JMenuItem("Level 5");
        //cutMenuItem.setActionCommand("Cut");
        JMenuItem level6 = new JMenuItem("Level 6");
        //copyMenuItem.setActionCommand("Copy");
        levelMenu.add(level1);
        levelMenu.add(level2);
        levelMenu.add(level3);
        levelMenu.add(level4);
        levelMenu.add(level5);
        levelMenu.add(level6);
        menuBar.add(ai);
        menuBar.add(levelMenu);
        menuBar.add(undo);
        menuBar.add(reset);
        this.setJMenuBar(menuBar);

        this.setTitle("Reversi");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public static void main(String[] args) {

        new GameWindow();
    }

}
