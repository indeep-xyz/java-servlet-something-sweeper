package model.cell;

/**
 * セルデータ
 * @author indeep-xyz
 *
 */
public abstract class Cell {

	/**
	 * 周囲にある Something の数。負数で未判定
	 */
	protected int aroundSomething;
	
	/**
	 * セルが開放済みか否か
	 */
	private boolean isOpen;
	
	/**
	 * コンストラクタ
	 */
	public Cell() {
		this.isOpen = false;
		this.aroundSomething = -1;
	}

	/**
	 * 開放状態を返す
	 * @return 開放済みなら true
	 */
	public boolean isOpen() {
		return this.isOpen;
	}

	/**
	 * セルを開放状態にする
	 */
	public void open() {
		this.isOpen = true;
	}
	
	/**
	 * セルが Something か否かを返す
	 * @return Something なら true
	 */
	public abstract boolean isSomething();

	/**
	 * 周辺にある Something の数を返す
	 * @return 周辺にある Something の数
	 */
	public int getAroundSomething() {
		return this.aroundSomething;
	}
	
	/**
	 * 周辺にある Something の数を設定する
	 * @param somethingCount Something の数
	 */
	public void setAroundSomething(int somethingCount) {
		this.aroundSomething = somethingCount;
	}
}
