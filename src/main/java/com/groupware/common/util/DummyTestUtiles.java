package com.groupware.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.groupware.dto.CellDataStructure;

/**
* DummyTestUtiles
* テスト用ユーティリティ（不要。確認後削除）
* @author　N.Hirai
* @version　1.0.0
*/
public final class DummyTestUtiles {
	private DummyTestUtiles() {
		throw new AssertionError("Constant class should not be instantiated.");
	}
	
	public static void testExcelOutput() {
		// 出力データ構造体リスト
        List<CellDataStructure> dataList = new ArrayList<>();

        // A1セル: "レポートタイトル" (太字、サイズ20、赤文字、黄色背景)
        dataList.add(new CellDataStructure(
            0, 0, true, 20, IndexedColors.RED.getIndex(), IndexedColors.YELLOW.getIndex(), FillPatternType.SOLID_FOREGROUND, "レポートタイトル"
        ));

        // A3セル: "データ値" (標準)
        dataList.add(new CellDataStructure(
            2, 0, false, 11, IndexedColors.BLACK.getIndex(), IndexedColors.WHITE.getIndex(), FillPatternType.NO_FILL, "データ値"
        ));

        // B3セル: "12345" (標準)
        dataList.add(new CellDataStructure(
            2, 1, false, 11, IndexedColors.BLACK.getIndex(), IndexedColors.WHITE.getIndex(), FillPatternType.NO_FILL, "12345"
        ));

        // 2. Excelファイルの出力実行
        String fileName = "test_dummy_" + System.currentTimeMillis() + ".xlsx";

        try {
        	ExcelOutputUtiles.createAndSaveExcel(dataList, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Excelファイルの作成に失敗しました。");
        }
	}
}
