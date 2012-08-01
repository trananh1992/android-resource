package com.fishjoy.game;

import java.util.ArrayList;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.fishjoy.Entity.Bullet;
import com.fishjoy.Entity.Fish;


public class GameControl {
	public static final int FishKindsNum = 5;		// 鱼的种类
	public static final int MaxFishNum = 30;
	
	public static final int NET_KIND_NUM = 5;		//网的种类
	public static final int A_KIND_NUM = 5;			//子弹种类
	
	public static int GAMEMODE = 0;					//游戏模式：1为简单，2为普通，3为困难
	
	public static float score = 0;					//游戏得分
	
	public static float SCALEX;
	public static float SCALEY;
	
	public static float CAMERA_WIDTH; 				//视觉宽度
	public static float CAMERA_HEIGHT;				//视觉高度
	
	public static int CANNON_WIDTH = 64;			//炮台长
	public static int CANNON_HEIGHT = 88;			//炮台宽
	
	public static int SOUND_WIDTH = 32;
	public static int SOUND_HEIGHT = 15;
	
	public static int NUM_WIDTH = 28;
	public static int NUM_HEIGHT = 32;
	
	public static Boolean musicNeeded = true;		//判断是否需要音乐
	public static Music bgMusic;					//背景音乐
	
	public static float catchProbabiity = 8;

	//所打出的子弹列表，用于检测碰撞
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	// 游动鱼的列表（鱼超出边界或者被捕获后清除）
	public static ArrayList<Fish> movingFish = new ArrayList<Fish>();
	//时间精灵，用于显示时间
	public static ArrayList<AnimatedSprite> timeAnimatedSprites = new ArrayList<AnimatedSprite>();
	//分数精灵，用于显示分数
	public static ArrayList<AnimatedSprite> scoreAnimatedSprites = new ArrayList<AnimatedSprite>();
	
	//鱼的速度	
	public static final float fishSpeed[] = {120, 50, 60, 70, 80};	
	//鱼的范围：TiledTextRegion.getWidth()/getHeight()
	public static final float fishRegion[][] = {{60,30}, {55,36}, {112,50}, {80,48}, {115,51}};
	// 鱼的游动方向
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
