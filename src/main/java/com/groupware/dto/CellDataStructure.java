package com.groupware.dto;

import org.apache.poi.ss.usermodel.FillPatternType;

/**
* CellDataStructure
* Excelセル出力構造体
* @author　N.Hirai
* @version　1.0.0
*/

public class CellDataStructure {
	private int row; // 行
	private int col; // カラム
	private boolean bold; // 太字
	private int size; // 文字サイズ
	private short color; // 文字カラー
	private short solid; // セルの背景の塗りつぶしカラー
	private FillPatternType pattern; // セルの背景の塗りつぶしパターン
	private String value; // 出力データ

	/**
	* Excelセル出力構造体へ値セット
	* 
	* @param　row 行
	* @param　col カラム
	* @param　bold 太字
	* @param　size 文字サイズ
	* @param　color 文字カラー
	* @param　solid セルの背景の塗りつぶしカラー
	* @param　pattern セルの背景の塗りつぶしパターン
	* @param　value 出力データ
	* @return
	*/
	public CellDataStructure(int row, int col, boolean bold, int size, short color, short solid, FillPatternType pattern, String value) {
		this.row = row;
		this.col = col;
		this.bold = bold;
		this.size = size;
		this.color = color;
		this.solid = solid;
		this.pattern = pattern;
		this.value = value;
	}

	// 行の取得
	public int getRow() {
		return row;
	}

	// カラムの取得
	public int getCol() {
		return col;
	}

	// 太字の取得
	public boolean isBold() {
		return bold;
	}

	// 文字サイズの取得
	public int getSize() {
		return size;
	}

	// 文字カラーの取得
	public short getColor() {
		return color;
	}

	// セルの背景の塗りつぶしカラーの取得
	public short getSolid() {
		return solid;
	}

	// セルの背景の塗りつぶしパターンの取得
	public FillPatternType getPattern() {
		return pattern;
	}
	
	// 出力データの取得
	public String getValue() {
		return value;
	}
}
