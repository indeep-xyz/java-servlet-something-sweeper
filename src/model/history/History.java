package model.history;

import java.io.Serializable;
import java.util.ArrayList;

import model.field.Field;

/**
 * 進行中のゲームの履歴を管理するクラス。
 * @author indeep-xyz
 *
 */
public class History
		implements Serializable
		{
	
	/**
	 * シリアルバージョン ID 。
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 一連の履歴。
	 * 新しいものは後ろに追加されていく。
	 */
	private ArrayList<HistoryRecord> records;

	/**
	 * コンストラクタ。
	 * @param openedId 開いたセルの ID の一覧
	 */
	public History() {
		this.records = new ArrayList<HistoryRecord>();
	}

	/**
	 * 一連の履歴を返す。 JavaBeans 用。
	 * @return 一連の履歴
	 */
	public ArrayList<HistoryRecord> getRecords() {
		return records;
	}

	/**
	 * 一連の履歴を設定する。 JavaBeans 用。
	 * @param records 一連の履歴
	 */
	public void setRecords(ArrayList<HistoryRecord> records) {
		this.records = records;
	}

	/**
	 * 開放したセル ID の一覧を登録する。
	 * @param openedId 開放したセル のID 
	 */
	public void add(ArrayList<Integer> openedId) {
		HistoryRecord record = new HistoryRecord(openedId);
		this.records.add(record);
	}

	/**
	 * 情報を JSON 化して返す。
	 * 
	 * @param field 戻り値の JSON にセルのデータを付けるためのインスタンス
	 * @param startRecordIndex 履歴の開始インデックス
	 * @return インスタンス全体の情報をもつ JSON
	 */
	public String toJson(Field field, int startRecordIndex) {
		StringBuilder jsonSource = new StringBuilder("{");

		jsonSource.append(convertRecordsToJson(field, startRecordIndex));
		jsonSource.append("}");
		
		return jsonSource.toString();
	}

	/**
	 * 範囲内のレコードの情報を JSON 化して返す。
	 * @param field 戻り値の JSON にセルのデータを付けるためのインスタンス
	 * @param startRecordIndex 履歴の開始インデックス
	 * @return レコードの JSON
	 */
	private String convertRecordsToJson(Field field, int startRecordIndex) {
		StringBuilder jsonSource = new StringBuilder();

		jsonSource.append("\"records\":[");
		
		for (int i = startRecordIndex; i < this.records.size(); i++) {
			HistoryRecord record = this.records.get(i);
			
			if (0 < i) {
				jsonSource.append(",");
			}

			jsonSource.append(
					convertRecordToJson(record, field, i));
		}
		
		jsonSource.append("]");
		
		return jsonSource.toString();
	}

	/**
	 * 単一レコードの情報を JSON 化して返す。
	 * @param record レコード
	 * @param field 戻り値の JSON にセルのデータを付けるためのインスタンス
	 * @param orderNumber レコードの順番
	 * @return レコードの JSON
	 */
	private String convertRecordToJson(HistoryRecord record, Field field, int orderNumber) {
		StringBuilder jsonSource = new StringBuilder();

		jsonSource.append("{");
		jsonSource.append("\"orderNumber\":" + orderNumber);
		jsonSource.append(",\"record\":" + record.toJson(field));
		jsonSource.append("}");
		
		return jsonSource.toString();
	}
}
