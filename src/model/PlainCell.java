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

	/**
	 * セルを表す HTML 文を返す。
	 * @return セルを表わす HTML 文
	 */
	public String getHtml() {
		String arounds = "";
		
		if (this.aroundSomething > 0) {
			arounds = "" + this.aroundSomething;
		}
		
		return "<div class=\"opened plain\">" + arounds + "</div>";
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
