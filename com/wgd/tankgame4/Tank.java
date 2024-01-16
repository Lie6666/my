package com.wgd.tankgame4;

public class Tank {
    private int x;//x.y为坦克左上角点的位置
    private int y;
    private int direct;//坦克朝向上右下左
    private int speed = 5;//坦克移动速度
    boolean isLive = true;


    //构造器
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //get和set方法

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
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


    //上右下左移动方法
    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

}
