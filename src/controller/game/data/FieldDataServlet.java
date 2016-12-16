package controller.game.data;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.game.GameMaster;
import controller.tool.ParameterAgent;
import model.field.FieldCreator;

/**
 * ゲームのフィールド情報の出力を行うサーブレット。
 * @author indeep-xyz
 */
@WebServlet("/FieldData")
public class FieldDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GET によるアクセス。
	 * 読み込み可能なゲーム状態があればそのフィールド情報を出力する。
	 * なければ何も出力しない。
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request);
		
		if (master.loadGameData()) {
			dispatchJsonInGame(master, request, response);
		}
	}

	/**
	 * POST によるアクセス。
	 * 読み込み可能なゲーム状態があれば受け取った値と状況によって処理とフィールド情報の出力を行う。
	 * なければ何も出力しない。
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request);

		if (master.loadGameData()) {
			runGame(master, request, response);
		}
	}

	/**
	 * ゲームの状態によって処理を行い、フィールド情報の出力を行う。
	 * 
	 * @param master ゲームの状態
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	private void runGame(GameMaster master, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParameterAgent paramGetter = new ParameterAgent(request);
		int id = paramGetter.getInt("clicked", -1);
		
		master.openCell(id);
		dispatchJsonInGame(master, request, response);
	}
	
	/**
	 * フィールド情報の出力を行う。
	 * 
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchJsonInGame(GameMaster master, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/parameter/FieldInGame.jsp");
		dispatcher.forward(request, response);
	}
}

