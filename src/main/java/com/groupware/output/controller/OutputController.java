package com.groupware.output.controller;

import java.time.YearMonth;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.common.constant.OutputCategory;
import com.groupware.dto.UserDto;
import com.groupware.output.form.OutputForm;
import com.groupware.output.service.OutputService;

/**
 * OutputController
 * @author　S.daigo
 * @version　1.0.0
 */
@Controller
public class OutputController {

	private static final Logger logger = LoggerFactory.getLogger(OutputController.class);

	@Autowired
	private OutputService outputService;

	/**
	 * 初期表示
	 */
	@GetMapping("/output")
	public String showOutputForm(Model model, HttpSession session,
			@ModelAttribute("outputForm") OutputForm form) {

		//セッション切れ確認
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		//初期値設定
		if (form.getTargetYearMonth() == null) {
			form.setTargetYearMonth(YearMonth.now().minusMonths(1).toString());
		}

		model.addAttribute("reportTypes", OutputCategory.values());

		return "output/output";
	}

	/**
	 * 出力管理画面のフォーム送信処理
	 */
	@PostMapping("/output_submit")
	public String handleSubmit(
			@ModelAttribute("outputForm") OutputForm form,
			@RequestParam("action") String action,
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		// セッション切れ確認
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		switch (action) {
		case "check":
			// 勤怠確認処理
			executeCheck(form);
			break;

		case "output":
			// ファイル出力処理
			executeOutput(form);
			break;

		case "home":
			// HOME画面へ遷移
			return "redirect:/userflow/home";

		default:
			logger.warn("想定外のアクションが指定されました: {}", action);
			break;
		}

		redirectAttributes.addFlashAttribute("outputForm", form);
		return "redirect:/output";
	}

	/**
	 * 勤怠確認処理
	 */
	private void executeCheck(OutputForm form) {
		logger.info("勤怠確認開始: 項目={}", form.getOutputItem());
		//ここに勤怠確認のロジックを記述してください
	}

	/**
	 * ファイル出力処理の振り分け
	 */
	private void executeOutput(OutputForm form) {
		logger.info("出力処理開始: 年月={}", form.getTargetYearMonth());

		try {

			switch (form.getOutputItem()) {
			case "attendance":
				//ここに勤怠管理の出力ロジックを実装してください
				break;

			case "paid_leave":
				//ここに有給管理簿の出力ロジックを実装してください
				break;

			case "internal_pj":
				form.setOutputItem("社内PJ");
				String msg = outputService.createInternalProjectExcel(form);
				logger.info("出力完了: " + msg);
				break;

			case "study":
				//ここに勉強会の出力ロジックを実装してください
				break;

			default:
				logger.warn("未定義の出力項目が指定されました: {}", form.getOutputItem());
				break;
			}
		} catch (Exception e) {
			logger.error("出力エラー", e);
		}
	}
}