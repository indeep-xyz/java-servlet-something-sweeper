package model.field.cell;

import java.io.Serializable;

/**
 * 安全なセルを表すクラス
 * @author indeep-xyz
 *
 */
class PlainCell
		extends Cell
		implements Serializable
		{
	private static final long serialVersionUID = 1L;

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
