package com.groupware.output.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.util.ExcelOutputUtiles;
import com.groupware.dao.InternalPJDao;
import com.groupware.dto.CellDataStructure;
import com.groupware.dto.InternalPJDto;
import com.groupware.output.form.OutputForm;

@Service
public class OutputServiceImpl implements OutputService {
    
  //DAOの宣言
	//社内PJ
    @Autowired
    private InternalPJDao internalProjectWorkDao;

    @Override
    public String createExcelOutput(OutputForm form) {
        // 分岐処理
        switch (form.getOutputItem()) {
            case "勤怠管理":
            case "有給管理簿":
            case "社内PJ":
            case "勉強会":
            case "勤怠確認":
                return createExcel(form);
            default:
                return "エラー：無効な出力項目が指定されました。";
        }
    }

    private String createExcel(OutputForm form) {
        //DBからデータを取得し、CellDataStructureリストに変換
        List<CellDataStructure> cellDataList;
        
        switch (form.getOutputItem()) {
        case "社内PJ":
            cellDataList = createCellDataForShainPJ(form);
            break;
            
        default:
            cellDataList = new ArrayList<>();
            break;
    }
        

        String fileName = form.getTargetYearMonth() + " " + form.getOutputItem()  + ".xlsx";

        try {
            ExcelOutputUtiles.createAndSaveExcel(cellDataList, fileName);
            return form.getOutputItem() + "のExcelを正常に作成しました: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return form.getOutputItem() + "のExcel作成に失敗しました: " + e.getMessage();
        }
    }

    
    //社内PJ
    private List<CellDataStructure> createCellDataForShainPJ(OutputForm form) {
        List<CellDataStructure> list = new ArrayList<>();

        String targetMonth = form.getTargetYearMonth(); // "2023-11" など
        List<InternalPJDto> dbList = internalProjectWorkDao.selectInternalProjectWorkTime(targetMonth);

        //ヘッダー（１行目）
        String[] headers = {"氏名", "社内PJ対応時間", "残業手当", "金額", "繰り上げ"};
        for (int i = 0; i < headers.length; i++) {
            list.add(createHeaderCell(0, i, headers[i]));
        }

        //データ（２行目以降）
        int rowIndex = 1;
        for (InternalPJDto dto : dbList) {
          
            if (dto == null) {
                continue;
            }

            String timeStr = "0";
            if (dto.getTotalHours() != null) {
                timeStr = dto.getTotalHours().stripTrailingZeros().toPlainString();
            }
            
            String userName = (dto.getUsername() != null) ? dto.getUsername() : "";

            list.add(createDataCell(rowIndex, 0, userName));
            list.add(createDataCell(rowIndex, 1, timeStr));
            list.add(createDataCell(rowIndex, 2, ""));
            list.add(createDataCell(rowIndex, 3, "0"));
            list.add(createDataCell(rowIndex, 4, "0"));

            rowIndex++;
        }
        
        return list;
    }

    // ヘッダー（１行目）
    private CellDataStructure createHeaderCell(int row, int col, String value) {
        return new CellDataStructure(
            row, col, 
            false, // 太字なし
            10,   // サイズ
            IndexedColors.BLACK.getIndex(),     // 文字色：黒
            IndexedColors.SEA_GREEN.getIndex(), // 背景色：緑
            FillPatternType.SOLID_FOREGROUND, 
            value
        );
    }

    // データ（２行目以降）
    private CellDataStructure createDataCell(int row, int col, String value) {
        return new CellDataStructure(
            row, col, 
            false, // 太字なし
            10,    // サイズ
            IndexedColors.BLACK.getIndex(),        // 文字色：黒
            IndexedColors.LIGHT_YELLOW.getIndex(), // 背景色：薄黄色
            FillPatternType.SOLID_FOREGROUND, 
            value
        );
    }
}