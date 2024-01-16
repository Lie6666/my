package com.wgd.tankgame4;


import java.util.Vector;

//敌人的坦克
public class EnemyTank extends Tank implements Runnable {
    /**
     * 敌方发射子弹
     * 1、在敌人坦克类，使用vector保存多个short子弹【意义为每个敌方坦克都带有多个子弹】
     * 2、在panel构造器初始化循环定义多个敌方坦克的时候，
     * 给每个敌方坦克增加一个short子弹对象，并启动线程
     * 3、在panel画布paint中，循环画敌方坦克的时候
     * 再循环遍历每个坦克的shorts集合，画出每个坦克的多个存活的short子弹，
     * 如果不存活，就从shorts集合中删掉
     */
    Vector<Short> shorts = new Vector<>();


    public EnemyTank(int x, int y) {

        super(x, y);
    }

    public void moveUp() {//向上移动
        if (getDirect() != 0)
            setDirect(0);
        else {
            for (int i = 0; i < 30 * Math.random(); i++) {
                if (getY() > 0) {
                    super.moveUp();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void moveRight() {//向右移动
        if (getDirect() != 1)
            setDirect(1);
        else {
            for (int i = 0; i < 30 * Math.random(); i++) {
                if (getX() + 50 < 100) {
                    super.moveRight();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void moveDown() {//向下移动
        if (getDirect() != 2)
            setDirect(2);
        else {
            for (int i = 0; i < 30 * Math.random(); i++) {
                if (getY() + 60 < 750) {
                    super.moveDown();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void moveLeft() {//向左移动
        if (getDirect() != 3)
            setDirect(3);
        else {
            for (int i = 0; i < 30 * Math.random(); i++) {
                if (getX() - 10 > 0) {
                    super.moveLeft();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    //敌方坦克自由随机移动的线程
    @Override
    public void run() {
        while (true) {
            //当敌方子弹死亡时，重新发射子弹
            if (isLive && shorts.size() == 0) {
                Short s = null;
                //判断当前坦克方向创建新的子弹对象
                switch (getDirect()) {
                    case 0://向上
                        s = new Short(getX() + 20, getY(), 0);
                        // aShort=new Short(getX()+20,getY(),0);
                        break;
                    case 1://向右
                        s = new Short(getX() + 50, getY() + 30, 1);
                        //aShort=new Short(getX()+50,getY()+30,1);
                        break;
                    case 2://向下
                        s = new Short(getX() + 20, getY() + 60, 2);
                        //aShort=new Short(getX()+20,getY()+60,2);
                        break;
                    case 3://向左
                        s = new Short(getX() - 10, getY() + 30, 3);
                        // aShort=new Short(getX()-10,getY()+30 ,3);
                        break;
                }
                shorts.add(s);
                new Thread(s).start();
                //new Thread(aShort).start();
            }
            //根据目前方向移动一次
            switch (getDirect()) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveRight();
                    break;
                case 2:
                    moveDown();
                    break;
                case 3:
                    moveLeft();
                    break;

            }

            //移动完就休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //然后随机改变坦克方向0-3
            setDirect((int) (Math.random() * 4));

            //如果敌人坦克死掉，就退出
            if (isLive == false) {
                break;
            }
        }

    }

}
