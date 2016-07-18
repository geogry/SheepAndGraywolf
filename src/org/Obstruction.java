package org;

import java.awt.image.BufferedImage;

public class Obstruction {
	//����
	private int x;
	private int y;
	//����
	private int type;
	//��ʼ����
	private int startType;
	//��ʾ��ͼƬ
	private BufferedImage showImage = null;
	
	public Obstruction(int x, int y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
		this.startType = type;
		this.setImage();
	}
	//�����ϰ��﷽��
	public void reset(){
		//�޸�����Ϊԭʼ����
    	this.type = startType;
    	//�ı���ʾͼƬ
    	this.setImage();
	}
	//�������͸ı���ʾͼƬ
	public void setImage(){
		showImage = StaticValue.allObstructionImage.get(type);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public BufferedImage getShowImage() {
		return showImage;
	}
	
}
