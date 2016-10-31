package controller;

import javax.servlet.http.HttpServletRequest;

/**
 * request.getParameter による値の取得を簡単にするためのクラス
 * @author indeep-xyz
 *
 */
public class ParamGetter {
	
	/**
	 * リクエスト用のオブジェクト
	 */
	private HttpServletRequest request;
	
	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 */
	public ParamGetter(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * HttpServletRequest から指定の名前の数値を得る
	 * @param name パラメータの名前
	 * @param defaultValue 戻り値のデフォルト値
	 * @return 取得した値、もしくはデフォルト値
	 */
	public int getInt(String name, int defaultValue) {
		int n = defaultValue;
		String nSrc = this.request.getParameter(name);

		if (nSrc != null) {
			try {
				n = Integer.parseInt(nSrc);
			} catch (NumberFormatException e) {
				;
			}
		}
		
		return n;
	}
}
