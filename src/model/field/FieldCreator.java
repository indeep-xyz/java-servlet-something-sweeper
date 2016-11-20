package model.field;

import javax.servlet.http.HttpServletRequest;

import controller.tool.ParameterAgent;
import model.cell.Cell;
import model.cell.CellPot;

/**
 * 領域データの作成・初期化を担当する。
 * @author indeep-xyz
 *
 */
public class FieldCreator {

	/**
	 * 横幅。
	 */
	private Integer width;
	
	/**
	 * 縦幅。
	 */
	private Integer height;

	/**
	 * ゲームの難易度。
	 */
	private Integer difficulty;

	/**
	 * コンストラクタ。
	 * @param request リクエスト
	 */
	public FieldCreator(HttpServletRequest request) {
		initializeByRequestParameter(request);
	}

	/**
	 * リクエストのパラメータにより各フィールド値を初期化する。
	 * @param request リクエスト
	 */
	private void initializeByRequestParameter(HttpServletRequest request) {
		ParameterAgent pAgent = new ParameterAgent(request);

		this.width      = pAgent.getInt("width", null);
		this.height     = pAgent.getInt("height", null);
		this.difficulty = pAgent.getInt("difficulty", null);
	}

	/**
	 * 領域データがフォーマット可能な状態かを返す。
	 * @return フォーマット可能な状態であれば true
	 */
	public boolean isFormattable() {
		return (this.width != null && this.height != null && this.difficulty != null);
	}

	/**
	 * 表データを新規作成する。
	 */
	public Field create() {
		Field field = new Field(this.width, this.height, this.difficulty);
		CellPot pot = createCellPot();
		int index = 0;
		
		while (pot.hasNext()) {
			Cell cell = pot.pickRandomOne();
			field.setCell(index++, cell);
		}
		
		return field;
	}
	
	/**
	 * CellPot インスタンスの作成。
	 * @return CellPot インスタンス
	 */
	private CellPot createCellPot(){
		int cellLength = this.width * this.height;
		int somethingAmount = (int)Math.ceil((double)cellLength / 10 * this.difficulty);
		
		return new CellPot(cellLength, somethingAmount);
	}
}
