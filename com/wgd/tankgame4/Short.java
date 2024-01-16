package com.wgd.tankgame4;

/**
 * short线程实际就是子弹被射出后的移动的过程
 * 射击全过程解析：
 * 1、主方法创建new MyPanel时，自动调用里面的paint方法
 * 在画布上画出所有我方和敌方的所有坦克，并等待键盘端的按键指令
 * 2、当键盘按下J键时，调用hero的shortEnemyTank射击方法，
 * 来初始化子弹出生位置和方向，并启动short线程方法【子弹持续移动】
 */
public class Short implements Runnable {
    int x;
     int y;//子弹横纵坐标
    int direct;//子弹方向
    int speed = 10;//子弹速度
    boolean isLive = true;//记录子弹是否还存活

    //构造器初始化子弹的横纵坐标和方向，速度用set方法设置
    public Short(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    //属性的get，set方法

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    /**
     *子弹的线程方法,子弹被射出后移动的过程
     *先休眠50秒，再根据方向改变X，或Y的值，再打印出x和y的值，
     *再判断子弹位置是否越界，如果越界就标记死亡后break跳出死循环结束此线程
     */
    @Override
    public void run() {//射击
        while (true) {

            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变x和y的值
            switch (direct) {
                case 0://向上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2://向下
                    y += speed;
                    break;
                case 3://向左
                    x -= speed;
                    break;
            }
            //用来测试，输出一下x和y的坐标
            System.out.println("子弹的x=" + x + "  y=" + y);
            //挡子弹移动到面板边界的时候就销毁(把启动的子弹线程销毁)
            if (!(x <= 1000 && x >= 0&& y >= 0&& y <= 750&&isLive)) {
                System.out.println("子弹越界，break退出");
                isLive = false;
                break;
            }


        }

    }
}
