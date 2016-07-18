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
	//����һ���Ƿ�ʼ�ı��
	private boolean isStart = false;
	
	public MyFrame(){
		this.setTitle("Sheep and Graywolf");
		this.setSize(900,600);
		//���ó���λ��
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - 900)/2, (height - 600)/2);
		//���������
		this.addKeyListener(this);
		
		this.setResizable(false);
		//��ʼ��ͼƬ
		StaticValue.init();
		//�õ�����
		for(int i = 1; i <= 6; i++){
			this.allBG.add(new BackGround(i, i == 6?true:false));
		}
		//����һ����������Ϊ��ǰ����
		this.nowBG = this.allBG.get(0);
		//��ʼ��Sheep��
		this.sheep = new Sheep(0, 480);
		this.sheep.setBg(nowBG);
		this.sheep.setLife(3);
		this.sheep.setScore(0);
		//�����߳�
		this.t = new Thread(this);
		t.start();
		//�����ʱ��
		timer = new Timer(1000, new Listener());
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws MalformedURLException{
		new MyFrame();
	}
	
	public void paint(Graphics g){
		//����˫���弼��������һ����ʱ�Ļ���ͼƬ����ֹ��Ļ��˸
		BufferedImage image = new BufferedImage(900, 600, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
		g2.setColor(Color.black);
		g2.setFont(new Font("����", Font.BOLD, 18));
		if(this.isStart){
			//��ʼ��ʱ
			this.timer.start();
			//���Ʊ���
			g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);
			//���Ƶ���
			Iterator<Enemy> iterEnemy = this.nowBG.getAllEnemy().iterator();
			while(iterEnemy.hasNext()){
				Enemy e = iterEnemy.next();
				g2.drawImage(e.getShowImage(), e.getX(), e.getY(), this);
			}
			//�����ϰ���
			Iterator<Obstruction> iterObstruction = this.nowBG.getAllObstruction().iterator();
			while(iterObstruction.hasNext()){
				Obstruction ob = iterObstruction.next();
				g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
			}
			//����Sheep
			g2.drawImage(this.sheep.getShowImage(), this.sheep.getX(), this.sheep.getY(), this);
			//�ڴ����м��������������
			g2.drawString("Life: " + this.sheep.getLife(), 60, 60);
			g2.drawString("Score: " + this.sheep.getScore(), 780, 60);
			g2.drawString("Time: " + time, 420, 60);
		}else{
			g2.drawImage(StaticValue.startImage, 0, 0, this);
			g2.drawString("��Ϸ��飺����Ϸ������ģ���������ù�˾�ĳ����������Ϸ��", 50, 200);
			g2.drawString("������Ϸ�����ǲο���һЩС��Ϸ�е���ƣ���ϲ�������̫�����֡�", 150, 220);
			g2.drawString("������������ʼ��Ϸ���ո����ʼ��Ϸ   ���ƣ����Ҽ����������ƶ����ո����Ծ", 50, 240);
			g2.drawString("����ߣ���ѩ    �    ����", 350, 540);
		}
		//���ƻ���ͼƬ
		g.drawImage(image, 0, 0, this);
	}

	public void keyPressed(KeyEvent e) {
		if(this.isStart){
			//������39��-->��ʱ��sheep�����ƶ�
			if(e.getKeyCode() == 39){
				this.sheep.rightMove();
			}
			//������37��<--��ʱ��sheep�����ƶ�
			if(e.getKeyCode() == 37){
				this.sheep.leftMove();
			}
			//������32���ո�ʱ��Sheep��Ծ
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
			//��̧��39��-->��ʱ��sheepֹͣ�����ƶ�
			if(e.getKeyCode() == 39){
				this.sheep.rightStop();
			}
			//��̧��37��<--��ʱ��sheepֹͣ�����ƶ�
			if(e.getKeyCode() == 37){
				this.sheep.leftStop();
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}
	
	public void run() {
		while(true){
			//�л�����
			if(this.sheep.getX() > 840){
				this.nowBG = this.allBG.get(this.nowBG.getSort());
				//���������õ�Sheep��
				this.sheep.setBg(nowBG);
				//��Sheep�ĺ�����������Ϊ0
				this.sheep.setX(0);
				//����ʱ�����еĵ��˽��
				this.nowBG.enemyStartMove();
			}
			//Sheep����
			if(this.sheep.getLife() >= 0){
				if(this.sheep.isDead()){
					//��������һ
					this.sheep.setLife(this.sheep.getLife() - 1);
					//����������
					this.sheep.setX(0);
					this.sheep.setY(480);
					//����ʾͼƬ����
					this.sheep.setShowImage(StaticValue.allSheepImage.get(0));
					//������״̬����Ϊfalse
					this.sheep.setDead(false);
					//���ó���
					this.nowBG.reset();
					//��ͣ5��
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
			
			//�����Ϸ��
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
