package com.fishjoy.controller;

import java.util.ArrayList;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.fishjoy.activity.GameActivity;
import com.fishjoy.model.GameParas;


public class TextRegionFactory implements GameParas {

private static TextRegionFactory singleInstance = null;

	// ��ȡ������������
	public static TextRegionFactory getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new TextRegionFactory();
		return singleInstance;
	}
	
	// Ϊ5���㴴��TiledTextureRegion
	public void CreateRegionForFish(ArrayList<TiledTextureRegion> FishRegion, Engine mEngine, 
			GameActivity game)
	{
		BitmapTextureAtlas texture =new BitmapTextureAtlas(128, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		TiledTextureRegion  tiledRegion;

		for(int i = 1; i <= FishKindsNum; i++)	// FishKindsNum==5
		{
			String path = "fish_" + String.valueOf(i) + ".png";
			int column = 1;
			int row = 5;
			tiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
					texture, game, path, 0, 0, column, row);
			FishRegion.add(tiledRegion);		// �Ž��б�
		}
		mEngine.getTextureManager().loadTexture(texture);
	}
	
}







