package com.fishjoy.game;

import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import android.graphics.Color;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.fishjoy.Entity.Cannon;
import com.fishjoy.Entity.Fish;
import com.fishjoy.Entity.Net;
import com.fishjoy.factory.FishFactory;
import com.fishjoy.factory.FishMonitor;
import com.fishjoy.factory.TextRegionFactory;

public class GamePlay extends BaseGameActivity implements IOnSceneTouchListener, IOnMenuItemClickListener{
	
	private Camera myCamera;
	private Engine mEngine;

	public Scene mMainScene = new Scene();
	private MenuScene mPauseScene;

	public ArrayList<TiledTextureRegion> FishRegion = new ArrayList<TiledTextureRegion>();	
	public ArrayList<TiledTextureRegion> BulletRegion = new ArrayList<TiledTextureRegion>();		
	public ArrayList<TiledTextureRegion> NetRegion = new ArrayList<TiledTextureRegion>();			
	public ArrayList<TiledTextureRegion> ScoreRegion = new ArrayList<TiledTextureRegion>();	
	
	protected TiledTextureRegion cannonTextureRegion, mSoundTextureRegion, 
								 mTimeNumTextureRegion, mScoreNumTextureRegion, mGoldTextureRegion;	
	protected TextureRegion bgEasyTextureRegion, bgNormalTextureRegion, bgHardTextureRegion;
	protected TextureRegion mReturnTextureRegion, mResetTextureRegion, mQuitTextureRegion,
								mPauseTextureRegion, mSTTextureRegion;	
	
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;

	private Cannon cannon;

	private AnimatedSprite soundon;
	private Sprite pause ;
	
	private float timeRunning;			// 游戏进行时间
	private boolean gamePause = false;
	private boolean gameRunning = true;
	
