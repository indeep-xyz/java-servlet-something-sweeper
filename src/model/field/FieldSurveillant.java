package model.field;

import model.cell.Cell;

/**
 * セル周辺の情報を調査するクラス
 * @author indeep-xyz
 *
 */
public class FieldSurveillant {

	/**
	 * 表データ
	 */
	private Field field;
	
	/**
	 * コンストラクタ
	 * @param field 表データ
	 */
	public FieldSurveillant(Field field) {
		this.field = field;
	}

	/**
	 * セルの周りにあるセルを配列にして返す
	 * 
	 */
	public void surveyAll() {
		Cell[] cells = this.field.getCells();
		
		for (int i = 0; i < cells.length; i++) {
			survey(i);
		}
	}
	
	/**
	 * セルの周りにある Something セルをもとに、セルの周囲にある Something の数を設定する
	 * @param id セルの ID
	 * 
	 */
	private void survey(int id) {
		Cell[] aroundCells = this.field.getAroundCells(id);
		int somethingCount = 0;
		
		for (int i = 0; i < aroundCells.length; i++) {
			Cell cell = aroundCells[i];
			
			if(cell != null && cell.isSomething()) {
				somethingCount++;
			}
		}
		
		this.field.getCell(id).setAroundSomething(somethingCount);
	}
	
}
