package model;

import java.util.Random;

/**
 * セルを作成するクラス
 * @author indeep-xyz
 *
 */
public class CellCreator {

	/**
	 * 難易度
	 */
	private int difficulty;
	
	/**
	 * 初期化
	 * @param difficulty 難易度
	 */
	public CellCreator(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * 難易度に沿ったランダムな Cell を作成して返す
	 * @return Cell インスタンス
	 */
	public Cell pickRandom() {
		Random rand = new Random();
		Cell cell = null;
		
		if (rand.nextInt(20) <= this.difficulty) {
			cell = new SomethingCell();
		}
		else {
			cell = new PlainCell();
		}
		
		return cell;
	}
}
