package com.wgd.tankgame4;

import javax.swing.*;


public class TankGame04 extends JFrame {//JFrame相当于画框
    //定义一个画板(绘图区域)并放入画框
    private MyPanel mp=null;

    public static void main(String[] args) {

        TankGame04 tankGame03 = new TankGame04();
    }

    //画框的构造器
    public TankGame04(){
        mp=new MyPanel();
        this.add(mp);//把面板加进去
        new Thread(mp).start();//启动panel画布线程
        this.setSize(1000,750);//设置画框的大小
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点×自动退出
        this.setVisible(true);//可显示

    }

}
