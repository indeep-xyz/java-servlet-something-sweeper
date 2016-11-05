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
 * Servlet implementation class SomethingSweeper
 */
@WebServlet("/SomethingSweeper")
public class SomethingSweeper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request, response);
		
		if (master.isFieldFormattable()) {
			master.formatField();
			runGame(master, request, response);
			return;
		}
		
		runIntroduction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request, response);
		master.loadGameData();
		
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
	 * 初期画面の表示
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	public void runIntroduction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Introduction.jsp");
		dispatcher.forward(request, response);
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
}

