package controller.game.status;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.istack.internal.logging.Logger;

import model.field.Field;
import model.field.FieldCreator;
import model.field.FieldSurveillant;
import model.history.History;

/**
 * ゲーム本編の制御用クラス。
 * @author indeep-xyz
 *
 */
public class GameMaster {
	
	/**
	 * フィールド状況を記録するためのセッション名。
	 */
	public static String SESSION_FIELD_DATA = "field";

	/**
	 * ゲームが進行可能か否かを記録するためのセッション名。
	 */
	public static String SESSION_GAME_END_FLAG = "isGameEnd";

	/**
	 * 現在進行中のゲームの履歴を記録するためのセッション名。
	 */
	public static String SESSION_HISTORY = "history";
	
	/**
	 * リクエスト用のオブジェクト
	 */
	private HttpServletRequest request;

	/**
	 * フィールドデータ
	 */
	private Field field;

	/**
	 * ゲームが進行可能か否か。
	 */
	private boolean isGameEnd;

	/**
	 * 進行中のゲームの履歴。
	 */
	private History history;

	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 * @param response レスポンス用のオブジェクト
	 */
	public GameMaster(HttpServletRequest request) {
		this.request = request;
		this.isGameEnd = false;
		this.history = new History();
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
		session.setAttribute(SESSION_GAME_END_FLAG, this.isGameEnd);
		session.setAttribute(SESSION_HISTORY, this.history);
	}

	/**
	 * ゲーム状態の読み込み
	 * @return 正常に読み込みができれば true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean loadGameData() throws ServletException, IOException {
		boolean succeeded = false;
		Field newField = null;
		History newHistory = null;
		
		try {
			HttpSession session = this.request.getSession();
			newField = (Field) session.getAttribute(SESSION_FIELD_DATA);
			newHistory = (History) session.getAttribute(SESSION_HISTORY);
			this.isGameEnd = (boolean) session.getAttribute(SESSION_GAME_END_FLAG);
		} catch (NullPointerException e) {
			;
		} finally {
			if (newField != null) {
				this.field = newField;
				succeeded = true;
			}
			
			if (newHistory != null) {
				this.history = newHistory;
				succeeded = true;
			}
		}
		
		return succeeded;
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
		ArrayList<Integer> openedId = this.field.openCell(id);
		
		if (0 < openedId.size()) {
			this.history.add(openedId);
		}
		
		saveGameData();
	}

	/**
	 * ゲームが進行不可能な状態に設定する。
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void endGame() throws ServletException, IOException {
		this.isGameEnd = true;
		saveGameData();
	}

	/**
	 * ゲームが進行可能か否かを返す。
	 * @return 進行可能な場合は true
	 */
	public boolean isGameEnd() {
		return this.isGameEnd;
	}

	/**
	 * フィールド内のパラメータを JSON に変換して返す。
	 * 未開放セルの内部状態を隠す。
	 * 
	 * @see model.field.Field#toJsonInGame()
	 * @return フィールドの情報を表す JSON 文字列
	 */
	public String getFieldJsonInGame() {
		return this.field.toJsonInGame();
	}

	/**
	 * フィールド内の全てのパラメータを JSON に変換して返す。
	 * 未開放セルの内部状態を隠さない。
	 * 
	 * @see model.field.Field#toJsonResult()
	 * @return フィールドの情報を表す JSON 文字列
	 */
	public String getFieldJsonResult() {
		return this.field.toJsonResult();
	}

}
