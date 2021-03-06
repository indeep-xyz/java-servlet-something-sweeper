package model.field.cell;

import java.io.Serializable;

/**
 * セルデータ
 * @author indeep-xyz
 *
 */
public abstract class Cell
		implements Serializable
		{
	private static final long serialVersionUID = 1L;

	/**
	 * 周囲にある Something の数。負数で未判定
	 */
	protected int aroundSomething;

	/**
	 * セルに充てられたインデックス番号。初期値は null 。
	 */
	private Integer index;

	/**
	 * セルが開放済みか否か
	 */
	private boolean isOpen;

	/**
	 * コンストラクタ
	 */
	public Cell() {
		this.index = null;
		this.isOpen = false;
		this.aroundSomething = -1;
	}

	/**
	 * セルが Something か否かを返す
	 * @return Something なら true
	 */
	public abstract boolean isSomething();

	/**
	 * セルのインデックス番号を返す。
	 * @return セルのインデックス番号
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * セルのインデックス番号を設定する。
	 * @param index セルのインデックス番号
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

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
	
	/**
	 * 開放状態を返す
	 * @return 開放済みなら true
	 */
	public boolean isOpen() {
		return this.isOpen;
	}

	/**
	 * セルを開放状態にする
	 * @return 開放に成功したら true
	 */
	public boolean open() {
		boolean isUpdated = false;
		
		if (!this.isOpen) {
			this.isOpen = true;
			isUpdated = true;
		}
		
		return isUpdated;
	}

	/**
	 * セルの状態を JSON 文字列に変換して返す。
	 * 
	 * {
	 *     index: An index in the field.
	 *     isOpen: The instance is opened or not.
	 *     aroundSomething: Number of something cells around.
	 *     isSomething: The instance is a something cell or not.
	 * }
	 *     
	 * @return  JSON 文字列に変換されたセルの状態
	 */
	public String toJson() {
		StringBuilder jsonSource = new StringBuilder("{");
		
		if (index != null) {
			jsonSource.append("\"index\":" + this.index);
			jsonSource.append(",");
		}

		jsonSource.append("\"isOpen\":" + true);
		jsonSource.append(",\"aroundSomething\":" + this.aroundSomething);
		jsonSource.append(",\"isSomething\":" + this.isSomething());
		
		jsonSource.append("}");
		
		return jsonSource.toString();
	}

	/**
	 * 開放状態に応じてセルの状態を JSON 文字列に変換して返す。
	 * 
	 * {
	 *     index: An index in the field.
	 *     isOpen: The instance is opened or not.
	 *     aroundSomething: Number of something cells around. This parameter is contained only if the instance opened.
	 *     isSomething: The instance is a something cell or not. This parameter is contained only if the instance opened.
	 * }
	 * 
	 * @return  JSON 文字列に変換されたセルの状態
	 */
	public String toJsonInGame() {
		StringBuilder jsonSource = new StringBuilder("{");
		
		if (index != null) {
			jsonSource.append("\"index\":" + this.index);
			jsonSource.append(",");
		}

		jsonSource.append("\"isOpen\":" + this.isOpen);
		
		if (this.isOpen) {
			jsonSource.append(",\"aroundSomething\":" + this.aroundSomething);
			jsonSource.append(",\"isSomething\":" + this.isSomething());
		}
		
		jsonSource.append("}");
		
		return jsonSource.toString();
	}
}
