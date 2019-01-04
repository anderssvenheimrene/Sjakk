package com.company.JChess;

import com.company.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final static Dimension OUTTER_DIM = new Dimension(1000,1000);
    private final static Dimension BOARD_PANEL_DIM = new Dimension(400,400 );
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    public Table(){
        final JMenuBar tableMenuBar = createTableMenuBar();

        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setSize(OUTTER_DIM);
        this.gameFrame.setVisible(true);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
    }
    private JMenuBar createTableMenuBar(){
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }
    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("file");
        final JMenuItem openPGN = new JMenuItem("Load PGN File"); // in order to load old games
        openPGN.addActionListener(new ActionListener() {
            /**
             * this can be replaced with:
             * (e -> System.out.println("Open up that pgn file!"))
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open up that pgn file!");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }

    private class BoardPanel extends JPanel{
        final List <TilePanel> boardTiles;
        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i< Board.DIM; i++){
                for(int j = 0; j<Board.DIM; j++){
                    final TilePanel tilePanel = new TilePanel(this, i); 
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIM);
            validate();
        }
    }
    private class TilePanel extends JPanel{

        private final int tileID;

        TilePanel(final BoardPanel boardPanel, final int tileID){
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }
        private void assignTileColor(){}
    }
}
