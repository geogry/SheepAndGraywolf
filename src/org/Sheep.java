package org;

import java.awt.image.BufferedImage;

public class Sheep implements Runnable{
	//����
	private int x;
	private int y;
	//�ƶ��ٶ�
	private int xmove = 0;
	private int ymove = 0;
	//��ǰ״̬
	private String status;
	//��ǰ��ʾ��ͼƬ
	private BufferedImage showImage = null;
	//�������ͷ���
	private int score;
	private int life;
	//�����߳�
	Thread t = null;
	//����һ����ǰ�ƶ��е�ͼƬ������
	private int moving = 0;
	//ȡ�ó������Եõ��ϰ������꣬�ж�Sheep�Ƿ�����ƶ�
	private BackGround bg;
	//����һ������ʱ��
	private int upTime = 0;
	//��Ŷ�ܹ���һ���Ƿ������ı�־
	private boolean isDead = false;
	//�Ƿ������Ϸ�ı��
	private boolean isClear = false;
	
	public Sheep(int x, int y){
		this.x = x;
		this.y = y;
		//��ʼSheep
		this.showImage = StaticValue.allSheepImage.get(0);
		//��ʼ״̬
		this.status = "right--standing";
		this.t = new Thread(this);
		//��ʼ�߳�
		t.start();
	}

	public void leftMove(){
		//�����ƶ��ٶ�
		this.xmove = -5;
		//�ı�״̬
		//���ʵ����Ծ�У�Ӧ�ñ���ԭ��״̬�������ܸı���Ծ״̬
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
			this.upTime = 19;//��18��Ϊ19��ʱSheep�����������ϰ���
		}
	}
	
	public void down(){
		//���ϰ������ƶ�����ʱ��Ҳ���½�����һ������Ծ�����䣬ע���jump()�е�����
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
			//��Ϸ����
			if(this.bg.isFlag() && this.x == 840){
				this.isClear = true;
			}else{
				//�����Ƿ�����ƶ��ı��
				boolean canRight = true;
				boolean canLeft = true;
				//����һ��Sheep�Ƿ����ϰ����ϵı�־
				boolean onLand = false;
				for(int i = 0; i < this.bg.getAllObstruction().size(); i++){
					Obstruction ob = this.bg.getAllObstruction().get(i);
					//��������ƶ��Ĺ����������ϰ����������������ƶ�
					if(ob.getX() == this.x + 55 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canRight = false;
					}
					//��������ƶ��Ĺ����������ϰ����������������ƶ�
					if(ob.getX() == this.x - 55 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canLeft = false;
					}
					//�жϵ�ǰ�Ƿ���һ���ϰ�����
					if(ob.getY() == this.y + 60 && (ob.getX() + 55 > this.x && ob.getX() - 55 < this.x)){
						onLand = true;
					}
					//�ж�����Ծ���Ƿ񶥵�һ���ϰ���
					if(ob.getY() == this.y - 60 && (ob.getX() + 55 > this.x && ob.getX() - 55 < this.x)){
						//��ש����жϣ���ש���Ƴ�
						if(ob.getType() == 2){
							this.bg.getAllObstruction().remove(ob);
							//��ӵ��Ƴ����ϰ����У��Ա����ó�����ʱ��ʹ��
							this.bg.getRemovedObstruction().add(ob);
							//��������
							this.score += 50;
						}
						//�ԡ�0���Ĵ���
						if(ob.getType() == 3){
							ob.setType(4);
							//����ͼƬ���ı���ʾ����
							ob.setImage();
							//��������
							this.score += 200;
						}
						//ʹSheepײ���ϰ���������½�
						upTime = 0;
					}
				}
				//�Ե��˵��ж�
				for(int i = 0; i < this.bg.getAllEnemy().size(); i++){
					Enemy e = this.bg.getAllEnemy().get(i);
					//��Graywolf���ж�
					if((e.getX() + 60 > this.x && e.getX() - 60 < this.x) && (e.getY() + 60 > this.y && e.getY() - 60 < this.y)){
						this.dead();
					}
					if(e.getY() == this.y + 60 &&(e.getX() + 60 > this.x && e.getX() - 60 < this.x)){
						if(e.getType() == 1){
							e.dead();
							//��������
	                		this.score += 100;
	                		//�ȵ���������һ�ξ���
	                		this.upTime = 3;
	                		this.ymove = -10;
	                	}else if(e.getType() == 2){//����ȵ�Redwolf,Sheep����
	            		    this.dead();
	                	}
					}
				}
				//���ϰ������ƶ�
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
					//��ǰΪ����״̬
					if(upTime != 0){
						upTime--;
					}else{
						this.down();
					}
					//��Ծ�в��������
					if(this.y - 30 <= 0){
						upTime = 0;
					}
					this.y += this.ymove;
				}
	
				if((canLeft && xmove < 0) || (canRight && xmove > 0)){
					//�ı�����
					x += xmove;
					if(this.x < 0){
						this.x = 0;
					}
				}
				if(this.y >= 540){
					this.dead();
				}
				//����һ��ͼƬ��ʼ������
				int temp = 0;
				//��ǰΪ�����ƶ�
				if(this.status.indexOf("left") != -1){
					temp += 5;
				}
				//�жϵ�ǰ�Ƿ����ƶ�
				if(this.status.indexOf("moving") != -1){
					temp += this.moving;
					this.moving++;
					if(this.moving == 4){
						this.moving = 0;
					}
				}
				//�������Ծ״̬���ı���ʾͼƬ
				if(this.status.indexOf("jumping") != -1){
					temp += 4;
				}
				//�ı���ʾͼƬ
				this.showImage = StaticValue.allSheepImage.get(temp);
				//���˯��ʱ��
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
