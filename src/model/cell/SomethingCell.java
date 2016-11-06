package model.cell;

/**
 * It is a something.
 * @author indeep-xyz
 *
 */
public class SomethingCell
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

	/**
	 * セルを表す HTML 文を返す。
	 * @return セルを表わす HTML 文
	 */
	public String getHtml() {
		return "<div class=\"opened something\"></div>";
	}

	/**
	 * ゲームの状況に応じたセルを表す HTML 文を返す。
	 * @return セルを表す HTML 文
	 */
	public String getHtmlInGame(int index) {
		if (this.isOpen()) {
			return getHtml();
		}
		
		return getUnknownHtml(index);
	}
	
}
