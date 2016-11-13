package model.field;

import javax.servlet.http.HttpServletRequest;

import controller.tool.ParameterAgent;

/**
 * 領域データの初期化や作成を担当する。
 * @author indeep-xyz
 *
 */
class FieldFormatter {

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
	public FieldFormatter(HttpServletRequest request) {
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
		return createNiceField();
	}
	
	/**
	 * Something をある程度保持する領域データを作成する。
	 * @return 領域データ
	 */
	public Field createNiceField() {
		Field field = null;
		int limit = 20;
		
		while (limit-- > 0) {
			field = new Field(this.width, this.height, this.difficulty);
			
			if (field.countSomethingUnknownCells() > this.difficulty + 1) {
				break;
			}
		}
		
		return field;
	}
}
