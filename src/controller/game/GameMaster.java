package controller.game;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.field.Field;
import model.field.FieldCreator;
import model.field.FieldSurveillant;

/**
 * ゲーム本編の制御用クラス
 * @author indeep-xyz
 *
 */
public class GameMaster {
	
	/**
	 * フィールド状況を記録するためのセッション名。
	 */
	public static String SESSION_FIELD_DATA = "field";
	
	/**
	 * リクエスト用のオブジェクト
	 */
	private HttpServletRequest request;

	/**
	 * フィールドデータ
	 */
	private Field field;

	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 * @param response レスポンス用のオブジェクト
	 */
	public GameMaster(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * リクエストの状態から field を初期化可能なら初期化し、そうでなければ初期化しない。
	 * @return 初期化する状況がそろっていれば true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean isFieldFormattable() {
		FieldCreator formatter = new FieldCreator(this.request);
		return formatter.isFormattable();
	}
	
	/**
	 * field を初期化して状態を記録する。
	 * @param formatter
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void formatField() throws ServletException, IOException {
		FieldCreator formatter = new FieldCreator(this.request);
		Field field = formatter.create();
		FieldSurveillant surveillant = new FieldSurveillant(field);
		surveillant.surveyAll();
		
		this.field = field;
		saveGameData();
	}

	/**
	 * ゲーム状態の保存
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveGameData() throws ServletException, IOException {
		HttpSession session = this.request.getSession();
		session.setAttribute(SESSION_FIELD_DATA, this.field);
	}

	/**
	 * ゲーム状態の読み込み
	 * @return 正常に読み込みができれば true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean loadGameData() throws ServletException, IOException {
		HttpSession session = this.request.getSession();
		this.field = (Field) session.getAttribute(SESSION_FIELD_DATA);
		
		return (this.field != null);
	}

	/**
	 * 安全なセルを全て開放済みにしたかを確認する。
	 * @return 未知の Something が存在しない場合は true
	 */
	public boolean isCompleted() {
		return (this.field.countUnknownSafetyCells() < 1);
	}
	
	/**
	 * 指定 ID のセルが Something か否かを返す。
	 * @param id セルの ID
	 * @return セルが Something なら true
	 */
	public boolean isFailed(int id) {
		return this.field.isSomething(id);
	}

	/**
	 * セルを開けて状態を記録する。
	 * @return セルが Something の場合は true
	 * @throws ServletException
	 * @throws IOException
	 */
	public void openCell(int id) throws ServletException, IOException {
		this.field.openCell(id);
		saveGameData();
	}

}
