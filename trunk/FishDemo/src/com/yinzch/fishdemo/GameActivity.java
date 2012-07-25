package com.yinzch.fishdemo;

import java.util.ArrayList;
import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;

import android.util.Log;

import com.yinzch.data.GameParas;
import com.yinzch.data.Matrix;

public class GameActivity extends BaseGameActivity implements GameParas
{
	private Engine mEngine;
	private Camera mCamera;
	private Scene mScene;
	
	private TextureRegion mBackgroundTextureRegion;
	
	HashMap<Fish_Name, TiledTextureRegion> allMovingFishTextureRegionMap = 
			new HashMap<Fish_Name, TiledTextureRegion>();
	private ArrayList<Fish> movingFishList = new ArrayList<Fish>();
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
		return mEngine;
	}

	@Override
	public void onLoadResources() 
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		BitmapTextureAtlas mBackgroundTexture = new BitmapTextureAtlas(1024, 512, 
				TextureOptions.DEFAULT);
		mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBackgroundTexture, this, "background_easy.png", 0, 0);
		
		BitmapTextureAtlas SARDINETexture =new BitmapTextureAtlas(64, 256, 
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion  SARDINETiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				SARDINETexture, this, "sardine_1.png", 0, 0, 1, 5);			// 1列5行
		// 放进容器
		allMovingFishTextureRegionMap.put(Fish_Name.SARDINE, SARDINETiledTextureRegion);
		
		this.mEngine.getTextureManager().loadTextures(mBackgroundTexture, SARDINETexture);
	}

	@Override
	public Scene onLoadScene() {
		mScene = new Scene();
		mScene.attachChild(new Entity());
		Log.i("层数", String.valueOf(mScene.getChildCount()));
		// 将背景精灵附加到背景层
		mScene.setBackgroundEnabled(false);
		mScene.getFirstChild().attachChild(new Sprite(0, 0, mBackgroundTextureRegion));
		
		mScene.attachChild(new Entity());			// 鱼层
		String str = "EASY";
		int distance = create_Char_Move(str.charAt(0), 0)*38+38;	
		
		// TiledTextureRegion.clone()
		/*Fish fish1 = new Fish(Fish_Name.SARDINE, allMovingFishTextureRegionMap.get(Fish_Name.SARDINE));
		fish1.animate(100);
		mScene.attachChild(new Entity());
		mScene.getChild(1).attachChild(fish1);
		Log.i("层数", String.valueOf(mScene.getChildCount()));*/
		
		return mScene;
	}

	int create_Char_Move(char ch, int i)
	{
		//Move_Direction direction = Move_Direction.RIGHT;		// 移动方向：RANDOM,LEFT,RIGHT		
		Matrix matrix = new Matrix();
		int[][] c = matrix.get_Matrix(String.valueOf(ch));
	
		int column=c.length;
		int row=c[0].length;
		Log.i("行数", String.valueOf(row));			// row == 3
		
		for(int t = 0; t < column; t++){
			for(int j=0; j < row; j++)
			{
				if(c[t][j]!=0)
				{
					// 创建动画精灵
					Fish fish = new Fish(Fish_Name.SARDINE, allMovingFishTextureRegionMap.get(Fish_Name.SARDINE).clone());
					// 创建控制器，调整鱼的方位
					//FishOperation fishOperation=new FishOperation();
					// 互相绑定，一对一
					//fishOperation.set_Controller(fish);
					//fish.set_Fish_Operation(fishOperation);
					fish.set_side(Move_Direction.RIGHT);				// 设置鱼向右游动
					fish.set_fixed_Y(t*33+60);							// 设置鱼的位置坐标
					fish.set_fixed_X(CAMERA_WIDTH+j*38+i);
					// Move_Direction.RIGHT == 0
					fish.set_Direct_Move(0);
					
					fish.animate(100);
					fish.setSize(36, 18);			// 鱼的实际大小

					mScene.getChild(1).attachChild(fish);
					movingFishList.add(fish);
				}
			}
		}	
		return row;
	}
	
	@Override
	public void onLoadComplete() {
		
	}
}

