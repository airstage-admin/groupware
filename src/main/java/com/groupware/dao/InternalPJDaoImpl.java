package com.groupware.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.InternalPJDto;

@Repository // これをつけると「私はDAOの実装ですよ」と認識される
public class InternalPJDaoImpl implements InternalPJDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate; // SQLを実行する道具

    @Override
    public List<InternalPJDto> selectInternalProjectWorkTime(String targetMonth) {
        
        // SQL文（:targetMonth がプレースホルダーになります）
        String sql = """
            SELECT 
                u.employee_no AS employeeNo, 
                u.user_name AS username, 
                COALESCE(SUM(T2.rounded_work_time_h), 0) AS totalHours
            FROM users u 
            INNER JOIN ( 
                SELECT 
                    T1.user_id, 
                    (FLOOR( T1.net_work_minutes_day / 15 ) * 15) / 60.0 AS rounded_work_time_h
                FROM (
                    SELECT 
                        a.user_id, 
                        a.working_day, 
                        SUM( (TIME_TO_SEC(a.clock_out) - TIME_TO_SEC(a.clock_in)) / 60 - (TIME_TO_SEC(COALESCE(a.break_time, '00:00:00')) / 60) - (TIME_TO_SEC(COALESCE(a.night_break_time, '00:00:00')) / 60) ) AS net_work_minutes_day
                    FROM attendance a 
                    WHERE DATE_FORMAT(a.working_day, '%Y-%m') = :targetMonth 
                      AND a.delflg = 0 
                      AND a.clock_in IS NOT NULL AND a.clock_in != '00:00:00' 
                      AND a.clock_out IS NOT NULL AND a.clock_out != '00:00:00' 
                      AND vacation_category = 18
                    GROUP BY a.user_id, a.working_day
                ) AS T1
            ) AS T2 ON u.id = T2.user_id 
            WHERE u.delflg = 0 AND u.department != 1 
            GROUP BY u.id, u.employee_no, u.user_name 
            ORDER BY u.employee_no ASC
        """;

        // パラメータの設定
        Map<String, Object> params = Map.of("targetMonth", targetMonth);

        // SQL実行と結果の詰め込み
        // BeanPropertyRowMapperを使うと、SQLの結果(totalHours)をDTOの変数(totalHours)に自動で入れてくれます
        return jdbcTemplate.query(
                sql, 
                params, 
                new BeanPropertyRowMapper<>(InternalPJDto.class)
        );
    }
}
