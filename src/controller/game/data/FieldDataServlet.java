package controller.game.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.game.GameMaster;
import controller.tool.ParameterAgent;
import model.field.Field;
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
		String resultMode = request.getParameter("result");
		GameMaster master = new GameMaster(request);

		if (master.loadGameData()) {
			if (resultMode != null
					&& master.isGameEnd()
					){
				printJsonResult(master, response);
			}
			else {
				printJsonInGame(master, response);
			}
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
		printJsonInGame(master, response);
	}
	
	/**
	 * 設定済みの PrintWriter インスタンスを作成して返す。 
	 * 
	 * @param response レスポンス
	 * @return 設定済みの PrintWriter インスタンス
	 * @throws IOException
	 */
	private PrintWriter createPrintWriter(HttpServletResponse response) throws IOException {
	    response.setContentType("text/html; charset=UTF-8");
	    return response.getWriter();
	}
	
	/**
	 * 進行中のゲームのフィールド情報の出力を行う。
	 * 
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws IOException
	 */
	private void printJsonInGame(GameMaster master, HttpServletResponse response) throws IOException {
	    PrintWriter writer = createPrintWriter(response);
	    String fieldJson = master.getFieldJsonInGame();
	    
	    writer.println(fieldJson);
	}

	/**
	 * 結果用の JSON を出力する。
	 * 
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws IOException 
	 */
	private void printJsonResult(GameMaster master, HttpServletResponse response) throws IOException {
	    PrintWriter writer = createPrintWriter(response);
	    String fieldJson = master.getFieldJsonResult();
	    
	    writer.println(fieldJson);
	}
}

