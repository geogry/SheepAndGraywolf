package org;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
	//����
	private int x;
	private int y;
	//��ʼ����
	private int xstart;
	private int ystart;
	//Redwolf�����ƶ�����
	private int upMax;
	private int downMax;
	//��������
	private int type;
	//����һ��ͼƬ������
	private int temp = 0;
	//��ǰ����ʾͼƬ
	private BufferedImage showImage = null;
	//ͼƬ��ʼ����
	private int imageType = 0;
	//��ǰ�����ĳ���
	private BackGround bg;
	//��ǰ���ƶ�״̬
	private boolean isUpOrLeft;
	//����һ���߳�
	private Thread t = new Thread(this);
	
	//��Graywolf�Ĺ��췽��
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
		//������̣��ڿ�ʼ��Ϸǰ�����˾�ֹ
		if(this.bg.getSort() == 1){
			t.suspend();
		}
	}
	//��Redwolf�Ĺ��췽��
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
		//����һ����ǰ��ͼƬ����
		int graywolfTemp = 4;
		int redwolfTemp = 0;
		while(true){
			//���ڲ�ͬ�ĵ�������ͬ����
			//��Graywolf�Ĵ���
			this.temp = 0;
			if(this.type == 1){
				if(this.isUpOrLeft){
					this.x -= 2;
				}else{
					this.x += 2;
				}
				//���ϰ�����ж�
				//�����Ƿ�����ƶ����
				boolean canLeft = true;
				boolean canRight = true;
				for(int i = 0; i < this.bg.getAllObstruction().size(); i++){
					Obstruction ob = this.bg.getAllObstruction().get(i);
					//��������ƶ��Ĺ����������ϰ����������������ƶ�
					if(ob.getX() == this.x + 60 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)){
						canRight = false;
					}
					//��������ƶ��Ĺ����������ϰ����������������ƶ�
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
			//��Redwolf�Ĵ���
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
		 //��ʾ����ͼƬ
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
		//��ʼ����Ľ���
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
