package com.fishjoy.Entity;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import com.fishjoy.game.GameControl;
import com.fishjoy.game.GamePlay;

public class Cannon extends AnimatedSprite{
	
	protected PhysicsHandler mPhysicsHandler;
	
	private int type;				//炮台类型
	private float angle;			//旋转角度
	
	public Cannon(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		this.type = 1;
		setSize(getWidth() * GameControl.SCALEX - 30, getHeight() * GameControl.SCALEY);
	}

	/*
	 * 炮台旋转
	 */
	public void rotate(float x, float y) {
		
		angle = MathUtils.atan2(getY() + getHeight()/2 - y, 
				(getX() + getWidth()/2 - x));
		registerEntityModifier(new RotationModifier((float) 0.2, getRotation(), MathUtils.radToDeg(angle) - 90 ));
	}
	
	/*
	 * 生成子弹和运动路径
	 */
	public void generateBullet(TiledTextureRegion bulletTextureRegion, float x, float y, 
			final GamePlay gamePlay, Scene scene) {
		// TODO Auto-generated method stub
		if (GameControl.musiceffect) {	
			GameControl.fireMusic.play();
		}
		
		float anglex = MathUtils.radToDeg(angle) - 90;
		double sin = Math.sin(anglex * Math.PI / 180);
		double cos = Math.cos(anglex * Math.PI / 180);
		float xx = (float) (getX()+getRotationCenterX() + getHeight() / GameControl.SCALEY * 0.5 * sin - 10);
		float yy = (float) (getY()+getRotationCenterY() - getHeight() / GameControl.SCALEY * 0.5 * cos);
		final Bullet bullet = new Bullet(xx, yy, getType(), bulletTextureRegion.clone());		
		bullet.setRotation(anglex);
		
		Path path = new Path(2).to(bullet.getX(), bullet.getY()).to(x - bullet.getWidth()/2, 
				y - bullet.getHeight()/2);
		float xxx = x - bullet.getX();
		float yyy = y - bullet.getY();
		float k = (float) Math.sqrt(xxx * xxx + yyy * yyy);
		PathModifier pModifier = new PathModifier(k / 200, path);
		
		// 为路径添加监听器
		pModifier.setPathModifierListener(new IPathModifierListener() 
		{
			// 运动结束后将子弹清除
			public void onPathWaypointFinished(PathModifier arg0, IEntity arg1, int arg2) {
				// TODO Auto-generated method stub
				gamePlay.runOnUpdateThread(new Runnable() {
					public void run() {
						/* Now it is save to remove the entity! */
						final int x = GameControl.bullets.indexOf(bullet);
						if (GameControl.bullets.size() > x) {
							gamePlay.mMainScene.detachChild(bullet);
							GameControl.bullets.remove(x);
						}
					}
				});	
			}
			
			public void onPathStarted(PathModifier arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPathFinished(PathModifier arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				
			}
		});
		
		bullet.registerEntityModifier(pModifier);
		GameControl.bullets.add(bullet);
		scene.attachChild(bullet);
	}
	
	/*
	 * 转换炮台
	 */
	public void switchit(){
		if (GameControl.musiceffect) {	
			GameControl.switchMusic.play();
		}
		if(getType() == 1){
	    	animate(new long[]{0, 100, 0, 0, 0});
	    }
	    else if(getType() == 2){
	    	animate(new long[]{0, 0, 100, 0, 0});
	    }
	    else if(getType() == 3){
	    	animate(new long[]{0, 0, 0, 100, 0});
	    }
	    else if(getType() == 4){
	    	animate(new long[]{0, 0, 0, 0, 100});
	    }
	    else if(getType() == 5){
	    	animate(new long[]{100, 0, 0, 0, 0});			            	
	    }
	    setType();
	}
	
	public void setType() {
		if(type < 5)
			type++;
		else {
			type = 1;
		}
	}
	
	public void setType(int x){
		if (GameControl.musiceffect) {	
			GameControl.switchMusic.play();
		}
		if(x > 0 && x < 6)
			type = x;
		animate(new long[]{100}, new int[]{x - 1}, 1);
	}
	
	public int getType() {
		return type;
	}
	
	public PhysicsHandler getPhysicsHandler() {
		return this.mPhysicsHandler;
	}
	
	@Override
	public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
		// TODO Auto-generated method stub
		this.mPhysicsHandler = (PhysicsHandler) pUpdateHandler;
		super.registerUpdateHandler(pUpdateHandler);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		
		super.onManagedUpdate(pSecondsElapsed);
	}
}
