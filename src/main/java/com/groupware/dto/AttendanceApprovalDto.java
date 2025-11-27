package com.groupware.dto;

import java.time.LocalDate;

/**
* AttendanceApprovalDto
* attendance_approvalテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/
public class AttendanceApprovalDto {
	private long id;
	private long user_id;
	private LocalDate working_day;
	private boolean approval;
	private int approval_by;
	private boolean delflg;
	private int created_by;
	private String created_at;
	private int updated_by;
	private String updated_at;

	// 承認ID
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// ユーザーID
	public long getUserId() {
		return user_id;
	}

	public void setUserId(long user_id) {
		this.user_id = user_id;
	}

	// 勤怠年月日
	public LocalDate getWorkingDay() {
		return working_day;
	}

	public void setWorkingDay(LocalDate working_day) {
		this.working_day = working_day;
	}

	// 承認区分
	public boolean getApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	
	// 承認者ID
	public int getApprovalBy() {
		return approval_by;
	}

	public void setApprovalBy(int approval_by) {
		this.approval_by = approval_by;
	}
	
	// 削除フラグ
	public boolean getDelflg() {
		return delflg;
	}

	public void setDelflg(boolean delflg) {
		this.delflg = delflg;
	}
	
	// 作成者ID
	public int getCreatedBy() {
		return created_by;
	}

	public void setCreatedBy(int created_by) {
		this.created_by = created_by;
	}

	// 作成日時
	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}

	// 更新者ID
	public int getUpdatedBy() {
		return updated_by;
	}

	public void setUpdatedBy(int updated_by) {
		this.updated_by = updated_by;
	}

	// 更新日時
	public String getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
}
