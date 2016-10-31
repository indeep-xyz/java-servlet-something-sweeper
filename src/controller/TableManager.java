package controller;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

/**
 * 表データのフォーマットを担当する
 * @author indeep-xyz
 *
 */
public abstract class TableManager {

	/**
	 * リクエスト用のオブジェクト
	 */
	protected HttpServletRequest request;

	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 */
	public TableManager(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * HttpServletRequest から指定の名前の数値を得る
	 * @param name パラメータの名前
	 * @param defaultValue 戻り値のデフォルト値
	 * @return 取得した値、もしくはデフォルト値
	 */
	protected int getIntParameter(String name, int defaultValue) {
		ParamGetter paramGetter = new ParamGetter(this.request);
		return paramGetter.getInt(name, -1);
	}
}
