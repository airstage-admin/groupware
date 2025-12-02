package com.groupware.employee.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.groupware.common.registry.DepartmentRegistry;
import com.groupware.common.registry.EmployeeCategoryRegistry;
import com.groupware.dto.UserDto;
import com.groupware.employee.form.EmployeeLoginIdForm;
import com.groupware.employee.form.EmployeePasswordForm;
import com.groupware.employee.service.EmployeeService;

/**
* AccountController
* Account（社員管理）用コントローラー
* 
* @author　A.Watanabe
* @version　1.0.0
*/
@Controller
public class AccountController {
	
	private final EmployeeService employeeService;

    public AccountController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
	/**
	* 社員アカウント管理画面を表示する
	* 
	* @param session セッション
	* @param model モデル
	* @return 遷移先テンプレート名
	*/
	@GetMapping("/account_home")
	public String employeeEdit(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// セッションのユーザーIDを使って最新情報を取得
		UserDto dto = employeeService.findByUser(loginUser.getId());
		
		// データが存在しない場合
		if (dto == null) {
		    session.invalidate();
		    return "redirect:/index";
		}

		// 取得データをmodelにセット
		model.addAttribute("user", loginUser);
		model.addAttribute("selectedDepartmentCategory", dto.getDepartment());
		model.addAttribute("selectedEmployeeCategory", dto.getEmployeeType());
		model.addAttribute("userdto", dto);
		model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(false));
		model.addAttribute("employeeCategoryMap", EmployeeCategoryRegistry.employeeCategorySelectSet());
		
		return "employee/account_home";
	}
	
	/**
	 * ログインID変更画面へ遷移する
	 * 
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先テンプレート名
	 */
	@PostMapping("/account_loginid")
	public String accountLoginIdEdit(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// 編集対象のデータを取得
		UserDto dto = employeeService.findByUser(loginUser.getId());

		// 画面に必要なデータを渡す
		model.addAttribute("user", loginUser);
		model.addAttribute("userdto", dto);
		
		//フォームのインスタンスを作成
		EmployeeLoginIdForm form = new EmployeeLoginIdForm();
				
		//現在のログインIDをセットする
		form.setNewLoginId(dto.getLoginid());
				
		// 値が入ったフォームを画面に渡す
		model.addAttribute("employeeLoginIdForm", form);
				
		return "employee/account_loginid"; 
	}
	
	/**
	 * ログインIDの変更処理を実行する
	 * 
	 * @param form ログインID変更フォーム
	 * @param bindingResult バリデーション結果
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先（成功時はログイン画面、失敗時は自画面）
	 */	
	@PostMapping(value = "/account_loginid", params = "action=update")
	public String employeeSubmitUpdate(
			@Validated @ModelAttribute("employeeLoginIdForm") EmployeeLoginIdForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		
		 UserDto dto = employeeService.findByUser(loginUser.getId());

		// 入力チェック（必須、文字数、正規表現など）
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", loginUser);
			model.addAttribute("userdto", dto);
			return "employee/account_loginid";
		}

		try {
			// 変更後ログインIDがすでに使用されていないか重複チェック
			if (employeeService.existsByLoginid(loginUser.getId(), form.getNewLoginId())) {
				model.addAttribute("message", "そのログインIDはすでに使用されています。");
				model.addAttribute("user", loginUser);
				model.addAttribute("userdto", dto); 
				return "employee/account_loginid";
			}

			// DB更新
			  UserDto updateDto = new UserDto();
	          updateDto.setId(loginUser.getId());
	          updateDto.setLoginid(form.getNewLoginId()); 
	          updateDto.setUpdatedBy(loginUser.getId());
	            
	          employeeService.updateLoginId(updateDto);

			// 更新後の処理（ログアウトしてログイン画面へ）
			session.invalidate(); // セッションを破棄
			return "redirect:/index";

		} catch (Exception e) {
			model.addAttribute("message", "アカウント更新でエラーが発生しました。");
			model.addAttribute("user", loginUser);
			return "employee/account_loginid";
		}
	}
	/**
	 * ログインID変更画面でキャンセルボタンが押された時の処理
	 * 
	 * @param session セッション
	 * @param model モデル
	 * @return アカウント管理画面へのリダイレクト
	 */
	@PostMapping(value = "/account_loginid", params = "action=cancel")
	public String accountLoginIdCancel(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		// キャンセル時はアカウント管理画面へ戻る
		return "redirect:/account_home";
	}
	
	/**
	 * パスワード変更画面へ遷移する
	 * 
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先テンプレート名
	 */
	@PostMapping("/account_password")
	public String accountPasswordEdit(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		
		// 入力用の「空のフォーム」を渡す
		model.addAttribute("user", loginUser);
		model.addAttribute("passwordForm", new EmployeePasswordForm()); 
		
		return "employee/account_password"; 
	}
	
	/**
	 * パスワード変更処理を実行する。
	 * 
	 * @param form パスワード変更フォーム
	 * @param bindingResult バリデーション結果
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先（成功時はログイン画面、失敗時は自画面）
	 */
	@PostMapping(value = "/account_password", params = "action=update")
	public String passwordUpdate(
			@Validated @ModelAttribute("passwordForm") EmployeePasswordForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}

		//アノテーションによる入力チェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", loginUser);
			return "employee/account_password";
		}

		//現在のパスワードが正しいかDBと照合
		boolean isCorrect = employeeService.checkPassword(loginUser.getId(), form.getCurrentPassword());

		if (!isCorrect) {
			// "currentPassword" フィールドに対してエラーメッセージを設定
			bindingResult.rejectValue("currentPassword", "", "入力された現在のパスワードが登録情報と一致しません。");
			
			model.addAttribute("user", loginUser);
			return "employee/account_password";
		}

		try {
			//新しいパスワードを渡す
			employeeService.updatePassword(loginUser.getId(), form.getNewPassword());

			//更新完了後の処理
			session.invalidate(); 
			return "redirect:/index";

		} catch (Exception e) {
			model.addAttribute("message", "パスワード更新中にエラーが発生しました。");
			model.addAttribute("user", loginUser);
			return "employee/account_password";
		}
	}

	/**
	 * パスワード変更画面でキャンセルボタン押下時の処理
	 * 
	 * @param session セッション
	 * @return アカウント管理画面へのリダイレクト
	 */
	@PostMapping(value = "/account_password", params = "action=cancel")
	public String passwordCancel(HttpSession session) {
		// アカウント管理画面へ戻る
		return "redirect:/account_home";
	}
}