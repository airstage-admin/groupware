package com.groupware.employee.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.groupware.dto.UserDto;

/**
* UserService
* Userサービスインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface EmployeeService {
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
	 * ログインIDのみを更新する
	 * 
	 * @param　userDto 更新対象のIDと新しいログインIDを含むデータ
	 * @throws DataAccessException データアクセス層でエラーが発生した場合
	 */
	 void updateLoginId(UserDto userDto);

	/**
	* 社員アカウントデータを停止する
	* 
	* @param　id 社員アカウントID
	* @param　updateId 更新者ID
	* @return　UserDto
	*/
	void accountStop(int id, int updateId);
	
	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	boolean is_admin(int id);
	
	/**
	 * 現在のパスワードが正しいかチェックする
	 * 
	 * @param id ユーザーID
	 * @param currentPassword 入力された現在のパスワード
	 * @return true：一致（正しい）、false：不一致
	 */
	boolean checkPassword(int id, String currentPassword);

	/**
	 * パスワードを更新する
	 * 
	 * @param id ユーザーID
	 * @param newPassword 新しいパスワード
	 */
	void updatePassword(int id, String newPassword);
}
