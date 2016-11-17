package model.field;

/**
 * JSP 内で Field データを扱うとき用のインターフェイス。
 * @author indeep-xyz
 */
public interface IFieldJsp {

	/**
	 * 横幅を返す。
	 * @return 横幅
	 */
	public int getWidth();

	/**
	 * 縦幅を返す。
	 * @return 縦幅
	 */
	public int getHeight();

	/**
	 * 指定座標のセルの HTML 文を返す。
	 * @param x セルの x 座標
	 * @param y セルの y 座標
	 * @return セルの HTML 文
	 */
	public String getCellHtml(int x, int y);

	/**
	 * 指定座標のセルの、ゲーム状況に沿った HTML 文を返す。
	 * @param x セルの x 座標
	 * @param y セルの y 座標
	 * @return セルの HTML 文
	 */
	public String getCellHtmlInGame(int x, int y);
}
