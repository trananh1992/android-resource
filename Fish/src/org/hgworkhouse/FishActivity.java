package org.hgworkhouse;

import java.util.ArrayList;

import org.hgworkhouse.model.Card;
import org.hgworkhouse.model.GameInitor;
import org.hgworkhouse.model.GameUtil;
import org.hgworkhouse.model.Player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FishActivity extends Activity {
	/** Called when the activity is first created. */
	// 0:开始
	// 1：暂停
	// 2：结束
	private final int GAMESTART = 0;
	private final int GAMEPAUSE = 1;
	private final int GAMEOVER = 2;
	// 游戏状态记录
	private int GAMESTATE = 0;
	// 玩家姓名
	private String playerName;
	// 纸牌副数
	private int cardSets;
	// 玩家数目
	private int playerNum;
	// 游戏控制对话框ID
	private final int GAMEALTER_ID = 1;

	@Override
	protected Dialog onCreateDialog(int id) {

		Dialog dialog = null;
		switch (id) {
		case GAMEALTER_ID:
			dialog = CreateGameAlterDialog();
			break;
		default:
			dialog = super.onCreateDialog(id);
			break;
		}

		return dialog;
	}

	// 创建游戏控制对话框
	private Dialog CreateGameAlterDialog() {
		// 设定标题
		// 设定按钮事件
		return new AlertDialog.Builder(this).setTitle(
				this.getResources().getString(R.string.game_control))
				.setNegativeButton(R.string.back,
						new DialogInterface.OnClickListener() {
							// 点击AlertDialog上的按钮的事件处理代码
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 结束该界面
								FishActivity.this.finish();
							}
						}).setPositiveButton(R.string.replay,
						new DialogInterface.OnClickListener() {
							// 点击AlertDialog上的按钮的事件处理代码
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 初始化游戏数据玩家数和牌的情况
								GameInitor gameInitor = new GameInitor();
								GameView gv = (GameView) FishActivity.this
										.findViewById(R.id.gameView1);
								gv.setPlayers(gameInitor.InitorPlayer(
										playerNum, cardSets));
								// 设定玩家数据信息
								Player player = gv.getPlayers().get(0);
								player.setName(playerName);
								player.setComputer(false);
								// 清空桌面上的纸牌
								gv.getCards().clear();
								// 设定游戏重新开始
								GAMESTATE = GAMESTART;
								gv.invalidate();
							}
						}).create();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取屏幕大小
		GameUtil.FitScreen(this);

		GameView gv = (GameView) this.findViewById(R.id.gameView1);
		// 查看是否是继续的游戏
		if (savedInstanceState == null) {
			// 初始化游戏数据玩家数和牌的情况
			GameInitor gameInitor = new GameInitor();
			// 获取游戏设置数据
			Intent intent = this.getIntent();
			playerName = intent.getStringExtra("player_name");
			cardSets = intent.getIntExtra("cardsSet", 1);
			playerNum = intent.getIntExtra("playerNum", 2);
			// 初始化玩家数据
			gv.setPlayers(gameInitor.InitorPlayer(playerNum, cardSets));
			// 设定玩家数据信息
			Player player = gv.getPlayers().get(0);
			player.setName(playerName);
			player.setComputer(false);
		} else {
			// 恢复存储的数据
			ArrayList<Player> players = null;
			ArrayList<Card> cards = null;
			GameUtil.RestoreData(savedInstanceState, players, cards);
			gv.setPlayers(players);
			gv.setCards(cards);
		}

		// 设定相应的控制按钮
		Button randomButton = (Button) this.findViewById(R.id.button1);

		randomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 显示洗牌状态
				ShowInfo(FishActivity.this.getResources().getString(
						R.string.randCard));
				GameView gv = (GameView) FishActivity.this
						.findViewById(R.id.gameView1);
				// 重新随即排序纸牌
				if (GAMESTATE != GAMEOVER) {
					GameUtil.RandCards(gv.getPlayers().get(0).getCards());
				}
			}
		});

		Button pushButton = (Button) this.findViewById(R.id.button2);

		pushButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 显示出牌状态
				ShowInfo(FishActivity.this.getResources().getString(
						R.string.pushCard));
				GameView gv = (GameView) FishActivity.this
						.findViewById(R.id.gameView1);
				// 玩家结束
				boolean playerOver = false;
				// 电脑是否结束
				boolean computerOver = false;
				// 游戏结束判断
				// 玩家没牌
				if (gv.getPlayers().get(0).getCards().size() == 0) {
					playerOver = true;
				}
				// 电脑没牌
				for (int i = 1; i < gv.getPlayers().size(); i++) {
					// 如果有一个玩家没有纸牌
					if (gv.getPlayers().get(i).getCards().size() == 0) {
						computerOver = true;
					} else {
						computerOver = false;
						break;
					}
				}
				// 判断是否结束
				if (computerOver || playerOver) {
					GAMESTATE = GAMEOVER;
					FishActivity.this.showDialog(GAMEALTER_ID);
				}

				if (GAMESTATE != GAMEOVER) {
					gv.setFishActivity(FishActivity.this);
					gv.PushCardInTurn();
				}
			}
		});
		// 显示游戏开始状态
		ShowInfo("游戏开始");
		GAMESTATE = GAMESTART;
	}

	public void ShowInfo(String content) {
		// 用来显示数据信息的字符界面
		TextView tv = (TextView) FishActivity.this.findViewById(R.id.textView1);
		tv.setText(content);
	}

	@Override
	protected void onPause() {
		super.onPause();
		GAMESTATE = GAMEPAUSE;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// just have the View's thread save its state into our Bundle
		super.onSaveInstanceState(outState);
		// 保存游戏数据
		GameUtil.SaveData(outState, ((GameView) this
				.findViewById(R.id.gameView1)).getPlayers(), ((GameView) this
				.findViewById(R.id.gameView1)).getCards());
	}

}