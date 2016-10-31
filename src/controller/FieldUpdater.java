package controller;

import javax.servlet.http.HttpServletRequest;

import model.Cell;
import model.CellSurveillant;
import model.Field;

public class FieldUpdater
		extends TableManager {

	/**
	 * 表データ。ロード済みのものを使う
	 */
	private Field field;
	
	/**
	 * コンストラクタ
	 * @param request リクエスト
	 * @param table 表データ
	 */
	public FieldUpdater(HttpServletRequest request, Field table) {
		super(request);
		this.field = table;
	}
	
	/**
	 * テーブルのセルを開放状態にする。開放するセルは request.getParameter から得る
	 * @return セルが Something の場合は true
	 */
	public boolean openCell() {
		int id = getIntParameter("clicked", -1);
		return openCell(id);
	}

	
	/**
	 * テーブルのセルを開放状態にする
	 * @return セルが Something の場合は true
	 */
	public boolean openCell(int id) {
		Cell cell = this.field.getCell(id);
		cell.open();
		
		if (cell.getAroundSomething() < 1) {
			openAroundCells(id);
		}
		
		return cell.isSomething();
	}
	
	/**
	 * 指定 ID のセルを開放状態にする
	 * @param id セルの ID
	 */
	public void openAroundCells(int id) {
		CellSurveillant surveillant = new CellSurveillant(this.field);
		int[] idArray = surveillant.getAroundCellIds(id);
		
		for (int i = 0; i < idArray.length; i++) {
			int aroundId = idArray[i];
			Cell cell = this.field.getCell(aroundId);
			
			if (cell != null && !(cell.isOpen())) {
				openCell(aroundId);
			}
		}
	}
}
