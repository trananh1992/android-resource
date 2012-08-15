package com.fishjoy.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
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
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.fishjoy.R;
import com.fishjoy.Entity.Cannon;
import com.fishjoy.Entity.Fish;
import com.fishjoy.Entity.Net;
import com.fishjoy.data.MyDbAdapter;
import com.fishjoy.factory.FishFactory;
import com.fishjoy.factory.FishMonitor;
import com.fishjoy.factory.TextRegionFactory;

public class GamePlay extends BaseGameActivity implements IOnSceneTouchListener, IOnMenuItemClickListener
{	
	private Camera myCamera;
	private Engine mEngine;
	
	public Scene mMainScene = new Scene();
	private MenuScene mPauseScene, mPopUpMenuScene;

	public ArrayList<TiledTextureRegion> FishRegion = new ArrayList<TiledTextureRegion>();	
	public ArrayList<TiledTextureRegion> BulletRegion = new ArrayList<TiledTextureRegion>();		
	public ArrayList<TiledTextureRegion> NetRegion = new ArrayList<TiledTextureRegion>();			
	public ArrayList<TiledTextureRegion> ScoreRegion = new ArrayList<TiledTextureRegion>();	
	//public ArrayList<TiledTextureRegion> toolRegion = new ArrayList<TiledTextureRegion>();
	public ArrayList<TiledTextureRegion> goldRegion = new ArrayList<TiledTextureRegion>();
	
	protected TiledTextureRegion cannonTextureRegion, mSoundTextureRegion, 
								 mTimeNumTextureRegion, mScoreNumTextureRegion;	
	protected TextureRegion bgEasyTextureRegion, bgNormalTextureRegion, bgHardTextureRegion;
	protected TextureRegion mReturnTextureRegion, mResetTextureRegion, mQuitTextureRegion,
								mPauseTextureRegion, mSTTextureRegion, mMenuBgteTextureRegion,
								mBackToMenuTextureRegion, mPlayAgainTextureRegion;	
	
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;

	private Cannon cannon;

	private AnimatedSprite soundon;
	private Sprite pause ;
	
	private boolean gameRunning;
	
	public MyDbAdapter db;
	
