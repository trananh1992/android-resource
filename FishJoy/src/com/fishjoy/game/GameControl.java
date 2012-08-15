package com.fishjoy.game;

import java.util.ArrayList;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.fishjoy.Entity.Bullet;
import com.fishjoy.Entity.Fish;


public class GameControl {
	public static final int NormalFishNum = 8;		// 普通鱼的种类
	public static final int MagicFishNum = 2;		// 道具鱼的种类
	
	public static final int MaxFishNum = 20;
	
	public static final int NET_KIND_NUM = 5;		//网的种类
	public static final int A_KIND_NUM = 5;			//子弹种类
	public static int A_TOTAL = 250;				//子弹个数
	
	public static float score = 0;					//游戏得分
	
	//public static float time = 153;				
	public static final float GAME_TIME = 150;		// 游戏规定时间
	public static float GAME_LAST_TIME = 150;		// 游戏剩余时间
	public static float[] AWARD_TIME = {0, 10, 20, 30};		// 游戏奖励时间
	public static final float GAMN_END_TIME = 160;
	
	public static int GAMEMODE = 0;					//游戏模式：1为简单，2为普通，3为困难
	public static String playerName = "default";	//唯一的用户名
	
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
	public static Boolean musiceffect = true;
	
	public static Music bgMusic;					//背景音乐
	public static Music fireMusic;
	public static Music switchMusic;
	public static Music coinMusic;
	
	// 该模式的捕鱼概率
	public static float catchProbabiity = 8;
	// 不同炮台的捕鱼概率
	public static float cannonPobability[] = {(float) 0.2, (float) 0.4, (float) 0.6, (float) 0.8, (float) 1};
	
	//过关分数
	public static int GameScore[] = {0, 4000, 2500, 1500};

	//所打出的子弹列表，用于检测碰撞
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	// 游动鱼的列表（鱼超出边界或者被捕获后清除）
	public static ArrayList<Fish> movingFish = new ArrayList<Fish>();
	//时间精灵，用于显示时间
	public static ArrayList<AnimatedSprite> timeAnimatedSprites = new ArrayList<AnimatedSprite>();
	//分数精灵，用于显示分数
	public static ArrayList<AnimatedSprite> scoreAnimatedSprites = new ArrayList<AnimatedSprite>();
	
	
	//鱼的速度	
	public static float fishSpeed[] = {120, 110, 100, 90, 90, 80, 80, 120, 100, 100};
	
	public static final float catchParas[] = {1.0f, 0.9f, 0.7f, 0.5f, 0.5f, 0.5f, 0.5f, 0.25f, 0.5f, 0.5f};

	//鱼的范围：TiledTextRegion.getWidth()/getHeight()
	public static final int fishText[][] = {{128,512},{128,512},{128,512},{128,512},{128,512},{128,1024},{256,512},{256,1024},{128, 512},{128, 512},         
											{128,64 },{128,128},{64,128},{128,128},{128,128},{128,128}, {256,128}, {256,256}, {128,256}, {128,256}};
	public static final int fishFrame[][] = {{1,7},{1,7},{1,7},{1,7},{1,7},{1,7},{1,7},{1,7},{1,7},{1,7},
											 {1,2},{1,2},{1,2},{1,2},{1,2},{1,2},{1,2},{1,2},{1,2},{1,2}};
	public static final float fishRegion[][] = {{78,58}, {83,52}, {69,45}, {96,51}, {75,62}, {110,76}, {128,68}, {180,107}, {97,70}, {97,70},
												{67,30}, {83,57}, {62,52}, {94,38}, {73,58}, {113,52},{131, 56}, {179,81 }, {92,70}, {92,70}};
	public static final float fishScore[] = {1, 4, 7, 10, 30, 50, 80, 100, 0, 0};
	public static final float pathSlapsTime[] = {0, 25.0f, 25.0f, 35.0f};
			
	// 鱼的游动方向
	public static final String fishDir[] = {"Left", "Right", "Up", "Down"};
	
	public static final int groupWay[][] = {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}};
	
	public static final int diamond[][] = {{0,0,1,0,0}, {0,1,0,1,0}, {1,0,1,0,1}, {0,1,0,1,0}, {0,0,1,0,0}};
	
	public static final double fishJoy2[][] = {
		{0.0,0.0},{68.0,0.0},{136.0,0.0},{0.0,45.0},{0.0,90.0},{68.0,90.0},{136.0,90.0},{0.0,135.0},{0.0,180.0},
		{249.0,0.0},{317.0,0.0},{385.0,0.0},{317.0,45.0},{317.0,90.0},{317.0,135.0},{249.0,180.0},{317.0,180.0},{385.0,180.0},
		{566.0,0.0},{634.0,0.0},{702.0,0.0},{498.0,45.0},{566.0,90.0},{634.0,90.0},{702.0,135.0},{498.0,180.0},{566.0,180.0},{634.0,180.0},
		{815.0,0.0},{1019.0,0.0},{815.0,45.0},{1019.0,45.0},{815.0,90.0},{883.0,90.0},{951.0,90.0},{1019.0,90.0},{815.0,135.0},{1019.0,135.0},{815.0,180.0},{1019.0,180.0},
		{1268.0,0.0},{1336.0,0.0},{1336.0,45.0},{1336.0,90.0},{1200.0,135.0},{1336.0,135.0},{1268.0,180.0},{1336.0,180.0},
		{1517.0,0.0},{1585.0,0.0},{1653.0,0.0},{1449.0,45.0},{1721.0,45.0},{1449.0,90.0},{1721.0,90.0},{1449.0,135.0},{1721.0,135.0},{1517.0,180.0},{1585.0,180.0},{1653.0,180.0},
		{1834.0,0.0},{2106.0,0.0},{1902.0,45.0},{2038.0,45.0},{1970.0,90.0},{1970.0,135.0},{1970.0,180.0}
		};
	
	// 鱼加速
	public static void speedUpgrade()
	{
		for(int i = 0; i < fishSpeed.length; i++)
			fishSpeed[i] *= 2.0f;
	}
	// 速度重置
	public static void resetSpeed()
	{
		if(fishSpeed[0] > 120.f)
		{
			for(int i = 0; i < fishSpeed.length; i++)
				fishSpeed[i] *= 0.5f;
		}
	}
	
}

	