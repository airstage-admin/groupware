package com.groupware.userflow.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.batch.service.BatchService;
import com.groupware.common.registry.DepartmentRegistry;
import com.groupware.common.registry.EmployeeCategoryRegistry;
import com.groupware.common.registry.PlaceCategoryRegistry;
import com.groupware.common.registry.VacationCategoryRegistry;
import com.groupware.dto.DepartmentTypeDto;
import com.groupware.dto.EmployeeCategoryDto;
import com.groupware.dto.PlaceCategoryDto;
import com.groupware.dto.UserDto;
import com.groupware.dto.VacationCategoryDto;
import com.groupware.userflow.service.UserFlowService;

/**
* UserFlowController
* ログイン・HOME画面用コントローラー
* @author　N.Hirai
* @version　1.0.0
*/
@Controller
public class UserFlowController {
	public UserFlowController(UserFlowService userFlowService) {
		this.userFlowService = userFlowService;
	}

	@Autowired
	private UserFlowService userFlowService;

	@Autowired
	private BatchService reportService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	* INDEX処理（GET用）
	*/
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	/**
	* LOGIN処理（GET用）
	*/
	@GetMapping("/login")
	public String login() {
		return "index";
	}

	/**
	* LOGOUT処理（GET用）
	*/
	@GetMapping("/logout")
	public String logout() {
		return "index";
	}

	/**
	* HOME画面表示（GET用）
	* 
	* @param　session セッション
	* @param　model モデル
	*/
	@GetMapping("/userflow/home")
	public String home(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		// 取得データをmodelにセット
		session.setAttribute("loginUser", loginUser);
		model.addAttribute("user", loginUser);
		model.addAttribute("admin",userFlowService.is_admin(loginUser.getId()));
		return "/userflow/home";
	}

	/**
	* ログイン処理を行う
	* 
	* @param　loginid 入力されたログインID
	* @param　password 入力されたパスワード
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping("/login")
	public String login(@RequestParam String loginid, @RequestParam String password, HttpSession session, Model model) {
		try {
			// ログインを確認する
			UserDto user = userFlowService.login(loginid, password);
			if (user != null) {
				// 部署マスター読込処理
				List<DepartmentTypeDto> departmentList = userFlowService.findByDepartmentList();
				DepartmentRegistry.initialize(departmentList);
				
				// 勤怠休暇区分マスター読込処理
				List<VacationCategoryDto> vacationCategoryList = userFlowService.findByVacationCategoryList();
				VacationCategoryRegistry.initialize(vacationCategoryList);
				
				// 社員区分マスター読込処理
				List<EmployeeCategoryDto> employeeCategoryList = userFlowService.findByEmployeeCategoryList();
				EmployeeCategoryRegistry.initialize(employeeCategoryList);
				
				// 勤務先区分マスター読込処理
				List<PlaceCategoryDto> placeCategoryList = userFlowService.findByPlaceCategoryList();
				PlaceCategoryRegistry.initialize(placeCategoryList);

				session.setAttribute("loginUser", user);
				model.addAttribute("user", user);
				return "redirect:/userflow/home";

			} else {
				model.addAttribute("error", "ログインに失敗しました。ユーザー名またはパスワードが間違っています。");
				return "index";
			}
		} catch (Exception e) {
			logger.error("ログイン処理中にエラーが発生しました", e);
			model.addAttribute("error", "システムエラーが発生しました。管理者に連絡してください。");
			return "index";
		}
	}

	/**
	* ログアウト処理を行う
	* 
	* @param　session セッション
	* @return　
	*/
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		// セッションを破棄
		session.invalidate();
		return "index";
	}
}
