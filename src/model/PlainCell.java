package model;

/**
 * 安全なセルを表すクラス
 * @author indeep-xyz
 *
 */
public class PlainCell
		extends Cell {

	public PlainCell() {
		super();
	}

	/**
	 * セルが Something か否かを返す
	 * @return Something なら true
	 */
	@Override
	public boolean isSomething() {
		return false;
	}
}
