package com.yinzch.fishdemo;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.yinzch.data.GameParas;

public class Fish extends AnimatedSprite implements GameParas{

	private PhysicsHandler mPhysicsHandler;
	Fish_Name name;
	Fish_Move move;					// Circle/Curve/Random
	Move_Direction direction;		// RIGHT/LEFT/RANDOM
	float current_X,current_Y;
	float current_rotation;
	Edge_Position position;
	
	int way, X, Y;
	
	public Fish(Fish_Name _name,TiledTextureRegion pTiledTextureRegion) 
	{
		// 必须构造父类
		super(0, 0, pTiledTextureRegion);
		
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);		// 精灵自身注册更新处理器
		name=_name;
	}
	
	// 设置直线游动的初始速度（方向和大小）
	private void Direct_initial(int rotation)
	{
		float speed = 60;
		switch(way)				// 向右游动还是向左
		{
		case 0:					// 向右游动 : way = 0 = Move_Direction.RIGHT
		{			
			setPosition(X, Y);	
			current_X = X;
			current_Y = Y;
			current_rotation = rotation;
			setRotation(current_rotation);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(current_rotation*3.14/180),
					-1*speed*(float)Math.sin(current_rotation*3.14/180));		
			break;
		}
		case 1:
		{
			setPosition(X, Y);
			current_X = X;
			current_Y = Y;
			current_rotation = rotation + 160;
			setRotation(current_rotation);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(current_rotation*3.14/180),
					-1*speed*(float)Math.sin(current_rotation*3.14/180));	
			break;
		}
		};
	}
	
	// 精灵自身注册的更新处理器会调用这个更新
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		super.onManagedUpdate(pSecondsElapsed);
		// 更新鱼
		float x=this.getX();			// 获取鱼精灵当前的坐标(继承)
		float y=this.getY();
		float rotation = this.getRotation();		// 当前方位角度（继承）
		
		if(this.move.equals(Fish_Move.Direct))		// 直线游动
		{
			//if(isOutOfBound())
			{
			}
		}
		current_rotation = rotation;
		current_X = x;
		current_Y = y;
	}

	// 初始化直线游动类型
	public void set_Direct_Move(int rotation)
	{
		this.move=Fish_Move.Direct;					// 游动类型：直线游动
		Direct_initial(rotation);					// rotation参数代表RIGHT/LEFT/RANDOM
	}
	
	// 设置鱼的游动方向（左右或两者之一）
	public void set_side(Move_Direction _diretion)
	{
		this.direction=_diretion;
		
		if(this.direction.equals(Move_Direction.RANDOM))
		{
			/*controller.way=Math.abs(random.nextInt())%2;
			if(controller.way==1)
			{
				controller.X=-1*ModelInformationController.getInstance().getFishInformation(controller.name).get_TextureRegion_width();
			}
			else
			{
				controller.X=CAMERA_WIDTH;
			}*/
		}
		else
		{
			switch(this.direction)
			{
			case LEFT:
			{
				this.way = 1;
				// controller.X=-1*ModelInformationController.getInstance().getFishInformation(controller.name).get_TextureRegion_width();
				break;
			}
			case RIGHT:
			{
				this.way = 0;
				this.X = CAMERA_WIDTH;
				break;
			}
			}
		}
	}
	
	public void set_fixed_Y(int _position)
	{
		this.Y = _position;
	}
	
	public void set_fixed_X(int _position)
	{
		this.X = _position;
	}
}




