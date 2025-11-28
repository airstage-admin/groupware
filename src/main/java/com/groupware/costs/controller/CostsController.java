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
		return "internal_cost/internal_cost_home";
	}
}
