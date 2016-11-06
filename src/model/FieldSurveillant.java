package model;

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
		Cell[] aroundCells = getAroundCells(id);
		int somethingCount = 0;
		
		for (int i = 0; i < aroundCells.length; i++) {
			Cell cell = aroundCells[i];
			
			if(cell != null && cell.isSomething()) {
				somethingCount++;
			}
		}
		
		this.field.getCell(id).setAroundSomething(somethingCount);
	}
	
	/**
	 * 指定セル ID の周りにあるセルを配列にして返す
	 * 配列は null を含む可能性がある
	 * @param id セルの ID
	 * @return 周りにあるセル
	 */
	public Cell[] getAroundCells(int id) {
		Cell[] cells = new Cell[8];
		int[] idArray = getAroundCellIds(id);

		for (int i = 0; i < idArray.length; i++) {
			cells[i] = this.field.getCell(idArray[i]);
		}
		
		return cells;
	}

	/**
	 * 指定セル ID の周りにあるセルの ID を配列にして返す
	 * 配列は null を含む可能性がある
	 * @param id セルの ID
	 * @return 周りにあるセルの ID
	 * 
	 */
	public int[] getAroundCellIds(int id) {
		Field t = this.field;
		int[] coord = t.getCellCoordinates(id);
		int[] idArray = new int[] {
				// 左上、上、右上
				t.getCellId(coord[0] - 1, coord[1] - 1),
				t.getCellId(coord[0]    , coord[1] - 1),
				t.getCellId(coord[0] + 1, coord[1] - 1),

				// 左、右
				t.getCellId(coord[0] - 1, coord[1]),
				t.getCellId(coord[0] + 1, coord[1]),

				// 左下、下、右下
				t.getCellId(coord[0] - 1, coord[1] + 1),
				t.getCellId(coord[0]    , coord[1] + 1),
				t.getCellId(coord[0] + 1, coord[1] + 1)
		};
		
		return idArray;
	}
}
