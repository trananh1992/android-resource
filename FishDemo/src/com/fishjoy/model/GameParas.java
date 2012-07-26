package com.fishjoy.model;

public interface GameParas
{
	public static final int FishKindsNum = 5;
	
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
	
	public enum Fish_Move{
		Direct,Curve, Circle,Random
		};
		
	public enum Fish_State{
		moving,captured,finished
		};
		
	public enum Fish_Name{
		SARDINE, CLOWNFISH, PUFFERFISH,TORTOISE,SHARK
	}
	
	public enum Artillery_Rank{
		RANK1, RANK2, RANK3, RANK4, RANK5, 
	}
	
	public enum Artillery_Operate{
		STRENGTHEN, WEAKEN,
	}

	public enum Move_Direction{
		RANDOM,LEFT,RIGHT
	}
	
	public enum Edge_Position{
		RANDOM,UP,MIDDLE,DOWN
	}
	
	public enum Game_Rank{
		EASY, COMMON, HARD
	}
	
	public enum Fish_Group_Way{
		Curve_Group, Circle_Group, Random_Single
	}

}

