package org.hgworkhouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class StartActivity extends Activity {
	// �������
	private String player_name = "linfeng";
	// ֽ�Ƹ���
	private int cardsSet = 1;
	// �����Ŀ
	private int playerNum = 2;
	// �Ի���ID
	private final int GAMESET_ID = 1;
	private final int GAMEDES_ID = 2;
	private final int GAMEABOUT_ID = 3;
	// ��Ϸ�趨�Ի����еĿؼ�
	private EditText editText1;
	private EditText editText2;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;

	private void GetGameDataInPreferences() {
		SharedPreferences sp = getSharedPreferences("Setting_fishCard",
				MODE_PRIVATE);
		/*
		 * �������������Ҫ�ڳ����������ʱ����������ȡ֮ǰ������, ��Ȼ���ǻ�û�б����κ��������Կ϶��Ҳ�������
		 * ����Ҳ���Ҳû��ϵ��Ĭ�Ϸ���һ������ֵ��������ķ��������֪��
		 */
		player_name = sp.getString("player_name", player_name);
		cardsSet = sp.getInt("cardsSet", 1);
		playerNum = sp.getInt("playerNum", 2);
	}

	private void SaveGameDataInPreferences() {
		SharedPreferences sp = getSharedPreferences("Setting_fishCard",
				MODE_PRIVATE);
		sp.edit().putString("player_name", player_name).putInt("cardsSet",
				cardsSet).putInt("playerNum", playerNum).commit();
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case GAMESET_ID:
			// ��Ϸ�趨�����еĿؼ��趨ֵ �Լ� ��ȡ
			editText1 = ((EditText) dialog.findViewById(R.id.editText1));
			editText1.setText(player_name);
			editText2 = ((EditText) dialog.findViewById(R.id.editText2));
			editText2.setText(String.valueOf(cardsSet));

			radioButton1 = (RadioButton) dialog
					.findViewById(R.id.myRadioButton1);
			radioButton2 = (RadioButton) dialog
					.findViewById(R.id.myRadioButton2);
			radioButton3 = (RadioButton) dialog
					.findViewById(R.id.myRadioButton3);

			int radioId = R.id.myRadioButton1;
			switch (playerNum) {
			case 2:
				radioId = R.id.myRadioButton1;
				break;
			case 4:
				radioId = R.id.myRadioButton2;
				break;
			case 8:
				radioId = R.id.myRadioButton3;
				break;
			}
			RadioButton radioButton = (RadioButton) dialog
					.findViewById(radioId);
			radioButton.setChecked(true);

			break;
		case GAMEDES_ID:
			break;
		case GAMEABOUT_ID:
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case GAMESET_ID:
			dialog = CreateGameSetDialog();
			break;
		case GAMEDES_ID:
			dialog = CreateGameDesDialog();
			break;
		case GAMEABOUT_ID:
			dialog = CreateGameAboutDialog();
			break;
		default:
			dialog = super.onCreateDialog(id);
			break;
		}

		return dialog;
	}

	// ������Ϸ���� �Ի���
	private Dialog CreateGameAboutDialog() {
		return new AlertDialog.Builder(StartActivity.this).setTitle(
				StartActivity.this.getResources()
						.getString(R.string.game_about)).setItems(
				new String[] { StartActivity.this.getResources().getString(
						R.string.game_about_content) },
				new DialogInterface.OnClickListener() {
					// ���AlertDialog�ϵİ�ť���¼��������
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();						
						intent.setAction("android.intent.action.VIEW");						 
						Uri content_uri_browsers = Uri.parse("http://hgworkhouse.org");						 
						intent.setData(content_uri_browsers);						 
						intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");						
						startActivity(intent);
					}
				}).setNeutralButton(R.string.close,
				new DialogInterface.OnClickListener() {
					// ���AlertDialog�ϵİ�ť���¼��������
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
	}

	// ������Ϸ���� �Ի���
	private Dialog CreateGameDesDialog() {
		return new AlertDialog.Builder(StartActivity.this).setTitle(
				StartActivity.this.getResources().getString(R.string.game_des))
				.setMessage(
						StartActivity.this.getResources().getString(
								R.string.game_des_content)).setNeutralButton(
						R.string.close, new DialogInterface.OnClickListener() {
							// ���AlertDialog�ϵİ�ť���¼��������
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).create();
	}

	// �趨��ѡ��ť�ļ����¼� ������ȡ�����
	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == R.id.myRadioButton1) {
				/* ��mRadio1�����ݴ���mTextView1 */
				playerNum = 2;
			} else if (checkedId == R.id.myRadioButton2) {
				/* ��mRadio2�����ݴ���mTextView1 */
				playerNum = 4;
			} else if (checkedId == R.id.myRadioButton3) {
				/* ��mRadio2�����ݴ���mTextView1 */
				playerNum = 8;
			}
		}
	};

	// ������Ϸ�趨�Ի���
	private Dialog CreateGameSetDialog() {
		// ��ȡ�Ի���Ĳ���
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.game_set,
				(ViewGroup) findViewById(R.id.linearLayout1));

		RadioGroup mRadioGroup1 = (RadioGroup) dialoglayout
				.findViewById(R.id.myRadioGroup);

		/* RadioGroup��OnCheckedChangeListener������ */
		mRadioGroup1.setOnCheckedChangeListener(mChangeRadio);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialoglayout);
		builder.setTitle(getResources().getString(R.string.game_set));
		// �趨���ð�ť�¼�
		Button resetButton = (Button) dialoglayout.findViewById(R.id.button7);
		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����趨��Һ�ֽ�Ƹ��� ��Ϣ
				editText1.setText(player_name);
				editText2.setText(String.valueOf(cardsSet));

				switch (playerNum) {
				case 2:
					radioButton1.setChecked(true);
					break;
				case 4:
					radioButton2.setChecked(true);
					break;
				case 8:
					radioButton3.setChecked(true);
					break;
				}
			}
		});
		// �趨ȷ����ť�¼�
		Button sureButton = (Button) dialoglayout.findViewById(R.id.button8);
		sureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// У������������Ƿ���ȷ
				boolean isRight = true;
				String temp_name = editText1.getText().toString();
				// �����������Ϊ��
				if (!temp_name.isEmpty()) {
					// �����ȷ���趨����Ϊ��ɫ
					editText1.setTextColor(Color.BLACK);
					player_name = temp_name;
				} else {
					// ������ִ�����趨����Ϊ��ɫ
					isRight = isRight && false;
					editText1.setTextColor(Color.RED);
				}

				String temp_set = editText2.getText().toString();
				// ֽ�Ƹ���ҪΪ����
				try {
					// �����ȷ���趨����Ϊ��ɫ
					cardsSet = Integer.parseInt(temp_set);
					editText2.setTextColor(Color.BLACK);
				} catch (NumberFormatException e) {
					// ������ִ�����趨����Ϊ��ɫ
					isRight = isRight && false;
					editText2.setTextColor(Color.RED);
				}

				if (isRight) {
					// ���������ȷ�����ضԻ���
					StartActivity.this.dismissDialog(GAMESET_ID);
				}
			}
		});
		return builder.create();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		// ��ȡ�������Ϸ����
		GetGameDataInPreferences();
		// �趨��Ϸ��ʼ�¼�
		Button startButton = (Button) this.findViewById(R.id.button1);

		// ����ť�趨�����¼�������
		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// �趨��Ϸ���ݵĲ��� �� ���������ֽ�Ƹ����������Ŀ
				Intent intent = new Intent(StartActivity.this,
						FishActivity.class);
				intent.putExtra("player_name", player_name);
				intent.putExtra("cardsSet", cardsSet);
				intent.putExtra("playerNum", playerNum);
				startActivity(intent);
			}
		});
		// �趨��Ϸ�趨�¼�
		Button setButton = (Button) this.findViewById(R.id.button3);

		// ����ť�趨�����¼�������
		setButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StartActivity.this.showDialog(GAMESET_ID);
			}
		});
		// �趨��Ϸ������ť�¼�
		Button desButton = (Button) this.findViewById(R.id.button4);

		// ����ť�趨�����¼�������
		desButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʾAlertDialog
				StartActivity.this.showDialog(GAMEDES_ID);
			}
		});
		// �趨��Ϸ���ڰ�ť�¼�
		Button aboutButton = (Button) this.findViewById(R.id.button5);

		// ����ť�趨�����¼�������
		aboutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʾAlertDialog
				StartActivity.this.showDialog(GAMEABOUT_ID);
			}
		});
		// �趨��Ϸ�Ƴ��¼�
		Button exitButton = (Button) this.findViewById(R.id.button6);

		// ����ť�趨�����¼�������
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �رոô���
				StartActivity.this.finish();
			}
		});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		// ������������
		SaveGameDataInPreferences();
		// �˳������
		System.exit(0);
	}

}
