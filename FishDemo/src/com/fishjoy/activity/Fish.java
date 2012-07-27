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
	
	int way;			// �����������������ζ�����
	float X, Y;			// ��ĳ�ʼλ��
	
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
			if(isOutOfBound())
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
		if(	this.getX() > CAMERA_WIDTH  
				|| this.getX() < -1 * fishRegion[this.Id-1][0]
				|| this.getY() > CAMERA_HEIGHT
				|| this.getY() < -1 * fishRegion[this.Id-1][1]
		    )
			return true;
		else 
			return false;
	}
	
	// ��ʼ��ֱ��·��
	public void setLinePath()
	{
		pathType = "Line";
		//float speed=ModelInformationgetInstance().getFishInformation(name).get_speed();
		float speed = fishSpeed[this.Id];
		switch(way)
		{
		case 0:
		{			
			setPosition(X, Y);	
			setRotation(0);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(0*3.14/180),
					-1*speed*(float)Math.sin(0*3.14/180));		
			break;
		}
		case 1:
		{
			setPosition(X, Y);
			setRotation(180);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(180*3.14/180),
					-1*speed*(float)Math.sin(180*3.14/180));	
			break;
		}
		};
	}
	
	// ��ʼ��Բ��·��
	public void setCirclePath()
	{
		pathType = "Circle";
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
	
	public void setY(float p_y)
	{
		this.Y = p_y;
	}
	
	public void setX(float p_x)
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
			// ���Xֻ�ǳ�ʼֵ��Yֱ��Ҫ����ָ��
			this.X   = -1 * fishRegion[this.Id-1][0];
			this.way = 1;
		}
		else if(dir == "Right")
		{
			this.way = 0;
			this.X = CAMERA_WIDTH;
		}
	}
}




