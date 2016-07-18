package org;

import java.awt.image.BufferedImage;

public class Obstruction {
	//坐标
	private int x;
	private int y;
	//类型
	private int type;
	//初始类型
	private int startType;
	//显示的图片
	private BufferedImage showImage = null;
	
	public Obstruction(int x, int y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
		this.startType = type;
		this.setImage();
	}
	//重置障碍物方法
	public void reset(){
		//修改类型为原始类型
    	this.type = startType;
    	//改变显示图片
    	this.setImage();
	}
	//根据类型改变显示图片
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
