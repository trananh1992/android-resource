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
		super(0, 0, pTiledTextureRegion);
		// ��������ע����´�����
		//mPhysicsHandler = new PhysicsHandler(this);
		//this.registerUpdateHandler(mPhysicsHandler);		
	}
	
	// ��������ע��ĸ��´�����������������
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		Log.i("��ʾ", "Fish.onManagedUpdate()");	// ÿʱÿ�̶��ڵ���
		super.onManagedUpdate(pSecondsElapsed);
	}

	public void show()
	{
		Log.i("��ʾ", "����Fish�ķ���");	
	}

}




