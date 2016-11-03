package controller.game;

import javax.servlet.http.HttpServletRequest;

import controller.tool.ParameterAgent;
import model.CellSurveillant;
import model.Field;

/**
 * 表データの初期化や作成を担当する
 * @author indeep-xyz
 *
 */
public class FieldCreator {

	/**
	 * 横幅
	 */
	private Integer width;
	
	/**
	 * 高さ
	 */
	private Integer height;

	/**
	 * 難易度
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
	 * 表データがフォーマット可能な状態かを返す。
	 * @return フォーマット可能な状態であれば true
	 */
	public boolean isFormattable() {
		return (this.width != null && this.height != null && this.difficulty != null);
	}

	/**
	 * 表データを新規作成する
	 */
	public Field create() {
		Field field = createNiceTable();
		CellSurveillant surveillant = new CellSurveillant(field);
		surveillant.surveyAll();
		
		return field;
	}
	
	/**
	 * Something がある程度あるテーブルを作成する
	 * @return 表データ
	 */
	public Field createNiceTable() {
		Field field = null;
		int limit = 20;
		
		while (limit-- > 0) {
			field = new Field(this.width, this.height, this.difficulty);
			
			if (field.countUnknownSomethingCell() > this.difficulty + 1) {
				break;
			}
		}
		
		return field;
	}

}
