package com.groupware.dao;

import java.time.YearMonth;
import java.util.List;

import com.groupware.dto.UserApprovalDto;
import com.groupware.dto.UserDto;

/**
* UserDao
* usersテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface UserDao {
	/**
	* 入力されたloginid、Passwordでログイン処理を行う
	* 
	* @param　loginid ログインID
	* @param　password パスワード
	* @return　
	*/
	UserDto login(String loginid, String password);

	/**
	* 有効な社員アカウントデータ一覧を取得する
	* 
	* @param　categoryCode 部署コード
	* @return　List<UserDto>
	*/
	List<UserDto> findByUsersList(int categoryCode);

	/**
	* 社員アカウントデータを取得する
	* 
	* @param　id 社員アカウントID
	* @param　categoryCode 部署コード
	* @return　UserDto
	*/
	UserDto findByUser(int id);

	/**
	* 既存データに登録するログインIDがあるかチェックする（自分自身は除く）
	* 
	* @param　id 社員アカウントID
	* @param　loginid ログインID
	* @return　false：無し、true：有り
	*/
	boolean existsByLoginid(int id, String loginid);

	/**
	* 社員アカウントデータを登録更新する
	* 
	* @param　userDto 社員アカウントデータ
	* @return
	*/
	void update(UserDto userDto);

	/**
	* 社員アカウントデータを停止する
	* 
	* @param　id 社員アカウントID
	* @param　updateId 更新者ID
	* @return　UserDto
	*/
	void accountStop(int id, int updateId);
	
	/**
	* attendance_approvalとJoinした社員データ一覧を取得する
	* 
	* @param　ym 対象年月
	* @return　List<UserApprovalDto>
	*/
	List<UserApprovalDto> findByUsersApprovalList(YearMonth ym);

	/**
	* 有給データを更新する
	* 
	* @param　id ユーザーID
	* @param　paidGrantDate 有給付与日
	* @param　paidLeaveGranted 有給付与日数
	* @param　paidLeaveRemaining 有給残日数
	* @return
	*/
	void paidLeaveUpdate(int id, String paidGrantDate, int paidLeaveGranted, float paidLeaveRemaining);

	/**
	* 有給残日数を更新する
	* 
	* @param　id ユーザーID
	* @param　paidLeaveRemaining 有給残日数
	* @return
	*/
	void paidLeaveRemainingUpdate(int id, float paidLeaveRemaining);
}
