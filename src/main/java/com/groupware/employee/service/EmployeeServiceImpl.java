package com.groupware.employee.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.constant.CommonConstants;
import com.groupware.dao.DepartmentTypeDao;
import com.groupware.dao.MonthlyCycleDao;
import com.groupware.dao.UserDao;
import com.groupware.dto.MonthlyCycleDto;
import com.groupware.dto.UserDto;
import com.groupware.userflow.controller.UserFlowController;

/**
* UserServiceImpl
* Userサービス
* @author　N.Hirai
* @version　1.0.0
*/
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private DepartmentTypeDao departmentTypeDao;

	@Autowired
	private MonthlyCycleDao monthlyCycleDao;

	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	* 有効な社員アカウントデータ一覧を取得する
	* 
	* @param　categoryCode 部署コード
	* @return　List<UserDto>
	*/
	@Override
	public List<UserDto> findByUsersList(int categoryCode) {
		return userDao.findByUsersList(categoryCode);
	}

	/**
	* 社員アカウントデータを取得する
	* 
	* @param　id 社員アカウントID
	* @param　categoryCode 部署コード
	* @return　UserDto
	*/
	@Override
	public UserDto findByUser(int id) {
		return userDao.findByUser(id);
	}

	/**
	* 既存データに登録するログインIDがあるかチェックする（自分自身は除く）
	* 
	* @param　id 社員アカウントID
	* @param　loginid ログインID
	* @return　false：無し、true：有り
	*/
	@Override
	public boolean existsByLoginid(int id, String loginid) {
		return userDao.existsByLoginid(id, loginid);
	}

	/**
	* 社員アカウントデータを登録更新する
	* 
	* @param　userDto 社員アカウントデータ
	* @return
	*/
	@Override
	public void update(UserDto userDto) {
		// 入社年月日
		String hireDate = userDto.getHireDate();
		try {
			LocalDate localhireDate = LocalDate.parse(hireDate, CommonConstants.DATE_FORMAT); // 入社年月日
			LocalDate sixMonthsAgo = LocalDate.now().minusMonths(CommonConstants.SIX_MONTH); // 6か月前の年月日

			// 入社年月日が半年以内かチェック
			boolean isWithinSixMonths = localhireDate.isAfter(sixMonthsAgo) || localhireDate.isEqual(sixMonthsAgo);

			// 入社年月日が半年以内なら有給付与年月は入社年月日+6ヶ月後
			// 入社年月日が半年以前なら勤怠付与サイクルマスタから算出した有給付与年月
			String grantDate = isWithinSixMonths
					? CommonConstants.DATE_FORMAT.format(localhireDate.plusMonths(CommonConstants.SIX_MONTH))
					: getTargetDate(hireDate);
			userDto.setPaidGrantDate(grantDate);

		} catch (Exception e) {
			logger.error("日付の解析中にエラーが発生しました:", e);
		}
		userDao.update(userDto);
	}

	/**
	* 社員アカウントデータを停止する
	* 
	* @param　id 社員アカウントID
	* @param　updateId 更新者ID
	* @return　UserDto
	*/
	@Override
	public void accountStop(int id, int updateId) {
		userDao.accountStop(id, updateId);
	}

	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	@Override
	public boolean is_admin(int id) {
		return departmentTypeDao.is_admin(id);
	}

	/**
	* 勤怠付与サイクルマスタから有給付与年月を取得
	* 
	* @param　hireDate 入社年月日
	* @return　有給付与年月
	*/
	private String getTargetDate(String hireDate) {
		// 入社年月日をmonthly_cycleテーブルと比較できるように変換する（年は2000に固定）
		String[] dateParts = hireDate.split(CommonConstants.SLASH_FORMAT);
		String comparisonDate = CommonConstants.CYCLE_YEAR + CommonConstants.SLASH_FORMAT + dateParts[1]
				+ CommonConstants.SLASH_FORMAT + dateParts[2];
		LocalDate comparisonLocalDate = LocalDate.parse(comparisonDate, CommonConstants.DATE_FORMAT);

		// 入社年月日から有給付与年月を取得
		MonthlyCycleDto dto = monthlyCycleDao.findByTargetDate(comparisonLocalDate);

		// 取得した有給付与年月の年を本年にする
		String[] joiningDateParts = dto.getTargetDate().split(CommonConstants.HYPHEN_FORMAT);
		String joiningChangeDate = String.valueOf(LocalDate.now().getYear()) + CommonConstants.SLASH_FORMAT
				+ joiningDateParts[1] + CommonConstants.SLASH_FORMAT + joiningDateParts[2];

		// 有給付与年月が過ぎている場合は、1年後をリターン
		// 有給付与年月がまだの場合は、そのままリターン
		LocalDate targetDate = LocalDate.parse(joiningChangeDate, CommonConstants.DATE_FORMAT);
		if (!LocalDate.now().isBefore(targetDate)) {
			LocalDate oneYearLater = LocalDate.parse(joiningChangeDate, CommonConstants.DATE_FORMAT)
					.plusYears(CommonConstants.ONE_YEAR);
			return oneYearLater.toString().replace(CommonConstants.HYPHEN_FORMAT,
					CommonConstants.SLASH_FORMAT);
		} else {
			return joiningChangeDate;
		}
	}
}
