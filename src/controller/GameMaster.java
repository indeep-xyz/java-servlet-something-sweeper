package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Field;

/**
 * ゲーム本編の制御用クラス
 * @author indeep-xyz
 *
 */
public class GameMaster {
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
	 * テーブルを初期化する状態であるか否かを検証する
	 * @param request リクエスト
	 * @param response レスポンス
	 * @return 初期化する状況がそろっていれば true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean tryFormatting() {
		FieldCreator manager = new FieldCreator(this.request);
		boolean formattable = manager.checkFormattable();

		if (formattable) {
			this.field = manager.create();
		}
		
		return formattable;
	}

	/**
	 * 初期画面の表示
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runIntroduction() throws ServletException, IOException {
		forward("/WEB-INF/view/Introduction.jsp");
	}

	/**
	 * ゲーム画面の表示
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runGame() throws ServletException, IOException {
		saveGameData();
		forward("/WEB-INF/view/Game.jsp");
	}

	/**
	 * ゲーム失敗画面の表示
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runGameFailed() throws ServletException, IOException {
		saveGameData();
		forward("/WEB-INF/view/GameFailed.jsp");
	}

	/**
	 * ゲーム成功画面の表示
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runGameSucceeded() throws ServletException, IOException {
		forward("/WEB-INF/view/GameSucceeded.jsp");
	}
	
	/**
	 * ゲーム状態の保存
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveGameData() throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("table", this.field);
	}

	/**
	 * ゲーム状態の読み込み
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadGameData() throws ServletException, IOException {
		HttpSession session = request.getSession();
		this.field = (Field) session.getAttribute("table");
	}

	/**
	 * ビューへのフォワードをおこなう
	 * @param path ビューのパス
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forward(String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.request.getRequestDispatcher(path);
		dispatcher.forward(this.request, this.response);
	}

	/**
	 * セルを開ける
	 * @return セルが Something の場合は true
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean openCell() throws ServletException, IOException {
		FieldUpdater manager = new FieldUpdater(this.request, this.field);
		boolean isSomething = manager.openCell();
		
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
