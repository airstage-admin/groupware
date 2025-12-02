package com.groupware.output.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.constant.CommonConstants;
import com.groupware.dao.AttendanceDao;
import com.groupware.dao.UserDao;
import com.groupware.dto.AttendanceDto;
import com.groupware.dto.UserDto;

@Service
public class OutputServiceImp implements OutputService {

    private static final Logger logger = LoggerFactory.getLogger(OutputServiceImp.class);

    @Autowired
    private AttendanceDao attendanceDao;
    
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, String> getYearMonthList() {
        Map<String, String> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        // 前月から過去12ヶ月
        YearMonth start = current.minusMonths(1);
        for (int i = 0; i < 12; i++) {
            YearMonth target = start.minusMonths(i);
            map.put(target.toString(), target.getYear() + "年" + target.getMonthValue() + "月");
        }
        return map;
    }

    @Override
    public byte[] validateAttendance(YearMonth ym) {
        // 1. 全データ取得
        List<AttendanceDto> allData = attendanceDao.findAllByMonth(ym);
        if (allData == null || allData.isEmpty()) {
            return null; // データなし＝エラーなし
        }

        List<String[]> errorRows = new ArrayList<>();
        errorRows.add(new String[]{"社員番号", "氏名", "日付", "エラー内容"}); // ヘッダー

        // ユーザーごとにデータを分割
        Map<Long, List<AttendanceDto>> byUser = allData.stream()
                .collect(Collectors.groupingBy(AttendanceDto::getUserId));

        byUser.forEach((userId, userAtts) -> {
            // 日付ごとにグルーピング
            Map<String, List<AttendanceDto>> byDate = userAtts.stream()
                    .collect(Collectors.groupingBy(dto -> dto.getWorkingDay().toString()));

            byDate.forEach((dateStr, list) -> {
                if (list.size() > 1) {
                    // 1. 勤怠時刻重複チェック (簡易実装: 複数の勤務先で時刻入力がある場合)
                    long timeEntryCount = list.stream()
                        .filter(a -> a.getClockIn() != null && !CommonConstants.INIT_TIME.equals(a.getClockIn()) 
                                  && a.getClockOut() != null && !CommonConstants.INIT_TIME.equals(a.getClockOut()))
                        .count();
                    
                    if (timeEntryCount > 1) {
                         UserDto user = userDao.findByUser((int)(long)userId);
                         String empNo = user != null ? user.getEmployeeNo() : String.valueOf(userId);
                         String name = user != null ? user.getUsername() : "";
                         errorRows.add(new String[]{empNo, name, dateStr, "勤怠時刻重複"});
                    }

                    // 2. 勤怠/休暇区分重複チェック (0:なし 以外が複数ある場合)
                    long vacationCount = list.stream()
                        .filter(a -> a.getVacationCategory() != 0) 
                        .count();
                    if (vacationCount > 1) {
                        UserDto user = userDao.findByUser((int)(long)userId);
                        String empNo = user != null ? user.getEmployeeNo() : String.valueOf(userId);
                        String name = user != null ? user.getUsername() : "";
                        errorRows.add(new String[]{empNo, name, dateStr, "勤怠/休暇区分重複"});
                    }
                }
            });
        });

        // エラー行がヘッダーのみならnullを返す（＝OK）
        if (errorRows.size() <= 1) {
            return null;
        }

        // エラーがあればExcel生成
        return createExcel("勤怠確認エラー", errorRows);
    }

    @Override
    public byte[] generateReport(String item, YearMonth ym) {
        // ダミーデータ生成（実際はここでDBからデータを取得して整形する）
        List<String[]> dataRows = new ArrayList<>();
        dataRows.add(new String[]{"帳票名", "対象年月", "備考"}); // ヘッダー
        dataRows.add(new String[]{item, ym.toString(), "出力テストデータです"});

        return createExcel(item, dataRows);
    }

    // Excel生成の共通メソッド
    private byte[] createExcel(String sheetName, List<String[]> rows) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet(sheetName);
            int rowIdx = 0;
            for (String[] rowData : rows) {
                Row row = sheet.createRow(rowIdx++);
                int colIdx = 0;
                for (String val : rowData) {
                    Cell cell = row.createCell(colIdx++);
                    cell.setCellValue(val);
                }
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            logger.error("Excel出力エラー", e);
            return null;
        }
    }
}