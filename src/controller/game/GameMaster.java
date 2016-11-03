package controller.game;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.tool.ParameterAgent;
import model.Field;
import model.FieldFormatter;
import model.FieldSurveillant;

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
	 * レスポンス用のオブジェクト
	 */
	private HttpServletResponse response;

	/**
	 * フィールドデータ
	 */
	private Field field;

	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 * @param response レスポンス用のオブジェクト
	 */
	public GameMaster(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * リクエストの状態から field を初期化可能なら初期化し、そうでなければ初期化しない。
	 * @return 初期化する状況がそろっていれば true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean isFieldFormattable() {
		FieldFormatter formatter = new FieldFormatter(this.request);
		return formatter.isFormattable();
	}
	
	/**
	 * field を初期化する。
	 * @param formatter
	 */
	public void formatField() {
		FieldFormatter formatter = new FieldFormatter(this.request);
		Field field = formatter.create();
		FieldSurveillant surveillant = new FieldSurveillant(field);
		surveillant.surveyAll();
		
		this.field = field;
	}

	/**
	 * ゲーム状態の保存
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveGameData() throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_FIELD_DATA, this.field);
	}

	/**
	 * ゲーム状態の読み込み
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadGameData() throws ServletException, IOException {
		HttpSession session = request.getSession();
		this.field = (Field) session.getAttribute(SESSION_FIELD_DATA);
	}

	/**
	 * セルを開ける
	 * @return セルが Something の場合は true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean openCell() throws ServletException, IOException {
		ParameterAgent paramGetter = new ParameterAgent(request);
		int id = paramGetter.getInt("clicked", -1);
		boolean isSomething = this.field.openCell(id);
		
		saveGameData();
		
		return isSomething;
	}

	/**
	 * 未知の Something が存在するか否かを返す
	 * @return 未知の Something が存在する場合は true
	 */
	public boolean checkUnknownSomething() {
		int unknownSomethingCell = this.field.countUnknownSomethingCell();
		int unknownCell = this.field.countUnknownCell();
		
		return (unknownSomethingCell != unknownCell);
	}
}