	public Engine onLoadEngine() {	
		//��ȡ��Ļ����
		WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        GameControl.CAMERA_WIDTH = display.getWidth();
        GameControl.CAMERA_HEIGHT = display.getHeight();

        GameControl.SCALEX = GameControl.CAMERA_WIDTH / 480;
        GameControl.SCALEY = GameControl.CAMERA_HEIGHT / 320;
        
        // ���������Ϸ���ݵĳ�ʼ����GameControlֻ�ڵ�һ�γ�ʼ��
        GameControl.score = 0;
        //GameControl.time = 0.0f;
        GameControl.GAME_LAST_TIME = GameControl.GAME_TIME;
        GameControl.timeAnimatedSprites.clear();
        GameControl.scoreAnimatedSprites.clear();
        GameControl.bullets.clear();
        GameControl.movingFish.clear();
        GameControl.catchProbabiity = 8 - 2 * GameControl.GAMEMODE;
        GameControl.A_TOTAL = 250;
        
        db = new MyDbAdapter(this);
        gameRunning = true;
   
		this.myCamera = new Camera(0, 0, GameControl.CAMERA_WIDTH, GameControl.CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), myCamera).setNeedsMusic(true));
		return mEngine;
	}
	
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
		//this.mGoldTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForGold(this.mEngine, this);
		
		this.mTimeNumTextureRegion = TextRegionFactory.getSingleInstance().createRegionForTimeNum(this.mEngine, this);
		this.mScoreNumTextureRegion = TextRegionFactory.getSingleInstance().createRegionForScoreNum(this.mEngine, this);
		this.mSTTextureRegion = TextRegionFactory.getSingleInstance().createRegionForST(this.mEngine, this);
		
		this.mMenuBgteTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForMenuBackground(mEngine, GamePlay.this);
		this.mBackToMenuTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForMenuBackToMenu(mEngine, this);
		this.mPlayAgainTextureRegion = TextRegionFactory.getSingleInstance().CreateRegionForMenuPlayAgain(mEngine, this);
		
		
		TextRegionFactory.getSingleInstance().CreateRegionForFish(FishRegion, mEngine, this);
		TextRegionFactory.getSingleInstance().CreateRegionForNet(NetRegion, this.mEngine, this);
		TextRegionFactory.getSingleInstance().CreateRegionForBullet(BulletRegion, this.mEngine, this);
		TextRegionFactory.getSingleInstance().createRegionForScore(ScoreRegion, this.mEngine, this);
		// Ϊ��Һ͵��ߴ�������
		TextRegionFactory.getSingleInstance().createRegionForGold(goldRegion, this.mEngine, this);
		

		MusicFactory.setAssetBasePath("music/");
		try {
			GameControl.bgMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "BGSound0.ogg");
			GameControl.bgMusic.setLooping(true);
			
			GameControl.fireMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "cannonfire.ogg");
			GameControl.coinMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "coin.ogg");
			GameControl.switchMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "cannonswitch.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		
		this.mEngine.getTextureManager().loadTextures( this.mFontTexture);	
		this.mEngine.getFontManager().loadFont(this.mFont);
	}

	public Scene onLoadScene() {
		this.doAsync(R.string.resid1, R.string.resid2, new Callable<Void>(){  
			// ϣ��AndEngine�첽���ص�����  
			public Void call() throws Exception 
			{  
				createMainScene();		
				createMenuScene();	
				createCannon();
				createFish();
				createPauseButton();

				//��ʾʱ�䡢�������ڵ�������������
				TimeAndScore.createST(mSTTextureRegion, mTimeNumTextureRegion, mScoreNumTextureRegion, mMainScene, GamePlay.this);
				
				//��ʾ���ְ�ť
				soundon = Musicplay.createBgMusic(mSoundTextureRegion, mMainScene);

				//��ʾ��ʼ��Ϸ�ı���
				GamePlay.this.displayText("��Ϸ��ʼ!\nĿ�����:" + GameControl.GameScore[GameControl.GAMEMODE]);

				//���½��̣������ײ�벶��
				update();
				
				//������Ϸ���水ť
				setListeners();

				mMainScene.registerUpdateHandler(new TimerHandler(3.0f, new ITimerCallback() 
				{
					public void onTimePassed(TimerHandler arg0) {
						//Log.i("��ʾ", "����");  
						gameRunning = true;
					}
				}));

				return null;
			}

			// ��������ɺ�ص������ڴ˽���һЩ������ϵ��º���  
		}, new org.anddev.andengine.util.Callback<Void>() {  
			public void onCallback(final Void pCallbackValue) {  
				Log.d("Callback", "over");  
			}  
		});  
		
		return mMainScene;
	}
	
	public void onLoadComplete() 
	{
		
	}
	
	// ��ʾ��Ϸ�ı�
	public void displayText(String text)
	{
		TextSE ttSe = new TextSE();
		ttSe.createTexts(mFont, mMainScene, GamePlay.this, text);
	}
	
	// ��ʱ��С��0ʱ���ã�������Ϸ������������
	public void stopGame()
	{
		gameRunning = false;
		//GameControl.GAME_LAST_TIME = 0;
		for (int i = 1; i < 4; i++) {
			GameControl.timeAnimatedSprites.get(i).stopAnimation();
			GameControl.scoreAnimatedSprites.get(i).stopAnimation();
		}
		createPopUpMenuScene();
		mMainScene.setChildScene(mPopUpMenuScene, false, true, true);
		saveScore();
	}
	
	protected void createMainScene() 
	{
		mMainScene.setOnSceneTouchListener(this);
		
		//������Ϸģʽ���ñ���
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		Sprite background = null;
		if(GameControl.GAMEMODE == 1)
			background = new Sprite(0, 0, this.bgEasyTextureRegion);
		else if(GameControl.GAMEMODE == 2)
			background = new Sprite(0, 0, this.bgNormalTextureRegion);
		else {
			background = new Sprite(0, 0, this.bgHardTextureRegion);
		}
		background.setSize(GameControl.CAMERA_WIDTH, GameControl.CAMERA_HEIGHT);	
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0f, background));
		mMainScene.setBackground(autoParallaxBackground);
	}
	
	protected void createMenuScene() {
		this.mPauseScene = new MenuScene(this.myCamera);

		final SpriteMenuItem returnItem = new SpriteMenuItem(0, this.mReturnTextureRegion);
		returnItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPauseScene.addMenuItem(returnItem);

		final SpriteMenuItem quitItem = new SpriteMenuItem(1, this.mQuitTextureRegion);
		quitItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPauseScene.addMenuItem(quitItem);
		
		this.mPauseScene.buildAnimations();
		this.mPauseScene.setBackgroundEnabled(false);
		this.mPauseScene.setOnMenuItemClickListener(this);
	}
	
	//����������ť��ͷ
	 protected void createPopUpMenuScene() {
		  this.mPopUpMenuScene = new MenuScene(this.myCamera);
		  
		  Sprite sbg = new Sprite((GameControl.CAMERA_WIDTH - mMenuBgteTextureRegion.getWidth()) * 0.5f,
				  (GameControl.CAMERA_HEIGHT - mMenuBgteTextureRegion.getHeight()) * 0.5f , mMenuBgteTextureRegion);
		  mPopUpMenuScene.attachChild(sbg);
		  Text mGameOverTextx = null;
		  Log.e("�ؿ�", ""+GameControl.GAMEMODE);
		  if (GameControl.score >= GameControl.GameScore[GameControl.GAMEMODE]) {
			  mGameOverTextx = new Text(0, 0, mFont, "�ɹ�ͨ��!", HorizontalAlign.CENTER);
		  }
		  else if (GameControl.score < GameControl.GameScore[GameControl.GAMEMODE]) {
			  mGameOverTextx = new Text(0, 0, mFont, "ͨ��ʧ��!", HorizontalAlign.CENTER);
		  }
		  
		  mGameOverTextx.setPosition((GameControl.CAMERA_WIDTH - mGameOverTextx.getWidth()) * 0.5f, (GameControl.CAMERA_HEIGHT - mGameOverTextx.getHeight()) * 0.5f - 70 );
		  mGameOverTextx.setSize(300, 20);
		  mPopUpMenuScene.attachChild(mGameOverTextx);
		  
		  final Text mGameOverText = new Text(0, 0, mFont, "���ķ���\n" + GameControl.score, HorizontalAlign.CENTER);
		  mGameOverText.setPosition((GameControl.CAMERA_WIDTH - mGameOverText.getWidth()) * 0.5f, (GameControl.CAMERA_HEIGHT - mGameOverText.getHeight()) * 0.5f );
		  mGameOverText.setSize(300, 20);
		  mPopUpMenuScene.attachChild(mGameOverText);
		  
		  final Sprite restartSprite = new Sprite(mGameOverText.getX() - 55, mGameOverText.getY() + 100, mPlayAgainTextureRegion);
		  restartSprite.setSize(130, 50);
		  mPopUpMenuScene.attachChild(restartSprite);
		  
		  final Sprite menuSprite = new Sprite(restartSprite.getX() + restartSprite.getWidth() + 10, mGameOverText.getY() + 100, mBackToMenuTextureRegion);
		  menuSprite.setSize(130, 50);
		  mPopUpMenuScene.attachChild(menuSprite);
		  
		  mPopUpMenuScene.registerTouchArea(restartSprite);
		  mPopUpMenuScene.registerTouchArea(menuSprite);
		  mPopUpMenuScene.setOnAreaTouchListener(new IOnAreaTouchListener() {
		
		public boolean onAreaTouched(TouchEvent arg0, ITouchArea arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			if (arg1 == restartSprite) {
				Intent intent = new Intent();
				intent.setClass(GamePlay.this, GamePlay.class);			// ����������ǰ����
				startActivity(intent);
				finish();
			}
			else {
				finish();			// ������ǰ���棬������һ������
			}
			return false;
			}
	  });
	  this.mPopUpMenuScene.setMenuAnimator(new SlideMenuAnimator());
	  this.mPopUpMenuScene.buildAnimations();
	  this.mPopUpMenuScene.setBackgroundEnabled(false);
	  this.mPopUpMenuScene.setOnMenuItemClickListener(this);
	}

	
	protected void setListeners() {
		//Ϊ��̨�����ְ�ť����ͣ��ť���ü�����
		mMainScene.registerTouchArea(cannon);	
		mMainScene.registerTouchArea(soundon);
		mMainScene.registerTouchArea(pause);
		mMainScene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			
			public boolean onAreaTouched(TouchEvent arg0, ITouchArea arg1, float arg2,
					float arg3) {
				// TODO Auto-generated method stub
				if (arg1 == cannon) {					
					 //ת����̨			
					 if (arg0.getAction() == TouchEvent.ACTION_DOWN) {  
				            Cannon can = (Cannon) arg1;
				            can.switchit();
					 }
				}
				else if(arg1 == soundon){					
					 // ֹͣ��ʼ��������
					 if (arg0.getAction() == TouchEvent.ACTION_DOWN) {
						if(GameControl.musicNeeded == true){
							soundon.animate(new long[]{0, 100});
							GameControl.musicNeeded = false;
							GameControl.musiceffect = false;
							GameControl.bgMusic.pause();
							
						}
						else {
							soundon.animate(new long[]{100, 0});
							GameControl.musicNeeded = true;
							GameControl.musiceffect = true;
							GameControl.bgMusic.play();
						}
					 }
				}
				else if (arg1 == pause) {
					if(mMainScene.hasChildScene())
						/* Remove the menu and reset it. */
						mPauseScene.back();
					else 
						/* Attach the menu. */
						mMainScene.setChildScene(mPauseScene, false, true, true);
				}
			     return true; 
			}
		});	
	}
	
	protected void saveScore() {
	   	db.open();
		db.insertData(GameControl.playerName, (int) GameControl.score, GameControl.GAMEMODE);
		db.close();
		gameRunning = false;
	}
	
	protected void createCannon() {
		cannon = new Cannon(GameControl.CAMERA_WIDTH/2 - GameControl.CANNON_WIDTH/2, 
				GameControl.CAMERA_HEIGHT - GameControl.CANNON_HEIGHT, cannonTextureRegion);
		mMainScene.attachChild(cannon);
	}
		
	protected void createFish() 
	{
		// ��Ļ�� ����1��
		mMainScene.attachChild(new Entity());
		FishFactory.setEngine(mEngine);
		
		// �ٶ����ã��ٶȱ��ı��δ��ԭʱ�˳���Ϸ������ٶ��쳣
		GameControl.resetSpeed(); 
		
		// ע��ʱ���������0.05�뼶��
		mMainScene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
		{
			// ����һЩʱ��Ƭ
			private float timeRunning = 0.0f;
			private float timeSlaps1  =  0.0f;
			private float timeSlaps2 = 0.0f;
			private float timeSlaps3 = 0.0f;
			private float timeSlaps4 = 0.0f;
			
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				timeRunning += 1 / 20.0f;
				timeSlaps1  += 1 / 20.0f;
				timeSlaps2  += 1 / 20.0f;
				timeSlaps3  += 1 / 20.0f;
				timeSlaps4  += 1 / 20.0f;
				
				// ��3��󴴽���ʼ·��
				if (timeSlaps2 > 3.0f )		 	
				{
					FishFactory.getSingleInstance().createInitialPath(mMainScene, GameControl.movingFish, FishRegion, GameControl.GAMEMODE);
					timeSlaps2 = -100000.0f;
				}
				
				// ÿ��5����ֵ����㣺���ڵ���
				if(timeSlaps3 > 5.0f)
				{
					FishFactory.getSingleInstance().createSingleFish(mMainScene, GameControl.movingFish, FishRegion, 8);
					timeSlaps3 = 0.0f;
				}
				
				// ����Ӧʱ�����ʽ��ʼ��Ϸ
				if (timeRunning > GameControl.pathSlapsTime[GameControl.GAMEMODE]) 
				{
					// ���������
					FishFactory.getSingleInstance().createRandomFish(mMainScene, GameControl.movingFish, FishRegion);
					
					if (timeSlaps1 > 10.0f) 
					{ 
						// ÿ��10�봴��һ����Ⱥ�������㡢�����㣨��ʱ�䣡��
						FishFactory.getSingleInstance().createFishGroup(mMainScene, GameControl.movingFish, FishRegion);
						FishFactory.getSingleInstance().createSingleFish(mMainScene, GameControl.movingFish, FishRegion, 7);
						FishFactory.getSingleInstance().createSingleFish(mMainScene, GameControl.movingFish, FishRegion, 9);
						timeSlaps1 = 0.0f;
					}
					
					// ÿ��60����٣�20��
					if(timeSlaps4 > 60.0f)
					{
						GameControl.speedUpgrade();
						timeSlaps4 = -20.0f;
					}
					// ����2����������ٶȲ����ɲʺ�
					if(timeSlaps4 > -1.0f && timeSlaps4 < 0.0f)
					{
						GameControl.resetSpeed();
						FishFactory.getSingleInstance().createRainbowPath(mMainScene, GameControl.movingFish, FishRegion);
						timeSlaps4 = 0.0f;
					}

					// ��������ʵʱ���³����е��㣨��������������㣩
					if (GameControl.movingFish.size() > 0)
						FishMonitor.getSingleInstance().onFishMove(GameControl.movingFish);
					
				}
			}
		 }));
	}
	
	protected void createPauseButton() {
		pause = new Sprite(GameControl.CAMERA_WIDTH - 100, GameControl.CAMERA_HEIGHT - 100, this.mPauseTextureRegion);
		mMainScene.attachChild(pause);
	}	
	
	protected void update() {
		mMainScene.registerUpdateHandler(new IUpdateHandler() {
			
			public void reset() {
			}
			
			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				if(gameRunning)
					if (GameControl.bullets != null) 
					{
						int size = GameControl.bullets.size();
						
						//������ǰ�����е������ӵ�������Ƿ�������
						for (int i = 0; i < size; i++) 
						{	
							for (int j = 0; j < GameControl.movingFish.size(); j++) {
								if (GameControl.movingFish.get(j).collidesWith(GameControl.bullets.get(i))) 
								{	// ����ӵ������㣬����������ͬʱ����Ƿ�ץ����
									final Net net1 = GameControl.bullets.get(i).generateNet(NetRegion.get(cannon.getType() - 1), mMainScene);

									for (int k = 0; k < GameControl.movingFish.size(); k++) 
									{
										Fish fishx = GameControl.movingFish.get(k);
										if(net1.collidesWith(fishx))
										{
											int t = fishx.getType();
											if (Math.random()*10 < GameControl.catchProbabiity * GameControl.catchParas[t] * GameControl.cannonPobability[cannon.getType() - 1]) 
												catchFish(GameControl.movingFish.get(k), k);
										}
									}
									// ����ײ���ӵ����
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
		runOnUpdateThread(new Runnable() {
			public void run() {
				/* Now it is save to remove the entity! */
				GameControl.movingFish.get(m).detachSelf();	
				if (GameControl.movingFish.size() > m) {
					//GameControl.score += GameControl.movingFish.get(m).getScore();	// ��¼��Ӧ�ķ���
					mMainScene.detachChild(GameControl.movingFish.get(m));
					GameControl.movingFish.get(m).detachSelf();	
					GameControl.movingFish.remove(m);
				}
			}
		});	
		
		// ������������
		FishFactory.getSingleInstance().createCapturedFish(fishx, mMainScene, FishRegion);
		// Ϊ��ͨ�����ɷ���
		CatchFish.createScore(fishx, mMainScene, ScoreRegion);		
		// �����㣺���/���߶���
		CatchFish.createGold(fishx, mMainScene, goldRegion, this);		
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
		if(gameRunning){
			//��ת��̨
			cannon.rotate(arg1.getX(), arg1.getY() );
			if (GameControl.A_TOTAL >= cannon.getType()) {
				//�����ӵ�		
				cannon.generateBullet(BulletRegion.get(cannon.getType() - 1), arg1.getX(), arg1.getY(), this, mMainScene);
				// �ķ��ӵ�����������̨�������Ӧ
				GameControl.A_TOTAL -= cannon.getType();
			}
			else if (GameControl.A_TOTAL > 0) {
				
				cannon.setType(GameControl.A_TOTAL);
				
				//�����ӵ�		
				cannon.generateBullet(BulletRegion.get(cannon.getType() - 1), arg1.getX(), arg1.getY(), this, mMainScene);
				// �ķ��ӵ�����������̨�������Ӧ
				GameControl.A_TOTAL -= cannon.getType();
			}
		}
		return false;
	}
	
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		case 0:
			this.mPauseScene.back();
			return true;
		case 1:
			this.finish();
			return true;
		default:
			return false;
		}
	}
}
