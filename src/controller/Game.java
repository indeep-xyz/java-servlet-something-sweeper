package controller;

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
import model.field.FieldFormatter;

/**
 * ゲーム画面の表示をおこなうサーブレット。
 * @author indeep-xyz
 */
@WebServlet("/Game")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GET によるアクセス。
	 * 読み込み可能なゲーム状態があればそれを表示し、なければコンフィグ画面へ移動する。
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request);
		
		if (master.loadGameData()) {
			dispatchGame(request, response);
		}
		else {
			redirectToConfig(response);
		}
	}

	/**
	 * POST によるアクセス。
	 * 読み込み可能なゲーム状態があれば受け取った値と状況によって処理と画面表示をおこなう。
	 * なければコンフィグ画面へ移動する。
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request);

		if (master.loadGameData()) {
			runGame(master, request, response);
		}
		else {
			redirectToConfig(response);
		}
	}

	/**
	 * ゲームの状態によって処理を行い、セルの開放と表示の処理をおこなう。
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
		
		if (master.isFailed(id)){
			dispatchGameFailed(request, response);
		}
		else if (master.isCompleted()) {
			dispatchGameSucceeded(request, response);
		}
		else {
			dispatchGame(request, response);
		}
	}
	
	/**
	 * ゲーム画面を表示する。
	 * 
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Game.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム失敗画面を表示する。
	 * 
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchGameFailed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GameFailed.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム成功画面を表示する。
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchGameSucceeded(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GameSucceeded.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * コンフィグ画面へのリダイレクト。
	 * 
	 * @param response レスポンス
	 * @throws IOException
	 */
	private void redirectToConfig(HttpServletResponse response) throws IOException {
		response.sendRedirect("Config");
	}
}

