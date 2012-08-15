package org.hgworkhouse.model;

public class Card {
	// 根据纸牌数字判定相同
	public boolean equals(Card o) {

		boolean result = false;
		if (o != null) {
			if (o.getNumber() == this.getNumber()) {
				result = true;
			}
		}
		return result;
	}

	// 显示纸牌的花色和数值
	public String toString() {
		String typeColor = "";
		switch (type) {
		case 1:
			typeColor = "黑桃";
			break;
		case 2:
			typeColor = "红桃";
			break;
		case 3:
			typeColor = "草花";
			break;
		case 4:
			typeColor = "方块";
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

	// 纸牌数值
	private int number;
	// 纸牌花色
	private int type;

	// getter setter 函数
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
