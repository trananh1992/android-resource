package org.hgworkhouse.model;

import java.util.ArrayList;

public class GameInitor {

	public ArrayList<Player> InitorPlayer(int size, int setNum) {
		// ��ʼ���������
		ArrayList<Player> result = new ArrayList<Player>();
		// ��ʼ�����е�ֽ��
		ArrayList<Card> allCards = InitorAllCards(setNum);
		GameUtil.RandCards(allCards);
		for (int i = 0; i < size; i++) {
			Player player = new Player(0, true, "���" + i, i);
			result.add(player);
		}
		int playerIndex = 0;
		// ����
		for (Card card : allCards) {
			result.get(playerIndex).getCards().add(card);
			playerIndex++;

			if (playerIndex >= size) {
				playerIndex = 0;
			}
		}

		return result;
	}

	public ArrayList<Card> InitorAllCards(int setNum) {
		ArrayList<Card> result = new ArrayList<Card>();
		setNum = setNum > 1 ? setNum : 1;
		// ������Ҫ������
		for (int i = 1; i <= setNum; i++) {
			// ��ֽ�Ƶ�����ע�뵽���е�ֽ����
			for (int typeIndex = 1; typeIndex <= 4; typeIndex++) {
				for (int numIndex = 1; numIndex <= 13; numIndex++) {
					Card card = new Card();
					card.setType(typeIndex);
					card.setNumber(numIndex);
					result.add(card);
				}
			}
		}
		return result;
	}
}
