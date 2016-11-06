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
		GameMaster master = new GameMaster(request, response);
		
		if (!master.loadGameData()) {
			redirectToConfig(response);
			return;
		}
		
		runGame(master, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request, response);

		if (!master.loadGameData()) {
			redirectToConfig(response);
			return;
		}
		
		if (master.openCell()){
			runGameFailed(master, request, response);
		}
		else {
			if (master.hasNext()) {
				runGame(master, request, response);
			}
			else {
				runGameSucceeded(request, response);
			}
		}
	}

	/**
	 * ゲーム画面の表示
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runGame(GameMaster master, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		master.saveGameData();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Game.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム失敗画面の表示
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runGameFailed(GameMaster master, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		master.saveGameData();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GameFailed.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム成功画面の表示
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runGameSucceeded(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

