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
		// ���빹�츸��
		super(0, 0, pTiledTextureRegion);
		
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);		// ��������ע����´�����
		name=_name;
	}
	
	// ����ֱ���ζ��ĳ�ʼ�ٶȣ�����ʹ�С��
	private void Direct_initial(int rotation)
	{
		float speed = 60;
		switch(way)				// �����ζ���������
		{
		case 0:					// �����ζ� : way = 0 = Move_Direction.RIGHT
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
	
	// ��������ע��ĸ��´�����������������
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		super.onManagedUpdate(pSecondsElapsed);
		// ������
		float x=this.getX();			// ��ȡ�㾫�鵱ǰ������(�̳�)
		float y=this.getY();
		float rotation = this.getRotation();		// ��ǰ��λ�Ƕȣ��̳У�
		
		if(this.move.equals(Fish_Move.Direct))		// ֱ���ζ�
		{
			//if(isOutOfBound())
			{
			}
		}
		current_rotation = rotation;
		current_X = x;
		current_Y = y;
	}

	// ��ʼ��ֱ���ζ�����
	public void set_Direct_Move(int rotation)
	{
		this.move=Fish_Move.Direct;					// �ζ����ͣ�ֱ���ζ�
		Direct_initial(rotation);					// rotation��������RIGHT/LEFT/RANDOM
	}
	
	// ��������ζ��������һ�����֮һ��
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




