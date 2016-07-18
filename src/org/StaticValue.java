package org;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class StaticValue {
	
	public static ArrayList<BufferedImage> allSheepImage = new ArrayList<BufferedImage>();
	
	public static ArrayList<BufferedImage> allGraywolfImage = new ArrayList<BufferedImage>();
	
	public static ArrayList<BufferedImage> allRedwolfImage = new ArrayList<BufferedImage>();
	
	public  static ArrayList<BufferedImage> allObstructionImage = new ArrayList<BufferedImage>();
	
	public static ArrayList<BufferedImage> allBackGroundImage = new ArrayList<BufferedImage>();
	
	public static BufferedImage endImage = null;
	
	public static BufferedImage startImage = null;
	
	public static BufferedImage sheepOverImage = null;
	
	public static BufferedImage graywolfOverImage = null;
	
	public static AudioClip music = null;
	
	public static String imagePath = System.getProperty("user.dir") + "/bin/";
	
	public static void init(){
		for(int i = 1; i <= 10; i++){
			//��������RedwolfͼƬ
			if(i <= 3){
				try {
					allRedwolfImage.add(ImageIO.read(new File(imagePath + "Redwolf" + i + ".gif")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//��������GraywolfͼƬ
			if(i <= 8){
				try {
					allGraywolfImage.add(ImageIO.read(new File(imagePath + "Graywolf" + i + ".gif")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//��������SheepͼƬ
			try {
				allSheepImage.add(ImageIO.read(new File(imagePath + i + ".gif")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//����sheep��graywolf������ͼƬ���Լ���Ϸ��ʼ�ͽ�����ͼƬ
		try {
			sheepOverImage = ImageIO.read(new File(imagePath + "Sheepover.gif"));
			graywolfOverImage = ImageIO.read(new File(imagePath + "GraywolfOver.gif"));
			startImage = ImageIO.read(new File(imagePath + "start.gif"));
			endImage = ImageIO.read(new File(imagePath + "end.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�������г���ͼƬ
		for(int i = 1; i <= 5; i++){
			try {
				allBackGroundImage.add(ImageIO.read(new File(imagePath + "bg" + i + ".gif")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//���������ϰ���ͼƬ
		for(int i = 1; i <= 9; i++){
			try {
				allObstructionImage.add(ImageIO.read(new File(imagePath + "ob" + i + ".gif")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//��������
		try {
			music = Applet.newAudioClip(new URL("file", "localhost", "SheepAndGraywolf.wav"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
