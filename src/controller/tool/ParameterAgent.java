package controller.tool;

import javax.servlet.http.HttpServletRequest;

/**
 * request.getParameter による値の取得を簡単にするためのクラス
 * @author indeep-xyz
 *
 */
public class ParameterAgent {
	
	/**
	 * リクエスト用のオブジェクト
	 */
	private HttpServletRequest request;
	
	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 */
	public ParameterAgent(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * HttpServletRequest から指定の名前の数値を得る
	 * @param name パラメータの名前
	 * @param defaultValue 戻り値のデフォルト値
	 * @return 取得した値、もしくはデフォルト値
	 */
	public Integer getInt(String name, Integer defaultValue) {
		Integer number = defaultValue;
		String source = request.getParameter(name);

		if (source != null) {
			try {
				number = Integer.parseInt(source);
			} catch (NumberFormatException e) {
				;
			}
		}
		
		return number;
	}
}
