package com.fishjoy.activity;

import java.util.Random;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import com.fishjoy.model.GameParas;

public class Fish extends AnimatedSprite implements GameParas{

	private PhysicsHandler mPhysicsHandler;
	private int Id;							// 5种鱼的编号:0~4
	//String direction;						// 游动方向：RIGHT/LEFT/RANDOM
	String pathType="Line";						// 路径类型:Circle/Line
	
	int direction;						// 辅助变量：标记鱼的游动方向
	float X=0.0f, Y=0.0f;			// 鱼的初始坐标位置
	
	private final Random rand = new Random();
	
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
		float rotation = this.getRotation();	// 当前方位角度（继承）
		
		if(pathType == "Line")				// 直线游动
		{
			if(isOutOfBound())
			{
			}
		}
		// 做曲线运动时，方位角rotation在不断变化，为了保持速度，需要实时调整分速度
		else if(pathType == "Circle")
		{
			switch(direction)
			{
			case 0:		// 从左向右
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
			case 1:		// 从右到左
			{
				if(rotation>=360)
				{
					mPhysicsHandler.setAngularVelocity(0);
					mPhysicsHandler.setVelocity(-40, 0);
					setRotation(360);								
				}
				else if(x<(CAMERA_WIDTH/2) || (x>(CAMERA_WIDTH/2) && y<Y))
				{
					mPhysicsHandler.setAngularVelocity(40);			// 角速度=40
					mPhysicsHandler.setVelocity(-1*(float)Math.cos(rotation*3.14/180)*40, -1*(float)Math.sin(rotation*3.14/180)*40);
				}
				
				break;
			}
			};
		}
		else if(pathType == "Curve")
		{
			if((direction==0) && (x>CAMERA_WIDTH/4) || (direction == 1) && (x<CAMERA_WIDTH/4*3))
			{
					mPhysicsHandler.setAccelerationY(5);			// 竖直方向加速度
					//mPhysicsHandler.setVelocity(-1*(float)Math.cos(rotation*3.14/180)*80, -1*(float)Math.sin(rotation*3.14/180)*80);
					float v_Y = mPhysicsHandler.getVelocityY();
					float v_X = mPhysicsHandler.getVelocityX();
					//Log.i("提示", String.valueOf(v_Y / v_X));
					this.setRotation((float) (Math.atan(v_Y / v_X) * 180 / Math.PI));
			}
			else if((direction==0) && (x>CAMERA_WIDTH/2) ||(direction == 1) && (x<CAMERA_WIDTH/2))
			{
				mPhysicsHandler.setAccelerationY(0);			// 直线游动
			}
		}
	}

	public void setCurvePath()
	{
		pathType = "Curve";
		switch(direction)
		{
		case 0:		// 从左向右
		{
			setPosition(X, Y);
			mPhysicsHandler.setVelocity(40, 0);
			setRotation(180);
			break;
		}
		case 1:		// 从右向左
		{
			setPosition(X, Y);		
			mPhysicsHandler.setVelocity(-40,0);
			setRotation(0);
			break;
		}
		};
	}
	
	public boolean isOutOfBound()
	{
		//if(this.mX + this.getWidth() > CAMERA_WIDTH)
		//if(this.mY + this.getHeight() > CAMERA_HEIGHT)
		if(	this.getX() > CAMERA_WIDTH  
				|| this.getX() < -1 * fishRegion[this.Id][0]
				|| this.getY() > CAMERA_WIDTH
				|| this.getY() < -1 * fishRegion[this.Id][0]
		    )
			return true;
		else 
			return false;
	}
	
	// 初始化直线路径
	public void setLinePath()
	{
		pathType = "Line";
		//float speed=ModelInformationgetInstance().getFishInformation(name).get_speed();
		float speed = fishSpeed[this.Id];
		switch(direction)
		{
		case 0:		//从左向右游动
		{			
			setPosition(X, Y);
			// 旋转180度，鱼头向右，从左向右游动
			setRotation(180);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(180*3.14/180),
					-1*speed*(float)Math.sin(180*3.14/180));	
			break;
		}
		case 1:		// 从右向左
		{
			setPosition(X, Y);	
			setRotation(0);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(0*3.14/180),
					-1*speed*(float)Math.sin(0*3.14/180));		
			break;
		}
		case 2:		// 从上向下
		{
			setPosition(X, Y);	
			setRotation(-90);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(-90*3.14/180),
					-1*speed*(float)Math.sin(-90*3.14/180));		
			break;
		}
		case 3:		// 从下向上
		{
			setPosition(X, Y);	
			setRotation(90);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(90*3.14/180),
					-1*speed*(float)Math.sin(90*3.14/180));		
			break;
		}
		};
	}
	
	// 初始化圆形路径
	public void setCirclePath()
	{
		pathType = "Circle";
		switch(direction)
		{
		case 0:		// 从左向右
		{
			setPosition(X, Y);
			mPhysicsHandler.setVelocity(40, 0);		// 横向速度为正，表示水平方向速度向右
			mPhysicsHandler.setAngularVelocity(0);	// 角速度为0
			setRotation(180);	// 调整鱼头方向(图片中鱼头向左)
			Log.i("调整180度后坐标：", this.getX() + "," + this.getY());
			break;
		}
		case 1:		// 从右向左
		{
			setPosition(X, Y);		
			mPhysicsHandler.setVelocity(-40,0);
			mPhysicsHandler.setAngularVelocity(0);
			setRotation(0);
			break;
		}
		};
	}
	
	public void setY(float p_y)
	{
		this.Y = p_y;
	}
	
	public void setX(float p_x)
	{
		this.X = p_x;
	}
	
	// 设置鱼的游动方向,它影响到横坐标的初始位置
	public void setDirection(String dir)
	{
		//this.direction = dir;
		if(dir == "Left")
		{
			// 该位置在X轴左侧，距离刚好是TextureRegion的宽度
			// 这个X只是初始值，Y直需要另外指定
			this.X   = -1 * fishRegion[this.Id][0];
			float p_Y = Math.abs(rand.nextFloat()) * CAMERA_HEIGHT;
			this.setY(p_Y);
			this.direction = 0;
			Log.i("游动方向", "从左向右");
		}
		else if(dir == "Right")
		{
			this.direction = 1;
			this.X = CAMERA_WIDTH;
			float p_Y = Math.abs(rand.nextFloat()) * CAMERA_HEIGHT;		//随机获取Y坐标
			this.setY(p_Y);
			Log.i("游动方向", "从右向左");
		}
		else if(dir == "Up")		// 从上到下
		{
			Log.i("游动方向", "从上到下");
			this.direction = 2;
			this.Y = -1 * fishRegion[this.Id][0];
			//this.Y = CAMERA_HEIGHT / 2;
			this.X = Math.abs(rand.nextFloat()) * CAMERA_WIDTH;
		}
		else if(dir == "Down")
		{
			Log.i("游动方向", "从下到上");
			this.direction = 3;
			this.Y = CAMERA_HEIGHT;
			this.X = Math.abs(rand.nextFloat()) * CAMERA_WIDTH;
		}
	}
}




