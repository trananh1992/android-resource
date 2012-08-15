package com.fishjoy.game;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Musicplay {
	
	public static AnimatedSprite createBgMusic(TiledTextureRegion mSoundTextureRegion, Scene scene){	
		AnimatedSprite soundon = new AnimatedSprite(GameControl.CAMERA_WIDTH - GameControl.SOUND_WIDTH - 30, 
				5, mSoundTextureRegion);
		soundon.setSize(70, 50);
		scene.attachChild(soundon);
		
		if(GameControl.musicNeeded == true)
			soundon.animate(new long[]{100, 0});
		else {
			soundon.animate(new long[]{0, 100});
		}
		
		if(GameControl.musicNeeded == true) {
			GameControl.bgMusic.play();
		} 
		else {
			GameControl.bgMusic.play();
			GameControl.bgMusic.pause();
		}
		return soundon;
	}
}
