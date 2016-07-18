package org;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements KeyListener, Runnable {
	
	private List<BackGround> allBG = new ArrayList<BackGround>();
	
	private BackGround nowBG = null;
	
	private Sheep sheep = null;
	
	private Thread t = null;
	
	private Timer timer;
	
	private int time;
	//定义一个是否开始的标记
	private boolean isStart = false;
	
	public MyFrame(){
		this.setTitle("Sheep and Graywolf");
		this.setSize(900,600);
		//设置出现位置
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - 900)/2, (height - 600)/2);
		//加入监听器
		this.addKeyListener(this);
		
		this.setResizable(false);
		//初始化图片
		StaticValue.init();
		//得到场景
		for(int i = 1; i <= 6; i++){
			this.allBG.add(new BackGround(i, i == 6?true:false));
		}
		//将第一个场景设置为当前场景
		this.nowBG = this.allBG.get(0);
		//初始化Sheep类
		this.sheep = new Sheep(0, 480);
		this.sheep.setBg(nowBG);
		this.sheep.setLife(3);
		this.sheep.setScore(0);
		//加入线程
		this.t = new Thread(this);
		t.start();
		//加入计时器
		timer = new Timer(1000, new Listener());
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws MalformedURLException{
		new MyFrame();
	}
	
	public void paint(Graphics g){
		//利用双缓冲技术，建立一个临时的缓冲图片，防止屏幕闪烁
		BufferedImage image = new BufferedImage(900, 600, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
		g2.setColor(Color.black);
		g2.setFont(new Font("宋体", Font.BOLD, 18));
		if(this.isStart){
			//开始计时
			this.timer.start();
			//绘制背景
			g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);
			//绘制敌人
			Iterator<Enemy> iterEnemy = this.nowBG.getAllEnemy().iterator();
			while(iterEnemy.hasNext()){
				Enemy e = iterEnemy.next();
				g2.drawImage(e.getShowImage(), e.getX(), e.getY(), this);
			}
			//绘制障碍物
			Iterator<Obstruction> iterObstruction = this.nowBG.getAllObstruction().iterator();
			while(iterObstruction.hasNext()){
				Obstruction ob = iterObstruction.next();
				g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
			}
			//绘制Sheep
			g2.drawImage(this.sheep.getShowImage(), this.sheep.getX(), this.sheep.getY(), this);
			//在窗体中加入分数和生命数
			g2.drawString("Life: " + this.sheep.getLife(), 60, 60);
			g2.drawString("Score: " + this.sheep.getScore(), 780, 60);
			g2.drawString("Time: " + time, 420, 60);
		}else{
			g2.drawImage(StaticValue.startImage, 0, 0, this);
			g2.drawString("游戏简介：本游戏界面上模仿了任天堂公司的超级马里奥游戏，", 50, 200);
			g2.drawString("部分游戏代码是参考的一些小游戏中的设计，将喜洋洋与灰太狼再现。", 150, 220);
			g2.drawString("基本操作：开始游戏：空格键开始游戏   控制：左右键控制左右移动，空格键跳跃", 50, 240);
			g2.drawString("设计者：岳雪    杨超    郭俊", 350, 540);
		}
		//绘制缓冲图片
		g.drawImage(image, 0, 0, this);
	}

	public void keyPressed(KeyEvent e) {
		if(this.isStart){
			//当按下39（-->）时，sheep向右移动
			if(e.getKeyCode() == 39){
				this.sheep.rightMove();
			}
			//当按下37（<--）时，sheep向左移动
			if(e.getKeyCode() == 37){
				this.sheep.leftMove();
			}
			//当按下32（空格）时，Sheep跳跃
			if(e.getKeyCode() == 32){
				this.sheep.jump();
			}
		}else{
			if(e.getKeyCode() == 32){
				this.isStart = true;
				this.nowBG.enemyStartMove();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(this.isStart){
			//当抬起39（-->）时，sheep停止向右移动
			if(e.getKeyCode() == 39){
				this.sheep.rightStop();
			}
			//当抬起37（<--）时，sheep停止向左移动
			if(e.getKeyCode() == 37){
				this.sheep.leftStop();
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}
	
	public void run() {
		while(true){
			//切换场景
			if(this.sheep.getX() > 840){
				this.nowBG = this.allBG.get(this.nowBG.getSort());
				//将场景放置到Sheep中
				this.sheep.setBg(nowBG);
				//将Sheep的横坐标重新置为0
				this.sheep.setX(0);
				//将此时场景中的敌人解挂
				this.nowBG.enemyStartMove();
			}
			//Sheep死亡
			if(this.sheep.getLife() >= 0){
				if(this.sheep.isDead()){
					//生命数减一
					this.sheep.setLife(this.sheep.getLife() - 1);
					//将坐标重置
					this.sheep.setX(0);
					this.sheep.setY(480);
					//将显示图片重置
					this.sheep.setShowImage(StaticValue.allSheepImage.get(0));
					//将死亡状态重置为false
					this.sheep.setDead(false);
					//重置场景
					this.nowBG.reset();
					//暂停5秒
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.sheep.stopMove();
					this.nowBG.enemyStopMove();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Sheep's life has been used up!The game is over");
				System.exit(0);
			}
			
			//完成游戏后
			if(this.sheep.isClear()){
				JOptionPane.showMessageDialog(null, "Sheep has win!The game is over,please wait other game and later checkpoint!");
				System.exit(0);
			}
			this.repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class Listener implements ActionListener{
		public void actionPerformed(ActionEvent a) {
			time += 1;
		}
	}
}
