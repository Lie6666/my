package com.wgd.Text;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    Image image=Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("image/bomb_1.gif"));
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image,100,100,60,60,this);

    }
}
