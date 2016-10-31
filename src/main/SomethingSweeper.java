package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.GameMaster;
import controller.FieldCreator;

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
		
		if (master.tryFormatting()) {
			master.runGame();
			return;
		}
		
		master.runIntroduction();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameMaster master = new GameMaster(request, response);
		master.loadGameData();
		
		if (master.openCell()){
			master.runGameFailed();
		}
		else {
			if (master.checkUnknownSomething()) {
				master.runGame();
			}
			else {
				master.runGameSucceeded();
			}
		}
	}

}
