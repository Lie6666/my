package com.wgd.tankgame3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//坦克大战绘图区域
@SuppressWarnings({"all"})

public class MyPanel extends JPanel implements KeyListener ,Runnable{
    //定义我的坦克
    Hero hero = null;

    //定义敌人的坦克放入vector集合中
    Vector<EnemyTank> enemyTanks=new Vector<>();
    //集合大小
    int enemyTanksSize=3;




    //构造器初始化坦克，包括坦克方向位置等
    public MyPanel() {
        //初始化自己的坦克
        hero = new Hero(100, 100);

        //初始化敌人的坦克
        for (int i=0;i<enemyTanksSize;i++){
            //创建敌人坦克
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            //设置此坦克的初始化方向
            enemyTank.setDirect(2);
            //把此对象添加到vector集合
            enemyTanks.add(enemyTank);
        }
    }



    //绘图区
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充黑色覆盖背景的矩形
        g.fillRect(0,0,1000,750);
        //直接在这画坦克不太好，封装到方法中


        //绘制自己的hero坦克
        drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        //绘制自己坦克的子弹
        /**
         * 应该是子弹发射才绘制
         * hero.sort!=null 意味着 有short对象，说明发射了
         * isLive！=false 意味着  子弹还存活【没越界，没撞到敌方坦克等情况】
         */
        if (hero.sort!=null&&hero.sort.isLive!=false){
            //g.draw3DRect(hero.sort.x,hero.sort.y,2,2,false);
            g.fillOval(hero.sort.x-1,hero.sort.y-1,3,3);
        }


        //绘制敌方的坦克，遍历vector数组
        for (int i=0;i<enemyTanks.size();i++){
            //遍历获取enemytank对象
            EnemyTank enemyTank=enemyTanks.get(i);
            //画出遍历集合数量的敌方坦克对象
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),0);
        }
    }



    //编写方法绘制坦克（需要传入坦克的x，y位置方向和类型）
    /**
     * 通过给定的X，Y的位置、给的画笔，
     * 先判断本次要画坦克类型(己方坦克或者敌方坦克)  选择画笔颜色，
     * 再判断坦克的方向，绘制不同的坦克
     *
     * @param x      坦克左上角的x坐标
     * @param y      坦克左上角的y坐标
     * @param g      画笔
     * @param direct 坦克头的方向
     *               上  右   下   左
     *               w   d    s    a
     *               0   1    2    3
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型的坦克，设置不同的画笔颜色
        switch (type) {
            case 0://敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1://我的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向绘制坦克
        switch (direct) {
            case 0://绘制向上的坦克
                g.fill3DRect(x, y, 10, 60, false);//坦克左轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中间体
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右轮子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间圆盖
                g.drawLine(x + 20, y + 30, x + 20, y);//坦克炮筒
                break;
            case 1://绘制向右的坦克
                g.fill3DRect(x - 10, y + 10, 60, 10, false);//坦克左轮子
                g.fill3DRect(x, y + 20, 40, 20, false);//坦克中间体
                g.fill3DRect(x - 10, y + 40, 60, 10, false);//坦克右轮子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间圆盖
                g.drawLine(x + 20, y + 30, x + 50, y + 30);
                break;
            case 2://绘制向下的坦克
                g.fill3DRect(x, y, 10, 60, false);//坦克左轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中间体
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右轮子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间圆盖
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//坦克炮筒
                break;
            case 3://绘制向左的坦克
                g.fill3DRect(x - 10, y + 10, 60, 10, false);//坦克左轮子
                g.fill3DRect(x, y + 20, 40, 20, false);//坦克中间体
                g.fill3DRect(x - 10, y + 40, 60, 10, false);//坦克右轮子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间圆盖
                g.drawLine(x + 20, y + 30, x - 10, y + 30);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }




    @Override
    public void keyTyped(KeyEvent e) {

    }

    //当某个键被按下时，该方法会被触发
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//w键上移
            if (hero.getDirect() != 0)
                hero.setDirect(0);
            else
                hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//d键右移
            if (hero.getDirect() != 1)
                hero.setDirect(1);
            else
                hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//s键下移
            if (hero.getDirect() != 2)
                hero.setDirect(2);
            else
                hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//a键左移
            if (hero.getDirect() != 3)
                hero.setDirect(3);
            else
                hero.moveLeft();
        }
        //如果用户按下的键是J，就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //drawsort(hero.sort.getX(),hero.sort.getY(),g,0);
            hero.shortEnemyTank();
        }

        //每按一下键盘会让画布repaint重绘一次
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }



    /**
     * 为了让panel不停的重绘子弹，需要将panel设置成一个独立线程，当作线程使用
     * 坦克移动会重绘是因为 按键监听器【public void keyPressed(KeyEvent e)】方法中
     *  重绘了，作用是每按一次键盘重绘一次画布
     * 但是子弹的触发只按一次键，也就只重绘一次，后续是自动跑并不会重绘，因此需要不停重绘画布
     */
    @Override
    public void run() {//每隔 100毫秒，重绘panel画布
        while (true){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.repaint();
        }

    }
}
