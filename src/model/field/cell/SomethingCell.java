package model.field.cell;

import java.io.Serializable;

/**
 * It is a something.
 * @author indeep-xyz
 *
 */
class SomethingCell
		extends Cell
		implements Serializable
		{

	public SomethingCell() {
		super();
	}

	/**
	 * セルが Something か否かを返す
	 * @return Something なら true
	 */
	@Override
	public boolean isSomething() {
		return true;
	}
}
