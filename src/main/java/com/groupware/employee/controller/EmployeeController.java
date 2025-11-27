package com.groupware.employee.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groupware.common.constant.CommonConstants;
import com.groupware.common.registry.DepartmentRegistry;
import com.groupware.common.registry.EmployeeCategoryRegistry;
import com.groupware.dto.UserDto;
import com.groupware.employee.form.EmployeeForm;
import com.groupware.employee.service.EmployeeService;
import com.groupware.userflow.controller.UserFlowController;

@Controller
public class EmployeeController {
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Autowired
	private final EmployeeService employeeService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	* 社員アカウント一覧表示処理（GET用）
	* 
	* @param　departmentCategoryCookieValue　部署クッキー値
	* @param　session セッション
	* @param　model モデル
	* @param　redirectAttributes　表示されるデータ
	* @return　
	*/
	@GetMapping("/employee_list")
	public String employeeListGet(
			@CookieValue(value = "departmentCategoryCookie", required = false) String departmentCategoryCookieValue,
			HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		try {
			// 勤務先のクッキー設定してあるデータを取得する
			String initialDlaceCategory = getDepartmentCategoryCookieValue(departmentCategoryCookieValue);
			int departmenCode = CommonConstants.UNSELECTED_CODE;
			if (initialDlaceCategory != null && !initialDlaceCategory.isEmpty()) {
				departmenCode = Integer.parseInt(initialDlaceCategory);
			}

			// 社員アカウント一覧データ取得
			List<UserDto> list = employeeService.findByUsersList(departmenCode);

			// 取得データをmodelにセット
			model.addAttribute("selectedDepartmentCategory", Integer.parseInt(initialDlaceCategory));
			model.addAttribute("userList", list);
		    model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(true));
		    
			return "/employee/employee_list";
		} catch (Exception e) {
			logger.error("社員アカウント一覧表示処理中にエラーが発生しました", e);
			model.addAttribute("error", "社員アカウント一覧表示処理中にエラーが発生しました。管理者に連絡してください。");
			return "redirect:/index";
		}
	}

	/**
	* 社員アカウント一覧表示処理（POST用）
	*/
	@PostMapping("/employee_list")
	public String employeeListPost() {
		return "redirect:/employee_list";
	}

	/**
	* 社員アカウント新規作成処理を行う
	* 
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping("/employee_create")
	public String employeeCreate(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// 社員アカウントDto
		UserDto dto = new UserDto();

		// 取得データをmodelにセット
		dto.setPassword(CommonConstants.PASSWORD);
		model.addAttribute("user", loginUser);
		model.addAttribute("userdto", dto);
		model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(false));
		model.addAttribute("employeeCategoryMap", EmployeeCategoryRegistry.employeeCategorySelectSet());

		return "/employee/employee_edit";
	}

	/**
	* 勤怠確認処理を行う
	* 
	* @param　id 社員アカウントID
	* @param　selectedMonthYear　対象年月クッキー値
	* @param　placeCategoryCookieValue　勤務先クッキー値
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping("/employee_attendance")
	public String employeeAttendance(@RequestParam("id") int id, HttpSession session, Model model) {
		UserDto adminLoginUser = (UserDto) session.getAttribute("loginUser");
		if (adminLoginUser == null) {
			return "redirect:/index";
		}

		try {
			// 社員アカウントデータ取得
			UserDto user = employeeService.findByUser(id);
			if (user != null) {
				session.setAttribute("loginUser", user);
				session.setAttribute("adminLoginUser", adminLoginUser);
				model.addAttribute("user", user);
				return "redirect:/attendance_calendar";
			} else {
				logger.error("勤怠確認処理処理中にエラーが発生しました");
				model.addAttribute("error", "システムエラーが発生しました。管理者に連絡してください。");
				return "index";
			}
		} catch (Exception e) {
			logger.error("勤怠確認処理処理中にエラーが発生しました", e);
			model.addAttribute("error", "システムエラーが発生しました。管理者に連絡してください。");
			return "index";
		}
	}

	/**
	* 社員アカウント更新処理を行う
	* 
	* @param　id 社員アカウントID
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping("/employee_edit")
	public String employeeEdit(@RequestParam("id") int id, HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// 社員アカウントデータ取得
		UserDto dto = employeeService.findByUser(id);

		// 取得データをmodelにセット
		model.addAttribute("user", loginUser);
		model.addAttribute("selectedDepartmentCategory", dto.getDepartment());
		model.addAttribute("selectedEmployeeCategory", dto.getEmployeeType());
		model.addAttribute("userdto", dto);
		model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(false));
		model.addAttribute("employeeCategoryMap", EmployeeCategoryRegistry.employeeCategorySelectSet());
		
		return "/employee/employee_edit";
	}

	/**
	* アカウント停止処理を行う
	* 
	* @param　id 社員アカウントID
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping("/employee_stop")
	public String employeeStop(@RequestParam("id") int id, HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// アカウント停止処理を行う
		employeeService.accountStop(id, loginUser.getId());

		return "redirect:/employee_list";
	}

	/**
	* アカウント登録を行う
	* 
	* @param　id ユーザーID
	* @param　attendance 勤怠管理テーブルモデル
	* @param　アノテーションによるエラーチェック結果
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping(value = "/employee_submit", params = "action=update")
	public String employeeSubmitUpdate(@RequestParam("id") long id,
			@Validated @ModelAttribute("userdto") EmployeeForm employeeForm,
			BindingResult bindingResult, HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// FormからDtoへの変換処理
		UserDto userDto = convertFormToDto(employeeForm);

		// アノテーションによるエラーチェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("selectedDepartmentCategory", userDto.getDepartment());
			model.addAttribute("selectedEmployeeCategory", userDto.getEmployeeType());
			model.addAttribute("user", loginUser);
			model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(false));
			model.addAttribute("employeeCategoryMap", EmployeeCategoryRegistry.employeeCategorySelectSet());

			return "/employee/employee_edit";
		}

		try {
			// アカウント情報の登録更新更新を行う
			userDto.setCreatedBy(loginUser.getId());
			userDto.setUpdatedBy(loginUser.getId());
			employeeService.update(userDto);
		} catch (Exception e) {
			model.addAttribute("message", "アカウント更新でエラーが発生しました。");
			model.addAttribute("user", loginUser);
			return "redirect:/index";
		}

		model.addAttribute("user", loginUser);
		return employeeService.is_admin(loginUser.getId())
				? "redirect:/employee_list"
				: "redirect:/index";
	}

	@PostMapping(value = "/employee_submit", params = "action=cancel")
	public String employeeSubmitCancel(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		model.addAttribute("user", loginUser);
		return employeeService.is_admin(loginUser.getId())
				? "redirect:/employee_list"
				: "redirect:/userflow/home";
	}

	/**
	* アカウント編集に遷移する（社員用）
	* 
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping("/employee_account_edit")
	public String employeeAccountEdit(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// 社員アカウントデータ取得
		UserDto dto = employeeService.findByUser(loginUser.getId());

		// 取得データをmodelにセット
		model.addAttribute("user", loginUser);
		model.addAttribute("selectedDepartmentCategory", dto.getDepartment());
		model.addAttribute("selectedEmployeeCategory", dto.getEmployeeType());
		model.addAttribute("userdto", dto);
		model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(false));
		
		return "/employee/employee_edit";
	}

	/**
	* 部署のクッキー設定してあるデータを取得する
	* 
	* @param　departmentCategoryCookieValue 部署のクッキー値
	* @return　部署ENUM値
	*/
	private String getDepartmentCategoryCookieValue(String departmentCategoryCookieValue) {
		return (departmentCategoryCookieValue != null && !departmentCategoryCookieValue.isEmpty())
				? departmentCategoryCookieValue
				: null;
	}

	/**
	* FormからDtoへの変換処理
	* 
	* @param　form EmployeeForm
	* @return　UserDto
	*/
	private UserDto convertFormToDto(EmployeeForm form) {
		UserDto dto = new UserDto();

		dto.setId(form.getId());
		dto.setLoginid(form.getLoginid());
		dto.setPassword(form.getPassword());
		dto.setHireDate(form.getHireDate());
		dto.setUsername(form.getUsername());
		dto.setEmployeeNo(form.getEmployeeNo());
		dto.setMail(form.getMail());
		dto.setEmployeeType(Integer.valueOf(form.getEmployeeType()));
		dto.setDepartment(Integer.valueOf(form.getDepartmentCategory()));

		return dto;
	}
}
