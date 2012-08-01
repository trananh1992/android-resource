package com.fishjoy.game;

import java.util.ArrayList;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.fishjoy.Entity.Bullet;
import com.fishjoy.Entity.Fish;


public class GameControl {
	public static final int FishKindsNum = 5;		// �������
	public static final int MaxFishNum = 30;
	
	public static final int NET_KIND_NUM = 5;		//��������
	public static final int A_KIND_NUM = 5;			//�ӵ�����
	
	public static int GAMEMODE = 0;					//��Ϸģʽ��1Ϊ�򵥣�2Ϊ��ͨ��3Ϊ����
	
	public static float score = 0;					//��Ϸ�÷�
	
	public static float SCALEX;
	public static float SCALEY;
	
	public static float CAMERA_WIDTH; 				//�Ӿ����
	public static float CAMERA_HEIGHT;				//�Ӿ��߶�
	
	public static int CANNON_WIDTH = 64;			//��̨��
	public static int CANNON_HEIGHT = 88;			//��̨��
	
	public static int SOUND_WIDTH = 32;
	public static int SOUND_HEIGHT = 15;
	
	public static int NUM_WIDTH = 28;
	public static int NUM_HEIGHT = 32;
	
	public static Boolean musicNeeded = true;		//�ж��Ƿ���Ҫ����
	public static Music bgMusic;					//��������
	
	public static float catchProbabiity = 8;

	//��������ӵ��б����ڼ����ײ
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	// �ζ�����б��㳬���߽���߱�����������
	public static ArrayList<Fish> movingFish = new ArrayList<Fish>();
	//ʱ�侫�飬������ʾʱ��
	public static ArrayList<AnimatedSprite> timeAnimatedSprites = new ArrayList<AnimatedSprite>();
	//�������飬������ʾ����
	public static ArrayList<AnimatedSprite> scoreAnimatedSprites = new ArrayList<AnimatedSprite>();
	
	//����ٶ�	
	public static final float fishSpeed[] = {120, 50, 60, 70, 80};	
	//��ķ�Χ��TiledTextRegion.getWidth()/getHeight()
	public static final float fishRegion[][] = {{60,30}, {55,36}, {112,50}, {80,48}, {115,51}};
	// ����ζ�����
	public static final String fishDir[] = {"Left", "Right", "Up", "Down"};
	
	public static final int groupWay[][] = {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}};
	
	public static final int diamond[][] = {{0,0,1,0,0}, {0,1,0,1,0}, {1,0,0,0,1}, {0,1,0,1,0}, {0,0,1,0,0}};
	
	public static final int fishJoy[][] = {{1,1,1},{1,0,0},{1,1,0},{1,0,0},{1,0,0},
		{1,1,1},{0,1,0},{0,1,0},{0,1,0},{1,1,1},
		{0,1,1,1},{1,0,0,0},{0,1,1,0},{0,0,0,1},{1,1,1,0},
		{1,0,0,1},{1,0,0,1},{1,1,1,1},{1,0,0,1},{1,0,0,1},
		{0,1,1},{0,0,1},{0,0,1},{1,0,1},{0,1,1},
		{0,1,1,1,0},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1},{0,1,1,1,0},
		{1,0,0,0,1},{0,1,0,1,0},{0,0,1,0,0},{0,0,1,0,0},{0,0,1,0,0}};
	
}
