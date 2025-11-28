package com.groupware.attendance.controller;

import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

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

import com.groupware.attendance.form.AttendanceForm;
import com.groupware.attendance.service.AttendanceService;
import com.groupware.common.constant.CommonConstants;
import com.groupware.common.model.PlaceCategory;
import com.groupware.common.registry.DepartmentRegistry;
import com.groupware.common.registry.PlaceCategoryRegistry;
import com.groupware.common.registry.VacationCategoryRegistry;
import com.groupware.dto.AttendanceDto;
import com.groupware.dto.UserApprovalDto;
import com.groupware.dto.UserDto;
import com.groupware.userflow.controller.UserFlowController;

@Controller
public class AttendanceController {
	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	@Autowired
	private final AttendanceService attendanceService;

	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	* 社員用勤怠カレンダ一覧表示処理（GET用）
	* 
	* @param　selectedMonthYear　対象年月クッキー値
	* @param　placeCategoryCookieValue　勤務先クッキー値
	* @param　session セッション
	* @param　model モデル
	* @param　redirectAttributes　表示されるデータ
	* @return　
	*/
	@GetMapping("/attendance_calendar")
	public String attendanceCalendarGet(
			@CookieValue(value = "selected_month_year", required = false) String selectedMonthYear,
			@CookieValue(value = "placeCategoryCookie", required = false) String placeCategoryCookieValue,
			HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		UserDto adminLoginUser = (UserDto) session.getAttribute("adminLoginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		try {
			// 勤務先のクッキー設定してあるデータを取得する
			String initialPlaceCategory = getPlaceCategoryCookieValue(placeCategoryCookieValue);

			// 処理年月のクッキー設定してあるデータを取得
			YearMonth ym = selectedMonthYear != null ? YearMonth.parse(selectedMonthYear) : YearMonth.now();

			// 一覧に表示する対象データ取得する
			int placeCode = 0;
			if (initialPlaceCategory != null && !initialPlaceCategory.isEmpty()) {
				placeCode = Integer.parseInt(initialPlaceCategory);
			}
			List<AttendanceDto> list = attendanceService.findByUserAndMonth(loginUser.getId(), ym, placeCode);

			// 取得データをmodelにセット
			model.addAttribute("selectedPlaceCategory", placeCode);
			model.addAttribute("user", loginUser);
			model.addAttribute("attendanceList", list);
			model.addAttribute("vacationCategoryMap", VacationCategoryRegistry.vacationCategorySelectSet());
			model.addAttribute("placeCategoryMap", PlaceCategoryRegistry.placeCategorySelectSet());
			model.addAttribute("admin", adminLoginUser != null);
			model.addAttribute("approval",
					adminLoginUser != null && !attendanceService.existsByApprovalDate(loginUser.getId(), ym));
			if (adminLoginUser != null) {
				session.setAttribute("loginUser", adminLoginUser);
			}

			return "/attendance/attendance_calendar_list";
		} catch (Exception e) {
			logger.error("勤怠一覧表示処理中にエラーが発生しました", e);
			model.addAttribute("error", "勤怠一覧表示処理中にエラーが発生しました。管理者に連絡してください。");
			return "redirect:/index";
		}
	}

	/**
	* 社員用勤怠カレンダ一覧表示処理（POST用）
	* 
	*/
	@PostMapping("/attendance_calendar")
	public String attendanceCalendarPost() {
		return "redirect:/attendance_calendar";
	}

	/**
	* 社員一覧表示処理（GET用）
	* 
	* @param　departmentCategoryCookieValue　部署クッキー値
	* @param　session セッション
	* @param　model モデル
	* @param　redirectAttributes　表示されるデータ
	* @return　
	*/
	@GetMapping("/attendance_employee_list")
	public String attendanceEmployeeListGet(
			@CookieValue(value = "departmentCategoryCookie", required = false) String departmentCategoryCookieValue,
			@CookieValue(value = "selected_month_year", required = false) String monthYearCookieValue,
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
			List<UserApprovalDto> list = attendanceService
					.findByUsersApprovalList(getYearMonthCookieValue(monthYearCookieValue));

			// 取得データをmodelにセット
			model.addAttribute("selectedDepartmentCategory", departmenCode);
			model.addAttribute("userList", list);
			model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(true));

