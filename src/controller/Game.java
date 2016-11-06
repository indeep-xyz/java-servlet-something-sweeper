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
import model.FieldFormatter;

/**
 * ゲーム画面
 * @author indeep-xyz
 */
@WebServlet("/Game")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
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
	 * ゲームの状態によってセルの開放と表示の処理をおこなう。
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
	 * ゲーム画面の表示
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Game.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム失敗画面の表示
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchGameFailed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GameFailed.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム成功画面の表示
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchGameSucceeded(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GameSucceeded.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム画面へのリダイレクト
	 * @param response レスポンス
	 * @throws IOException
	 */
	private void redirectToConfig(HttpServletResponse response) throws IOException {
		response.sendRedirect("Config");
	}
}

