package org.hgworkhouse;

import java.util.ArrayList;

import org.hgworkhouse.model.Card;
import org.hgworkhouse.model.GameUtil;
import org.hgworkhouse.model.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class GameView extends View implements Runnable {
	// 游戏界面高度和宽度
	private int allHeight = 600;
	private int allWidth = 480;
	// 外部整体视图类用来输出文字
	private FishActivity fishActivity;
	// 是否在出牌
	public boolean isPushCard = false;
	// 是否在收牌
	public boolean isGetCard = false;
	// 游戏是否结束
	public boolean isOver = false;
	// 是否在显示效果
	public boolean isEffection = false;
	// 是否已经打出牌
	public boolean isHaveCard = false;
	// 是否有需要收得牌
	public boolean isAddCard = false;
	// 出牌的顺序位置
	private int pushTurn = 0;
	// 出牌顺序是否结束
	private boolean isTurnOver = true;
	// 绘制图片的alpha值 用来实现图片闪动出现
	private int alfa = 255;
	// 用来标识收牌的位置
	private int getCardIndex = -1;
	// 当前的需要收牌的纸牌之间的距离
	private int currentAddOffset = -1;
	// 纸牌收牌距离变化速度
	private int addOffsetValue = 5;
	// 纸牌闪动alpha变化速度
	private int addAlfaValue = 100;
	// 玩家当前出的牌和玩家数据
	private Card cardTemp = null;
	private Player playerTemp = null;

	// 标定为需要出牌的效果
	public void SetPushCardEffection() {
		// 有效果、出牌阶段、没有收牌
		this.isEffection = true;
		this.isPushCard = true;
		this.isGetCard = false;
		this.alfa = 5;
	}

	// 标定为结束出牌的效果
	public void ClosePushCardEffection() {
		// 没有效果、没有出牌、没有收牌
		this.isEffection = false;
		this.isPushCard = false;
		this.isGetCard = false;
		this.alfa = 255;

		this.isHaveCard = true;

	}

	// 标定为收牌的效果
	public void SetGetCardEffection() {
		// 有效果、没有出牌、有收牌
		this.isEffection = true;
		this.isGetCard = true;
		this.isPushCard = false;
		currentAddOffset = GameUtil.addOffset;
	}

	// 标点结束收牌的效果
	public void CloseGetCardEffection() {
		// 没有效果、没有出牌、没有收牌 并且判断是不是最后一个玩家如果是 没有顺序出牌
		this.isEffection = false;
		this.isGetCard = false;
		this.isPushCard = false;
		this.isHaveCard = false;
		// 让下一个玩家出牌
		pushTurn++;
		// 如果是最后一个玩家
		if (pushTurn == players.size()) {
			isTurnOver = true;
			fishActivity.ShowInfo("请出牌");
			pushTurn = 0;
		}
	}

	// 玩家获得纸牌
	private void GetCards(Card card, Player player) {
		// 如果玩家和纸牌都不为空
		if (cardTemp != null && playerTemp != null) {
			// 判断是否有纸牌收
			ArrayList<Card> getCards = GameUtil.CompareCards(cards, cardTemp);
			int cardLength = getCards.size();
			if (cardLength > 0) {
				// 有牌收加入到玩家中
				// 同时标识有纸牌要收
				getCardIndex = cards.size() - cardLength;
				// 获取纸牌
				fishActivity.ShowInfo(player.getName() + "收牌："
						+ card.toString());
				playerTemp.GetCard(getCards);
				this.isAddCard = true;
			} else {
				// 没有纸牌
				getCardIndex = -1;
			}
		}
		// 开起收牌效果
		SetGetCardEffection();
	}

	// 出牌
	private void PushCard(Player player) {
		Card card = player.PushCard();

		// 记录 当前 打出的牌和玩家
		cardTemp = card;
		playerTemp = player;

		// 如果还有牌
		if (card != null) {
			// 将纸牌显示在桌面
			fishActivity.ShowInfo(player.getName() + "出牌：" + card.toString());
			getCards().add(card);
			SetPushCardEffection();
		} else {
			// 关闭出牌效果 为了使得进入收牌判断
			ClosePushCardEffection();
		}
	}

	public void PushCardInTurn() {
		// 按顺序玩家出牌
		if (isTurnOver) {
			pushTurn = 0;
			isTurnOver = false;
		}
	}

	// 玩家数据
	private ArrayList<Player> players;
	// 纸牌数据
	private ArrayList<Card> cards;
	// 画笔
	private final Paint mPaint = new Paint();

	// 覆盖这个方法可以使得scrollView能够得到画布的大小
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(allWidth, allHeight);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化玩家信息
		// 载入图片数据
		players = new ArrayList<Player>();
		cards = new ArrayList<Card>();
		GameUtil.LoadBitmaps(this.getResources());

		new Thread(this).start();
	}

	private void DrawCards(Canvas canvas) {
		// 获取偏移量和递增量
		int xOffset = GameUtil.xOffset;
		int yOffset = GameUtil.yOffset;
		int addOffset = GameUtil.addOffset;

		int length = cards.size();

		for (int i = 0; i < length; i++) {
			Card card = cards.get(i);
			// 根据是否为出牌的状态来 决定其绘制的 alpha的值
			if ((length - 1) == i) {
				if (this.isPushCard) {
					if (this.alfa < 255) {
						this.alfa += this.addAlfaValue;
					} else {
						// 出牌效果消失
						ClosePushCardEffection();
					}
				}
				this.mPaint.setAlpha(this.alfa);
			} else {
				this.mPaint.setAlpha(255);
			}

			// 采用原始大小缩放2倍的绘制
			// 绘制纸牌背景
			Rect cardsource = new Rect(0, 0, 63, 85);
			Rect cardtarget = new Rect(xOffset + 0, yOffset + 0, xOffset + 126,
					yOffset + 170);
			canvas.drawBitmap(GameUtil.GetBitmap(0), cardsource, cardtarget,
					mPaint);

			allHeight = yOffset + 170 > GameUtil.height ? yOffset + 170
					: GameUtil.height;
			// 根据纸牌花色绘制纸牌数字
			if (card.getType() == 1 || card.getType() == 3) {
				Rect numSource = new Rect(0, 12 * (card.getNumber() + 12), 12,
						12 * (card.getNumber() + 13));
				Rect numTarget = new Rect(xOffset + 10, yOffset + 10,
						xOffset + 30, yOffset + 30);
				canvas.drawBitmap(GameUtil.GetBitmap(1), numSource, numTarget,
						mPaint);
			} else {
				Rect numSource = new Rect(0, 12 * (card.getNumber() - 1), 12,
						12 * card.getNumber());
				Rect numTarget = new Rect(xOffset + 10, yOffset + 10,
						xOffset + 30, yOffset + 30);
				canvas.drawBitmap(GameUtil.GetBitmap(1), numSource, numTarget,
						mPaint);
			}

			// 绘制纸牌花色
			Rect typeSource = new Rect(0, 20 * (card.getType() - 1), 21,
					20 * card.getType());
			Rect typeTarget = new Rect(xOffset + 30, yOffset + 10,
					xOffset + 50, yOffset + 30);
			canvas.drawBitmap(GameUtil.GetBitmap(2), typeSource, typeTarget,
					mPaint);
			// 如果是要被收的纸牌动态修改纸牌之间位置
			if (isGetCard && isAddCard) {
				if (getCardIndex != -1 && getCardIndex <= i) {
					yOffset += currentAddOffset;
				} else {
					yOffset += addOffset;
				}
			} else {
				// 设定偏移量
				yOffset += addOffset;
			}
		}

		if (isGetCard) {
			if (isAddCard) {
				// 减少需要收的纸牌距离
				currentAddOffset -= this.addOffsetValue;
				if (currentAddOffset < 0) {
					// 收牌效果消失
					CloseGetCardEffection();
					// 将桌面纸牌清空

					int size = length - getCardIndex;
					for (int i = 0; i < size; i++) {
						cards.remove(getCardIndex);
					}
					// 没有牌需要收
					isAddCard = false;
				}
			} else {
				// 收牌效果消失
				CloseGetCardEffection();
			}
		}

	}

	private void DrawPlayer(Canvas canvas) {
		// 获取玩家偏移量和递增量
		int xOffset = GameUtil.playerXOffset;
		int yOffset = GameUtil.playerYOffset;
		int addOffset = GameUtil.playerAddOffset;
		mPaint.setTextSize(GameUtil.textSize);
		// 绘制玩家信息
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			// 如果是当前的玩家 使用不同的背景
			if (i == pushTurn) {
				mPaint.setColor(Color.WHITE);
				canvas.drawRect(xOffset, yOffset - 20, GameUtil.xOffset,
						yOffset + 20, mPaint);
			}
			mPaint.setColor(Color.BLACK);
			canvas.drawText("玩家:" + player.getName() + "-剩余:"
					+ player.getCards().size(), xOffset, yOffset, mPaint);
			yOffset += addOffset;
		}
	}

	private void UpdateLogic() {
		// 顺序出牌没有结束
		if (!isTurnOver) {
			// 出牌效果已经结束
			if (!isEffection) {
				// 如果没有出牌 、收牌、已经出牌
				if (!isPushCard && !isGetCard && !isHaveCard) {
					// 玩家出牌
					PushCard(players.get(pushTurn));
				}

				// 如果没有出牌 并且没有收牌 但是有已经出牌
				if (!isPushCard && !isGetCard && isHaveCard) {
					GetCards(cardTemp, playerTemp);
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		// 绘制玩家和纸牌
		mPaint.setAntiAlias(true);
		// 绘制玩家
		DrawPlayer(canvas);
		// 绘制纸牌
		DrawCards(canvas);
		// 更新游戏逻辑
		UpdateLogic();
		// 重新设定视图的大小

		LayoutParams linearParams = this.getLayoutParams();
		linearParams.height = this.allHeight;
		this.setLayoutParams(linearParams);

	}

	// 心跳函数 不断绘制游戏界面
	public void run() {
		while (!isOver) {
			try {
				Thread.sleep(40);
				postInvalidate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Getter setter 函数
	public int getAllHeight() {
		return allHeight;
	}

	public void setAllHeight(int allHeight) {
		this.allHeight = allHeight;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public FishActivity getFishActivity() {
		return fishActivity;
	}

	public void setFishActivity(FishActivity fishActivity) {
		this.fishActivity = fishActivity;
	}

}
