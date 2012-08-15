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
	// 玩家姓名
	private String player_name = "linfeng";
	// 纸牌副数
	private int cardsSet = 1;
	// 玩家数目
	private int playerNum = 2;
	// 对话框ID
	private final int GAMESET_ID = 1;
	private final int GAMEDES_ID = 2;
	private final int GAMEABOUT_ID = 3;
	// 游戏设定对话框中的控件
	private EditText editText1;
	private EditText editText2;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;

	private void GetGameDataInPreferences() {
		SharedPreferences sp = getSharedPreferences("Setting_fishCard",
				MODE_PRIVATE);
		/*
		 * 下面代码是我们要在程序刚启动的时候我们来读取之前的数据, 当然我们还没有保存任何数据所以肯定找不到！！
		 * 如果找不到也没关系会默认返回一个参数值，看下面的方法含义便知！
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
			// 游戏设定界面中的控件设定值 以及 获取
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

	// 创建游戏关于 对话框
	private Dialog CreateGameAboutDialog() {
		return new AlertDialog.Builder(StartActivity.this).setTitle(
				StartActivity.this.getResources()
						.getString(R.string.game_about)).setItems(
				new String[] { StartActivity.this.getResources().getString(
						R.string.game_about_content) },
				new DialogInterface.OnClickListener() {
					// 点击AlertDialog上的按钮的事件处理代码
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
					// 点击AlertDialog上的按钮的事件处理代码
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
	}

	// 创建游戏描述 对话框
	private Dialog CreateGameDesDialog() {
		return new AlertDialog.Builder(StartActivity.this).setTitle(
				StartActivity.this.getResources().getString(R.string.game_des))
				.setMessage(
						StartActivity.this.getResources().getString(
								R.string.game_des_content)).setNeutralButton(
						R.string.close, new DialogInterface.OnClickListener() {
							// 点击AlertDialog上的按钮的事件处理代码
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).create();
	}

	// 设定单选按钮的监听事件 用来获取玩家数
	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == R.id.myRadioButton1) {
				/* 把mRadio1的内容传到mTextView1 */
				playerNum = 2;
			} else if (checkedId == R.id.myRadioButton2) {
				/* 把mRadio2的内容传到mTextView1 */
				playerNum = 4;
			} else if (checkedId == R.id.myRadioButton3) {
				/* 把mRadio2的内容传到mTextView1 */
				playerNum = 8;
			}
		}
	};

	// 创建游戏设定对话框
	private Dialog CreateGameSetDialog() {
		// 获取对话框的布局
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.game_set,
				(ViewGroup) findViewById(R.id.linearLayout1));

		RadioGroup mRadioGroup1 = (RadioGroup) dialoglayout
				.findViewById(R.id.myRadioGroup);

		/* RadioGroup用OnCheckedChangeListener来运行 */
		mRadioGroup1.setOnCheckedChangeListener(mChangeRadio);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialoglayout);
		builder.setTitle(getResources().getString(R.string.game_set));
		// 设定重置按钮事件
		Button resetButton = (Button) dialoglayout.findViewById(R.id.button7);
		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 重新设定玩家和纸牌副数 信息
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
		// 设定确定按钮事件
		Button sureButton = (Button) dialoglayout.findViewById(R.id.button8);
		sureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 校验输入的数据是否正确
				boolean isRight = true;
				String temp_name = editText1.getText().toString();
				// 玩家姓名不能为空
				if (!temp_name.isEmpty()) {
					// 如果正确就设定字体为黑色
					editText1.setTextColor(Color.BLACK);
					player_name = temp_name;
				} else {
					// 如果出现错误就设定字体为红色
					isRight = isRight && false;
					editText1.setTextColor(Color.RED);
				}

				String temp_set = editText2.getText().toString();
				// 纸牌副数要为整数
				try {
					// 如果正确就设定字体为黑色
					cardsSet = Integer.parseInt(temp_set);
					editText2.setTextColor(Color.BLACK);
				} catch (NumberFormatException e) {
					// 如果出现错误就设定字体为红色
					isRight = isRight && false;
					editText2.setTextColor(Color.RED);
				}

				if (isRight) {
					// 如果输入正确就隐藏对话框
					StartActivity.this.dismissDialog(GAMESET_ID);
				}
			}
		});
		return builder.create();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		// 获取保存的游戏数据
		GetGameDataInPreferences();
		// 设定游戏开始事件
		Button startButton = (Button) this.findViewById(R.id.button1);

		// 给按钮设定单击事件监听器
		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设定游戏传递的参数 ： 玩家姓名、纸牌副数、玩家数目
				Intent intent = new Intent(StartActivity.this,
						FishActivity.class);
				intent.putExtra("player_name", player_name);
				intent.putExtra("cardsSet", cardsSet);
				intent.putExtra("playerNum", playerNum);
				startActivity(intent);
			}
		});
		// 设定游戏设定事件
		Button setButton = (Button) this.findViewById(R.id.button3);

		// 给按钮设定单击事件监听器
		setButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StartActivity.this.showDialog(GAMESET_ID);
			}
		});
		// 设定游戏描述按钮事件
		Button desButton = (Button) this.findViewById(R.id.button4);

		// 给按钮设定单击事件监听器
		desButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 显示AlertDialog
				StartActivity.this.showDialog(GAMEDES_ID);
			}
		});
		// 设定游戏关于按钮事件
		Button aboutButton = (Button) this.findViewById(R.id.button5);

		// 给按钮设定单击事件监听器
		aboutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 显示AlertDialog
				StartActivity.this.showDialog(GAMEABOUT_ID);
			}
		});
		// 设定游戏推出事件
		Button exitButton = (Button) this.findViewById(R.id.button6);

		// 给按钮设定单击事件监听器
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 关闭该窗口
				StartActivity.this.finish();
			}
		});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		// 保存配置数据
		SaveGameDataInPreferences();
		// 退出整个活动
		System.exit(0);
	}

}
