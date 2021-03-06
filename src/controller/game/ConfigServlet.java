package controller.game;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.game.status.GameMaster;

/**
 * コンフィグ設定画面の表示をおこなうサーブレット。
 * @author indeep-xyz
 */
@WebServlet("/Config")
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * GET でのアクセス。
	 * パラメータを渡されても取り扱わずにコンフィグ設定画面を表示する。
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchConfig(request, response);
	}

	/**
	 * POST でのアクセス。
	 * 正しいパラメータを渡されている場合はその情報を元にゲームの状態を生成し、ゲーム画面へと移行する。
	 * パラメータが不適当、またはない場合はコンフィグ設定画面を表示する。
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request);

		if (master.isFieldFormattable()) {
			master.formatField();
			redirectToGame(response);
		}
		else {
			dispatchConfig(request, response);
		}
	}

	/**
	 * コンフィグ設定画面の表示。
	 * 
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
	 * ゲーム画面にリダイレクトする。
	 * 
	 * @param response レスポンス
	 * @throws IOException
	 */
	private void redirectToGame(HttpServletResponse response) throws IOException {
		response.sendRedirect("Game");
	}

}
