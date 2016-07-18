package org;

import java.awt.image.BufferedImage;

public class Sheep implements Runnable{
	//坐标
	private int x;
	private int y;
	//移动速度
	private int xmove = 0;
	private int ymove = 0;
	//当前状态
	private String status;
	//当前显示的图片
	private BufferedImage showImage = null;
	//生命数和分数
	private int score;
	private int life;
	//加入线程
	Thread t = null;
	//定义一个当前移动中的图片索引数
	private int moving = 0;
	//取得场景，以得到障碍物坐标，判断Sheep是否可以移动
	private BackGround bg;
	//定义一个上升时间
	private int upTime = 0;
	//第哦能够以一个是否死亡的标志
	private boolean isDead = false;
	//是否完成游戏的标记
	private boolean isClear = false;
	
	public Sheep(int x, int y){
		this.x = x;
		this.y = y;
		//初始Sheep
		this.showImage = StaticValue.allSheepImage.get(0);
		//初始状态
		this.status = "right--standing";
		this.t = new Thread(this);
		//开始线程
		t.start();
	}

	public void leftMove(){
		//设置移动速度
		this.xmove = -5;
		//改变状态
		//如果实在跳跃中，应该保持原有状态，而不能改变跳跃状态
		if(this.status.indexOf("jumping") != -1){
			this.status = "left--jumping";
		}else{
			this.status = "left--moving";
		}
	}
	
	public void rightMove(){
		this.xmove = 5;
		if(this.status.indexOf("jumping") != -1){
			this.status = "right--jumping";
		}else{
			this.status = "right--moving";
		}
	}
	
	public void leftStop(){
		this.xmove = 0;
		if(this.status.indexOf("jumping") != -1){
			this.status = "left--jumping";
		}else{
			this.status = "left--standing";
		}
	}
	
	public void rightStop(){
		this.xmove = 0;
		if(this.status.indexOf("jumping") != -1){
			this.status = "right--jumping";
		}else{
			this.status = "right--standing";
		}
	}
	
	public void jump(){
		if(this.status.indexOf("jumping") == -1){
			if(this.status.indexOf("left") != -1){
				this.status = "left--jumping";
			}else{
				this.status = "right--jumping";
			}
			this.ymove = -10;
			this.upTime = 19;//将18改为19，时Sheep更容易跳上障碍物
		}
	}
	
	public void down(){
		//在障碍物上移动落下时，也是下降，不一定是跳跃中下落，注意和jump()中的区别
		if(this.status.indexOf("left") != -1){
			this.status = "left--jumping";
		}else{
			this.status = "right--jumping";
		}
		this.ymove = 10;
	}
	
	public void dead(){
		this.isDead = true;
		this.showImage = StaticValue.sheepOverImage;
	}

	public void run() {
		while(true){
			//游戏结束
			if(this.bg.isFlag() && this.x == 840){
				this.isClear = true;
			}else{
				//定义是否可以移动的标记
				boolean canRight = true;
				boolean canLeft = true;
				//定义一个Sheep是否在障碍物上的标志
				boolean onLand = false;
				for(int i = 0; i < this.bg.getAllObstruction().size(); i++){
					Obstruction ob = this.bg.getAllObstruction().get(i);
					//如果向右移动的过程中碰到障碍物，不允许继续向右移动
					if(ob.getX() == this.x + 55 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canRight = false;
					}
					//如果向左移动的过程中碰到障碍物，不允许继续向左移动
					if(ob.getX() == this.x - 55 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canLeft = false;
					}
					//判断当前是否处在一个障碍物上
					if(ob.getY() == this.y + 60 && (ob.getX() + 55 > this.x && ob.getX() - 55 < this.x)){
						onLand = true;
					}
					//判断在跳跃中是否顶到一个障碍物
					if(ob.getY() == this.y - 60 && (ob.getX() + 55 > this.x && ob.getX() - 55 < this.x)){
						//对砖块的判断，将砖块移除
						if(ob.getType() == 2){
							this.bg.getAllObstruction().remove(ob);
							//添加到移除的障碍物中，以便重置场景的时候使用
							this.bg.getRemovedObstruction().add(ob);
							//分数增加
							this.score += 50;
						}
						//对“0”的处理
						if(ob.getType() == 3){
							ob.setType(4);
							//重置图片，改变显示内容
							ob.setImage();
							//分数增加
							this.score += 200;
						}
						//使Sheep撞到障碍物后立刻下降
						upTime = 0;
					}
				}
				//对敌人的判断
				for(int i = 0; i < this.bg.getAllEnemy().size(); i++){
					Enemy e = this.bg.getAllEnemy().get(i);
					//对Graywolf的判断
					if((e.getX() + 60 > this.x && e.getX() - 60 < this.x) && (e.getY() + 60 > this.y && e.getY() - 60 < this.y)){
						this.dead();
					}
					if(e.getY() == this.y + 60 &&(e.getX() + 60 > this.x && e.getX() - 60 < this.x)){
						if(e.getType() == 1){
							e.dead();
							//分数增加
	                		this.score += 100;
	                		//踩到敌人上升一段距离
	                		this.upTime = 3;
	                		this.ymove = -10;
	                	}else if(e.getType() == 2){//如果踩到Redwolf,Sheep死亡
	            		    this.dead();
	                	}
					}
				}
				//在障碍物上移动
				if(onLand && upTime == 0){
					if(this.status.indexOf("left") != -1){
					    if(xmove != 0){
						    this.status = "left--moving";
					    }
					    else{
					    	this.status = "left--standing";
					    }
				    }
				    else{
				    	if(xmove != 0){
				    		this.status = "right--moving";
				    	}
				    	else{
				    		this.status = "right--standing";
				    	}
			    	}
				}else{
					//当前为上升状态
					if(upTime != 0){
						upTime--;
					}else{
						this.down();
					}
					//跳跃中不超出框架
					if(this.y - 30 <= 0){
						upTime = 0;
					}
					this.y += this.ymove;
				}
	
				if((canLeft && xmove < 0) || (canRight && xmove > 0)){
					//改变坐标
					x += xmove;
					if(this.x < 0){
						this.x = 0;
					}
				}
				if(this.y >= 540){
					this.dead();
				}
				//定义一个图片初始索引数
				int temp = 0;
				//当前为向左移动
				if(this.status.indexOf("left") != -1){
					temp += 5;
				}
				//判断当前是否在移动
				if(this.status.indexOf("moving") != -1){
					temp += this.moving;
					this.moving++;
					if(this.moving == 4){
						this.moving = 0;
					}
				}
				//如果是跳跃状态，改变显示图片
				if(this.status.indexOf("jumping") != -1){
					temp += 4;
				}
				//改变显示图片
				this.showImage = StaticValue.allSheepImage.get(temp);
				//添加睡眠时间
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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

	public BufferedImage getShowImage() {
		return showImage;
	}

	public void setShowImage(BufferedImage showImage) {
		this.showImage = showImage;
	}

	public void setBg(BackGround bg) {
		this.bg = bg;
	}

	public int getScore() {
		return score;
	}

	public int getLife() {
		return life;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isClear() {
		return isClear;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public void stopMove(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
