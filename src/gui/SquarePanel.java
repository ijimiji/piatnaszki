package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


class SquarePanel extends JLabel {
    public SquarePanel(String text){
        setText(text);
        setBorder(new LineBorder(Color.BLACK));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d;
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(10, 10);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (Math.min(w, h));
        return new Dimension(s, s);
    }
}
