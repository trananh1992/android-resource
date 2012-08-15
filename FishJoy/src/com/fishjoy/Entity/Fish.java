package com.fishjoy.Entity;

import java.util.Random;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.fishjoy.game.GameControl;

public class Fish extends AnimatedSprite{

	private PhysicsHandler controller;
	private int Id;							// 5����ı��:0~4
	//String direction;						// �ζ�����RIGHT/LEFT/UP/DOWN
	String pathType="Line";					// ·������:Circle/Line
	
	int direction;						// �����������������ζ�����
	float X=0.0f, Y=0.0f;				// ��ĳ�ʼ����λ��
	float speed	 =0;					// ���ٶ�
	float angular = 0.0f;
	float accX=0.0f;					// ���ٶ�
	float accY=0.0f;
	
	float score = 1;
	int type;
	
	private final Random rand = new Random();
	
	public float getScore() {
		return GameControl.fishScore[Id];
	}
	
	public int getType() {
		return Id;
	}
	
	public Fish(int fishId,TiledTextureRegion pTiledTextureRegion) 
	{
		// ���빹�츸��
		super(0, 0, pTiledTextureRegion);
		Id = fishId;
		type = 1;
		controller = new PhysicsHandler(this);
		this.registerUpdateHandler(controller);		// ��������ע����´�����
	}
	
	public Fish(float x2, float y2, TiledTextureRegion deepCopy) {
		// TODO Auto-generated constructor stub
		super(x2, y2, deepCopy);
		type = 1;
		controller = new PhysicsHandler(this);
		this.registerUpdateHandler(controller);	
	}

	public void setBridgePath(int s, boolean flag)
	{
		pathType = "Bridge";
		
		this.speed = 200 + 100*s;
		float rotation = 0.0f;
		
		int para = 2*s - 1;
		
		switch(direction)
		{
		case 0:		// ��������
		{
			setPosition(X, Y);
			rotation = para*135;
			break;
		}
		case 1:		// ��������
		{
			setPosition(X, Y);	
			rotation = para*45;	
			//this.accY = 40;
			break;
		}
		};
		setRotation(rotation);
		float speed_X = -1*speed*(float)Math.cos(rotation*Math.PI/180);
		float speed_Y = -1*speed*(float)Math.sin(rotation*Math.PI/180);
		controller.setVelocity(speed_X, speed_Y);
		if(flag == true)
			this.accY = para * 3 * Math.abs(speed_X * speed_Y) / GameControl.CAMERA_WIDTH;
		else
			this.accY = para * Math.abs(speed_X * speed_Y) / GameControl.CAMERA_WIDTH;
		controller.setAccelerationY(accY);
		//controller.setAccelerationY(accX);
	}
	
	public void setGroupPath(int num, float p_Y)
	{
		pathType = "Group";
		this.speed = GameControl.fishSpeed[this.Id];
		float rotation = 0.0f;
		switch(direction)
		{
		case 0:				//���������ζ�
			rotation = 180;
			this.setp_Y(p_Y + GameControl.groupWay[num][1]*GameControl.fishRegion[this.Id][1]);
			this.setp_X(-1*GameControl.groupWay[num][0]*GameControl.fishRegion[this.Id][0]);
			controller.setVelocity(speed, 0);
			break;
		case 1:				// ��������
			this.setp_Y(p_Y + GameControl.groupWay[num][1]*GameControl.fishRegion[this.Id][1]);
			this.setp_X(GameControl.CAMERA_WIDTH-1*GameControl.groupWay[num][0]*GameControl.fishRegion[this.Id][0]);
			controller.setVelocity(-1*speed, 0);
			rotation = 0;
			break;
		}
		setPosition(X, Y);	
		setRotation(rotation);
		float a = Math.abs(rand.nextFloat()*10) + 10.0f;
		this.accX = a;
		this.accY = a;
	}
	
	//��������·��
	public void setCurvePath(int dir)
	{
		pathType = "Curve";	
		//setDirection(GameControl.fishDir[dir]);
		this.speed = GameControl.fishSpeed[this.Id];
		switch(direction)
		{
		case 0:		// ��������
		{
			setPosition(X, Y);
			setRotation(180);
			controller.setVelocity(speed, 0);
			break;
		}
		case 1:		// ��������
		{
			setPosition(X, Y);	
			setRotation(0);	
			controller.setVelocity(-speed,0);
			break;
		}
		case 2:						// ��������
			setPosition(X, Y);	
			setRotation(-90);	
			controller.setVelocity(0,speed);
			break;
		case 3:						// ��������
			setPosition(X, Y);	
			setRotation(90);	
			controller.setVelocity(0,-speed);
			break;
		};
		float a = Math.abs(rand.nextFloat()*10) + 5.0f;
		this.accX = a;
		//this.accY = a;
		if(this.Y >= GameControl.CAMERA_HEIGHT/2)
			this.accY = -1 * a;						// ��ֱ������ٶ�
		if(this.Y < GameControl.CAMERA_HEIGHT/2)
			this.accY = a;
	}
	
	public boolean BoundaryCheck()
	{
		//if(this.mX + this.getWidth() > CAMERA_WIDTH)
		//if(this.mY + this.getHeight() > CAMERA_HEIGHT)
		if(	this.getX() > GameControl.CAMERA_WIDTH  
				|| this.getX() < -1 * GameControl.fishRegion[this.Id][0]
				|| this.getY() > GameControl.CAMERA_WIDTH
				|| this.getY() < -1 * GameControl.fishRegion[this.Id][0]
		    )
			return true;
		else 
			return false;
	}
	
