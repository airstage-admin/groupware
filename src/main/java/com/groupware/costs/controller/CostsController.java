package com.groupware.costs.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.groupware.common.registry.DepartmentRegistry;
import com.groupware.costs.service.MemberService;
import com.groupware.costs.service.SubjectService;
import com.groupware.dto.SubjectDto;
import com.groupware.dto.UserDto;

@Controller
public class CostsController {
	@Autowired
	private SubjectService subjectService;

	@Autowired
	private MemberService memberService;
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
	@GetMapping("/costs/detail")
	public String getCostDetail(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "detail");

		// TODO: 実際のデータはDBから取得する
		// 仮データをセット
		model.addAttribute("costId", "id");
		model.addAttribute("status", "rejected"); // pending, approved, rejected
		model.addAttribute("rejectReason", "経費として認められないため。");
		model.addAttribute("accountTitle", "旅費交通費");
		model.addAttribute("subCategory", "常駐先の会社に対する交通費（クライアント承認済）");
		model.addAttribute("amount", "620");
		model.addAttribute("purpose", "〇〇〇会社出勤時の地下鉄代（高田馬場～六本木一丁目駅）");
		model.addAttribute("purchaseDate", "2025/11/14");

		return "internal_cost/internal_cost_detail";
	}

	/**
	* 経費精算申請画面表示処理（GET用）
	*
	* @param session セッション
	* @param model モデル
	* @return 申請画面
	*/
	@GetMapping("/costs/new")
	public String getCostCreate(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "new");

		// 勘定科目一覧を取得
		List<SubjectDto> subjects = subjectService.findAll();
		model.addAttribute("subjects", subjects);

		return "internal_cost/internal_cost_create";
	}

	/**
	* 申請履歴画面表示処理（GET用）
	*/
	@GetMapping("/costs/history")
	public String getCostHistory(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "history");
		return "internal_cost/application_history";
	}

	/**
	* 承認一覧画面表示処理（GET用）
	*/
	@GetMapping("/costs/approval")
	public String getCostApproval(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "approval");
		return "internal_cost/approval_list";
	}

	/**
	* 会社情報管理マスタ画面表示処理（GET用）
	*/
	@GetMapping("/costs/company")
	public String getCompanyMaster(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "company");
		return "internal_cost/company_master";
	}

	/**
	* メンバーリスト管理マスタ画面表示処理（GET用）
	*/
	@GetMapping("/costs/member")
	public String getMemberMaster(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "member");

		// メンバー一覧を取得
		List<UserDto> members = memberService.findAll();
		model.addAttribute("members", members);

		// 部署マップを取得
		model.addAttribute("departmentMap", DepartmentRegistry.departmentTypeSelectSet(false));

		return "internal_cost/member_master";
	}

	/**
	* 勘定科目管理マスタ画面表示処理（GET用）
	*/
	@GetMapping("/costs/account")
	public String getAccountMaster(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "account");

		// 勘定科目一覧を取得
		List<SubjectDto> subjects = subjectService.findAll();
		model.addAttribute("subjects", subjects);

		return "internal_cost/account_master";
	}

	/**
	* 勘定科目サブカテゴリ管理マスタ画面表示処理（GET用）
	*/
	@GetMapping("/costs/account-subcategory")
	public String getAccountSubcategoryMaster(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "account-subcategory");
		return "internal_cost/account_subcategory_master";
	}

	/**
	* プロジェクト管理マスタ画面表示処理（GET用）
	*/
	@GetMapping("/costs/project")
	public String getProjectMaster(HttpSession session, Model model) {
		UserDto loginUser = (UserDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/index";
		}
		model.addAttribute("user", loginUser);
		model.addAttribute("currentPage", "project");
		return "internal_cost/project_master";
	}
}
