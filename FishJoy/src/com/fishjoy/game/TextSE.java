package com.fishjoy.game;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.HorizontalAlign;

public class TextSE {
	
	public static void createTexts(final Font font, final Scene scene, final GamePlay gamePlay) {
		/* The title-text. */
		final Text titleText = new Text(0, 0, font, "ÓÎÏ·¿ªÊ¼!", HorizontalAlign.CENTER);
		titleText.setPosition((GameControl.CAMERA_WIDTH - titleText.getWidth()) * 0.5f, 
				(GameControl.CAMERA_HEIGHT - titleText.getHeight()) * 0.5f);
		titleText.setScale(0.0f);
		titleText.registerEntityModifier(new ScaleModifier(2, 0.0f, 1.0f));
		scene.attachChild(titleText);

		/* The handler that removes the title-text and starts the game. */
		scene.registerUpdateHandler(new TimerHandler(3.0f, new ITimerCallback() {
			
			public void onTimePassed(final TimerHandler pTimerHandler) {
				scene.unregisterUpdateHandler(pTimerHandler);
				scene.detachChild(titleText);
			}
		}));

		scene.registerUpdateHandler(new TimerHandler(200.0f, new ITimerCallback() {
			
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				/* The game-over text. */
				final Text mGameOverText = new Text(0, 0, font, "Your score\n" + GameControl.score, HorizontalAlign.CENTER);
				mGameOverText.setPosition((GameControl.CAMERA_WIDTH - mGameOverText.getWidth()) * 0.5f, (GameControl.CAMERA_HEIGHT - mGameOverText.getHeight()) * 0.5f);
				mGameOverText.registerEntityModifier(new ScaleModifier(3, 0.1f, 2.0f));
				mGameOverText.registerEntityModifier(new RotationModifier(3, 0, 720));
				scene.unregisterUpdateHandler(pTimerHandler);
				scene.attachChild(mGameOverText);
			}
		}));
		
		scene.registerUpdateHandler(new TimerHandler(205.0f, new ITimerCallback() {
			
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				scene.unregisterUpdateHandler(pTimerHandler);
				gamePlay.finish();
			}
		}));
	}
}
