package com.wgd.Text;

import javax.swing.*;
import java.awt.*;

public class Exercise extends JFrame {
    private MyPanel mp=null;
    public static void main(String[] args) {
        Exercise exercise = new Exercise();

    }
    public Exercise(){
        mp=new MyPanel();
        this.add(mp);
        this.setSize(1000,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
