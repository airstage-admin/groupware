package com.groupware.output.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.dto.UserDto;
import com.groupware.output.form.OutputForm;
import com.groupware.output.service.OutputService;

@Controller
public class OutputController {

    private static final Logger logger = LoggerFactory.getLogger(OutputController.class);

    @Autowired
    private OutputService outputService;

    // 初期表示
    @GetMapping("/output_management")
    public String outputManagement(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/index";
        }

        // 初期値設定
        OutputForm form = new OutputForm();
        form.setOutputItem("attendance"); // 勤怠管理
        form.setTargetYearMonth(YearMonth.now().minusMonths(1).toString()); // 前月

        setupModel(model, form);
        
        // テンプレートのパス：src/main/resources/templates/output/output.html
        return "/output/output"; 
    }

    // 処理実行
    @PostMapping("/output_submit")
    public Object outputSubmit(
            @ModelAttribute OutputForm form, 
            @RequestParam(name="action") String action, 
            Model model, 
            HttpSession session) {
        
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/index";

        YearMonth ym = YearMonth.parse(form.getTargetYearMonth());

        // 1. キャンセル
        if ("cancel".equals(action)) {
            return "redirect:/attendance_employee_list";
        }

        // 2. 勤怠確認
        if ("check".equals(action)) {
            byte[] errorExcel = outputService.validateAttendance(ym);

            if (errorExcel == null) {
                // エラーなし -> メッセージ表示 & ボタン制御用フラグON
                model.addAttribute("message", "勤怠入力に特に問題ありませんでした。");
                form.setCheckOk(true);
            } else {
                // エラーあり -> Excelダウンロード
                String fileName = ym.toString() + " 勤怠確認エラー.xlsx";
                form.setCheckOk(false);
                return createDownloadResponse(errorExcel, fileName);
            }
        }

        // 3. 出力
        if ("output".equals(action)) {
            byte[] outputExcel = outputService.generateReport(form.getOutputItem(), ym);
            if (outputExcel != null) {
                String fileName = ym.toString() + "_" + form.getOutputItem() + ".xlsx";
                return createDownloadResponse(outputExcel, fileName);
            } else {
                model.addAttribute("error", "データの出力に失敗しました。");
            }
        }

        // 画面再表示
        setupModel(model, form);
        return "/output/output";
    }

    // ダウンロード用レスポンス生成
    private ResponseEntity<ByteArrayResource> createDownloadResponse(byte[] data, String fileName) {
        try {
            String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replace("+", "%20");
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(data.length)
                    .body(new ByteArrayResource(data));
        } catch (Exception e) {
            logger.error("ダウンロードレスポンス生成エラー", e);
            return null;
        }
    }

    // 画面表示用モデル設定
    private void setupModel(Model model, OutputForm form) {
        model.addAttribute("yearMonthMap", outputService.getYearMonthList());
        
        Map<String, String> itemMap = new LinkedHashMap<>();
        itemMap.put("attendance", "勤怠管理");
        itemMap.put("paid_leave", "有給管理簿");
        itemMap.put("internal_pj", "社内PJ");
        itemMap.put("study", "勉強会");
        model.addAttribute("itemMap", itemMap);
        
        model.addAttribute("outputForm", form);
    }
}