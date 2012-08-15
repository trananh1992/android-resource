package org.hgworkhouse.model;

import java.util.ArrayList;

public class Player {
	private int score;
	private boolean isComputer;
	private String name;
	private int id;
	private ArrayList<Card> cards;
	
	public Player()
	{
		cards = new ArrayList<Card>();
	}
	
	public Player(int score,boolean isComputer,String name,int id)
	{
		this.score = score;
		this.isComputer = isComputer;
		this.name = name;
		this.id = id;
		this.cards = new ArrayList<Card>();
	}
//	œ¥≈∆
	public void RandCards()
	{
		if(this.cards.size()>1)
		{
			GameUtil.RandCards(this.cards);
		}
	}
//	≥ˆ≈∆
	public Card PushCard()
	{
		if(this.cards.size()>0)
		{
			return this.cards.remove(0);
		}
		return null;
	}
//	ªÒ»°≈∆
	public void GetCard(ArrayList<Card> newCards)
	{
		this.cards.addAll(newCards);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isComputer() {
		return isComputer;
	}

	public void setComputer(boolean isComputer) {
		this.isComputer = isComputer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
