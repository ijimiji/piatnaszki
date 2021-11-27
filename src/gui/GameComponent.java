package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameComponent extends JFrame {
    GridLayout gridLayout = new GridLayout(4, 4);
    JPanel gridPanel = new JPanel();
    SquarePanel[][] grid = new SquarePanel[4][4];
    Integer tokenPositionVertical = 3;
    Integer tokenPositionHorizontal = 3;

    public GameComponent() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        add(gridPanel);
        gridPanel.setLayout(gridLayout);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new GameEventDispatcher());

        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                var cell = new SquarePanel(Integer.toString((i * 4 + j + 1) % 16));
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setVerticalAlignment(SwingConstants.CENTER);
                cell.setOpaque(true);
                grid[i][j] = cell;
                gridPanel.add(cell);
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        makeMoveByClick((SquarePanel) e.getSource());
                    }
                });
            }
        }
        makeMove(1, 0, 1);

        validateGrid();
        setVisible(true);
    }

    private void makeMoveByClick(SquarePanel panel) {
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                if (grid[i][j].equals(panel)) {
                    int dy = tokenPositionVertical - i;
                    int dx = tokenPositionHorizontal - j;
                    if (Math.abs(dx) + Math.abs(dy) < 2) {
                        makeMove(dy, dx, 1);
                    }
                }
            }
        }
    }

    private void makeMove(Integer vertical, Integer horizontal, Integer times) {
        for (var k = 0; k < times; k++) {
            Integer i = tokenPositionVertical;
            Integer j = tokenPositionHorizontal;
            int shiftedI = i - vertical;
            int shiftedJ = j - horizontal;

            if (shiftedI > 3 || shiftedJ > 3) {
                return;
            }
            if (shiftedI < 0 || shiftedJ < 0) {
                return;
            }

            grid[i][j].setText(grid[shiftedI][shiftedJ].getText());
            tokenPositionVertical = shiftedI;
            tokenPositionHorizontal = shiftedJ;
            grid[shiftedI][shiftedJ].setText("0");
            validateGrid();
        }
    }

    private void validateGrid() {
        boolean isOrdered = true;
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                var cell = grid[i][j];
                if (cell.getText().equals(Integer.toString((i * 4 + j + 1) % 16))) {
                    cell.setBackground(Color.GREEN);
                } else {
                    cell.setBackground(Color.RED);
                    isOrdered = false;
                }
                if (cell.getText().equals("0")) {
                    cell.setBackground(Color.WHITE);
                }
            }
        }
        if (isOrdered) {
            JOptionPane.showMessageDialog(null, "Congrats! You've solved the Puzzle!");
        }
    }

    private class GameEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() != KeyEvent.KEY_PRESSED) return false;
            Integer times = 1;
            if (e.isAltDown()) {
                times = 3;
            }

            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN -> makeMove(1, 0, times);
                case KeyEvent.VK_UP -> makeMove(-1, 0, times);
                case KeyEvent.VK_RIGHT -> makeMove(0, 1, times);
                case KeyEvent.VK_LEFT -> makeMove(0, -1, times);
            }
            return false;
        }
    }
}
