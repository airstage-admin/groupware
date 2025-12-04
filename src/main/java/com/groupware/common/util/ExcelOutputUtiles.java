package com.groupware.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.groupware.dto.CellDataStructure;
import com.groupware.userflow.controller.UserFlowController;

/**
* ExcelOutputUtiles
* Excel出力ユーティリティ
* @author　N.Hirai
* @version　1.0.0
*/
public final class ExcelOutputUtiles {
	private ExcelOutputUtiles() {
		throw new AssertionError("Constant class should not be instantiated.");
	}

	public static final String HOME = "user.home"; // ユーザーホーム
	public static final String DOWNLOAD = "Downloads"; // ダウンロードフォルダ

	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	 * 指定されたセルデータリストに基づいてExcelファイルを作成
	 *
	 * @param cellDataList 出力するセルのデータとスタイルのリスト
	 * @param fileName 保存するファイル名 (例: "report.xlsx")
	 * @throws IOException ファイル操作に失敗した場合
	 */
	public static void createAndSaveExcel(List<CellDataStructure> cellDataList, String fileName) throws IOException {
		// ワークブックの作成
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");

		// セルデータの処理と書き込み
		CellStyle style = null;
		for (CellDataStructure data : cellDataList) {
			// 指定された行が存在しなければ作成
			Row row = sheet.getRow(data.getRow());
			if (row == null) {
				row = sheet.createRow(data.getRow());
			}

			// セルを作成
			Cell cell = row.createCell(data.getCol());
			cell.setCellValue(data.getValue());

			// スタイルの適用
			style = createCellStyle(workbook, data);
			cell.setCellStyle(style);
		}

		//幅調整
		java.util.stream.IntStream.range(0, 10).forEach(i -> {
			sheet.autoSizeColumn(i);

			int currentWidth = sheet.getColumnWidth(i);
			sheet.setColumnWidth(i, (int) (currentWidth * 2));
		});

		// ファイルパスの決定 (Windowsのダウンロードフォルダ)
		Path downloadPath = getWindowsDownloadFolder();
		File outputFile = downloadPath.resolve(fileName).toFile();

		// ファイルとして保存
		try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
			workbook.write(fileOut);
			System.out.println("Excelファイルが正常に保存されました: " + outputFile.getAbsolutePath());
		} catch (Exception e) {
			logger.error("Excelファイルの出力でエラーになりました。: ", e);
			System.out.println("Excelファイルの出力でエラーになりました。: " + e);
		} finally {
			workbook.close();
		}

	}

	/**
	 * CellDataStructureに基づいてセルスタイルを生成
	 * @param workbook ワークブック
	 * @param data スタイル情報を含むCellDataStructure
	 * @return 生成されたCellStyle
	 */
	private static CellStyle createCellStyle(Workbook workbook, CellDataStructure data) {
		CellStyle style = workbook.createCellStyle();

		// フォントの作成と設定
		Font font = workbook.createFont();
		font.setBold(data.isBold()); // 太字
		font.setFontHeightInPoints((short) data.getSize()); // 文字サイズ
		font.setColor(data.getColor()); // 文字カラー
		style.setFont(font);

		style.setFillForegroundColor(data.getSolid());// セルの背景の塗りつぶしカラー
		style.setFillPattern(data.getPattern()); // セルの背景の塗りつぶしパターン

		return style;
	}

	/**
	 * Windowsのダウンロードフォルダのパスを取得
	 * @return ダウンロードフォルダのPathオブジェクト
	 */
	private static Path getWindowsDownloadFolder() {
		String home = System.getProperty(HOME);
		if (home == null) {
			throw new IllegalStateException("ユーザーディレクトリが見つかりません。");
		}
		return FileSystems.getDefault().getPath(home, DOWNLOAD);
	}

}
