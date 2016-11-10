package model.field;

import java.io.Serializable;

import model.cell.Cell;
import model.cell.CellCreator;

/**
 * ゲーム用の領域を表現・保持するクラス。
 * @author indeep-xyz
 *
 */
public class Field
		implements Serializable {
	
	/**
	 * 横幅。
	 */
	private int width;

	/**
	 * 縦幅。
	 */
	private int height;

	/**
	 * ゲームの難易度。
	 */
	private int difficulty;
	
	/**
	 * 領域内の各セル。
	 */
	private Cell[] cells;
	
	/**
	 * 引数なしコンストラクタ
	 */
	public Field(){
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param width 横幅
	 * @param height 縦幅
	 * @param difficulty 難易度
	 */
	public Field(int width, int height, int difficulty){
		this.setWidth(width);
		this.setHeight(height);
		this.setDifficulty(difficulty);
		initTable();
	}

	/**
	 * 領域データを返す。
	 * シリアライズ用。
	 * 
	 * @return 領域データ
	 */
	public Cell[] getCells() {
		return cells;
	}

	/**
	 * 領域データをセットする。
	 * シリアライズ用。
	 * 
	 * @param field 領域データ
	 */
	public void setTable(Cell[] field) {
		this.cells = field;
	}

	/**
	 * 縦幅を返す。
	 * 
	 * @return 縦幅
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 縦幅を設定する。
	 * 
	 * @param height 縦幅
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 横幅を返す。
	 * 
	 * @return 横幅
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 横幅を設定する。
	 * 
	 * @param width 横幅
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * ゲームの難易度を返す。
	 * 
	 * @return ゲームの難易度
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * ゲームの難易度を設定する。
	 * 
	 * @param difficulty ゲームの難易度
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * 領域データの初期化。
	 */
	private void initTable() {
		int size = this.width * this.height;
		Cell[] cells = new Cell[size];
		CellCreator cellCreator = new CellCreator(this.difficulty);
				
		for (int i = 0; i < size; i++) {
			cells[i] = cellCreator.pickRandom();
		}
		 
		this.cells = cells;
	}

	/**
	 * 指定座標のセルを返す。
	 * 
	 * @param x セルの x 座標
	 * @param y セルの y 座標
	 * @return セル
	 */
	public Cell getCell(int x, int y) {
		if (x < 0 || this.width <= x || y < 0 || this.height <= y){
			return null;
		}
		
		return this.cells[x + y * this.width];
	}
	
	/**
	 * 指定 ID のセルを返す。
	 * 
	 * @param id セルの ID
	 * @return セル
	 */
	public Cell getCell(int id) {
		if (id < 0 || this.cells.length <= id){
			return null;
		}
		
		return this.cells[id];
	}
	
	/**
	 * 指定 ID のセルの座標を返す。
	 * 
	 * @param id セルの ID
	 * @return セルが存在する場合は座標 ([0]: x, [1] : y) を、存在しない場合は null
	 */
	public int[] getCellCoordinates(int id) {
		if (getCell(id) != null) {
			return new int[]{id % this.width, id / this.width};
		}
		
		return null;
	}

	/**
	 * 指定座標のセルの ID を返す。
	 * 
	 * @param x 座標
	 * @param y 座標
	 * @return セルの ID。範囲外の場合は -1
	 */
	public int getCellId(int x, int y) {
		if (x < 0 || this.width <= x || y < 0 || this.height <= y){
			return -1;
		}
		
		return x + y * this.getWidth();
	}

	/**
	 * 未知の Something セルの個数を数えて返す。
	 * 
	 * @return 未知の Something セルの個数
	 */
	public int countSomethingUnknownCells() {
		int count = 0;
		
		for (int i = 0; i < this.cells.length; i++) {
			Cell cell = this.cells[i];
			
			if (!(cell.isOpen()) && cell.isSomething()){
				count++;
			}
		}

		return count;
	}

	/**
	 * 未知の安全なセルの個数を数えて返す。
	 * 
	 * @return 未知の非 Something セルの個数
	 */
	public int countUnknownSafetyCells() {
		return countUnknownCell() - countSomethingUnknownCells();
	}

	/**
	 * 未知のセルの個数を数えて返す。
	 * 
	 * @return 未知のセルの個数
	 */
	public int countUnknownCell() {
		int count = 0;
		
		for (int i = 0; i < this.cells.length; i++) {
			Cell cell = this.cells[i];
			
			if (!cell.isOpen()){
				count++;
			}
		}
		
		return count;
	}

	/**
	 * 指定 ID のセルが Something か否かを返す。
	 * 
	 * @param id セルの ID
	 * @return セルが Something なら true
	 */
	public boolean isSomething(int id){
		Cell cell = getCell(id);
		
		if (cell != null
				&& cell.isSomething()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 指定 ID のセルを開放状態にする。
	 * @param id セルの ID
	 */
	public void openCell(int id) {
		Cell cell = getCell(id);
		
		if (cell != null) {
			cell.open();
			
			if (cell.getAroundSomething() < 1) {
				openAroundCells(id);
			}
		}
	}
	
	/**
	 * 指定セル ID の周りにあるセルを開放状態にする。
	 * 
	 * @param centerId 中心となるセルの ID
	 */
	private void openAroundCells(int centerId) {
		int[] aroundIdArray = getAroundCellIds(centerId);
		
		for (int i = 0; i < aroundIdArray.length; i++) {
			int aroundId = aroundIdArray[i];
			Cell cell = getCell(aroundId);
			
			if (cell != null
					&& !(cell.isOpen())) {
				openCell(aroundId);
			}
		}
	}

	/**
	 * 指定セル ID の周りにあるセルを配列にして返す。
	 * セルのない位置には null が入る。
	 * 
	 * @param centerId 中心となるセルの ID
	 * @return 周りにあるセル
	 */
	public Cell[] getAroundCells(int centerId) {
		Cell[] aroundCellArray = new Cell[8];
		int[] aroundIdArray = getAroundCellIds(centerId);

		for (int i = 0; i < aroundIdArray.length; i++) {
			aroundCellArray[i] = getCell(aroundIdArray[i]);
		}
		
		return aroundCellArray;
	}

	/**
	 * 指定セル ID の周りにあるセルの ID を配列にして返す。
	 * セルのない位置には -1 が入る。
	 * 
	 * @param centerId 中心となるセルの ID
	 * @return 周りにあるセルの ID
	 * 
	 */
	private int[] getAroundCellIds(int centerId) {
		int[] coord =  getCellCoordinates(centerId);
		return new int[] {
				// 左上、上、右上
				getCellId(coord[0] - 1, coord[1] - 1),
				getCellId(coord[0]    , coord[1] - 1),
				getCellId(coord[0] + 1, coord[1] - 1),

				// 左、右
				getCellId(coord[0] - 1, coord[1]),
				getCellId(coord[0] + 1, coord[1]),

				// 左下、下、右下
				getCellId(coord[0] - 1, coord[1] + 1),
				getCellId(coord[0]    , coord[1] + 1),
				getCellId(coord[0] + 1, coord[1] + 1)
		};
	}
}
