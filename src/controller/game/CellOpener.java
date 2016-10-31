package controller.game;

import model.Cell;
import model.CellSurveillant;
import model.Field;

public class CellOpener {

	/**
	 * 表データ。ロード済みのものを使う
	 */
	private Field field;
	
	/**
	 * コンストラクタ
	 * @param request リクエスト
	 * @param table 表データ
	 */
	public CellOpener(Field table) {
		this.field = table;
	}
	
	/**
	 * テーブルのセルを開放状態にする
	 * @return セルが Something の場合は true
	 */
	public boolean openCell(int id) {
		Cell cell = field.getCell(id);
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
