package com.yinzch.data;

public interface GameParas extends GameEnum
{
	
	public static final int CAMERA_WIDTH = 480; 
	public static final int CAMERA_HEIGHT = 320;

	public static final int LAYER_COUNT = 4;
		
	// 场景中各层间的次序问题
	public static final int LAYER_BACKGROUND = 0;
	public static final int LAYER_FISH = LAYER_BACKGROUND + 1;
	public static final int LAYER_BULLET = LAYER_FISH;
	public static final int LAYER_NET = LAYER_BULLET;
	public static final int LAYER_ARTILLERY = LAYER_BULLET + 1;
	public static final int LAYER_BUTTON = LAYER_ARTILLERY - 1;
	public static final int LAYER_SCORE = LAYER_ARTILLERY + 1;	
	// 一层
	public static final int LAYER_STATE = LAYER_FISH + 1;
	
	public static final int ARTILLERY_MAX_RANK = 4;
	public static final int ARTILLERY_MIN_RANK = 0;
	
	public static final int FISHLIST_MAX = 25;
	
	public int TOTAL_TIME = 60;
	public static int MIN_SCORE = 480;

}

