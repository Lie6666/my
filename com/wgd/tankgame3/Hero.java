package com.wgd.tankgame3;

public class Hero extends Tank {
    //定义一个short对象，表示一个射击（线程）
    Short sort = null;

    public Hero(int x, int y) {

        super(x, y);
    }


    /**
     * 射击动作，坦克射出子弹的过程【包括子弹出生位置的确定，和子弹线程的启动】
     * 此方法依据hero坦克的位置、方向来创建位置、方向不同的子弹线程对象
     * 确定子弹出生位置和方向后，启动子弹移动线程（short线程）
     */
    public void shortEnemyTank(){
        //创建short对象，根据当前Hero坦克位置和方向来创建不同的short对象
        switch (getDirect()){
            case 0://向上
                sort =new Short(getX()+20,getY(),0);
                break;
            case 1://向右
                sort=new Short(getX()+50,getY()+30,1);
                break;
            case 2://向下
                sort=new Short(getX()+20,getY()+60,2);
                break;
            case 3://向左
                sort=new Short(getX()-10,getY()+30 ,3);
                break;
        }
        //启动我们的short线程
        new Thread(sort).start();
    }

}
