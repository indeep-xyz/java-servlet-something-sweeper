package model.field.cell;

import java.util.Random;

/**
 * 決まった割合で各種セルを取得するためのクラス。
 * @author indeep-xyz
 */
public class CellPot {

	/**
	 * セルの種類を決定する要素のストック。
	 */
	private boolean[] stock;
	
	/**
	 * コンストラクタ。
	 * @param leangth セルの最大数
	 * @param somethingAmount Something セルの個数
	 */
	public CellPot(int leangth, int somethingAmount){
		initializeStock(leangth, somethingAmount);
	}

	/**
	 * stock フィールドの初期化。
	 * @param leangth セルの最大数
	 * @param somethingAmount Something セルの個数
	 */
	private void initializeStock(int leangth, int somethingAmount) {
		this.stock = new boolean[leangth];

		for (int i = 0; i < somethingAmount; i++) {
			this.stock[i] = true;
		}

		for (int i = somethingAmount; i < stock.length; i++) {
			this.stock[i] = false;
		}
	}
	
	/**
	 * Stock フィールドに残りがあるか否かを返す。
	 * @return Stock フィールドに残りがある場合 true
	 */
	public boolean hasNext(){
		return this.stock.length > 0;
	}
	
	/**
	 * Stock フィールドの中からランダムな要素を取り出して返す。
	 * @return Stock フィールド内の値
	 */
	public Cell pickRandomOne(){
		Random rand = new Random();
		int index = rand.nextInt(this.stock.length);
		
		return pickOne(index);
	}

	/**
	 * Stock フィールドの指定要素番号の値を取り出して返す。
	 * @param index Stock フィールドの要素番号
	 * @return Stock フィールド内の指定要素の値
	 */
	private Cell pickOne(int index) {
		boolean cellFactor = this.stock[index];

		removeOne(index);
		return createCell(cellFactor);
	}
	
	/**
	 * 引数に従ってセルを作成する。
	 * @param b セルの種類を決定する要素
	 * @return true の場合 Something 、そうでなければ Plain セル
	 */
	private Cell createCell(boolean b){
		return (b)
				? new SomethingCell()
				: new PlainCell();
	}
	
	/**
	 * Stock フィールドの指定要素を取り除く。
	 * @param index Stock フィールドの要素番号
	 */
	private void removeOne(int index){
		boolean[] newStock = new boolean[this.stock.length - 1];
		
		for (int i = 0; i < index; i++) {
			newStock[i] = this.stock[i];
		}

		for (int i = index + 1; i < stock.length; i++) {
			newStock[i - 1] = this.stock[i];
		}

		this.stock = newStock;
	}
}
