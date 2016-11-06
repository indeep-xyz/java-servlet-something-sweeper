package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.game.GameMaster;

/**
 * コンフィグ画面
 * @author indeep-xyz
 */
@WebServlet("/Config")
public class Config extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchConfig(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request, response);

		if (master.isFieldFormattable()) {
			master.formatField();
			redirectToGame(response);
		}
		else {
			dispatchConfig(request, response);
		}
	}

	/**
	 * コンフィグ画面の表示
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchConfig(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Config.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * ゲーム画面へのリダイレクト
	 * @param response レスポンス
	 * @throws IOException
	 */
	private void redirectToGame(HttpServletResponse response) throws IOException {
		response.sendRedirect("Game");
	}

}
