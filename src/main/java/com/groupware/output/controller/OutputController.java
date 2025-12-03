package com.groupware.output.controller;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.common.constant.OutputCategory;
import com.groupware.output.form.OutputForm;

/**
 * OutputController
 * @author　S.dasgo
 * @version　1.0.0
 */
@Controller
public class OutputController {

    private static final Logger logger = LoggerFactory.getLogger(OutputController.class);

    /**
     * ドロップダウンリスト用のデータを準備し、Modelに追加する共通メソッド
     * @param　model モデル
     */
    private void populateDropdowns(Model model) {
        // 出力項目のマップをEnumから作成
        Map<String, String> itemMap = new LinkedHashMap<>();
        for (OutputCategory category : OutputCategory.values()) {
            itemMap.put(category.getCode(), category.getLabel());
        }
        model.addAttribute("itemMap", itemMap);

        // 対象年月のマップを作成 (システム年月の前月から過去12ヶ月)
        Map<String, String> yearMonthMap = new LinkedHashMap<>();
        YearMonth startMonth = YearMonth.now().minusMonths(1); // 前月から開始
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy - MM");
        
        for (int i = 0; i < 12; i++) {
            YearMonth ym = startMonth.minusMonths(i);
            yearMonthMap.put(ym.toString(), ym.format(formatter));
        }
        model.addAttribute("yearMonthMap", yearMonthMap);
    }

    /**
     * 初期表示
     * @param　model
     * @return　画面テンプレートのパス
     */
    @GetMapping("/output")
    public String showOutputForm(Model model) {
        logger.info("GET /output : 出力管理画面を表示します。");

        //フォームオブジェクトをモデルに設定
        if (!model.containsAttribute("outputForm")) {
            OutputForm form = new OutputForm();
            //初期値を設定
            form.setOutputItem(OutputCategory.ATTENDANCE.getCode());
            form.setTargetYearMonth(YearMonth.now().minusMonths(1).toString());
            model.addAttribute("outputForm", form);
        }
        
        //ドロップダウンリストのデータをモデルに追加
        populateDropdowns(model);

        return "output/output"; // resources/templates/output/output.html を指す
    }

    /**
     * 出力管理画面のフォーム送信処理 (POST)
     * @param　form フォームオブジェクト
     * @param　action 押されたボタンのvalue値 ("check", "output", "home")
     * @param　redirectAttributes リダイレクト先にメッセージを渡すためのオブジェクト
     * @return　リダイレクト先のURL
     */
    @PostMapping("/output_submit")
    public String handleSubmit(
            @ModelAttribute("outputForm") OutputForm form,
            @RequestParam("action") String action,
            RedirectAttributes redirectAttributes) {

        logger.info("POST /output_submit : action={}", action);
        
        switch (action) {
        case "check":
            logger.info("--- 勤怠確認処理が実行されました ---");
            logger.info("選択された項目: {}", form.getOutputItem());
            logger.info("選択された年月: {}", form.getTargetYearMonth());
            
            // 確認OKのフラグ設定などは削除し、メッセージのみ設定
            redirectAttributes.addFlashAttribute("message", "勤怠入力に特に問題ありませんでした。");
            break;

        case "output":
            logger.info("--- 出力処理が実行されました ---");
            logger.info("選択された項目: {}", form.getOutputItem());
            logger.info("選択された年月: {}", form.getTargetYearMonth());

            // if文によるチェック処理を削除し、直接完了メッセージを設定
            redirectAttributes.addFlashAttribute("message", "ファイルの出力処理を実行しました。（ダミー）");
            break;

        case "home":
            logger.info("--- 「HOME」処理が実行されました。HOME画面にリダイレクトします ---");
            return "redirect:/userflow/home";
    }
        
        redirectAttributes.addFlashAttribute("outputForm", form);

        // 処理後、再度出力管理画面にリダイレクトして結果を表示する
        return "redirect:/output";
    }
}