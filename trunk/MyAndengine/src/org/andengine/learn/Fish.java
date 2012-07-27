package org.andengine.learn;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.learn.Sprite01;

import android.util.Log;

public class Fish extends AnimatedSprite {

	private PhysicsHandler mPhysicsHandler;
	private int Id;							// 5����ı��
	String direction;						// �ζ�����RIGHT/LEFT/RANDOM
	String pathType;						// ·������:Circle/Line
	
	int way, X, Y;
	
	public Fish(TiledTextureRegion pTiledTextureRegion) 
	{
		// ���빹�츸��
		super(0, 150, pTiledTextureRegion);
		// ��������ע����´�����
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);
		float VelocityX = 40;
		float VelocityY = 40;
		mPhysicsHandler.setVelocity(VelocityX, 0);
	}
	
	// ���󣺾�������ע��ĸ��´�����������������
	// ֻҪfish�ڳ����壬�ͻ���ã������Ƿ�ע��
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		Log.i("��ʾ", "Fish.onManagedUpdate()");	// ÿʱÿ�̶��ڵ���
		super.onManagedUpdate(pSecondsElapsed);
		if(this.getX() > 240)
		{
			Log.i("Fish��ʾ��", "׼����onManagedUpdate���ӹ���");
			this.detachSelf();
			Sprite03.showKids();
		}
	}

	public void show()
	{
		Log.i("��ʾ", "����Fish�ķ���");	
	}

}