	// ��ʼ��ֱ��·��
	public void setLinePath(int dir)
	{
		//setDirection(GameControl.fishDir[dir]);
		pathType = "Line";
		speed = GameControl.fishSpeed[this.Id];
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
		controller.setVelocity(-1*speed*(float)Math.cos(rotation*3.14/180),
				-1*speed*(float)Math.sin(rotation*3.14/180));		
	}
	
	// ��ʼ��Բ��·��
	public void setCirclePath(int dir)
	{
		//setDirection(GameControl.fishDir[dir]);
		pathType = "Circle";
		this.speed = GameControl.fishSpeed[this.Id];
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
		controller.setVelocity(-1*speed*(float)Math.cos(rotation * Math.PI /180),
				-1*speed*(float)Math.sin(rotation * Math.PI/180));
		// ��������뾶��������ٶ�
		float r = Math.abs(rand.nextFloat()*3);
		this.angular = this.speed / r;
		float a = Math.abs(rand.nextFloat()*10) + 10.0f;
		this.accY = a;
	}
	
	// ��������ע��ĸ��´�����������������
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		//Log.i("��ʾ", "Fish.onManagedUpdate()");	// ÿʱÿ�̶��ڵ���
		super.onManagedUpdate(pSecondsElapsed);

		float x=this.getX();						// ��ȡ�㾫�鵱ǰ������(�̳�)
		float y=this.getY();
		float rotation = this.getRotation();		// ��ǰ��λ�Ƕȣ��̳У�
		float v_Y = controller.getVelocityY();		// ��ǰ�ٶ�
		float v_X = controller.getVelocityX();

		if(pathType == "Curve")
		{
			if((direction==0) && (x <= GameControl.CAMERA_WIDTH/2) ||(direction == 1) && (x >= GameControl.CAMERA_WIDTH/2))
			{
				controller.setAccelerationY(accY);
				this.setRotation((1-direction)*180 + (float)(Math.atan(v_Y / v_X)*180/Math.PI));
			}
			else if((direction==0) && (x > GameControl.CAMERA_WIDTH/2) ||(direction == 1) && (x < GameControl.CAMERA_WIDTH/2))
				controller.setAccelerationY(0);			// 
			else if(direction == 2 || direction == 3)
			{
				if(x < GameControl.CAMERA_WIDTH/4)
					controller.setAccelerationX(accX);
				else
					controller.setAccelerationX(-1*accX);
				float theta = (float)(Math.atan(v_Y/v_X)*180/Math.PI);
				if(theta <= 0)
					this.setRotation((direction-2)*180+theta);
				else
					this.setRotation((3-direction)*180+theta);	
			};
		}
		else if(pathType == "Circle")
		{
			
			if(rotation <= -180 || rotation >= 360)
			{
				controller.setAngularVelocity(0);
				this.pathType = "Group";			// �����˶�������ʼ�����˶�
			}
			else
			{
				float targetX = GameControl.CAMERA_WIDTH/4*(1+2*direction);			// ����Ŀ��λ��
				float targetA = this.angular * (2*direction - 1);					// ����Ŀ����ٶ�
				if((direction==0 && x>=targetX) || (direction==1 && x<=targetX))	// ����Ŀ��λ�ÿ�ʼ��ת
					;
				else if(y >= Y)
					return;
				controller.setAngularVelocity(targetA);
				controller.setVelocity((float)Math.cos((180-rotation)*Math.PI/180) * this.speed, 
						-1*(float)Math.sin((180-rotation)*Math.PI/180) * this.speed);
					
			}
		}
		else if(pathType == "Group")
		{
			if(y >= GameControl.CAMERA_HEIGHT/2)
				controller.setAccelerationY(-1*this.accY);			// ��ֱ������ٶ�
			if(y < GameControl.CAMERA_HEIGHT/2)
				controller.setAccelerationY(this.accY);
			this.setRotation((1-direction)*180 + (float)(Math.atan(v_Y / v_X)*180/Math.PI));
		}
		else if(pathType == "Bridge")
		{
			this.setRotation((1-direction)*180 + (float)(Math.atan(v_Y / v_X)*180/Math.PI));
		}
	}
		
	// ��������ζ�����,��Ӱ�쵽������ĳ�ʼλ��
	public void setDirection(String dir)
	{
		//this.direction = dir;
		if(dir == "Left")
		{
			// ��λ����X����࣬����պ���TextureRegion�Ŀ��
			// ���Xֻ�ǳ�ʼֵ��Yֱ��Ҫ����ָ��
			this.setp_X(-1 * GameControl.fishRegion[this.Id][0]);
			float p_Y = Math.abs(rand.nextFloat()) * GameControl.CAMERA_HEIGHT;
			this.setp_Y(p_Y);
			this.direction = 0;
			//Log.i("�ζ�����", "��������");
		}
		else if(dir == "Right")
		{
			this.direction = 1;
			this.setp_X(GameControl.CAMERA_WIDTH);
			float p_Y = Math.abs(rand.nextFloat()) * GameControl.CAMERA_HEIGHT;		//�����ȡY����
			this.setp_Y(p_Y);
			//Log.i("�ζ�����", "��������");
		}
		else if(dir == "Up")		// ���ϵ���
		{
			//Log.i("�ζ�����", "���ϵ���");
			this.direction = 2;
			this.setp_Y(-1 * GameControl.fishRegion[this.Id][0]);
			this.setp_X(Math.abs(rand.nextFloat()) * GameControl.CAMERA_WIDTH);
		}
		else if(dir == "Down")
		{
			//Log.i("�ζ�����", "���µ���");
			this.direction = 3;
			this.setp_Y(GameControl.CAMERA_HEIGHT);
			this.setp_X(Math.abs(rand.nextFloat()) * GameControl.CAMERA_WIDTH);
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




