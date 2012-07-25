package com.yinzch.fishdemo;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.yinzch.data.GameParas;

public class Fish extends AnimatedSprite implements GameParas{

	private PhysicsHandler mPhysicsHandler;
	Fish_Name name;
	
	public Fish(Fish_Name _name,TiledTextureRegion pTiledTextureRegion) 
	{
		// ���빹�츸��
		super(0, 0, pTiledTextureRegion);
		
		mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(mPhysicsHandler);		// ��������ע����´�����
		name=_name;
	}
}
