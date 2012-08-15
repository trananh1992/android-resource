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
	// 0:��ʼ
	// 1����ͣ
	// 2������
	private final int GAMESTART = 0;
	private final int GAMEPAUSE = 1;
	private final int GAMEOVER = 2;
	// ��Ϸ״̬��¼
	private int GAMESTATE = 0;
	// �������
	private String playerName;
	// ֽ�Ƹ���
	private int cardSets;
	// �����Ŀ
	private int playerNum;
	// ��Ϸ���ƶԻ���ID
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

	// ������Ϸ���ƶԻ���
	private Dialog CreateGameAlterDialog() {
		// �趨����
		// �趨��ť�¼�
		return new AlertDialog.Builder(this).setTitle(
				this.getResources().getString(R.string.game_control))
				.setNegativeButton(R.string.back,
						new DialogInterface.OnClickListener() {
							// ���AlertDialog�ϵİ�ť���¼��������
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// �����ý���
								FishActivity.this.finish();
							}
						}).setPositiveButton(R.string.replay,
						new DialogInterface.OnClickListener() {
							// ���AlertDialog�ϵİ�ť���¼��������
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// ��ʼ����Ϸ������������Ƶ����
								GameInitor gameInitor = new GameInitor();
								GameView gv = (GameView) FishActivity.this
										.findViewById(R.id.gameView1);
								gv.setPlayers(gameInitor.InitorPlayer(
										playerNum, cardSets));
								// �趨���������Ϣ
								Player player = gv.getPlayers().get(0);
								player.setName(playerName);
								player.setComputer(false);
								// ��������ϵ�ֽ��
								gv.getCards().clear();
								// �趨��Ϸ���¿�ʼ
								GAMESTATE = GAMESTART;
								gv.invalidate();
							}
						}).create();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��ȡ��Ļ��С
		GameUtil.FitScreen(this);

		GameView gv = (GameView) this.findViewById(R.id.gameView1);
		// �鿴�Ƿ��Ǽ�������Ϸ
		if (savedInstanceState == null) {
			// ��ʼ����Ϸ������������Ƶ����
			GameInitor gameInitor = new GameInitor();
			// ��ȡ��Ϸ��������
			Intent intent = this.getIntent();
			playerName = intent.getStringExtra("player_name");
			cardSets = intent.getIntExtra("cardsSet", 1);
			playerNum = intent.getIntExtra("playerNum", 2);
			// ��ʼ���������
			gv.setPlayers(gameInitor.InitorPlayer(playerNum, cardSets));
			// �趨���������Ϣ
			Player player = gv.getPlayers().get(0);
			player.setName(playerName);
			player.setComputer(false);
		} else {
			// �ָ��洢������
			ArrayList<Player> players = null;
			ArrayList<Card> cards = null;
			GameUtil.RestoreData(savedInstanceState, players, cards);
			gv.setPlayers(players);
			gv.setCards(cards);
		}

		// �趨��Ӧ�Ŀ��ư�ť
		Button randomButton = (Button) this.findViewById(R.id.button1);

		randomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʾϴ��״̬
				ShowInfo(FishActivity.this.getResources().getString(
						R.string.randCard));
				GameView gv = (GameView) FishActivity.this
						.findViewById(R.id.gameView1);
				// �����漴����ֽ��
				if (GAMESTATE != GAMEOVER) {
					GameUtil.RandCards(gv.getPlayers().get(0).getCards());
				}
			}
		});

		Button pushButton = (Button) this.findViewById(R.id.button2);

		pushButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʾ����״̬
				ShowInfo(FishActivity.this.getResources().getString(
						R.string.pushCard));
				GameView gv = (GameView) FishActivity.this
						.findViewById(R.id.gameView1);
				// ��ҽ���
				boolean playerOver = false;
				// �����Ƿ����
				boolean computerOver = false;
				// ��Ϸ�����ж�
				// ���û��
				if (gv.getPlayers().get(0).getCards().size() == 0) {
					playerOver = true;
				}
				// ����û��
				for (int i = 1; i < gv.getPlayers().size(); i++) {
					// �����һ�����û��ֽ��
					if (gv.getPlayers().get(i).getCards().size() == 0) {
						computerOver = true;
					} else {
						computerOver = false;
						break;
					}
				}
				// �ж��Ƿ����
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
		// ��ʾ��Ϸ��ʼ״̬
		ShowInfo("��Ϸ��ʼ");
		GAMESTATE = GAMESTART;
	}

	public void ShowInfo(String content) {
		// ������ʾ������Ϣ���ַ�����
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
		// ������Ϸ����
		GameUtil.SaveData(outState, ((GameView) this
				.findViewById(R.id.gameView1)).getPlayers(), ((GameView) this
				.findViewById(R.id.gameView1)).getCards());
	}

}