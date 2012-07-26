package com.fishjoy.activity;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.model.GameParas;

public class Fish extends AnimatedSprite implements GameParas{

	private PhysicsHandler mPhysicsHandler;
	private int Id;							// 5����ı��
	String direction;						// �ζ�����RIGHT/LEFT/RANDOM
	String pathType;						// ·������:Circle/Line
	
	int way, X, Y;
	
	public Fish(int fishId,TiledTextureRegion pTiledTextureRegion) 
	{
		// ���빹�츸��
		super(0, 0, pTiledTextureRegion);
		Id = fishId;
		
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);		// ��������ע����´�����
	}
	
	// ��������ע��ĸ��´�����������������
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		//Log.i("��ʾ", "Fish.onManagedUpdate()");	// ÿʱÿ�̶��ڵ���
		super.onManagedUpdate(pSecondsElapsed);
		// ������
		float x=this.getX();				// ��ȡ�㾫�鵱ǰ������(�̳�)
		float y=this.getY();
		float rotation = this.getRotation();// ��ǰ��λ�Ƕȣ��̳У�
		
		if(pathType == "Line")				// ֱ���ζ�
		{
			//if(isOutOfBound())
			{
			}
		}
		else if(pathType == "Circle")	// Ϊ�����ζ����������λ
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
	
	// ��������ζ�����,��Ӱ�쵽������ĳ�ʼλ��
	public void setDirection(String dir)
	{
		this.direction = dir;
		if(dir == "Left")
		{
			// ��λ����X����࣬����պ���TextureRegion�Ŀ��
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




