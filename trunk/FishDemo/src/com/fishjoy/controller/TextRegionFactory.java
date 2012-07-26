package com.fishjoy.controller;

import java.util.ArrayList;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.fishjoy.activity.GameActivity;
import com.fishjoy.model.GameParas;


public class TextRegionFactory implements GameParas 
{
	// 单实例对象
	private static TextRegionFactory singleInstance = null;

	
	// 获取工厂单例对象
	public static TextRegionFactory getSingleInstance(){
		if(singleInstance == null)
			singleInstance = new TextRegionFactory();
		return singleInstance;
	}
	
	
	public TextureRegion CreateRegionForBackground(Engine mEngine,GameActivity game)
	{
		BitmapTextureAtlas backTexture = new BitmapTextureAtlas(1024, 512, 
				TextureOptions.DEFAULT);
		TextureRegion back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backTexture, 
				game, "gfx/background_easy.png", 0, 0);
		
		mEngine.getTextureManager().loadTextures(backTexture);
		return back;
	}
	

	// 为5种鱼创建TiledTextureRegion
	public void CreateRegionForFish(ArrayList<TiledTextureRegion> FishRegion, Engine mEngine, 
			GameActivity game)
	{
		BitmapTextureAtlas texture;
		TiledTextureRegion  tiledRegion;

		for(int i = 1; i <= FishKindsNum; i++)	// FishKindsNum==5
		{
			texture =new BitmapTextureAtlas(128, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
			String path = "gfx/fish_" + String.valueOf(i) + ".png";
			int column = 1;
			int row = 5;
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, column, row);
			FishRegion.add(tiledRegion);		// 放进列表
			
			mEngine.getTextureManager().loadTexture(texture);
		}
	}
	
}







