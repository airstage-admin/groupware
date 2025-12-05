package com.groupware.output.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.constant.OutputCategory;
import com.groupware.common.constant.OutputConstants;
import com.groupware.common.util.ExcelOutputUtiles;
import com.groupware.dao.InternalPJDao;
import com.groupware.dao.StudyDao;
import com.groupware.dto.CellDataStructure;
import com.groupware.dto.InternalPJDto;
import com.groupware.dto.StudyDto;
import com.groupware.output.form.OutputForm;

/**
 * OutputServiceImpl
 * @author Tanaka
 * @version 1.0.0
 */
@Service
public class OutputServiceImpl implements OutputService {

	@Autowired
	private InternalPJDao internalProjectWorkDao; //社内PJ
	@Autowired
	private StudyDao StudyDao; //勉強会

	/**
	 * 社内PJのExcel作成処理
	 * 
	 * @param form 出力フォーム
	 * @return 処理結果メッセージ
	 */
	@Override
	public String createInternalProjectExcel(OutputForm form) {

		//DBからデータを取得し、Excel出力用のデータリストを作成
		List<CellDataStructure> cellDataList = createCellDataForInternalPJ(form);

		//ファイル名を生成
		String fileName = form.getTargetYearMonth() + " " + OutputCategory.INTERNAL_PJ.getLabel() + OutputConstants.EXTENSION_XLSX;

		try {
			ExcelOutputUtiles.createAndSaveExcel(cellDataList, fileName);
			// 成功メッセージ
			return OutputCategory.INTERNAL_PJ.getLabel() + OutputConstants.MSG_SUCCESS_CREATE + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			// 失敗メッセージ
			return OutputCategory.INTERNAL_PJ.getLabel() + OutputConstants.MSG_FAIL_CREATE + e.getMessage();
		}
	}

	/**
	 * 社内PJ用のExcelセルデータリスト作成
	 * 
	 * @param form 出力フォーム
	 * @return セルデータリスト
	 */

	private List<CellDataStructure> createCellDataForInternalPJ(OutputForm form) {
		List<CellDataStructure> list = new ArrayList<>();

		//対象年月を取得
		String targetMonth = form.getTargetYearMonth();
		//DBから社内PJのデータを取得
		List<InternalPJDto> dbList = internalProjectWorkDao.selectInternalProjectWorkTime(targetMonth);

		//ヘッダー行を作成してリストに追加
		String[] headers = OutputConstants.HEADER_INTERNAL_PJ;
		IntStream.range(0, headers.length).forEach(i -> list.add(createHeaderCell(0, i, headers[i])));

		List<InternalPJDto> validList = dbList.stream()
				.filter(Objects::nonNull)
				.toList();

		//データ行を作成してリストに追加
		IntStream.range(0, validList.size()).forEach(i -> {
			InternalPJDto dto = validList.get(i);
			int currentRow = i + 1; // 行番号は index + 1 (1行目はヘッダーのため)

			//時間データの整形（末尾のゼロを除去）
			String timeStr = OutputConstants.VAL_ZERO;
			if (dto.getTotalHours() != null) {
				timeStr = dto.getTotalHours().stripTrailingZeros().toPlainString();
			}

			//ユーザー名の取得（nullの場合は空文字）
			String userName = (dto.getUsername() != null) ? dto.getUsername() : OutputConstants.VAL_EMPTY;

			//各セルのデータ作成
			list.add(createDataCell(currentRow, 0, userName));
			list.add(createDataCell(currentRow, 1, timeStr));
			list.add(createDataCell(currentRow, 2, OutputConstants.VAL_EMPTY));
			list.add(createDataCell(currentRow, 3, OutputConstants.VAL_ZERO));
			list.add(createDataCell(currentRow, 4, OutputConstants.VAL_ZERO));
		});

		return list;
	}

