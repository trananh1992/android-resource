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
	String pathType="Line";					// ·������:Circle/Line
	
	int direction;						// �����������������ζ�����
	float X=0.0f, Y=0.0f;				// ��ĳ�ʼ����λ��
	float speed	 =0;					// ���ٶ�
	float angular = 0.0f;
	float accX=0.0f;				// ���ٶ�
	float accY=0.0f;
	
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
		float x=this.getX();						// ��ȡ�㾫�鵱ǰ������(�̳�)
		float y=this.getY();
		float rotation = this.getRotation();		// ��ǰ��λ�Ƕȣ��̳У�
		float v_Y = mPhysicsHandler.getVelocityY();	// ��ǰ�ٶ�
		float v_X = mPhysicsHandler.getVelocityX();
		
		if(pathType == "Circle")
		{
			switch(direction)
			{
			case 0:		// ��������
			{
				if(rotation<=-180)
				{
					mPhysicsHandler.setAngularVelocity(0);
					mPhysicsHandler.setVelocity(this.speed, 0);
					setRotation(-180);						
				}
				else if(x>(CAMERA_WIDTH/2) || (x<(CAMERA_WIDTH/2) && y<Y))
				{
					 mPhysicsHandler.setAngularVelocity(-this.angular);
					 mPhysicsHandler.setVelocity((float)Math.cos((180-rotation)*Math.PI/180) * this.speed, 
							 -1*(float)Math.sin((180-rotation)*Math.PI/180) * this.speed);
				}
				break;
			}
			case 1:		// ���ҵ���
			{
				if(rotation>=360)
				{
					mPhysicsHandler.setAngularVelocity(0);
					mPhysicsHandler.setVelocity(-this.speed, 0);
					setRotation(360);								
				}
				else if(x<(CAMERA_WIDTH/2) || (x>(CAMERA_WIDTH/2) && y<Y))
				{
					mPhysicsHandler.setAngularVelocity(this.angular);
					mPhysicsHandler.setVelocity(-1*(float)Math.cos(rotation*Math.PI/180) * this.speed, 
							-1*(float)Math.sin(rotation*Math.PI/180) * this.speed);
				}
				
				break;
			}
			};
		}
		else if(pathType == "Curve")
		{
			if((direction==0) && (x>CAMERA_WIDTH/4) || (direction == 1) && (x<CAMERA_WIDTH/4*3))
			{
				if(y >= CAMERA_HEIGHT/2)
					mPhysicsHandler.setAccelerationY(-5);			// ��ֱ������ٶ�
				if(y < CAMERA_HEIGHT/2)
					mPhysicsHandler.setAccelerationY(5);
				this.setRotation((1-direction)*180 + (float)(Math.atan(v_Y / v_X)*180/Math.PI));
			}
			else if((direction==0) && (x>CAMERA_WIDTH/2) ||(direction == 1) && (x<CAMERA_WIDTH/2))
				mPhysicsHandler.setAccelerationY(0);			// ֱ���ζ�
		}
		else if(pathType == "Group")
		{
			if(y >= CAMERA_HEIGHT/2)
				mPhysicsHandler.setAccelerationY(-5);			// ��ֱ������ٶ�
			if(y < CAMERA_HEIGHT/2)
				mPhysicsHandler.setAccelerationY(5);
			this.setRotation((1-direction)*180 + (float)(Math.atan(v_Y / v_X)*180/Math.PI));
		}
		else if(pathType == "Bridge")
		{
			this.setRotation((1-direction)*180 + (float)(Math.atan(v_Y / v_X)*180/Math.PI));
		}
	}

	public void setBridgePath(int i)
	{
		pathType = "Bridge";
		this.speed = fishSpeed[this.Id]+(i*40);
		float rotation = 0.0f;
		switch(direction)
		{
		case 0:		// ��������
		{
			setPosition(X, Y);
			rotation = 135;
			break;
		}
		case 1:		// ��������
		{
			setPosition(X, Y);	
			rotation = 45;	
			//this.accY = 40;
			break;
		}
		};
		setRotation(rotation);
		float speed_X = -1*speed*(float)Math.cos(rotation*Math.PI/180);
		float speed_Y = -1*speed*(float)Math.sin(rotation*Math.PI/180);
		mPhysicsHandler.setVelocity(speed_X, speed_Y);
		accY = 2 * Math.abs(speed_X * speed_Y) / CAMERA_WIDTH;
		mPhysicsHandler.setAccelerationY(accY);
	}
	
	public void setGroupPath(int num, float p_Y)
	{
		pathType = "Group";
		this.speed = fishSpeed[this.Id];
		float rotation = 0.0f;
		switch(direction)
		{
		case 0:				//���������ζ�
			rotation = 180;
			this.setp_Y(p_Y + groupWay[num][1]*fishRegion[this.Id][1]);
			this.setp_X(-1*groupWay[num][0]*fishRegion[this.Id][0]);
			mPhysicsHandler.setVelocity(speed, 0);
			break;
		case 1:				// ��������
			this.setp_Y(p_Y + groupWay[num][1]*fishRegion[this.Id][1]);
			this.setp_X(CAMERA_WIDTH-1*groupWay[num][0]*fishRegion[this.Id][0]);
			mPhysicsHandler.setVelocity(-1*speed, 0);
			rotation = 0;
			break;
		}
		setPosition(X, Y);	
		setRotation(rotation);
		//mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(rotation*3.14/180),
				//-1*speed*(float)Math.sin(rotation*3.14/180));	
	}
	
	//��������·��
	public void setCurvePath()
	{
		pathType = "Curve";
		this.speed = fishSpeed[this.Id];
		switch(direction)
		{
		case 0:		// ��������
		{
			setPosition(X, Y);
			setRotation(180);
			mPhysicsHandler.setVelocity(speed, 0);
			break;
		}
		case 1:		// ��������
		{
			setPosition(X, Y);	
			setRotation(0);	
			mPhysicsHandler.setVelocity(-speed,0);
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
		speed = fishSpeed[this.Id];
		float rotation = 0.0f;
		float temp = Math.abs(rand.nextFloat()*90);		// 0��90��һ��������
		switch(direction)
		{
		case 0:				//���������ζ�
			rotation = 180;
			break;
		case 1:				// ��������
			rotation = 0;
			break;
		case 2:				// ��������
		case 3:				// ��������
			rotation = (direction*2-5)*(45.0f+temp);
			break;
		}
		setPosition(X, Y);	
		setRotation(rotation);
		mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(rotation*3.14/180),
				-1*speed*(float)Math.sin(rotation*3.14/180));		
	}
	
	// ��ʼ��Բ��·��
	public void setCirclePath()
	{
		pathType = "Circle";
		this.speed = fishSpeed[this.Id];
		float rotation = 0.0f;
		switch(direction)
		{
		case 0:				//���������ζ�
			rotation = 180;
			break;
		case 1:				// ��������
			rotation = 0;
			break;
		}
		setPosition(X, Y);
		setRotation(rotation);
		mPhysicsHandler.setVelocity(-1*speed*(float)Math.cos(rotation * Math.PI /180),
				-1*speed*(float)Math.sin(rotation * Math.PI/180));
		// ��������뾶��������ٶ�
		float r = Math.abs(rand.nextFloat()*3);
		this.angular = this.speed / r;
	}
	
	// ��������ζ�����,��Ӱ�쵽������ĳ�ʼλ��
	public void setDirection(String dir)
	{
		//this.direction = dir;
		if(dir == "Left")
		{
			// ��λ����X����࣬����պ���TextureRegion�Ŀ��
			// ���Xֻ�ǳ�ʼֵ��Yֱ��Ҫ����ָ��
			this.setp_X(-1 * fishRegion[this.Id][0]);
			float p_Y = Math.abs(rand.nextFloat()) * CAMERA_HEIGHT;
			this.setp_Y(p_Y);
			this.direction = 0;
			Log.i("�ζ�����", "��������");
		}
		else if(dir == "Right")
		{
			this.direction = 1;
			this.setp_X(CAMERA_WIDTH);
			float p_Y = Math.abs(rand.nextFloat()) * CAMERA_HEIGHT;		//�����ȡY����
			this.setp_Y(p_Y);
			Log.i("�ζ�����", "��������");
		}
		else if(dir == "Up")		// ���ϵ���
		{
			Log.i("�ζ�����", "���ϵ���");
			this.direction = 2;
			this.setp_Y(-1 * fishRegion[this.Id][0]);
			this.setp_X(Math.abs(rand.nextFloat()) * CAMERA_WIDTH);
		}
		else if(dir == "Down")
		{
			Log.i("�ζ�����", "���µ���");
			this.direction = 3;
			this.setp_Y(CAMERA_HEIGHT);
			this.setp_X(Math.abs(rand.nextFloat()) * CAMERA_WIDTH);
		}
	}
	

	public void setp_Y(float p_y)
	{
		this.Y = p_y;
	}
	
	public void setp_X(float p_x)
	{
		this.X = p_x;
	}
	
}




