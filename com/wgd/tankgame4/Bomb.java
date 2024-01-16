package com.wgd.tankgame4;


/**
 * 炸弹类
 * 爆炸效果
 * 1、写炸弹类【坐标 血 存活状态】并写减血的方法
 * 2、炸弹属于画板的，在MyPanel中定义vector数组存放炸弹对象
 * 3、定义三张炸弹图片，用于显示血量不同的三个时刻的效果
 * 4、MyPanel画板构造器初始化三张图片，既获取三张图片资源
 * 5、当子弹击中坦克时，加入一个bomb对象到bombs数组中，
 * 在hitTank方法中处理，创建Bomb对象并加入
 * 6、画炸弹，paint方法中，遍历bombs数组，如果有bomb对象就画出来
 */
public class Bomb {
    int x,y;//炸弹坐标
    int life=9;//炸弹的生命周期
    boolean isLife=true;//存活状态


    //构造器
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *  减少生命值
     *  作用：如果血大于零就减血，否则将生存状态致死
     */
    public void lifeDown(){
        if (life>0){
            life--;
        }else {
            isLife=false;
        }
    }

}
