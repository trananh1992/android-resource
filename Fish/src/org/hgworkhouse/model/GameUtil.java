package org.hgworkhouse.model;

import java.util.ArrayList;

import org.hgworkhouse.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class GameUtil {
	// 素材图片
	private static Bitmap[] bitmaps;
	// 一些静态数据
	// 纸牌背景大小
	private static int cardWidth = 126;
	private static int cardHeight = 85;
	// 纸牌数值大小
	private static int numWidth = 12;
	private static int numHeight = 312;
	// 纸牌花色大小
	private static int typeWidth = 21;
	private static int typeHeight = 80;
	// 箭头大小
	private static int arrowWidth = 17;
	private static int arrowHeight = 15;
	// 绘制时候的纸牌偏移
	public static int xOffset = 280;
	public static int yOffset = 0;
	// 纸牌的递增量
	public static int addOffset = 34;
	// 绘制时候的玩家偏移
	public static int playerXOffset = 20;
	public static int playerYOffset = 40;
	// 玩家的地增量
	public static int playerAddOffset = 40;
	// 字体大小
	public static int textSize = 17;
	// 绘制画布大小
	public static int width = 480;
	public static int height = 600;
	// 真实屏幕的大小
	public static int realWidth;
	public static int realHeight;

	public static void FitScreen(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		realWidth = dm.widthPixels;
		realHeight = dm.heightPixels - 100;
	}

	// 获取素材图片
	public static Bitmap GetBitmap(int index) {
		return bitmaps[index];
	}

	// 载入图片素材数据
	public static void LoadBitmaps(Resources resource) {
		bitmaps = new Bitmap[5];
		Bitmap cardBgBitmap = Bitmap.createBitmap(cardWidth, cardHeight,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(cardBgBitmap);
		Drawable source = resource.getDrawable(R.drawable.da_card);
		source.setBounds(0, 0, cardWidth, cardHeight);
		source.draw(canvas);
		bitmaps[0] = cardBgBitmap;

		Bitmap cardNumBitmap = Bitmap.createBitmap(numWidth, numHeight,
				Bitmap.Config.ARGB_8888);
		canvas = new Canvas(cardNumBitmap);
		source = resource.getDrawable(R.drawable.card_number);
		source.setBounds(0, 0, numWidth, numHeight);
		source.draw(canvas);
		bitmaps[1] = cardNumBitmap;

		Bitmap cardTypeBitmap = Bitmap.createBitmap(typeWidth, typeHeight,
				Bitmap.Config.ARGB_8888);
		canvas = new Canvas(cardTypeBitmap);
		source = resource.getDrawable(R.drawable.card_color);
		source.setBounds(0, 0, typeWidth, typeHeight);
		source.draw(canvas);
		bitmaps[2] = cardTypeBitmap;

		Bitmap cardArrowBitmap = Bitmap.createBitmap(arrowWidth, arrowHeight,
				Bitmap.Config.ARGB_8888);
		canvas = new Canvas(cardArrowBitmap);
		source = resource.getDrawable(R.drawable.card_color);
		source.setBounds(0, 0, arrowWidth, arrowHeight);
		source.draw(canvas);
		bitmaps[3] = cardArrowBitmap;
		
		Bitmap bgBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bgBitmap);
		source = resource.getDrawable(R.drawable.bg);
		source.setBounds(0, 0, width, height);
		source.draw(canvas);
		bitmaps[4] = bgBitmap;
	}

	// 重新洗乱纸牌
	public static void RandCards(ArrayList<Card> cards) {
		int length = cards.size();
		// 将所有牌都随机一下交换
		for (int i = 0; i < length; i++) {
			int swapIndex = (int) Math.floor(length * Math.random());
			if (swapIndex != i) {
				Card temp = cards.get(i);
				cards.set(i, cards.get(swapIndex));
				cards.set(swapIndex, temp);
			}
		}

	}

	// 比对纸牌从而判定是否可以获得纸牌
	public static ArrayList<Card> CompareCards(ArrayList<Card> cards, Card card) {
		ArrayList<Card> result = new ArrayList<Card>();
		int index = -1;
		int length = cards.size()-1;
		// 找到相同牌位置
		for (int i = 0; i < length; i++) {
			if (cards.get(i).equals(card)) {
				index = i;
			}
		}
		// 将相同牌之后的数据都移到结果数据中
		if (index != -1) {
			int count = cards.size() - index;
			for (int i = 0; i < count; i++) {
				result.add(cards.get(index));
				index++;
			}
		}
		return result;
	}

	// 将游戏数据保存到内存中
	public static void RestoreData(Bundle inBoundle, ArrayList<Player> players,ArrayList<Card> pushCards) {
		// 保存玩家数
		int size = inBoundle.getInt("playerNum");
		players = new ArrayList<Player>();
		for (int i = 0; i < size; i++) {
			// 按照玩家顺序获取玩家数据
			Player player = new Player();
			player.setName(inBoundle.getString("name:" + i));
			player.setScore(inBoundle.getInt("score:" + i));
			player.setComputer(inBoundle.getBoolean("isComputer:" + i));
			player.setId(inBoundle.getInt("id:" + i));
			// 按照顺序获取玩家纸牌数据
			ArrayList<Integer> cards = inBoundle.getIntegerArrayList("cards:"
					+ i);
			player.setCards(GetCards(cards));			
			// 保存玩家数据到玩家信息中
			players.add(player);
		}
		
		pushCards = new ArrayList<Card>();
		// 获取左面的纸牌
		ArrayList<Integer> pushCardData = inBoundle.getIntegerArrayList("pushCards");
		pushCards = GetCards(pushCardData);
	}
	
	private static ArrayList<Integer> SetCards(ArrayList<Card> cards)
	{
//		将纸牌数据转化为整数数组
		ArrayList<Integer> cardData = new ArrayList<Integer>();
		for (Card card : cards) {
			cardData.add(card.getNumber());
			cardData.add(card.getType());
		}
		return cardData;
	}
	
	private static ArrayList<Card> GetCards(ArrayList<Integer> datas)
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		// 初始化数据
		int time = 0;
		Card card = new Card();
		// 纸牌数据被分为数字和花色两个值需要分两次获取保存到纸牌中
		for (Integer num : datas) {
			time++;
			// 第一次获取数值
			if (time == 1) {
				card.setNumber(num);
			}
			// 第二次获取花色 同时保存纸牌数据
			else if (time == 2) {
				card.setType(num);
				cards.add(card);
				card = new Card();
				time = 0;
			}
		}
		return cards;
	}

	// 保存玩家数据
	public static void SaveData(Bundle outBoundle, ArrayList<Player> players,ArrayList<Card> pushCards) {
		// 保存玩家数目
		outBoundle.putInt("playerNum", players.size());
		for (int i = 0; i < players.size(); i++) {
			// 保存玩家数据
			Player player = players.get(i);
			outBoundle.putString("name:" + i, player.getName());
			outBoundle.putInt("score:" + i, player.getScore());
			outBoundle.putBoolean("isComputer:" + i, player.isComputer());
			outBoundle.putInt("id:" + i, player.getId());
			// 保存玩家纸牌数据到数组中
			outBoundle.putIntegerArrayList("cards:" + i, SetCards(player.getCards()));
		}
//		保存打出的纸牌
		outBoundle.putIntegerArrayList("pushCards:", SetCards(pushCards));
	}
}
