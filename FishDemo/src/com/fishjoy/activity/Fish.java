package com.fishjoy.activity;

import java.util.Random;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import com.fishjoy.model.GameParas;

public class Fish extends AnimatedSprite implements GameParas{

	private PhysicsHandler mPhysicsHandler;
	private int Id;							// 5����ı��:0~4
	//String direction;						// �ζ�����RIGHT/LEFT/RANDOM
	String pathType="Line";						// ·������:Circle/Line
	
	int direction;						// �����������������ζ�����
	float X=0.0f, Y=0.0f;			// ��ĳ�ʼ����λ��
	
	private final Random rand = new Random();
	
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
		float rotation = this.getRotation();	// ��ǰ��λ�Ƕȣ��̳У�
		
		if(pathType == "Line")				// ֱ���ζ�
		{
			if(isOutOfBound())
			{
			}
		}
		// �������˶�ʱ����λ��rotation�ڲ��ϱ仯��Ϊ�˱����ٶȣ���Ҫʵʱ�������ٶ�
		else if(pathType == "Circle")
		{
			switch(direction)
			{
			case 0:		// ��������
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
			case 1:		// ���ҵ���
			{
				if(rotation>=360)
				{
					mPhysicsHandler.setAngularVelocity(0);
					mPhysicsHandler.setVelocity(-40, 0);
					setRotation(360);								
				}
				else if(x<(CAMERA_WIDTH/2) || (x>(CAMERA_WIDTH/2) && y<Y))
				{
					mPhysicsHandler.setAngularVelocity(40);			// ���ٶ�=40
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
					mPhysicsHandler.setAccelerationY(5);			// ��ֱ������ٶ�
					//mPhysicsHandler.setVelocity(-1*(float)Math.cos(rotation*3.14/180)*80, -1*(float)Math.sin(rotation*3.14/180)*80);
					float v_Y = mPhysicsHandler.getVelocityY();
					float v_X = mPhysicsHandler.getVelocityX();
					//Log.i("��ʾ", String.valueOf(v_Y / v_X));
					this.setRotation((float) (Math.atan(v_Y / v_X) * 180 / Math.PI));
			}
			else if((direction==0) && (x>CAMERA_WIDTH/2) ||(direction == 1) && (x<CAMERA_WIDTH/2))
			{
				mPhysicsHandler.setAccelerationY(0);			// ֱ���ζ�
			}
		}
	}

	public void setCurvePath()
	{
		pathType = "Curve";
		switch(direction)
		{
		case 0:		// ��������
		{
			setPosition(X, Y);
			mPhysicsHandler.setVelocity(40, 0);
			setRotation(180);
			break;
		}
		case 1:		// ��������
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
	
	// ��ʼ��ֱ��·��
	public void setLinePath()
	{
		pathType = "Line";
		//float speed=ModelInformationgetInstance().getFishInformation(name).get_speed();
		float speed = fishSpeed[this.Id];
		switch(direction)
		{
		case 0:		//���������ζ�
		{			
			setPosition(X, Y);
			// ��ת180�ȣ���ͷ���ң����������ζ�
			setRotation(180);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(180*3.14/180),
					-1*speed*(float)Math.sin(180*3.14/180));	
			break;
		}
		case 1:		// ��������
		{
			setPosition(X, Y);	
			setRotation(0);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(0*3.14/180),
					-1*speed*(float)Math.sin(0*3.14/180));		
			break;
		}
		case 2:		// ��������
		{
			setPosition(X, Y);	
			setRotation(-90);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(-90*3.14/180),
					-1*speed*(float)Math.sin(-90*3.14/180));		
			break;
		}
		case 3:		// ��������
		{
			setPosition(X, Y);	
			setRotation(90);
			mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(90*3.14/180),
					-1*speed*(float)Math.sin(90*3.14/180));		
			break;
		}
		};
	}
	
	// ��ʼ��Բ��·��
	public void setCirclePath()
	{
		pathType = "Circle";
		switch(direction)
		{
		case 0:		// ��������
		{
			setPosition(X, Y);
			mPhysicsHandler.setVelocity(40, 0);		// �����ٶ�Ϊ������ʾˮƽ�����ٶ�����
			mPhysicsHandler.setAngularVelocity(0);	// ���ٶ�Ϊ0
			setRotation(180);	// ������ͷ����(ͼƬ����ͷ����)
			Log.i("����180�Ⱥ����꣺", this.getX() + "," + this.getY());
			break;
		}
		case 1:		// ��������
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
	
	// ��������ζ�����,��Ӱ�쵽������ĳ�ʼλ��
	public void setDirection(String dir)
	{
		//this.direction = dir;
		if(dir == "Left")
		{
			// ��λ����X����࣬����պ���TextureRegion�Ŀ��
			// ���Xֻ�ǳ�ʼֵ��Yֱ��Ҫ����ָ��
			this.X   = -1 * fishRegion[this.Id][0];
			float p_Y = Math.abs(rand.nextFloat()) * CAMERA_HEIGHT;
			this.setY(p_Y);
			this.direction = 0;
			Log.i("�ζ�����", "��������");
		}
		else if(dir == "Right")
		{
			this.direction = 1;
			this.X = CAMERA_WIDTH;
			float p_Y = Math.abs(rand.nextFloat()) * CAMERA_HEIGHT;		//�����ȡY����
			this.setY(p_Y);
			Log.i("�ζ�����", "��������");
		}
		else if(dir == "Up")		// ���ϵ���
		{
			Log.i("�ζ�����", "���ϵ���");
			this.direction = 2;
			this.Y = -1 * fishRegion[this.Id][0];
			//this.Y = CAMERA_HEIGHT / 2;
			this.X = Math.abs(rand.nextFloat()) * CAMERA_WIDTH;
		}
		else if(dir == "Down")
		{
			Log.i("�ζ�����", "���µ���");
			this.direction = 3;
			this.Y = CAMERA_HEIGHT;
			this.X = Math.abs(rand.nextFloat()) * CAMERA_WIDTH;
		}
	}
}