	public Engine onLoadEngine() {
		//获取屏幕像素
		WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        GameControl.CAMERA_WIDTH = display.getWidth();
        GameControl.CAMERA_HEIGHT = display.getHeight();

        GameControl.SCALEX = GameControl.CAMERA_WIDTH / 480;
        GameControl.SCALEY = GameControl.CAMERA_HEIGHT / 320;
        
        //初始化游戏
        GameControl.score = 0;
        GameControl.timeAnimatedSprites.clear();
        GameControl.scoreAnimatedSprites.clear();
        GameControl.bullets.clear();
        GameControl.catchProbabiity = 8 - 2 * GameControl.GAMEMODE;
        
		this.myCamera = new Camera(0, 0, GameControl.CAMERA_WIDTH, GameControl.CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), myCamera).setNeedsMusic(true));
		return mEngine;
	}

	/**
	 * 读取游戏资源
	 */
	public void onLoadResources() {
		// TODO Auto-generated method stub
		FontFactory.setAssetBasePath("font/");
		this.mFontTexture = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, this, "Plok.ttf", 32, true, Color.WHITE);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pic/");
		
		this.mReturnTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForReturn(this.mEngine, this);
		this.mResetTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForReset(this.mEngine, this);
		this.mQuitTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForQuit(this.mEngine, this);
		this.mPauseTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForPause(this.mEngine, this);
		
		this.bgEasyTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForBackgroundEasy(this.mEngine, this);
		this.bgNormalTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForBackgroundNormal(this.mEngine, this);
		this.bgHardTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForBackgroundHard(this.mEngine, this);
		
		this.cannonTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForCannon(this.mEngine, this);		

		this.mSoundTextureRegion = TextRegionFactory.getSingleInstance().createRegionForSound(this.mEngine, this);
		this.mGoldTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForGold(this.mEngine, this);
		
		this.mTimeNumTextureRegion = TextRegionFactory.getSingleInstance().createRegionForTimeNum(this.mEngine, this);
		this.mScoreNumTextureRegion = TextRegionFactory.getSingleInstance().createRegionForScoreNum(this.mEngine, this);
		this.mSTTextureRegion = TextRegionFactory.getSingleInstance().createRegionForST(this.mEngine, this);
		
		TextRegionFactory.getSingleInstance().CreateRegionForFish(FishRegion, mEngine, this);
		TextRegionFactory.getSingleInstance().CreateRegionForNet(NetRegion, this.mEngine, this);
		TextRegionFactory.getSingleInstance().CreateRegionForBullet(BulletRegion, this.mEngine, this);
		TextRegionFactory.getSingleInstance().createRegionForScore(ScoreRegion, this.mEngine, this);
		

		MusicFactory.setAssetBasePath("music/");
		try {
			GameControl.bgMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "BGSound0.ogg");
			GameControl.bgMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
		
		this.mEngine.getTextureManager().loadTextures( this.mFontTexture);	
		this.mEngine.getFontManager().loadFont(this.mFont);
	}

	/**
	 * 读入场景
	 */
	public Scene onLoadScene() {
		
		this.createMainScene();		
		this.createMenuScene();	
		
		this.createCannon();
		this.createFish();
		
		//显示时间和分数
		TimeAndScore.createST(mSTTextureRegion, mTimeNumTextureRegion, 
				mScoreNumTextureRegion, mMainScene);
		
		//显示音乐按钮
		this.soundon = Musicplay.createBgMusic(mSoundTextureRegion, mMainScene);

		this.createPauseButton();
		
		this.update();
		TextSE.createTexts(this.mFont, mMainScene, this);

		//为炮台，音乐按钮，暂停按钮设置监听器
		mMainScene.registerTouchArea(cannon);	
		mMainScene.registerTouchArea(soundon);
		mMainScene.registerTouchArea(pause);
		mMainScene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			
			public boolean onAreaTouched(TouchEvent arg0, ITouchArea arg1, float arg2,
					float arg3) {
				// TODO Auto-generated method stub
				if (arg1 == cannon) {
					/**
					 * 转换炮台
					 */
					 if (arg0.getAction() == TouchEvent.ACTION_DOWN) {  
				            Cannon can = (Cannon) arg1;
				            can.switchit();
					 }
				}
				else if(arg1 == soundon){
					/**
					 * 停止或开始播放音乐
					 */
					 if (arg0.getAction() == TouchEvent.ACTION_DOWN) {
						if(GameControl.musicNeeded == true){
							soundon.animate(new long[]{0, 100});
							GameControl.musicNeeded = false;
							GameControl.bgMusic.pause();
							
						}
						else {
							soundon.animate(new long[]{100, 0});
							GameControl.musicNeeded =true;
							GameControl.bgMusic.play();
						}
					 }
				}
				else if (arg1 == pause) {
					if(mMainScene.hasChildScene()) {
						/* Remove the menu and reset it. */
						mPauseScene.back();
					} else {
						/* Attach the menu. */
						mMainScene.setChildScene(mPauseScene, false, true, true);		
					}
				}
			     return true; 
			}
		});	

		return mMainScene;
	}
	
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 创建主场景
	 */
	protected void createMainScene() {
		mMainScene.setOnSceneTouchListener(this);
		
		//根据游戏模式设置背景
		final AutoParallaxBackground autoParallaxBackground = 
				new AutoParallaxBackground(0, 0, 0, 5);
		Sprite background = null;
		if(GameControl.GAMEMODE == 1)
			background = new Sprite(0, 0, this.bgEasyTextureRegion);
		else if(GameControl.GAMEMODE == 2)
			background = new Sprite(0, 0, this.bgNormalTextureRegion);
		else {
			background = new Sprite(0, 0, this.bgHardTextureRegion);
		}
		background.setSize(GameControl.CAMERA_WIDTH, GameControl.CAMERA_HEIGHT);	
		autoParallaxBackground.attachParallaxEntity(
				new ParallaxEntity(0f, background));
		mMainScene.setBackground(autoParallaxBackground);
	}
	
	/**
	 * 创建菜单场景，菜单场景只出现在玩家点击返回键或者暂停按钮显示
	 */
	protected void createMenuScene() {
		this.mPauseScene = new MenuScene(this.myCamera);

		final SpriteMenuItem returnItem = new SpriteMenuItem(0, this.mReturnTextureRegion);
		returnItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPauseScene.addMenuItem(returnItem);
		
		final SpriteMenuItem resetItem = new SpriteMenuItem(1, this.mResetTextureRegion);
		resetItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPauseScene.addMenuItem(resetItem);

		final SpriteMenuItem quitItem = new SpriteMenuItem(2, this.mQuitTextureRegion);
		quitItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPauseScene.addMenuItem(quitItem);

		this.mPauseScene.buildAnimations();

		this.mPauseScene.setBackgroundEnabled(false);

		this.mPauseScene.setOnMenuItemClickListener(this);
	}
		
	/**
	 * 创建炮台
	 */
	protected void createCannon() {
		cannon = new Cannon(GameControl.CAMERA_WIDTH/2 - GameControl.CANNON_WIDTH/2, 
				GameControl.CAMERA_HEIGHT - GameControl.CANNON_HEIGHT, cannonTextureRegion);
		mMainScene.attachChild(cannon);
	}
	
	protected void createFish() {
		// 鱼的活动层 ：第1层
		mMainScene.attachChild(new Entity());
		// 根据不同游戏模式创建不同的初始游动序列（这里暂用简单模式）
		
		FishFactory.setEngine(mEngine);
		FishFactory.getSingleInstance().createInitialPath(mMainScene, GameControl.movingFish, FishRegion, GameControl.GAMEMODE);
	
		// 20秒后开始随机游动序列
				timeRunning = 0.0f;
				// 注册业务到业务线程
				mMainScene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
				{ //注册时间监听器（0.05秒级）
					private float timeSlaps = 0.0f;
					public void onTimePassed(final TimerHandler pTimerHandler) 
					{				
						//if (gamePause == true){}			// 暂时未添加游戏暂停逻辑
						//else if (gameRunning == true )
						//{
							timeRunning += 1 / 20.0f;
							timeSlaps   += 1 / 20.0f;
							if(timeRunning > 20.0f)			// 10秒后开始随机游动序列
							{
								FishFactory.getSingleInstance().createRandomFish(mMainScene, GameControl.movingFish, FishRegion);
								if(timeSlaps > 10.0f)
								{   // 每隔10秒创建一次鱼群
									FishFactory.getSingleInstance().createFishGroup(mMainScene, GameControl.movingFish, FishRegion);
									timeSlaps = 0.0f;				
								}	
								// 监视器：实时更新场景中的鱼（清除超出场景的鱼）
								if(GameControl.movingFish.size() > 0)
									FishMonitor.getSingleInstance().onFishMove(GameControl.movingFish);
							}	
						//}
					}
				}));
	}
	
	/**
	 * 创建暂停按钮
	 */
	protected void createPauseButton() {
		pause = new Sprite(GameControl.CAMERA_WIDTH - 100, GameControl.CAMERA_HEIGHT - 100, this.mPauseTextureRegion);
		mMainScene.attachChild(pause);
	}
	
	/**
	 * 更新进程
	 */
	protected void update() {
		mMainScene.registerUpdateHandler(new IUpdateHandler() {
			
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				if (GameControl.bullets != null) {
					int size = GameControl.bullets.size();
					
					//检测碰撞
					for (int i = 0; i < size; i++) {
						
						for (int j = 0; j < GameControl.movingFish.size(); j++) {
							if (GameControl.movingFish.get(j).collidesWith(GameControl.bullets.get(i))) {

								final Net net1 = GameControl.bullets.get(i).generateNet(NetRegion.get(cannon.getType() - 1), mMainScene);

								for (int k = 0; k < GameControl.movingFish.size(); k++) {
									if(net1.collidesWith(GameControl.movingFish.get(k))){
										if (Math.random()*10 < GameControl.catchProbabiity) {
											catchFish(GameControl.movingFish.get(k), k);
										}	
									}
								}
								final int x = i;
								runOnUpdateThread(new Runnable() {
									public void run() {
										/* Now it is save to remove the entity! */
										if (GameControl.bullets.size() > x) {
											mMainScene.detachChild(GameControl.bullets.get(x));
											GameControl.bullets.remove(x);
										}
										
									}
								});	
								size -= 1;
							}
						}
					}
				}
			}
		});
	}
	
	public void catchFish(Fish fishx, final int m){
		final float x = fishx.getX();
		final float y = fishx.getY();
		int type = fishx.getType();
		runOnUpdateThread(new Runnable() {
			public void run() {
				/* Now it is save to remove the entity! */
				if (GameControl.movingFish.size() > m) {
					GameControl.score += GameControl.movingFish.get(m).getScore();
				}
			}
		});	
		GameControl.movingFish.get(m).detachSelf();
		GameControl.movingFish.remove(m);
		final Fish fish = new Fish(x, y, GamePlay.this.FishRegion.get(2).deepCopy());
		mMainScene.attachChild(fish);
		fish.animate(100);
		fish.setSize(36 * GameControl.SCALEX, 18 * GameControl.SCALEY);
		mMainScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
			
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mMainScene.unregisterUpdateHandler(pTimerHandler);
				mMainScene.detachChild(fish);
			}
		}));
		CatchFish.createScore(x, y - 10, mMainScene, ScoreRegion.get(type));
		CatchFish.createGold(x, y, mMainScene, mGoldTextureRegion);
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(this.mMainScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mPauseScene.back();
			} else {
				/* Attach the menu. */
				this.mMainScene.setChildScene(this.mPauseScene, false, true, true);		
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
	
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		// TODO Auto-generated method stub
		//旋转炮台
		cannon.rotate(arg1.getX(), arg1.getY() );
		
		//发射渔网		
		cannon.generateBullet(BulletRegion.get(cannon.getType() - 1), arg1.getX(), arg1.getY(), this, mMainScene);
		
		return false;
	}
	
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		case 0:
			if(this.mMainScene.hasChildScene()) {
				this.mPauseScene.back();
			} 
		case 1:
			return true;
		case 2:
			this.finish();
			return true;
		default:
			return false;
		}
	}
}
