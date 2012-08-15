package com.fishjoy.factory;

import java.util.ArrayList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.fishjoy.game.GameControl;
import com.fishjoy.game.GamePlay;


public class TextRegionFactory
{
	// 单实例对象
	private static TextRegionFactory singleInstance = null;
	
	// 获取工厂单例对象
	public static TextRegionFactory getSingleInstance(){
		if(singleInstance == null)
			singleInstance = new TextRegionFactory();
		return singleInstance;
	}
	
	//创建背景纹理
	public TextureRegion CreateRegionForMenuBackground(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backTexture, game, "box.png", 0, 0);
		mEngine.getTextureManager().loadTexture(backTexture);
		return back;
	}

	//创建背景纹理
	public TextureRegion CreateRegionForMenuBackToMenu(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backTexture, game, "backtomenubutton.png", 0, 0);
		mEngine.getTextureManager().loadTexture(backTexture);
		return back;
	}
	
	//创建背景纹理
	public TextureRegion CreateRegionForMenuPlayAgain(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backTexture, game, "playagainbutton.png", 0, 0);
		mEngine.getTextureManager().loadTexture(backTexture);
		return back;
	}
	
	//创建背景纹理
	public TextureRegion CreateRegionForBackgroundEasy(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backTexture, game, "bg_easy.png", 0, 0);
		mEngine.getTextureManager().loadTexture(backTexture);
		return back;
	}
	
	//创建背景纹理
	public TextureRegion CreateRegionForBackgroundNormal(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backTexture, game, "bg_normal.png", 0, 0);
		mEngine.getTextureManager().loadTexture(backTexture);
		return back;
	}
	
	//创建背景纹理
	public TextureRegion CreateRegionForBackgroundHard(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backTexture, game, "bg_hard.png", 0, 0);
		mEngine.getTextureManager().loadTexture(backTexture);
		return back;
	}
	
	// 为5种鱼和对应的挣扎的鱼创建TiledTextureRegion
	public void CreateRegionForFish(ArrayList<TiledTextureRegion> FishRegion, Engine mEngine, 
			GamePlay game)
	{
		BitmapTextureAtlas texture;
		TiledTextureRegion  tiledRegion;

		for(int i = 0; i < (GameControl.NormalFishNum + GameControl.MagicFishNum) * 2; i++)	// FishKindsNum==5
		{
			texture =new BitmapTextureAtlas(GameControl.fishText[i][0], GameControl.fishText[i][1], 
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
			String path = "fish_" + i + ".png";
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, GameControl.fishFrame[i][0], GameControl.fishFrame[i][1]);
			FishRegion.add(tiledRegion);		// 放进列表
			
			mEngine.getTextureManager().loadTexture(texture);
		}
	}
	
	public void createRegionForScore(ArrayList<TiledTextureRegion> scoreRegion, Engine mEngine, GamePlay game) {
		BitmapTextureAtlas texture;
		TiledTextureRegion tiledRegion;

		for(int i = 0; i < GameControl.NormalFishNum; i++)
		{
			texture =new BitmapTextureAtlas(128, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
			String path = i + ".png";
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, 1, 6);
			scoreRegion.add(tiledRegion);		// 放进列表
			
			mEngine.getTextureManager().loadTexture(texture);
		}	
	}
	
	public void createRegionForGold(ArrayList<TiledTextureRegion> goldRegion, Engine mEngine, GamePlay game) {
		BitmapTextureAtlas texture;
		TiledTextureRegion tiledRegion;

		for(int i = 0; i < 1+GameControl.MagicFishNum; i++)
		{
			texture =new BitmapTextureAtlas(512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
			String path = "gold_"+ i + ".png";
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, 6, 1);
			goldRegion.add(tiledRegion);		// 放进列表
			
			mEngine.getTextureManager().loadTexture(texture);
		}	
	}
	
	public TiledTextureRegion CreateRegionForCannon(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas cannonTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TiledTextureRegion cannon = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				cannonTexture, game, "cannon.png", 0, 0, 5, 1);
		mEngine.getTextureManager().loadTexture(cannonTexture);
		return cannon;
	}
	
	public void CreateRegionForNet(ArrayList<TiledTextureRegion> NetRegion, Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas texture;
		TiledTextureRegion  tiledRegion;

		for(int i = 1; i <= GameControl.NET_KIND_NUM; i++)	// FishKindsNum==5
		{
			texture =new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
			String path = "net" + i + ".png";
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, 1, 5);
			NetRegion.add(tiledRegion);		// 放进列表
			
			mEngine.getTextureManager().loadTexture(texture);
		}
	}
	
	public void CreateRegionForBullet(ArrayList<TiledTextureRegion> BulletRegion, Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas texture;
		TiledTextureRegion  tiledRegion;

		for(int i = 1; i <= GameControl.A_KIND_NUM; i++)	// FishKindsNum==5
		{
			texture =new BitmapTextureAtlas(128, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
			String path = "A" + i + ".png";
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, 1, 1);
			BulletRegion.add(tiledRegion);		// 放进列表
			
			mEngine.getTextureManager().loadTexture(texture);
		}
	}
	
	public TextureRegion CreateRegionForReturn(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas returnTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				returnTexture, game, "menu_continue.png", 0, 0);
		mEngine.getTextureManager().loadTexture(returnTexture);
		return r;
	}
	
	public TextureRegion CreateRegionForReset(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas resetTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion reset = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				resetTexture, game, "menu_start.png", 0, 50);
		mEngine.getTextureManager().loadTexture(resetTexture);
		return reset;
	}
	
	public TextureRegion CreateRegionForQuit(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas quitTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion bullet =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				quitTexture, game, "menu_exit.png", 0, 100);
		mEngine.getTextureManager().loadTexture(quitTexture);
		return bullet;
	}
	
	public TiledTextureRegion createRegionForSound(Engine mEngine, GamePlay game) {
		BitmapTextureAtlas soundTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TiledTextureRegion sound = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				soundTexture, game, "sound.png", 0, 0, 1, 2);
		mEngine.getTextureManager().loadTexture(soundTexture);
		return sound;
	}
	
	public TextureRegion CreateRegionForPause(Engine mEngine, GamePlay game)
	{
		BitmapTextureAtlas pauseTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TextureRegion pause =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				pauseTexture, game, "pause.png", 0, 100);
		mEngine.getTextureManager().loadTexture(pauseTexture);
		return pause;
	}
	
	public TiledTextureRegion createRegionForTimeNum(Engine mEngine, GamePlay game) {
		BitmapTextureAtlas numTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TiledTextureRegion num = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				numTexture, game, "num_time.png", 0, 0, 5, 2);
		mEngine.getTextureManager().loadTexture(numTexture);
		return num;	
	}
	
	public TiledTextureRegion createRegionForScoreNum(Engine mEngine, GamePlay game) {
		BitmapTextureAtlas numTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		TiledTextureRegion num = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				numTexture, game, "num_score.png", 0, 0, 10, 1);
		mEngine.getTextureManager().loadTexture(numTexture);
		return num;	
	}
	
	public TextureRegion createRegionForST(Engine mEngine, GamePlay game) {
		BitmapTextureAtlas STTexture = new BitmapTextureAtlas(512, 512, TextureOptions.DEFAULT);
		TextureRegion num = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				STTexture, game, "timeandscore.png", 0, 0);
		mEngine.getTextureManager().loadTexture(STTexture);
		return num;	
	}
}