-- 考勤记录表
CREATE TABLE attendance_record (
                                   id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                   user_id VARCHAR(20) NOT NULL COMMENT '用户编号，与user表的userId对应',
                                   name VARCHAR(50) NOT NULL COMMENT '姓名，与user表的name对应',
                                   group_name VARCHAR(100) NULL COMMENT '所属团队/项目组名称，与user表的group_name对应',
                                   attendance_date DATE NOT NULL COMMENT '考勤日期',
                                   check_in TIME NULL COMMENT '签到时间',
                                   check_out TIME NULL COMMENT '签退时间',
                                   status ENUM('未签到', '正常', '迟到', '早退', '缺勤') DEFAULT '未签到' COMMENT '考勤状态',
                                   work_hours DECIMAL(4,1) NULL COMMENT '工作时长(小时)',
                                   remark VARCHAR(255) NULL COMMENT '备注信息',
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
                                   INDEX idx_user_id (user_id),
                                   INDEX idx_attendance_date (attendance_date),
                                   INDEX idx_status (status)
) COMMENT='考勤记录表';

-- 考勤规则表
CREATE TABLE attendance_rule (
                                 id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                 rule_name VARCHAR(50) NOT NULL COMMENT '规则名称',
                                 work_start_time TIME NOT NULL COMMENT '上班时间',
                                 work_end_time TIME NOT NULL COMMENT '下班时间',
                                 late_threshold INT NOT NULL DEFAULT 0 COMMENT '迟到阈值(分钟)',
                                 early_threshold INT NOT NULL DEFAULT 0 COMMENT '早退阈值(分钟)',
                                 absent_threshold INT NOT NULL DEFAULT 0 COMMENT '缺勤阈值(分钟)',
                                 status TINYINT(1) DEFAULT 1 COMMENT '规则状态：0停用，1启用',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
) COMMENT='考勤规则表';

-- 考勤异常申诉表
CREATE TABLE attendance_appeal (
                                   id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                   attendance_id INT NOT NULL COMMENT '关联的考勤记录ID',
                                   user_id VARCHAR(20) NOT NULL COMMENT '申诉人编号，与user表的userId对应',
                                   name VARCHAR(50) NOT NULL COMMENT '申诉人姓名，与user表的name对应',
                                   appeal_type ENUM('漏打卡', '打卡失败', '其他') NOT NULL COMMENT '申诉类型',
                                   appeal_reason TEXT NOT NULL COMMENT '申诉原因',
                                   proof_materials VARCHAR(500) NULL COMMENT '证明材料（图片/文件URL，多个用逗号分隔）',
                                   status ENUM('待审核', '已通过', '已拒绝') DEFAULT '待审核' COMMENT '审核状态',
                                   handler_id VARCHAR(20) NULL COMMENT '处理人编号，与user表的userId对应',
                                   handle_time DATETIME NULL COMMENT '处理时间',
                                   handle_result VARCHAR(255) NULL COMMENT '处理结果',
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
                                   INDEX idx_attendance_id (attendance_id),
                                   INDEX idx_user_id (user_id),
                                   INDEX idx_status (status)
) COMMENT='考勤异常申诉表';

-- 考勤统计表（按月统计）
CREATE TABLE attendance_statistics (
                                       id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                       user_id VARCHAR(20) NOT NULL COMMENT '用户编号，与user表的userId对应',
                                       name VARCHAR(50) NOT NULL COMMENT '姓名，与user表的name对应',
                                       group_name VARCHAR(100) NULL COMMENT '所属团队/项目组名称，与user表的group_name对应',
                                       statistics_month VARCHAR(7) NOT NULL COMMENT '统计月份(格式：YYYY-MM)',
                                       total_days INT NOT NULL DEFAULT 0 COMMENT '应出勤天数',
                                       actual_days INT NOT NULL DEFAULT 0 COMMENT '实际出勤天数',
                                       normal_days INT NOT NULL DEFAULT 0 COMMENT '正常出勤天数',
                                       late_times INT NOT NULL DEFAULT 0 COMMENT '迟到次数',
                                       early_times INT NOT NULL DEFAULT 0 COMMENT '早退次数',
                                       absent_times INT NOT NULL DEFAULT 0 COMMENT '缺勤次数',
                                       total_work_hours DECIMAL(6,1) NOT NULL DEFAULT 0 COMMENT '总工作时长',
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
                                       UNIQUE KEY uk_user_month (user_id, statistics_month),
                                       INDEX idx_statistics_month (statistics_month)
) COMMENT='考勤统计表';
