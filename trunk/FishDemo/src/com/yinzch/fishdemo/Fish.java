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
		case 0:					// ����ֱ���ζ� : way = 0 = Move_Direction.RIGHT
		{			
			setPosition(X, Y);	// �趨����ĳ�ʼλ��(X��Y����Ŀ��λ��)
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
			setPosition(X, Y);	// ����ֱ���ζ�
			current_X = X;
			current_Y = Y;
			current_rotation = rotation + 160;
			setRotation(current_rotation);
			// ����Ŀ�귽���趨X��Y�ٶ�
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
		else if(move.equals(Fish_Move.Circle))	// Ϊԭ���ζ����������λ
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
					mPhysicsHandler.setAngularVelocity(45);
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
		current_rotation = rotation;
		current_X = x;
		current_Y = y;
	}

	public boolean isOutOfBound()
	{
		if(this.getX() < -60)
			return true;
		else 
			return false;
	}
	
	// ��ʼ��ֱ���ζ�����
	public void set_Direct_Move(int rotation)
	{
		this.move=Fish_Move.Direct;					// �ζ����ͣ�ֱ���ζ�
		Direct_initial(rotation);					// rotation��������RIGHT/LEFT/RANDOM
	}
	
	public void set_edge_position(Edge_Position _position)
	{
		position=_position;
			switch(position)
			{
			case UP:
			{		
				Y=70;
				break;
			}
			case MIDDLE:
			{		
				Y=130;
				break;
			}
			case DOWN:
			{			
				Y=190;
				break;
			}
			}
	}
	
	public void set_Circle_Move()
	{
		this.move = Fish_Move.Circle;
		Circle_initial();
	}
	
	private void Circle_initial()
	{
		switch(way)
		{
		case 0:
		{
			setPosition(X, Y);		
			current_X=X;
			current_Y=Y;
			mPhysicsHandler.setVelocity(-40,0);
			mPhysicsHandler.setAngularVelocity(0);
			setRotation(0);
			break;
		}
		case 1:
		{
			setPosition(X, Y);
			current_X=X;
			current_Y=Y;
			mPhysicsHandler.setVelocity(40, 0);
			mPhysicsHandler.setAngularVelocity(0);
			setRotation(180);
		
			break;
		}
		};
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
				// ��λ����X����࣬����պ���TextureRegion�Ŀ��
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




