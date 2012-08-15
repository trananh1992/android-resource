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
	// �ز�ͼƬ
	private static Bitmap[] bitmaps;
	// һЩ��̬����
	// ֽ�Ʊ�����С
	private static int cardWidth = 126;
	private static int cardHeight = 85;
	// ֽ����ֵ��С
	private static int numWidth = 12;
	private static int numHeight = 312;
	// ֽ�ƻ�ɫ��С
	private static int typeWidth = 21;
	private static int typeHeight = 80;
	// ��ͷ��С
	private static int arrowWidth = 17;
	private static int arrowHeight = 15;
	// ����ʱ���ֽ��ƫ��
	public static int xOffset = 280;
	public static int yOffset = 0;
	// ֽ�Ƶĵ�����
	public static int addOffset = 34;
	// ����ʱ������ƫ��
	public static int playerXOffset = 20;
	public static int playerYOffset = 40;
	// ��ҵĵ�����
	public static int playerAddOffset = 40;
	// �����С
	public static int textSize = 17;
	// ���ƻ�����С
	public static int width = 480;
	public static int height = 600;
	// ��ʵ��Ļ�Ĵ�С
	public static int realWidth;
	public static int realHeight;

	public static void FitScreen(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		realWidth = dm.widthPixels;
		realHeight = dm.heightPixels - 100;
	}

	// ��ȡ�ز�ͼƬ
	public static Bitmap GetBitmap(int index) {
		return bitmaps[index];
	}

	// ����ͼƬ�ز�����
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

	// ����ϴ��ֽ��
	public static void RandCards(ArrayList<Card> cards) {
		int length = cards.size();
		// �������ƶ����һ�½���
		for (int i = 0; i < length; i++) {
			int swapIndex = (int) Math.floor(length * Math.random());
			if (swapIndex != i) {
				Card temp = cards.get(i);
				cards.set(i, cards.get(swapIndex));
				cards.set(swapIndex, temp);
			}
		}

	}

	// �ȶ�ֽ�ƴӶ��ж��Ƿ���Ի��ֽ��
	public static ArrayList<Card> CompareCards(ArrayList<Card> cards, Card card) {
		ArrayList<Card> result = new ArrayList<Card>();
		int index = -1;
		int length = cards.size()-1;
		// �ҵ���ͬ��λ��
		for (int i = 0; i < length; i++) {
			if (cards.get(i).equals(card)) {
				index = i;
			}
		}
		// ����ͬ��֮������ݶ��Ƶ����������
		if (index != -1) {
			int count = cards.size() - index;
			for (int i = 0; i < count; i++) {
				result.add(cards.get(index));
				index++;
			}
		}
		return result;
	}

	// ����Ϸ���ݱ��浽�ڴ���
	public static void RestoreData(Bundle inBoundle, ArrayList<Player> players,ArrayList<Card> pushCards) {
		// ���������
		int size = inBoundle.getInt("playerNum");
		players = new ArrayList<Player>();
		for (int i = 0; i < size; i++) {
			// �������˳���ȡ�������
			Player player = new Player();
			player.setName(inBoundle.getString("name:" + i));
			player.setScore(inBoundle.getInt("score:" + i));
			player.setComputer(inBoundle.getBoolean("isComputer:" + i));
			player.setId(inBoundle.getInt("id:" + i));
			// ����˳���ȡ���ֽ������
			ArrayList<Integer> cards = inBoundle.getIntegerArrayList("cards:"
					+ i);
			player.setCards(GetCards(cards));			
			// ����������ݵ������Ϣ��
			players.add(player);
		}
		
		pushCards = new ArrayList<Card>();
		// ��ȡ�����ֽ��
		ArrayList<Integer> pushCardData = inBoundle.getIntegerArrayList("pushCards");
		pushCards = GetCards(pushCardData);
	}
	
	private static ArrayList<Integer> SetCards(ArrayList<Card> cards)
	{
//		��ֽ������ת��Ϊ��������
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
		// ��ʼ������
		int time = 0;
		Card card = new Card();
		// ֽ�����ݱ���Ϊ���ֺͻ�ɫ����ֵ��Ҫ�����λ�ȡ���浽ֽ����
		for (Integer num : datas) {
			time++;
			// ��һ�λ�ȡ��ֵ
			if (time == 1) {
				card.setNumber(num);
			}
			// �ڶ��λ�ȡ��ɫ ͬʱ����ֽ������
			else if (time == 2) {
				card.setType(num);
				cards.add(card);
				card = new Card();
				time = 0;
			}
		}
		return cards;
	}

	// �����������
	public static void SaveData(Bundle outBoundle, ArrayList<Player> players,ArrayList<Card> pushCards) {
		// ���������Ŀ
		outBoundle.putInt("playerNum", players.size());
		for (int i = 0; i < players.size(); i++) {
			// �����������
			Player player = players.get(i);
			outBoundle.putString("name:" + i, player.getName());
			outBoundle.putInt("score:" + i, player.getScore());
			outBoundle.putBoolean("isComputer:" + i, player.isComputer());
			outBoundle.putInt("id:" + i, player.getId());
			// �������ֽ�����ݵ�������
			outBoundle.putIntegerArrayList("cards:" + i, SetCards(player.getCards()));
		}
//		��������ֽ��
		outBoundle.putIntegerArrayList("pushCards:", SetCards(pushCards));
	}
}
