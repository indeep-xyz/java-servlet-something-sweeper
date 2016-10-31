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
	private int width;
	
	/**
	 * 高さ
	 */
	private int height;

	/**
	 * 難易度
	 */
	private int difficulty;

	/**
	 * フォーマットに必要なパラメータが揃っているか否か
	 */
	private boolean isFormattableParameters;

	/**
	 * コンストラクタ
	 * @param request リクエスト用のオブジェクト
	 */
	public FieldCreator(HttpServletRequest request) {
		prepareParameters(request);
	}
	
	/**
	 * 表データがフォーマット可能な状態かを検証する
	 * @return フォーマット可能な状態であれば true
	 */
	public boolean checkFormattable() {
		return isFormattableParameters;
	}

	/**
	 * 表データがフォーマット可能な引数を保持しているかを検証する
	 * @param request 
	 * @return 保持していれば true
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void prepareParameters(HttpServletRequest request) {
		ParameterAgent pAgent = new ParameterAgent(request);
		String[] names = {"width", "height", "difficulty"};
		boolean isFormattableParameters = true;
		
		
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			int n = pAgent.getInt(name, -1);
			
			if (n < 1) {
				isFormattableParameters = false;
				break;
			}
			
			setParameter(name, n);
		}

		this.isFormattableParameters = isFormattableParameters;
		return;
	}
	
	/**
	 * 指定のフィールドに値を記録する
	 * @param name フィールド名
	 * @param n 値
	 */
	private void setParameter(String name, int n) {
		switch(name) {
		case "width":
			this.width = n;
			break;
		case "height":
			this.height = n;
			break;
		case "difficulty":
			this.difficulty = n;
			break;
		}
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
