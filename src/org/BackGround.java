package org;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BackGround {
	//当前场景图片
	private BufferedImage bgImage = null;
	//场景顺序
	private int sort;
	//定一个场景是否为最后一个场景的标记
	private boolean flag;
	//全部的敌人
	private List<Enemy> allEnemy = new ArrayList<Enemy>();
	//全部的障碍物
	private List<Obstruction> allObstruction = new ArrayList<Obstruction>();
	//被消灭的敌人
	private List<Enemy> removedEnemy = new ArrayList<Enemy>();
	//被消除的障碍物
	private List<Obstruction> removedObstruction = new  ArrayList<Obstruction>();
	
	//构造方法
	public BackGround(int sort, boolean flag){
		this.sort = sort;
		this.flag = flag;
		//得到背景图片
		if(flag){
			bgImage = StaticValue.endImage;
		}else{
			bgImage = StaticValue.allBackGroundImage.get(sort - 1);
		}
		//绘制第一个场景障碍物
		if(sort == 1){
			//添加地面
			for(int i = 0; i < 15; i++){
				this.allObstruction.add(new Obstruction(i * 60, 540, 0));
			}
			//添加砖块
			this.allObstruction.add(new Obstruction(120, 360, 3));
			this.allObstruction.add(new Obstruction(300, 360, 2));
			this.allObstruction.add(new Obstruction(360, 360, 3));
			this.allObstruction.add(new Obstruction(420, 360, 2));
			this.allObstruction.add(new Obstruction(480, 360, 3));
			this.allObstruction.add(new Obstruction(540, 360, 2));
			this.allObstruction.add(new Obstruction(420, 180, 3));
			//添加水管
			this.allObstruction.add(new Obstruction(660, 540, 7));
			this.allObstruction.add(new Obstruction(720, 540, 8));
			this.allObstruction.add(new Obstruction(660, 480, 5));
			this.allObstruction.add(new Obstruction(720, 480, 6));
			//添加敌人
			this.allEnemy.add(new Enemy(600, 480, true, 1, this));
			this.allEnemy.add(new Enemy(690, 540, true, 2, 420, 540, this));
		}
		//绘制第二个场景障碍物
		if(sort == 2){
			//添加地面
			for(int i = 0; i < 15; i++){
				this.allObstruction.add(new Obstruction(i * 60, 540, 0));
			}
			//添加水管
    		this.allObstruction.add(new Obstruction(60, 540, 7));
    		this.allObstruction.add(new Obstruction(120, 540, 8));
    		this.allObstruction.add(new Obstruction(60, 480, 7));
    		this.allObstruction.add(new Obstruction(120, 480, 8));
    		this.allObstruction.add(new Obstruction(60, 420, 5));
    		this.allObstruction.add(new Obstruction(120, 420, 6));
    		
    		this.allObstruction.add(new Obstruction(240, 540, 7));
    		this.allObstruction.add(new Obstruction(300, 540, 8));
    		this.allObstruction.add(new Obstruction(240, 480, 7));
    		this.allObstruction.add(new Obstruction(300, 480, 8));
    		this.allObstruction.add(new Obstruction(240, 420, 7));
    		this.allObstruction.add(new Obstruction(300, 420, 8));
    		this.allObstruction.add(new Obstruction(240, 360, 5));
    		this.allObstruction.add(new Obstruction(300, 360, 6));
    		
    		this.allObstruction.add(new Obstruction(660, 540, 7));
    		this.allObstruction.add(new Obstruction(720, 540, 8));
    		this.allObstruction.add(new Obstruction(660, 480, 7));
    		this.allObstruction.add(new Obstruction(720, 480, 8));
    		this.allObstruction.add(new Obstruction(660, 420, 5));
    		this.allObstruction.add(new Obstruction(720, 420, 6));
    		//添加砖块
    		this.allObstruction.add(new Obstruction(150, 270, 2));
    		this.allObstruction.add(new Obstruction(300, 180, 3));
    		this.allObstruction.add(new Obstruction(540, 300, 3));
    		this.allObstruction.add(new Obstruction(840, 300, 2));
    		this.allObstruction.add(new Obstruction(540, 60, 3));
    		//添加敌人
    		this.allEnemy.add(new Enemy(600, 480, true, 1, this));
    		this.allEnemy.add(new Enemy(90, 480, true, 2, 360, 480, this));
    		this.allEnemy.add(new Enemy(270, 360, true, 2, 300, 420, this));
    		this.allEnemy.add(new Enemy(690, 360, true, 2, 358, 480, this));
		}
		//绘制第三场景
		if(sort == 3){
			//添加地面
			for(int i = 0; i < 15; i++){
				this.allObstruction.add(new Obstruction(i * 60, 540, 0));
			}
			//添加水管
			this.allObstruction.add(new Obstruction(300, 540, 7));
			this.allObstruction.add(new Obstruction(360, 540, 8));
			this.allObstruction.add(new Obstruction(300, 480, 7));
			this.allObstruction.add(new Obstruction(360, 480, 8));
			this.allObstruction.add(new Obstruction(300, 420, 7));
			this.allObstruction.add(new Obstruction(360, 420, 8));
			this.allObstruction.add(new Obstruction(300, 360, 5));
			this.allObstruction.add(new Obstruction(360, 360, 6));
			
			this.allObstruction.add(new Obstruction(420, 540, 7));
			this.allObstruction.add(new Obstruction(480, 540, 8));
			this.allObstruction.add(new Obstruction(420, 480, 7));
			this.allObstruction.add(new Obstruction(480, 480, 8));
			this.allObstruction.add(new Obstruction(420, 420, 7));
			this.allObstruction.add(new Obstruction(480, 420, 8));
			this.allObstruction.add(new Obstruction(420, 360, 5));
			this.allObstruction.add(new Obstruction(480, 360, 6));
			//添加砖块
			this.allObstruction.add(new Obstruction(0, 360, 2));
			this.allObstruction.add(new Obstruction(60, 360, 3));
			this.allObstruction.add(new Obstruction(180, 360, 3));
			this.allObstruction.add(new Obstruction(240, 360, 2));
			
			this.allObstruction.add(new Obstruction(390, 120, 3));
			
			this.allObstruction.add(new Obstruction(660, 300, 2));
			this.allObstruction.add(new Obstruction(720, 300, 2));
			this.allObstruction.add(new Obstruction(780, 300, 2));
			this.allObstruction.add(new Obstruction(840, 300, 2));
			//添加石头
			this.allObstruction.add(new Obstruction(720, 480, 1));
			//添加敌人
			this.allEnemy.add(new Enemy(240, 480, true, 1,this));
			this.allEnemy.add(new Enemy(660, 480, true, 1,this));
			this.allEnemy.add(new Enemy(330, 300, true, 2, 298, 420, this));
			this.allEnemy.add(new Enemy(450, 420, true, 2, 300, 420, this));
		}
		//绘制第四个场景
		if(sort == 4){
			//添加地面
			for(int i = 0; i < 15; i++){
				if(i < 5 || i > 11){
					this.allObstruction.add(new Obstruction(i * 60, 540, 0));
				}
			}
			//添加水管
			this.allObstruction.add(new Obstruction(420, 540, 7));
			this.allObstruction.add(new Obstruction(480, 540, 8));
			this.allObstruction.add(new Obstruction(420, 480, 7));
			this.allObstruction.add(new Obstruction(480, 480, 8));
			this.allObstruction.add(new Obstruction(420, 420, 7));
			this.allObstruction.add(new Obstruction(480, 420, 8));
			this.allObstruction.add(new Obstruction(420, 360, 5));
			this.allObstruction.add(new Obstruction(480, 360, 6));
			//添加石头
			this.allObstruction.add(new Obstruction(240, 480, 1));
			this.allObstruction.add(new Obstruction(690, 480, 1));
			//添加砖块
			this.allObstruction.add(new Obstruction(120, 390, 3));
			this.allObstruction.add(new Obstruction(180, 390, 2));
			this.allObstruction.add(new Obstruction(600, 120, 3));
			this.allObstruction.add(new Obstruction(390, 120, 3));
			this.allObstruction.add(new Obstruction(780, 300, 2));
			this.allObstruction.add(new Obstruction(840, 300, 3));
			//添加敌人
			this.allEnemy.add(new Enemy(180, 480, true, 1, this));
			this.allEnemy.add(new Enemy(840, 480, true, 1, this));
			this.allEnemy.add(new Enemy(450, 420, true, 2, 300, 420, this));
		}
		//绘制第五个场景
		if(sort == 5){
			//添加地面
			for(int i = 0; i < 15; i++){
				if(i != 12 && i != 13){
					this.allObstruction.add(new Obstruction(i * 60, 540, 0));
				}
			}
			//添加水管
			this.allObstruction.add(new Obstruction(120, 540, 7));
			this.allObstruction.add(new Obstruction(180, 540, 8));
			this.allObstruction.add(new Obstruction(120, 480, 5));
			this.allObstruction.add(new Obstruction(180, 480, 6));
			
			this.allObstruction.add(new Obstruction(600, 540, 7));
			this.allObstruction.add(new Obstruction(660, 540, 8));
			this.allObstruction.add(new Obstruction(600, 480, 5));
			this.allObstruction.add(new Obstruction(660, 480, 6));
			//添加砖块
			this.allObstruction.add(new Obstruction(240, 300, 2));
			this.allObstruction.add(new Obstruction(300, 300, 3));
			this.allObstruction.add(new Obstruction(360, 300, 3));
			this.allObstruction.add(new Obstruction(420, 300, 3));
			this.allObstruction.add(new Obstruction(480, 300, 3));
			this.allObstruction.add(new Obstruction(540, 300, 2));
			this.allObstruction.add(new Obstruction(390, 60, 3));
			//添加敌人
			this.allEnemy.add(new Enemy(390, 480, true, 1, this));
			this.allEnemy.add(new Enemy(150, 540, true, 2, 420, 540, this));
			this.allEnemy.add(new Enemy(630, 420, true, 2, 418, 540, this));
		}
		//绘制最后一个场景
		if(sort == 6){
			//添加地面
			for(int i = 0; i < 15; i++){
				this.allObstruction.add(new Obstruction(i * 60, 540, 0));
			}
			//添加砖块
			this.allObstruction.add(new Obstruction(300, 480, 4));
			this.allObstruction.add(new Obstruction(360, 480, 4));
			this.allObstruction.add(new Obstruction(420, 480, 4));
			this.allObstruction.add(new Obstruction(480, 480, 4));
			this.allObstruction.add(new Obstruction(540, 480, 4));
			this.allObstruction.add(new Obstruction(360, 420, 4));
			this.allObstruction.add(new Obstruction(420, 420, 4));
			this.allObstruction.add(new Obstruction(480, 420, 4));
			this.allObstruction.add(new Obstruction(420, 360, 4));
			
			this.allObstruction.add(new Obstruction(60, 300, 3));
			this.allObstruction.add(new Obstruction(120, 300, 3));
			this.allObstruction.add(new Obstruction(180, 300, 3));
			this.allObstruction.add(new Obstruction(240, 300, 3));
			this.allObstruction.add(new Obstruction(300, 120, 3));
			this.allObstruction.add(new Obstruction(360, 180, 3));
			
			this.allObstruction.add(new Obstruction(120, 480, 1));
			this.allObstruction.add(new Obstruction(720, 480, 1));
		}
	}
	//sheep死亡时，重置场景
	public void reset(){
		for(Enemy e : this.removedEnemy){
			this.allEnemy.add(e);
		}
		
		for(Obstruction o: this.removedObstruction){
			this.allObstruction.add(o);
		}
		
		for(int i = 0; i < this.allEnemy.size(); i++){
			this.allEnemy.get(i).reset();
		}
		
		for(int i = 0; i < this.allObstruction.size(); i++){
			this.allObstruction.get(i).reset();
		}
	}

	public BufferedImage getBgImage() {
		return bgImage;
	}

	public List<Obstruction> getAllObstruction() {
		return allObstruction;
	}
	
	public List<Enemy> getAllEnemy() {
		return allEnemy;
	}

	public List<Enemy> getRemovedEnemy() {
		return removedEnemy;
	}

	public List<Obstruction> getRemovedObstruction() {
		return removedObstruction;
	}
	
	public int getSort() {
		return sort;
	}
	
	public void enemyStartMove(){
		for(int i = 0; i < this.allEnemy.size(); i++){
			this.allEnemy.get(i).startMove();
		}
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void enemyStopMove(){
		for(int i = 0; i < this.allEnemy.size(); i++){
			this.allEnemy.get(i).stopMove();
		}
	}
}
