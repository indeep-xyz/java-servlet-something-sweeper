package controller.game.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.game.status.GameMaster;
import controller.tool.ParameterAgent;

/**
 * 進行中のゲームの履歴情報の出力を行うサーブレット。
 * @author indeep-xyz
 */
@WebServlet("/HistoryData")
public class HistoryDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GET によるアクセス。
	 * 読み込み可能な履歴情報があればその情報を出力する。
	 * なければ何も出力しない。
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParameterAgent paramGetter = new ParameterAgent(request);
		GameMaster master = new GameMaster(request);
		int startRecordIndex = paramGetter.getInt("start", 0);
		
		if (master.loadGameData()) {
			if (0 < startRecordIndex
					&& master.isGameEnd()
					){
				printJson(master, response, 0);
			}
			else {
				printJson(master, response, startRecordIndex);
			}
		}
	}

	/**
	 * 設定済みの PrintWriter インスタンスを作成して返す。 
	 * 
	 * @param response レスポンス
	 * @return 設定済みの PrintWriter インスタンス
	 * @throws IOException
	 */
	private PrintWriter createPrintWriter(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		return response.getWriter();
	}
	
	/**
	 * 進行中のゲームの開始インデックス以降の履歴情報を JSON 形式で出力する。
	 * 
	 * @param request リクエスト
	 * @param response レスポンス
	 * @param startRecordIndex 履歴情報の開始インデックス
	 * @throws IOException 
	 */
	private void printJson(GameMaster master, HttpServletResponse response, int startRecordIndex) throws IOException {
		PrintWriter writer = createPrintWriter(response);
		String json = master.getHistoryAsJson(startRecordIndex);
		
		writer.println(json);
	}
}

