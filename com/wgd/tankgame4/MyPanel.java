package com.wgd.tankgame4;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//坦克大战绘图区域
@SuppressWarnings({"all"})

public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人的坦克放入vector集合中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌人坦克集合大小
    int enemyTanksSize = 5;


    //定义一个vector集合数组，用于存放炸弹
    //当子弹记中坦克时，加入一个bomb对象到bombs数组中
    Vector<Bomb> bombs = new Vector<>();

    //定义三张炸弹图片，用于显示炸弹效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    //构造器初始化坦克，包括坦克方向位置等
    public MyPanel() {
        //初始化自己的坦克
        hero = new Hero(480, 500);

        //初始化敌人的坦克
        for (int i = 0; i < enemyTanksSize; i++) {
            //创建敌人坦克
            EnemyTank enemyTank = new EnemyTank((130* (i + 1)), 50);
            //设置此坦克的初始化方向
            enemyTank.setDirect(0);
            //启动敌人线程移动
            new Thread(enemyTank).start();
            //给该enemyTank【敌方坦克】加入一颗子弹
            Short t = new Short(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            //加入enemyTank的Vector成员
            enemyTank.shorts.add(t);
            //启动子弹线程
            new Thread(t).start();
            //把此对象添加到vector集合
            enemyTanks.add(enemyTank);
        }

        //初始化图片对象，既引入三张图片
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getClassLoader().getResource("image/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getClassLoader().getResource("image/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getClassLoader().getResource("image/bomb_3.gif"));

    }


    //绘图区
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充黑色覆盖背景的矩形
        g.fillRect(0, 0, 1000, 750);
        //直接在这画坦克不太好，封装到方法中


        //绘制自己的hero坦克
        if (hero != null && hero.isLive)
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);


        //绘制自己坦克的子弹
        /**
         * 应该是子弹发射才绘制
         * hero.sort!=null 意味着 有short对象，说明发射了
         * isLive！=false 意味着  子弹还存活【没越界，没撞到敌方坦克等情况】
         */
        //自己坦克只能发射一颗子弹
        if (hero.sort != null && hero.sort.isLive) {
            //g.draw3DRect(hero.sort.x,hero.sort.y,2,2,false);
            g.fillOval(hero.sort.x - 1, hero.sort.y - 1, 3, 3);
        }
        //自己坦克可以发射多颗子弹
//        for (int i = 0; i < hero.shorts.size(); i++) {
//            Short aShort = hero.shorts.get(i);
//            if (aShort != null && aShort.isLive)
//                g.fillOval(aShort.x - 1, aShort.y - 1, 3, 3);
//            else
//                hero.shorts.remove(aShort);
//        }


        //爆炸效果，如果bombs数组中有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹对象
            Bomb bomb = bombs.get(i);
            //根据当前对象的life值去画对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 0) {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb的life为0了，就从bombs集合中删除此bomb
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }


        //绘制敌方的坦克，遍历vector数组
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克enemytank对象
            EnemyTank enemyTank = enemyTanks.get(i);
            //画出敌方坦克,  只有敌方坦克存活的时候才画
            if (enemyTank.isLive == true) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);

                //画出enemyTank的子弹
                for (int j = 0; j < enemyTank.shorts.size(); j++) {
                    //取出shorts集合中的子弹对象
                    Short aShort = enemyTank.shorts.get(j);
                    //判断对象是否死亡isLive，再绘制
                    if (aShort.isLive) {//如果子弹还存活，就绘制
                        g.fillOval(aShort.x - 1, aShort.y - 1, 3, 3);
                    } else {//如果子弹死了isLive==false，则把其从vector中删除
                        enemyTank.shorts.remove(aShort);

                    }

                }
            }

        }


    }

    //编写方法判断子弹是否击中敌方坦克，效果是如果击中就会把子弹和坦克生存状态致死
    public void hitTank(Short s, Tank enemyTank) {
        switch (enemyTank.getDirect()) {
            case 0:
            case 2://坦克向上向下
                //子弹位置在坦克坐标体内
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    //将子弹和坦克的生存状态置死
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //子弹记中坦克，创建Bomb对象，并加入bombs数组中
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);

                }
                break;
            case 1:
            case 3://坦克向左向右
                if (s.x > (enemyTank.getX() - 10) && s.x < (enemyTank.getX() + 50)
                        && s.y > (enemyTank.getY() + 10) && s.y < (enemyTank.getY() + 50)) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //子弹记中坦克，创建Bomb对象，并加入bombs数组中
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                }

                break;


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
            else if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//d键右移
            if (hero.getDirect() != 1)
                hero.setDirect(1);
            else if (hero.getX() + 50 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//s键下移
            if (hero.getDirect() != 2)
                hero.setDirect(2);
            else if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//a键左移
            if (hero.getDirect() != 3)
                hero.setDirect(3);
            else if (hero.getX() - 10 > 0) {
                hero.moveLeft();
            }
        }
        //如果用户按下的键是J，就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //drawsort(hero.sort.getX(),hero.sort.getY(),g,0);
            //判断自己的坦克子弹是否存在再决定能否发射子弹
            if (hero.sort == null || !hero.sort.isLive)
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
     * 重绘了，作用是每按一次键盘重绘一次画布
     * 但是子弹的触发只按一次键，也就只重绘一次，后续是自动跑并不会重绘，因此需要不停重绘画布
     */
    @Override
    public void run() {//每隔 100毫秒，重绘panel画布
        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //每隔100毫秒就判断自己坦克的子弹是否击中了敌方坦克
            /**
             * 敌方坦克消失*
             * 编写hitTank方法传入一个我方子弹一个敌方坦克，判断子弹是否在敌方坦克内
             * 在线程里如果自己坦克子弹对象存在【发射了子弹】且生存状态存活
             * 才每隔100毫秒遍历一遍所有的敌方坦克，调用hitTank方法如果击中就把子弹和坦克生存状态致死
             * 绘制坦克和子弹的时候，判断如果生存状态存活才绘制，否则不绘制
             */
            if (hero.sort != null && hero.sort.isLive) {//自己坦克子弹存活
                for (int i = 0; i < enemyTanks.size(); i++) {//遍历敌方所有坦克

                    //取出遍历的坦克对象
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.sort, enemyTank);
                }
            }
            //判断敌人子弹是否击中自己坦克
            for (int i = 0; i < enemyTanks.size(); i++) {//遍历敌方坦克
                EnemyTank enemyTank = enemyTanks.get(i);//取出敌方坦克
                for (int j = 0; j < enemyTank.shorts.size(); j++) {//遍历这个坦克的子弹
                    Short s = enemyTank.shorts.get(j);//取出坦克的一个子弹
                    if (hero.isLive && s.isLive)//判断自己的坦克是否存活，敌人子弹是否存活
                        hitTank(s, hero);
                }
            }

            this.repaint();
        }

    }
}
