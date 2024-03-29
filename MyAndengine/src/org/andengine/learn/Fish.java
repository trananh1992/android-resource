package org.andengine.learn;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.learn.Sprite01;

import android.util.Log;

public class Fish extends AnimatedSprite {

	private PhysicsHandler mPhysicsHandler;
	private int Id;							// 5种鱼的编号
	String direction;						// 游动方向：RIGHT/LEFT/RANDOM
	String pathType;						// 路径类型:Circle/Line
	
	int way, X, Y;
	
	public Fish(TiledTextureRegion pTiledTextureRegion) 
	{
		// 必须构造父类
		super(0, 150, pTiledTextureRegion);
		// 精灵自身注册更新处理器
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);
		float VelocityX = 40;
		float VelocityY = 40;
		mPhysicsHandler.setVelocity(VelocityX, 0);
	}
	
	// 错误：精灵自身注册的更新处理器会调用这个更新
	// 只要fish在场景冲，就会调用，不管是否注册
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed)
	{
		Log.i("提示", "Fish.onManagedUpdate()");	// 每时每刻都在调用
		super.onManagedUpdate(pSecondsElapsed);
		if(this.getX() > 240)
		{
			Log.i("Fish提示：", "准备在onManagedUpdate中子宫！");
			this.detachSelf();
			Sprite03.showKids();
		}
	}

	public void show()
	{
		Log.i("提示", "调用Fish的方法");	
	}

}




