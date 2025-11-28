package com.groupware.costs.controller;

//import java.time.LocalTime;
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.regex.Pattern;
//
import jakarta.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.dto.UserDto;

@Controller
public class CostsController {
	/**
	* 一般ユーザー・経費精算ホーム画面表示処理（POST用）
	*
	*/
	@PostMapping("/costs/list")
	public String postCosts() {
		return "redirect:/costs/list";
	}

	/**
	* 一般ユーザー・経費精算ホーム画面表示処理（GET用）
	*
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@GetMapping("/costs/list")
	public String getCosts(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "home");
		return "internal_cost/internal_cost_home";
	}

	/**
	* 経費精算詳細画面表示処理（GET用）
	*
	* @param id 経費ID
	* @param session セッション
	* @param model モデル
	* @return 詳細画面
	*/
	@GetMapping("/costs/detail/{id}")
	public String getCostDetail(@PathVariable("id") String id, HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "detail");
		
		// TODO: 実際のデータはDBから取得する
		// 仮データをセット
		model.addAttribute("costId", id);
		model.addAttribute("status", "rejected"); // pending, approved, rejected
		model.addAttribute("rejectReason", "経費として認められないため。");
		model.addAttribute("accountTitle", "旅費交通費");
		model.addAttribute("subCategory", "常駐先の会社に対する交通費（クライアント承認済）");
		model.addAttribute("amount", "620");
		model.addAttribute("purpose", "〇〇〇会社出勤時の地下鉄代（高田馬場～六本木一丁目駅）");
		model.addAttribute("purchaseDate", "2025/11/14");
		
		return "internal_cost/internal_cost_detail";
	}
}
