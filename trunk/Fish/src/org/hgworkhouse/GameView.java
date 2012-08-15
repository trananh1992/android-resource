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
	// ��Ϸ����߶ȺͿ��
	private int allHeight = 600;
	private int allWidth = 480;
	// �ⲿ������ͼ�������������
	private FishActivity fishActivity;
	// �Ƿ��ڳ���
	public boolean isPushCard = false;
	// �Ƿ�������
	public boolean isGetCard = false;
	// ��Ϸ�Ƿ����
	public boolean isOver = false;
	// �Ƿ�����ʾЧ��
	public boolean isEffection = false;
	// �Ƿ��Ѿ������
	public boolean isHaveCard = false;
	// �Ƿ�����Ҫ�յ���
	public boolean isAddCard = false;
	// ���Ƶ�˳��λ��
	private int pushTurn = 0;
	// ����˳���Ƿ����
	private boolean isTurnOver = true;
	// ����ͼƬ��alphaֵ ����ʵ��ͼƬ��������
	private int alfa = 255;
	// ������ʶ���Ƶ�λ��
	private int getCardIndex = -1;
	// ��ǰ����Ҫ���Ƶ�ֽ��֮��ľ���
	private int currentAddOffset = -1;
	// ֽ�����ƾ���仯�ٶ�
	private int addOffsetValue = 5;
	// ֽ������alpha�仯�ٶ�
	private int addAlfaValue = 100;
	// ��ҵ�ǰ�����ƺ��������
	private Card cardTemp = null;
	private Player playerTemp = null;

	// �궨Ϊ��Ҫ���Ƶ�Ч��
	public void SetPushCardEffection() {
		// ��Ч�������ƽ׶Ρ�û������
		this.isEffection = true;
		this.isPushCard = true;
		this.isGetCard = false;
		this.alfa = 5;
	}

	// �궨Ϊ�������Ƶ�Ч��
	public void ClosePushCardEffection() {
		// û��Ч����û�г��ơ�û������
		this.isEffection = false;
		this.isPushCard = false;
		this.isGetCard = false;
		this.alfa = 255;

		this.isHaveCard = true;

	}

	// �궨Ϊ���Ƶ�Ч��
	public void SetGetCardEffection() {
		// ��Ч����û�г��ơ�������
		this.isEffection = true;
		this.isGetCard = true;
		this.isPushCard = false;
		currentAddOffset = GameUtil.addOffset;
	}

	// ���������Ƶ�Ч��
	public void CloseGetCardEffection() {
		// û��Ч����û�г��ơ�û������ �����ж��ǲ������һ���������� û��˳�����
		this.isEffection = false;
		this.isGetCard = false;
		this.isPushCard = false;
		this.isHaveCard = false;
		// ����һ����ҳ���
		pushTurn++;
		// ��������һ�����
		if (pushTurn == players.size()) {
			isTurnOver = true;
			fishActivity.ShowInfo("�����");
			pushTurn = 0;
		}
	}

	// ��һ��ֽ��
	private void GetCards(Card card, Player player) {
		// �����Һ�ֽ�ƶ���Ϊ��
		if (cardTemp != null && playerTemp != null) {
			// �ж��Ƿ���ֽ����
			ArrayList<Card> getCards = GameUtil.CompareCards(cards, cardTemp);
			int cardLength = getCards.size();
			if (cardLength > 0) {
				// �����ռ��뵽�����
				// ͬʱ��ʶ��ֽ��Ҫ��
				getCardIndex = cards.size() - cardLength;
				// ��ȡֽ��
				fishActivity.ShowInfo(player.getName() + "���ƣ�"
						+ card.toString());
				playerTemp.GetCard(getCards);
				this.isAddCard = true;
			} else {
				// û��ֽ��
				getCardIndex = -1;
			}
		}
		// ��������Ч��
		SetGetCardEffection();
	}

	// ����
	private void PushCard(Player player) {
		Card card = player.PushCard();

		// ��¼ ��ǰ ������ƺ����
		cardTemp = card;
		playerTemp = player;

		// ���������
		if (card != null) {
			// ��ֽ����ʾ������
			fishActivity.ShowInfo(player.getName() + "���ƣ�" + card.toString());
			getCards().add(card);
			SetPushCardEffection();
		} else {
			// �رճ���Ч�� Ϊ��ʹ�ý��������ж�
			ClosePushCardEffection();
		}
	}

	public void PushCardInTurn() {
		// ��˳����ҳ���
		if (isTurnOver) {
			pushTurn = 0;
			isTurnOver = false;
		}
	}

	// �������
	private ArrayList<Player> players;
	// ֽ������
	private ArrayList<Card> cards;
	// ����
	private final Paint mPaint = new Paint();

	// ���������������ʹ��scrollView�ܹ��õ������Ĵ�С
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(allWidth, allHeight);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ��ʼ�������Ϣ
		// ����ͼƬ����
		players = new ArrayList<Player>();
		cards = new ArrayList<Card>();
		GameUtil.LoadBitmaps(this.getResources());

		new Thread(this).start();
	}

	private void DrawCards(Canvas canvas) {
		// ��ȡƫ�����͵�����
		int xOffset = GameUtil.xOffset;
		int yOffset = GameUtil.yOffset;
		int addOffset = GameUtil.addOffset;

		int length = cards.size();

		for (int i = 0; i < length; i++) {
			Card card = cards.get(i);
			// �����Ƿ�Ϊ���Ƶ�״̬�� ��������Ƶ� alpha��ֵ
			if ((length - 1) == i) {
				if (this.isPushCard) {
					if (this.alfa < 255) {
						this.alfa += this.addAlfaValue;
					} else {
						// ����Ч����ʧ
						ClosePushCardEffection();
					}
				}
				this.mPaint.setAlpha(this.alfa);
			} else {
				this.mPaint.setAlpha(255);
			}

			// ����ԭʼ��С����2���Ļ���
			// ����ֽ�Ʊ���
			Rect cardsource = new Rect(0, 0, 63, 85);
			Rect cardtarget = new Rect(xOffset + 0, yOffset + 0, xOffset + 126,
					yOffset + 170);
			canvas.drawBitmap(GameUtil.GetBitmap(0), cardsource, cardtarget,
					mPaint);

			allHeight = yOffset + 170 > GameUtil.height ? yOffset + 170
					: GameUtil.height;
			// ����ֽ�ƻ�ɫ����ֽ������
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

			// ����ֽ�ƻ�ɫ
			Rect typeSource = new Rect(0, 20 * (card.getType() - 1), 21,
					20 * card.getType());
			Rect typeTarget = new Rect(xOffset + 30, yOffset + 10,
					xOffset + 50, yOffset + 30);
			canvas.drawBitmap(GameUtil.GetBitmap(2), typeSource, typeTarget,
					mPaint);
			// �����Ҫ���յ�ֽ�ƶ�̬�޸�ֽ��֮��λ��
			if (isGetCard && isAddCard) {
				if (getCardIndex != -1 && getCardIndex <= i) {
					yOffset += currentAddOffset;
				} else {
					yOffset += addOffset;
				}
			} else {
				// �趨ƫ����
				yOffset += addOffset;
			}
		}

		if (isGetCard) {
			if (isAddCard) {
				// ������Ҫ�յ�ֽ�ƾ���
				currentAddOffset -= this.addOffsetValue;
				if (currentAddOffset < 0) {
					// ����Ч����ʧ
					CloseGetCardEffection();
					// ������ֽ�����

					int size = length - getCardIndex;
					for (int i = 0; i < size; i++) {
						cards.remove(getCardIndex);
					}
					// û������Ҫ��
					isAddCard = false;
				}
			} else {
				// ����Ч����ʧ
				CloseGetCardEffection();
			}
		}

	}

	private void DrawPlayer(Canvas canvas) {
		// ��ȡ���ƫ�����͵�����
		int xOffset = GameUtil.playerXOffset;
		int yOffset = GameUtil.playerYOffset;
		int addOffset = GameUtil.playerAddOffset;
		mPaint.setTextSize(GameUtil.textSize);
		// ���������Ϣ
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			// ����ǵ�ǰ����� ʹ�ò�ͬ�ı���
			if (i == pushTurn) {
				mPaint.setColor(Color.WHITE);
				canvas.drawRect(xOffset, yOffset - 20, GameUtil.xOffset,
						yOffset + 20, mPaint);
			}
			mPaint.setColor(Color.BLACK);
			canvas.drawText("���:" + player.getName() + "-ʣ��:"
					+ player.getCards().size(), xOffset, yOffset, mPaint);
			yOffset += addOffset;
		}
	}

	private void UpdateLogic() {
		// ˳�����û�н���
		if (!isTurnOver) {
			// ����Ч���Ѿ�����
			if (!isEffection) {
				// ���û�г��� �����ơ��Ѿ�����
				if (!isPushCard && !isGetCard && !isHaveCard) {
					// ��ҳ���
					PushCard(players.get(pushTurn));
				}

				// ���û�г��� ����û������ �������Ѿ�����
				if (!isPushCard && !isGetCard && isHaveCard) {
					GetCards(cardTemp, playerTemp);
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		// ������Һ�ֽ��
		mPaint.setAntiAlias(true);
		// �������
		DrawPlayer(canvas);
		// ����ֽ��
		DrawCards(canvas);
		// ������Ϸ�߼�
		UpdateLogic();
		// �����趨��ͼ�Ĵ�С

		LayoutParams linearParams = this.getLayoutParams();
		linearParams.height = this.allHeight;
		this.setLayoutParams(linearParams);

	}

	// �������� ���ϻ�����Ϸ����
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

	// Getter setter ����
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
