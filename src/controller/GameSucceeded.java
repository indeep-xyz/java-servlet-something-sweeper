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
import model.field.FieldCreator;

/**
 * ゲーム失敗時の画面の表示をおこなうサーブレット。
 * @author indeep-xyz
 */
@WebServlet("/GameSucceeded")
public class GameSucceeded extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GET によるアクセス。
	 * 読み込み可能なゲーム状態があれば、進行不可能にして失敗画面を表示する。
	 * なければコンフィグ設定画面へ移動する。
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request);
		
		if (master.loadGameData()) {
			master.endGame();
			dispatchGameFailed(request, response);
		}
		else {
			redirectToConfig(response);
		}
	}

	/**
	 * ゲーム失敗画面を表示する。
	 * 
	 * @param master 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchGameFailed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GameSucceeded.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * コンフィグ設定画面へのリダイレクト。
	 * 
	 * @param response レスポンス
	 * @throws IOException
	 */
	private void redirectToConfig(HttpServletResponse response) throws IOException {
		response.sendRedirect("Config");
	}
}

