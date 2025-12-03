package com.groupware.output.controller;

import java.time.YearMonth;

import jakarta.servlet.http.HttpSession;

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
import com.groupware.dto.UserDto;
import com.groupware.output.form.OutputForm;

/**
 * OutputController
 * @author　S.daigo
 * @version　1.0.0
 */
@Controller
public class OutputController {

	private static final Logger logger = LoggerFactory.getLogger(OutputController.class);

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

		logger.info("GET /output : 出力管理画面を表示します。");

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

		logger.info("POST /output_submit : action={}", action);

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

		switch (form.getOutputItem()) {
		case "attendance":
			executeOutputAttendance(form);
			break;
		case "paid_leave":
			executeOutputPaidLeave(form);
			break;
		case "internal_pj":
			executeOutputInternalPj(form);
			break;
		case "study":
			executeOutputStudy(form);
			break;
		}
	}

	/**
	 * ファイル出力処理：勤怠管理表
	 */
	private void executeOutputAttendance(OutputForm form) {
		logger.info("-> 勤怠管理表の出力処理を実行します。");
		//ここに勤怠管理の出力ロジックを実装してください
	}

	/**
	 * ファイル出力処理：有給管理簿
	 */
	private void executeOutputPaidLeave(OutputForm form) {
		logger.info("-> 有給管理簿の出力処理を実行します。");
		//ここに有給管理簿の出力ロジックを実装してください
	}

	/**
	 * ファイル出力処理：社内PJ
	 */
	private void executeOutputInternalPj(OutputForm form) {
		logger.info("-> 社内PJの出力処理を実行します。");
		//ここに社内PJの出力ロジックを実装してください
	}

	/**
	 * ファイル出力処理：勉強会参加
	 */
	private void executeOutputStudy(OutputForm form) {
		logger.info("-> 勉強会の出力処理を実行します。");
		//ここに勉強会の出力ロジックを実装してください
	}
}