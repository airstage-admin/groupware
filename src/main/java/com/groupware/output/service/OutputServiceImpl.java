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
import com.groupware.dto.CellDataStructure;
import com.groupware.dto.InternalPJDto;
import com.groupware.output.form.OutputForm;

@Service
public class OutputServiceImpl implements OutputService {

	//社内PJ
	@Autowired
	private InternalPJDao internalProjectWorkDao;

	@Override
	public String createInternalProjectExcel(OutputForm form) {

		List<CellDataStructure> cellDataList = createCellDataForShainPJ(form);

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

	private List<CellDataStructure> createCellDataForShainPJ(OutputForm form) {
		List<CellDataStructure> list = new ArrayList<>();

		String targetMonth = form.getTargetYearMonth();
		List<InternalPJDto> dbList = internalProjectWorkDao.selectInternalProjectWorkTime(targetMonth);

		String[] headers = OutputConstants.HEADER_INTERNAL_PJ;
		IntStream.range(0, headers.length).forEach(i -> list.add(createHeaderCell(0, i, headers[i])));

		List<InternalPJDto> validList = dbList.stream()
				.filter(Objects::nonNull)
				.toList();

		IntStream.range(0, validList.size()).forEach(i -> {
			InternalPJDto dto = validList.get(i);
			int currentRow = i + 1; // 行番号は index + 1 (1行目はヘッダーのため)

			String timeStr = OutputConstants.VAL_ZERO;
			if (dto.getTotalHours() != null) {
				timeStr = dto.getTotalHours().stripTrailingZeros().toPlainString();
			}

			String userName = (dto.getUsername() != null) ? dto.getUsername() : OutputConstants.VAL_EMPTY;

			list.add(createDataCell(currentRow, 0, userName));
			list.add(createDataCell(currentRow, 1, timeStr));
			list.add(createDataCell(currentRow, 2, OutputConstants.VAL_EMPTY));
			list.add(createDataCell(currentRow, 3, OutputConstants.VAL_ZERO));
			list.add(createDataCell(currentRow, 4, OutputConstants.VAL_ZERO));
		});

		return list;
	}

	//ヘッダーセル
	private CellDataStructure createHeaderCell(int row, int col, String value) {
		return new CellDataStructure(
				row, col, false,
				OutputConstants.FONT_SIZE_DEFAULT,
				OutputConstants.COLOR_TEXT_BLACK,
				OutputConstants.COLOR_BG_HEADER,
				FillPatternType.SOLID_FOREGROUND, value);
	}

	// データセル
	private CellDataStructure createDataCell(int row, int col, String value) {
		return new CellDataStructure(
				row, col, false,
				OutputConstants.FONT_SIZE_DEFAULT,
				OutputConstants.COLOR_TEXT_BLACK,
				OutputConstants.COLOR_BG_DATA,
				FillPatternType.SOLID_FOREGROUND, value);
	}
}