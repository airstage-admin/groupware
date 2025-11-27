package com.groupware.batch.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.constant.CommonConstants;
import com.groupware.common.model.DepartmentType;
import com.groupware.common.model.VacationCategory;
import com.groupware.common.registry.DepartmentRegistry;
import com.groupware.common.registry.VacationCategoryRegistry;
import com.groupware.dao.AttendanceDao;
import com.groupware.dao.NumberPaidDaysDao;
import com.groupware.dao.UserDao;
import com.groupware.dto.AttendanceDto;
import com.groupware.dto.UserDto;
import com.groupware.employee.service.EmployeeService;
import com.groupware.userflow.controller.UserFlowController;

/**
* BatchServiceImpl
* バッチサービス
* @author　N.Hirai
* @version　1.0.0
*/
@Service
public class BatchServiceImpl implements BatchService {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private NumberPaidDaysDao numberPaidDaysDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AttendanceDao attendanceDao;

	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	* 有給処理を行う
	* 
	* @param　ym 作業対象年月
	* @return
	*/
	@Override
	public void paidLeaveDate(YearMonth ym) {
		try {
			// 社員アカウント一覧データ取得
			List<UserDto> userLists = employeeService.findByUsersList(CommonConstants.UNSELECTED_CODE);
			userLists.stream()
		    // 管理者ではないユーザーのみをフィルタリング
		    .filter(userRs -> {
		        DepartmentType dept = DepartmentRegistry.fromCode(userRs.getDepartment());
		        // 管理者権限者 (dept.getAdmin() が true) ではない場合に true を返し、ストリームに残す
		        return !dept.getAdmin(); 
		    })
		    // フィルタリングされたユーザーに対して、付与処理と取得処理を実行
		    .forEach(userRs -> {
		        // 有給付与処理
		        paidLeaveProcessing(userRs);
		        
		        // 有給取得処理
		        paidLeaveAcquisitionProcessing(userRs, ym);
		    });
		} catch (Exception e) {
			logger.error("有給処理中にエラーが発生しました:", e);
		}
	}

	/**
	* 有給付与処理を行う
	* 
	* @param　dto UserDto
	* @return
	*/
	public void paidLeaveProcessing(UserDto dto) {
		try {
			// 有給付与処理
			LocalDate paidGrantDate = LocalDate.parse(dto.getPaidGrantDate(), CommonConstants.DATE_FORMAT);
			// 有給付与日を過ぎたら（当日含む）付与
			if (!LocalDate.now().isBefore(paidGrantDate)) {
				int beforePaidGrant = dto.getPaidLeaveGranted(); // 前回の有給付与日数
				float paidLeaveRemaining = dto.getPaidLeaveRemaining(); // 有給残日数
				// 前回の有給付与日数を超えた有給残日数は削除
				paidLeaveRemaining = Math.min(paidLeaveRemaining, beforePaidGrant);
				
				// 今回付与すべき有給日数取得
				long monthsPassed = ChronoUnit.MONTHS
						.between(LocalDate.parse(dto.getHireDate(), CommonConstants.DATE_FORMAT), LocalDate.now());
				int paidLeaveGranted = numberPaidDaysDao.findByPaidLeaveRranted(dto.getEmployeeType(),
						(int) monthsPassed);
				paidLeaveRemaining += paidLeaveGranted;

				// 有給付与日、有給付与日数、有給残日数の更新
				userDao.paidLeaveUpdate(dto.getId(),
						paidGrantDate.plusYears(1).format(CommonConstants.STRDATE_FORMAT), paidLeaveGranted,
						paidLeaveRemaining);
			}

		} catch (Exception e) {
			logger.error("有給付与処理中にエラーが発生しました:", e);
		}
	}

	/**
	* 有給所得処理を行う
	* 
	* @param　dto UserDto
	* @param　ym 作業対象年月
	* @return
	*/
	public void paidLeaveAcquisitionProcessing(UserDto dto, YearMonth ym) {
		try {
			List<AttendanceDto> attendanceLists = attendanceDao.findByUserAndMonthPaidDate(dto.getId(), ym);
			
			// 作業対象年月で取得した有給日を取得
			double paidVacationDate = attendanceLists.stream()
		            .map(AttendanceDto::getVacationCategory) 
		            .map(VacationCategoryRegistry::fromCode)
		            .filter(VacationCategory::getIsPaid)
		            .mapToDouble(VacationCategory::getPaidDate)
		            .sum();
			
			// 有給取得していたら有給残日数を更新
			// マイナスのままにしておく
			float paidLeaveRemaining = dto.getPaidLeaveRemaining();
			if (paidVacationDate > 0) {
				// 残日数計算してゼロチェック
				paidLeaveRemaining -= paidVacationDate;
				paidLeaveRemaining = Math.max(0, paidLeaveRemaining);
				// 残日数の更新処理
				userDao.paidLeaveRemainingUpdate(dto.getId(), paidLeaveRemaining);
			}

		} catch (Exception e) {
			logger.error("有給所得処理中にエラーが発生しました:", e);
		}
	}
}