	/**
	 * 勉強会のExcel作成処理
	 * 
	 * @param form 出力フォーム
	 * @return 処理結果メッセージ
	 */
	@Override
	public String createStudyExcel(OutputForm form) {

		//DBからデータを取得し、Excel出力用のデータリストを作成
		List<CellDataStructure> cellDataList = createCellDataForStudy(form);

		//ファイル名を生成
		String fileName = form.getTargetYearMonth() + " " + OutputCategory.STUDY.getLabel() + OutputConstants.EXTENSION_XLSX;

		try {
			ExcelOutputUtiles.createAndSaveExcel(cellDataList, fileName);
			// 成功メッセージ
			return OutputCategory.STUDY.getLabel() + OutputConstants.MSG_SUCCESS_CREATE + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			// 失敗メッセージ
			return OutputCategory.STUDY.getLabel() + OutputConstants.MSG_FAIL_CREATE + e.getMessage();
		}
	}

	/**
	 * 勉強会のExcelセルデータリスト作成
	 * 
	 * @param form 出力フォーム
	 * @return セルデータリスト
	 */

	private List<CellDataStructure> createCellDataForStudy(OutputForm form) {
		List<CellDataStructure> list = new ArrayList<>();

		//対象年月を取得
		String targetMonth = form.getTargetYearMonth();
		//DBから社内PJのデータを取得
		List<StudyDto> dbList = StudyDao.selectStudyTime(targetMonth);

		//ヘッダー行を作成してリストに追加
		String[] headers = OutputConstants.HEADER_STUDY;
		IntStream.range(0, headers.length).forEach(i -> list.add(createHeaderCell(0, i, headers[i])));

		List<StudyDto> validList = dbList.stream()
				.filter(Objects::nonNull)
				.toList();

		//データ行を作成してリストに追加
		IntStream.range(0, validList.size()).forEach(i -> {
			StudyDto dto = validList.get(i);
			int currentRow = i + 1; // 行番号は index + 1 (1行目はヘッダーのため)

			//時間データの整形（末尾のゼロを除去）
			String timeStr = OutputConstants.VAL_ZERO;
			if (dto.getTotalHours() != null) {
				timeStr = dto.getTotalHours().stripTrailingZeros().toPlainString();
			}

			//ユーザー名の取得（nullの場合は空文字）
			String userName = (dto.getUsername() != null) ? dto.getUsername() : OutputConstants.VAL_EMPTY;

			//各セルのデータ作成
			list.add(createDataCell(currentRow, 0, userName));
			list.add(createDataCell(currentRow, 1, timeStr));
			list.add(createDataCell(currentRow, 2, OutputConstants.VAL_EMPTY));
			list.add(createDataCell(currentRow, 3, OutputConstants.VAL_ZERO));
			list.add(createDataCell(currentRow, 4, OutputConstants.VAL_ZERO));
		});

		return list;
	}
	/**
	 * ヘッダー項目のセル作成処理
	 * 
	 * @param row 行番号
	 * @param col 列番号
	 * @param value セル値
	 * @return セルデータ
	 */
	private CellDataStructure createHeaderCell(int row, int col, String value) {
		return new CellDataStructure(
				row, col, false,
				OutputConstants.FONT_SIZE_DEFAULT,
				OutputConstants.COLOR_TEXT_BLACK,
				OutputConstants.COLOR_BG_HEADER,
				FillPatternType.SOLID_FOREGROUND, value);
	}

	/**
	 * データ項目のセル作成処理
	 * 
	 * @param row 行番号
	 * @param col 列番号
	 * @param value セル値
	 * @return セルデータ
	 */
	private CellDataStructure createDataCell(int row, int col, String value) {
		return new CellDataStructure(
				row, col, false,
				OutputConstants.FONT_SIZE_DEFAULT,
				OutputConstants.COLOR_TEXT_BLACK,
				OutputConstants.COLOR_BG_DATA,
				FillPatternType.SOLID_FOREGROUND, value);
	}
}