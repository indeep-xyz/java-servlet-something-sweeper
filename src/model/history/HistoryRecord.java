package model.history;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 進行中のゲームの履歴の一部を管理するクラス。
 * @author indeep-xyz
 *
 */
public class HistoryRecord
		implements Serializable
		{
	
	/**
	 * シリアルバージョン ID 。
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 開いたセルの ID の一覧。
	 */
	private ArrayList<Integer> openedId;
	
	/**
	 * コンストラクタ。
	 * @param openedId 開いたセルの ID の一覧
	 */
	public HistoryRecord(
			ArrayList<Integer> openedId) {
		setOpenedId(openedId); 
	}

	/**
	 * 開いたセルの ID の一覧を返す。
	 * @return 開いたセルの ID の一覧
	 */
	public ArrayList<Integer> getOpenedId() {
		return openedId;
	}

	/**
	 * 開いたセルの ID の一覧を設定する。
	 * @param openedId 開いたセルの ID の一覧
	 */
	public void setOpenedId(ArrayList<Integer> openedId) {
		if (openedId == null) {
			openedId = new ArrayList<Integer>();
		}
		
		this.openedId = openedId;
	}

	/**
	 * 情報を JSON 化して返す。
	 * @return インスタンス全体の情報をもつ JSON
	 */
	public String toJson() {
		StringBuilder jsonSource = new StringBuilder("{");

		jsonSource.append(createJsonWithOpenedId());

		jsonSource.append("}");
		
		return jsonSource.toString();
	}

	/**
	 * このレコードが扱う開放セルの情報を JSON 化して返す。
	 * @return レコードの JSON
	 */
	private String createJsonWithOpenedId() {
		StringBuilder jsonSource = new StringBuilder();

		jsonSource.append("\"openedId\":[");
		
		for (int i = 0; i < this.openedId.size(); i++) {
			Integer id = this.openedId.get(i);
			
			if (0 < i) {
				jsonSource.append(",");
			}

			jsonSource.append(id);
		}
		
		jsonSource.append("]");
		
		return jsonSource.toString();
	}
}
