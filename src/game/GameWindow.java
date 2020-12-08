package game;

import player.ai.AIPlayer;

import javax.swing.*;

public class GameWindow extends JFrame{

    public GamePanel gp = new GamePanel();
    public GamePlayer ai ;

    public GameWindow(){

        this.add(gp);

        //this.setSize(500,500);
        // set menu
        JMenuBar menuBar = new JMenuBar(); // Tao cac menu
        JMenu levelMenu = new JMenu("Level");
        JMenu m = new JMenu("...");
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                Undo(evt);
            }
        });
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                Reset(evt);
            }
        });

        JMenuItem setCpu = new JMenuItem("Alpha-Beta vs MiniMax");
        setCpu.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                CPU(evt);
            }
        });

        JMenu ai = new JMenu("Choose AI");
        JMenuItem minimax = new JMenuItem("MiniMax");
        minimax.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setMiniMax(evt);
            }
        });
        JMenuItem alphaBeta = new JMenuItem("Alpha - Beta");
        alphaBeta.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setAlphaBeta(evt);
            }
        });
        ai.add(minimax);
        ai.add(alphaBeta);

        // tao cac item
        JMenuItem level1 = new JMenuItem("Level 1");
        level1.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setLV1(evt);
            }
        });

        JMenuItem level2 = new JMenuItem("Level 2");
        level2.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setLV2(evt);
            }
        });

        JMenuItem level3 = new JMenuItem("Level 3");
        level3.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setLV3(evt);
            }
        });

        JMenuItem level4 = new JMenuItem("Level 4");
        level4.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setLV4(evt);
            }
        });

        JMenuItem level5 = new JMenuItem("Level 5");
        level5.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setLV5(evt);
            }
        });

        JMenuItem level6 = new JMenuItem("Level 6");
        level6.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                setLV6(evt);
            }
        });

        levelMenu.add(level1);
        levelMenu.add(level2);
        levelMenu.add(level3);
        levelMenu.add(level4);
        levelMenu.add(level5);
        levelMenu.add(level6);
        menuBar.add(ai);
        menuBar.add(levelMenu);
        m.add(setCpu);
        m.add(undo);
        m.add(reset);
        menuBar.add(m);

        this.setJMenuBar(menuBar);

        this.setTitle("Reversi");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void setLV1 (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            gp.reset();
            gp.setLevel(1);
        }
    }
    private void setLV2 (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            gp.reset();
            gp.setLevel(2);
        }
    }
    private void setLV3 (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            gp.reset();
            gp.setLevel(3);
        }
    }
    private void setLV4 (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            gp.reset();
            gp.setLevel(4);
        }
    }
    private void setLV5 (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            gp.reset();
            gp.setLevel(5);
        }
    }
    private void setLV6 (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            gp.reset();
            gp.setLevel(6);
        }
    }

    private void Undo (java.awt.event.ActionEvent evt){
        gp.Undo();
    }

    private void Reset (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);

        if(choose==0){
            GamePanel.CvsC = false;
            remove(gp);
            gp = new GamePanel();
            this.add(gp);
            paintAll(this.getGraphics());
            System.out.println("Restart");
        }

    }
    private void CPU (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            GamePanel.CvsC = true;
            remove(gp);
            gp = new GamePanel();
            this.add(gp);
            paintAll(this.getGraphics());
        }
    }
    private void setMiniMax (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            GamePanel.CvsC = false;
            gp.reset();
            this.ai = new AIPlayer(2,5,false);
            gp.setAI(this.ai);

        }
    }
    private void setAlphaBeta (java.awt.event.ActionEvent evt){
        int choose = JOptionPane.showConfirmDialog(null, "Restart game?", "Reversi", JOptionPane.YES_NO_OPTION);
        if(choose == 0){
            GamePanel.CvsC = false;
            gp.reset();
            this.ai = new AIPlayer(2,5,true);
            gp.setAI(this.ai);
        }
    }


    public static void main(String[] args) throws InterruptedException {/*
        int max =5;
        //new GameWindow();
        for(int i=0;i<4;i++){
            int a = max;
            while(a-->0){
                new GameWindow();
            }
            System.out.println(i);
            long start = System.currentTimeMillis();
            Thread.sleep(30000);
        }*/
        new GameWindow();
    }

}
