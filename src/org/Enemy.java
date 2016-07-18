package org;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
	//坐标
	private int x;
	private int y;
	//初始坐标
	private int xstart;
	private int ystart;
	//Redwolf上下移动极限
	private int upMax;
	private int downMax;
	//敌人类型
	private int type;
	//定义一个图片的索引
	private int temp = 0;
	//当前的显示图片
	private BufferedImage showImage = null;
	//图片初始类型
	private int imageType = 0;
	//当前所处的场景
	private BackGround bg;
	//当前的移动状态
	private boolean isUpOrLeft;
	//定义一个线程
	private Thread t = new Thread(this);
	
	//对Graywolf的构造方法
	public Enemy(int x, int y, boolean isLeft, int type, BackGround bg){
		this.x = x;
		this.y = y;
		this.isUpOrLeft = isLeft;
		this.bg = bg;
		this.type = type;
		this.xstart = x;
		this.ystart = y;
		this.imageType = type;
		if(this.type == 1){
			this.showImage = StaticValue.allGraywolfImage.get(4);
		}
		t.start();
		//挂起进程，在开始游戏前，敌人静止
		if(this.bg.getSort() == 1){
			t.suspend();
		}
	}
	//对Redwolf的构造方法
	public Enemy(int x, int y, boolean isUp, int type, int upMax, int downMax, BackGround bg){
		this.x = x;
		this.y = y;
		this.isUpOrLeft = isUp;
		this.downMax = downMax;
		this.upMax = upMax;
		this.bg = bg;
		this.type = type;
		this.xstart = x;
		this.ystart = y;
		this.imageType = type;
		if(this.type == 2){
			this.showImage = StaticValue.allRedwolfImage.get(0);
		}
		t.start();
		t.suspend();
	}

	public void run() {
		//定义一个当前的图片索引
		int graywolfTemp = 4;
		int redwolfTemp = 0;
		while(true){
			//对于不同的敌人做不同处理
			//对Graywolf的处理
			this.temp = 0;
			if(this.type == 1){
				if(this.isUpOrLeft){
					this.x -= 2;
				}else{
					this.x += 2;
				}
				//对障碍物的判断
				//定义是否可以移动标记
				boolean canLeft = true;
				boolean canRight = true;
				for(int i = 0; i < this.bg.getAllObstruction().size(); i++){
					Obstruction ob = this.bg.getAllObstruction().get(i);
					//如果向右移动的过程中碰到障碍物，不允许继续向右移动
					if(ob.getX() == this.x + 60 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canRight = false;
					}
					//如果向左移动的过程中碰到障碍物，不允许继续向左移动
					if(ob.getX() == this.x - 60 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canLeft = false;
					}
				}
				if(this.isUpOrLeft && !canLeft || this.x == 0){
					this.isUpOrLeft = false;
				}else if(!this.isUpOrLeft && !canRight || this.x == 840){
					this.isUpOrLeft = true;
				}
				if(this.isUpOrLeft){
					this.temp += graywolfTemp;
					graywolfTemp++;
					if(graywolfTemp == 7){
						graywolfTemp = 4;
					}
				}else{
					graywolfTemp--;
					this.temp += graywolfTemp;
					if(graywolfTemp == 0){
						graywolfTemp = 3;
					}
				}
				this.showImage = StaticValue.allGraywolfImage.get(temp);
			}
			//对Redwolf的处理
			if(this.type == 2){
				if(this.isUpOrLeft){
					this.y -= 2;
				}else{
					this.y += 2;
				}
				if(this.isUpOrLeft && this.y == this.upMax){
					this.isUpOrLeft = false;
				}
				if(!this.isUpOrLeft && this.y == this.downMax){
					this.isUpOrLeft = true;
				}
				temp += redwolfTemp;
				redwolfTemp++;
				if(redwolfTemp == 3){
					redwolfTemp = 0;
				}
				this.showImage = StaticValue.allRedwolfImage.get(temp);
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reset(){
		this.x = this.xstart;
		this.y = this.ystart;
		if(this.imageType == 1){
			this.showImage = StaticValue.allGraywolfImage.get(0);
		}else{
			this.showImage = StaticValue.allRedwolfImage.get(0);
		}
	}
	
	public void dead(){
		 this.bg.getAllEnemy().remove(this);
		 //显示死亡图片
		 this.showImage = StaticValue.graywolfOverImage;
		 this.bg.getRemovedEnemy().add(this);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public BufferedImage getShowImage() {
		return showImage;
	}
	
	public int getType() {
		return type;
	}
	
	public void startMove(){
		//开始挂起的进程
		t.resume();
	}
	
	public void stopMove(){
		try {
			t.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
