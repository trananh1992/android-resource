package com.fishjoy.activity;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.model.GameParas;

public class Fish extends AnimatedSprite implements GameParas{

	private PhysicsHandler mPhysicsHandler;
	private int Id;							// 5种鱼的编号
	String direction;						// 游动方向：RIGHT/LEFT/RANDOM
	String pathType;						// 路径类型:Circle/Line
	
	int way, X, Y;
	
	public Fish(int fishId,TiledTextureRegion pTiledTextureRegion) 
	{
		// 必须构造父类
		super(0, 0, pTiledTextureRegion);
		Id = fishId;
		
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);		// 精灵自身注册更新处理器
	}
	
	// 精灵自身注册的更新处理器会调用这个更新
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		//Log.i("提示", "Fish.onManagedUpdate()");	// 每时每刻都在调用
		super.onManagedUpdate(pSecondsElapsed);
		// 更新鱼
		float x=this.getX();				// 获取鱼精灵当前的坐标(继承)
		float y=this.getY();
		float rotation = this.getRotation();// 当前方位角度（继承）
		
		if(pathType == "Line")				// 直线游动
		{
			//if(isOutOfBound())
			{
			}
		}
		else if(pathType == "Circle")	// 为环型游动的鱼调整方位
		{
			switch(way)
			{
			case 0:
			{
				if(rotation>=360)
				{
					mPhysicsHandler.setAngularVelocity(0);
					mPhysicsHandler.setVelocity(-40, 0);
					setRotation(360);								
				}
				else if(x<(CAMERA_WIDTH/2) || (x>(CAMERA_WIDTH/2) && y<Y))
				{
					mPhysicsHandler.setAngularVelocity(40);
					mPhysicsHandler.setVelocity(-1*(float)Math.cos(rotation*3.14/180)*40, -1*(float)Math.sin(rotation*3.14/180)*40);
				}
				
				break;
			}
			case 1:
			{
				if(rotation<=-180)
				{
					mPhysicsHandler.setAngularVelocity(0);
					mPhysicsHandler.setVelocity(40, 0);
					setRotation(-180);						
				}
				else if(x>(CAMERA_WIDTH/2) || (x<(CAMERA_WIDTH/2) && y<Y))
				{
					 mPhysicsHandler.setAngularVelocity(-45);
					 mPhysicsHandler.setVelocity((float)Math.cos((180-rotation)*3.14/180)*40, -1*(float)Math.sin((180-rotation)*3.14/180)*40);
				}
				
				break;
			}
		};

		}
	}

	public boolean isOutOfBound()
	{
		if(this.getX() < -60)
			return true;
		else 
			return false;
	}
	
	public void setCirclePath()
	{
		pathType = "Circle";
		Circle_initial();
	}
	
	private void Circle_initial()
	{
		switch(way)
		{
		case 0:
		{
			setPosition(X, Y);		
			mPhysicsHandler.setVelocity(-40,0);
			mPhysicsHandler.setAngularVelocity(0);
			setRotation(0);
			break;
		}
		case 1:
		{
			setPosition(X, Y);
			mPhysicsHandler.setVelocity(40, 0);
			mPhysicsHandler.setAngularVelocity(0);
			setRotation(180);
		
			break;
		}
		};
	}
	
	public void setY(int p_y)
	{
		this.Y = p_y;
	}
	
	public void setX(int p_x)
	{
		this.X = p_x;
	}
	
	// 设置鱼的游动方向,它影响到横坐标的初始位置
	public void setDirection(String dir)
	{
		this.direction = dir;
		if(dir == "Left")
		{
			// 该位置在X轴左侧，距离刚好是TextureRegion的宽度
			this.X = -60;
			this.way = 1;
		}
		else if(dir == "Right")
		{
			this.way = 0;
			this.X = CAMERA_WIDTH;
		}
	}
	
	public void setPathType(String t)
	{
		pathType = t;
	}
}




