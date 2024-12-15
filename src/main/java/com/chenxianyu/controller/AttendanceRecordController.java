package com.chenxianyu.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chenxianyu.entity.AttendanceRecord;
import com.chenxianyu.entity.R;
import com.chenxianyu.service.IAttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤记录表控制器
 * 处理考勤记录相关的所有HTTP请求
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-13
 */
@CrossOrigin
@RestController
@RequestMapping("/attendanceRecord")
public class AttendanceRecordController {
    /**
     * 注入考勤记录服务
     */
    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    /**
     * 获取所有考勤记录列表
     * @return 返回所有考勤记录数据
     */
    @PostMapping("/list")
    public R list(){
        List<AttendanceRecord> list = attendanceRecordService.list();
        return R.success(list);
    }

    /**
     * 添加新的考勤记录
     * @param attendanceRecord 考勤记录实体
     * @return 添加操作的结果
     */
    @PostMapping("/add")
    public R add(@RequestBody AttendanceRecord attendanceRecord){
        boolean flag = attendanceRecordService.save(attendanceRecord);
        if(flag){
            return R.success("添加成功");
        }else{
            return R.fail("添加失败");
        }
    }

    /**
     * 更新考勤记录信息
     * @param attendanceRecord 需要更新的考勤记录实体
     * @return 更新操作的结果
     */
    @PostMapping("/update")
    public R update(@RequestBody AttendanceRecord attendanceRecord){
        boolean flag = attendanceRecordService.updateById(attendanceRecord);
        if(flag){
            return R.success("修改成功");
        }else{
            return R.fail("修改失败");
        }
    }

    /**
     * 删除考勤记录
     * @param attendanceRecord 需要删除的考勤记录实体（仅需ID）
     * @return 删除操作的结果
     */
    @PostMapping("/delete")
    public R delete(@RequestBody AttendanceRecord attendanceRecord){
        boolean flag = attendanceRecordService.removeById(attendanceRecord.getId());
        if(flag){
            return R.success("删除成功");
        }else{
            return R.fail("删除失败");
        }
    }

    /**
     * 根据ID查询单条考勤记录
     * @param attendanceRecord 包含目标ID的考勤记录实体
     * @return 查询到的考勤记录信息
     */
    @PostMapping("/getById")
    public R getById(@RequestBody AttendanceRecord attendanceRecord){
        AttendanceRecord record = attendanceRecordService.getById(attendanceRecord.getId());
        return R.success(record);
    }

    /**
     * 根据用户ID查询考勤记录
     * @param attendanceRecord 包含用户ID的考勤记录实体
     * @return 该用户的所有考勤记录列表
     */
    @PostMapping("/getByUserId")
    public R getByUserId(@RequestBody AttendanceRecord attendanceRecord){
        List<AttendanceRecord> list = attendanceRecordService.list(new QueryWrapper<AttendanceRecord>().eq("user_id", attendanceRecord.getUserId()));
        return R.success(list);
    }

    /**
     * 根据日期查询考勤记录
     * @param attendanceRecord 包含目标日期的考勤记录实体
     * @return 指定日期的考勤记录
     */
    @PostMapping("/getByDate")
    public R getByDate(@RequestBody AttendanceRecord attendanceRecord) {
        QueryWrapper<AttendanceRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attendance_date", attendanceRecord.getAttendanceDate())
                .eq("user_id", attendanceRecord.getUserId());

        try {
            // 查询指定日期的考勤记录
            List<AttendanceRecord> records = attendanceRecordService.list(queryWrapper);

            // 返回结果
            if (records != null && !records.isEmpty()) {
                return R.success( records,"查询成功");
            } else {
                return R.success("未找到考勤记录");
            }
        } catch (Exception e) {
            return R.fail("查询失败");
        }
    }

    @PostMapping("/getByMonth")
    public R getByMonth(@RequestBody Map<String, String> params) {
        try {
            String userId = params.get("userId");
            String month = params.get("month");  // 格式应为 "YYYY-MM"

            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(month)) {
                return R.fail("参数不能为空");
            }

            // 构建查询条件
            QueryWrapper<AttendanceRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId)
                    .apply("DATE_FORMAT(attendance_date, '%Y-%m') = {0}", month)
                    .orderByAsc("attendance_date");

            // 执行查询
            List<AttendanceRecord> list = attendanceRecordService.list(queryWrapper);

            return R.success(list,"查询成功");
        } catch (Exception e) {
            return R.fail("获取月度考勤记录失败");
        }
    }
}
