package org.hgworkhouse.model;

public class Card {
	// ����ֽ�������ж���ͬ
	public boolean equals(Card o) {

		boolean result = false;
		if (o != null) {
			if (o.getNumber() == this.getNumber()) {
				result = true;
			}
		}
		return result;
	}

	// ��ʾֽ�ƵĻ�ɫ����ֵ
	public String toString() {
		String typeColor = "";
		switch (type) {
		case 1:
			typeColor = "����";
			break;
		case 2:
			typeColor = "����";
			break;
		case 3:
			typeColor = "�ݻ�";
			break;
		case 4:
			typeColor = "����";
			break;
		default:
			break;
		}
		String num = "";
		switch (number) {
		case 1:
			num = "A";
			break;
		case 11:
			num = "J";
			break;
		case 12:
			num = "Q";
			break;
		case 13:
			num = "K";
			break;
		default:
			num = String.valueOf(number);
			break;
		}
		return typeColor + num;
	}

	// ֽ����ֵ
	private int number;
	// ֽ�ƻ�ɫ
	private int type;

	// getter setter ����
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
