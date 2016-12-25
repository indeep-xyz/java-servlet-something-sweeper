package model.field.cell;

/**
 * It is a something.
 * @author indeep-xyz
 *
 */
class SomethingCell
		extends Cell {

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