			return "/attendance/attendance_member_list";
		} catch (Exception e) {
			logger.error("社員一覧表示処理中にエラーが発生しました", e);
			model.addAttribute("error", "社員一覧表示処理中にエラーが発生しました。管理者に連絡してください。");
			return "redirect:/index";
		}
	}

	/**
	* 社員一覧表示処理（POST用）
	*/
	@PostMapping("/attendance_employee_list")
	public String attendanceEmployeeListPost() {
		return "redirect:/attendance_employee_list";
	}

	/**
	* 更新処理に遷移する
	* 
	* @param　id 勤怠管理ID
	* @param　session セッション
	* @param　model モデル
	* @param　placeWorkNameValue　勤務先名称クッキー値
	* @param　clockInValue　出勤時間クッキー値
	* @param　clockOutValue　退勤時間クッキー値
	* @param　breakTimeValue　休憩時間クッキー値
	* @param　nightBreakTimeValue　深夜休憩時間クッキー値
	* @return　
	*/
	@PostMapping("/attendance_edit")
	public String attendanceEdit(@RequestParam("id") long id, HttpSession session, Model model,
			@CookieValue(value = "placeWorkNameCookie", required = false) String placeWorkNameValue,
			@CookieValue(value = "clockInCookie", required = false) String clockInValue,
			@CookieValue(value = "clockOutCookie", required = false) String clockOutValue,
			@CookieValue(value = "breakTimeCookie", required = false) String breakTimeValue,
			@CookieValue(value = "nightBreakTimeCookie", required = false) String nightBreakTimeValue) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		try {
			AttendanceDto dto = attendanceService.findById(id);
			if (dto == null || dto.getUserId() != loginUser.getId()) {
				return "redirect:/attendance/attendance_calendar";
			}
			model.addAttribute("user", loginUser);

			// 新規入力時でクッキー設定が有れば初期値とする
			dto = setAttendanceEditCookieSet(placeWorkNameValue, clockInValue, clockOutValue, breakTimeValue,
					nightBreakTimeValue, dto);

			// 勤務先区分データ取得
			PlaceCategory pc = PlaceCategoryRegistry.fromCode(dto.getPlaceWork());

			model.addAttribute("attendance", dto);
			model.addAttribute("placeWork", pc.getCode());
			model.addAttribute("placeChk", pc.getIsName());
			model.addAttribute("placeWorkName", pc.getDisplayName() + CommonConstants.KORON);
			model.addAttribute("vacationCategoryMap", VacationCategoryRegistry.vacationCategorySelectSet());
			model.addAttribute("selectedVacationCategory", dto.getVacationCategory());

			return "/attendance/attendance_edit";
		} catch (Exception e) {
			logger.error("更新遷移処理でエラーが発生しました", e);
			return "redirect:/index";
		}
	}

	/**
	* 勤怠管理新規更新を行う
	* 
	* @param　attendance 勤怠管理テーブルモデル
	* @param　アノテーションによるエラーチェック結果
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping(value = "/attendance_submit", params = "action=update")
	public String attendanceSubmitUpdate(@Validated @ModelAttribute("attendance") AttendanceForm attendanceForm,
			BindingResult bindingResult, HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		UserDto userAdminDto = (UserDto) session.getAttribute("adminLoginUser");

		// FormからDtoへの変換処理
		AttendanceDto attendanceDto = convertFormToDto(attendanceForm);

		// アノテーションによるエラーチェック
		if (bindingResult.hasErrors()) {
			// 勤務先区分データ取得
			PlaceCategory pc = PlaceCategoryRegistry.fromCode(Integer.parseInt(attendanceForm.getPlaceWork()));
			model.addAttribute("placeWork", pc.getCode());
			model.addAttribute("placeChk", pc.getIsName());
			model.addAttribute("placeWorkName", pc.getDisplayName() + CommonConstants.KORON);
			return "/attendance/attendance_edit";
		}

		// セッションのユーザーIDを使って強制的に上書き（安全対策）
		attendanceDto.setUserId(loginUser.getId());

		try {
			// 勤怠情報の更新を行う
			attendanceService.update(attendanceDto, userAdminDto == null ? loginUser.getId() : userAdminDto.getId());
		} catch (Exception e) {
			model.addAttribute("message", "勤怠情報の更新でエラーが発生しました。");
			model.addAttribute("user", loginUser);
			return "/attendance/attendance_edit";
		}

		model.addAttribute("user", loginUser);
		session.setAttribute("loginUser", loginUser);
		return "redirect:/attendance_calendar";
	}

	@PostMapping(value = "/attendance_submit", params = "action=cancel")
	public String attendanceSubmitCancel(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		model.addAttribute("user", loginUser);
		session.setAttribute("loginUser", loginUser);
		return "redirect:/attendance_calendar";
	}

	/**
	* 勤怠管理承認を行う
	* 
	* @param　attendance 勤怠管理テーブルモデル
	* @param　アノテーションによるエラーチェック結果
	* @param　session セッション
	* @param　model モデル
	* @return　
	*/
	@PostMapping(value = "/attendance_approval")
	public String attendanceApprovalSubmit(
			@CookieValue(value = "selected_month_year", required = false) String monthYearCookieValue,
			@RequestParam("id") int id,
			HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		// 処理年月のクッキー設定してあるデータを取得
		YearMonth ym = monthYearCookieValue != null ? YearMonth.parse(monthYearCookieValue) : YearMonth.now();

		try {
			//  勤怠管理承認処理
			attendanceService.insertApproval(id, ym.atDay(1), loginUser.getId());
		} catch (Exception e) {
			logger.error(" 勤怠管理承認処理でエラーが発生しました", e);
		}

		return "redirect:/attendance_employee_list";
	}

	/**
	* FormからDtoへの変換処理
	* 
	* @param　form AttendanceForm
	* @return　AttendanceDto
	*/
	private AttendanceDto convertFormToDto(AttendanceForm form) {
		AttendanceDto dto = new AttendanceDto();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.TIME_FORMAT);
		dto.setId(form.getId());
		dto.setUserId(form.getUserId());
		dto.setPlaceWork(Integer.parseInt(form.getPlaceWork()));
		dto.setPlaceWorkName(form.getPlaceWorkName());
		dto.setClockIn(form.getClockIn());
		dto.setClockOut(form.getClockOut());
		try {
			dto.setBreakTime(LocalTime.parse(form.getBreakTime(), formatter));
		} catch (Exception e) {
			dto.setBreakTime(LocalTime.parse(CommonConstants.INIT_TIME, formatter));
		}
		try {
			dto.setNightBreakTime(LocalTime.parse(form.getNightBreakTime(), formatter));
		} catch (Exception e) {
			dto.setNightBreakTime(LocalTime.parse(CommonConstants.INIT_TIME, formatter));
		}
		dto.setVacationCategory(Integer.parseInt(form.getVacationCategory()));
		dto.setVacationNote(form.getVacationNote());

		return dto;
	}

	/**
	* 勤務先のクッキー設定してあるデータを取得する
	* 
	* @param　placeCategoryCookieValue 勤務先のクッキー値
	* @return　勤務先ENUM値
	*/
	private String getPlaceCategoryCookieValue(String placeCategoryCookieValue) {
		return placeCategoryCookieValue != null && !placeCategoryCookieValue.isEmpty()
				? placeCategoryCookieValue
				: null;
	}

	/**
	* 新規入力時でクッキー設定が有ればDtoの初期値とする
	* 
	* @param　placeWorkNameValue 勤務先名称のクッキー値
	* @param　clockInValue 出勤時刻のクッキー値
	* @param　clockOutValue 退勤時刻のクッキー値
	* @param　breakTimeValue 休憩時間のクッキー値
	* @param　nightBreakTimeValue 深夜休憩時間のクッキー値
	* @param　dto AttendanceDto
	* @return　AttendanceDto
	*/
	private AttendanceDto setAttendanceEditCookieSet(String placeWorkNameValue, String clockInValue,
			String clockOutValue, String breakTimeValue, String nightBreakTimeValue, AttendanceDto dto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.TIME_FORMAT);
		LocalTime zeroTime = LocalTime.MIDNIGHT;

		if ((placeWorkNameValue != null && !placeWorkNameValue.isEmpty()) &&
				(dto.getPlaceWorkName() == null || dto.getPlaceWorkName().isEmpty())) {
			dto.setPlaceWorkName(placeWorkNameValue);
		}

		// 出勤時間、退勤時間
		Pattern pattern = Pattern.compile(CommonConstants.CLOCK_REGEX);
		if ((clockInValue != null && !clockInValue.isEmpty()) && CommonConstants.INIT_TIME.equals(dto.getClockIn())) {
			dto.setClockIn(pattern.matcher(clockInValue).matches() ? clockInValue : CommonConstants.INIT_TIME);
		}
		if ((clockOutValue != null && !clockOutValue.isEmpty())
				&& CommonConstants.INIT_TIME.equals(dto.getClockOut())) {
			dto.setClockOut(pattern.matcher(clockOutValue).matches() ? clockOutValue : CommonConstants.INIT_TIME);
		}

		// 休憩時間、深夜休憩時間
		if ((breakTimeValue != null && !breakTimeValue.isEmpty()) &&
				(dto.getBreakTime().equals(zeroTime))) {
			try {
				dto.setBreakTime(LocalTime.parse(breakTimeValue, formatter));
			} catch (Exception e) {
				dto.setBreakTime(LocalTime.parse(CommonConstants.INIT_TIME, formatter));
			}
		}
		if ((nightBreakTimeValue != null && !nightBreakTimeValue.isEmpty()) &&
				(dto.getNightBreakTime().equals(zeroTime))) {
			try {
				dto.setNightBreakTime(LocalTime.parse(nightBreakTimeValue, formatter));
			} catch (Exception e) {
				dto.setBreakTime(LocalTime.parse(CommonConstants.INIT_TIME, formatter));
			}
		}

		return dto;
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
	* 年月のクッキー設定してあるデータを取得する
	* 
	* @param　yearMonthCookieValue 部署のクッキー値
	* @return　部署ENUM値
	*/
	private YearMonth getYearMonthCookieValue(String yearMonthCookieValue) {
		return (yearMonthCookieValue != null && !yearMonthCookieValue.isEmpty())
				? YearMonth.parse(yearMonthCookieValue)
				: YearMonth.now();
	}
}
