package com.chenxianyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chenxianyu.entity.LeaveApplication;
import com.chenxianyu.entity.R;
import com.chenxianyu.service.ILeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 请假申请表 前端控制器
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-15
 */
@CrossOrigin
@RestController
@RequestMapping("/leaveApplication")
public class LeaveApplicationController {

    @Autowired
    private ILeaveApplicationService leaveApplicationService;

    /**
     * 获取当前用户的请假申请列表
     * @param leaveApplication 包含用户ID的请假申请实体
     * @return 该用户的所有请假申请列表
     */
    @PostMapping("/myList")
    public R getMyList(@RequestBody LeaveApplication leaveApplication) {
        List<LeaveApplication> list = leaveApplicationService.list(
                new QueryWrapper<LeaveApplication>()
                        .eq("user_id", leaveApplication.getUserId())
                        .orderByDesc("created_at")
        );
        return R.success(list);
    }

    /**
     * 获取所有请假申请（管理员接口）
     * @return 所有请假申请列表
     */
    @PostMapping("/list")
    public R list() {
        List<LeaveApplication> list = leaveApplicationService.list(
                new QueryWrapper<LeaveApplication>()
                        .orderByDesc("created_at")
        );
        return R.success(list);
    }

    /**
     * 根据月份获取请假申请
     * @param params 包含用户ID和月份的参数Map
     * @return 指定月份的请假申请列表
     */
    @PostMapping("/getByMonth")
    public R getByMonth(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String month = params.get("month"); // 格式：YYYY-MM

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(month)) {
            return R.fail("参数不能为空");
        }

        QueryWrapper<LeaveApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .apply("DATE_FORMAT(start_time, '%Y-%m') = {0}", month)
                .orderByDesc("created_at");

        List<LeaveApplication> list = leaveApplicationService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 提交请假申请
     * @param leaveApplication 请假申请信息
     * @return 提交结果
     */
    @PostMapping("/submit")
    public R submit(@RequestBody LeaveApplication leaveApplication) {
        leaveApplication.setStatus("待审批");
        boolean success = leaveApplicationService.save(leaveApplication);
        return success ? R.success("提交成功") : R.fail("提交失败");
    }

    /**
     * 更新请假申请
     * @param leaveApplication 需要更新的请假申请信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public R update(@RequestBody LeaveApplication leaveApplication) {
        // 只允许更新待审批的申请
        LeaveApplication original = leaveApplicationService.getById(leaveApplication.getId());
        if (original == null || !"待审批".equals(original.getStatus())) {
            return R.fail("只能修改待审批的申请");
        }

        boolean success = leaveApplicationService.updateById(leaveApplication);
        return success ? R.success("更新成功") : R.fail("更新失败");
    }

    /**
     * 取消请假申请
     * @param leaveApplication 包含申请ID的请假申请实体
     * @return 取消结果
     */
    @PostMapping("/cancel")
    public R cancel(@RequestBody LeaveApplication leaveApplication) {
        LeaveApplication original = leaveApplicationService.getById(leaveApplication.getId());
        if (original == null || !"待审批".equals(original.getStatus())) {
            return R.fail("只能取消待审批的申请");
        }

        boolean success = leaveApplicationService.removeById(leaveApplication.getId());
        return success ? R.success("取消成功") : R.fail("取消失败");
    }

    /**
     * 审批通过请假申请
     * @param leaveApplication 包含审批信息的请假申请实体
     * @return 审批结果
     */
    @PostMapping("/approve")
    public R approve(@RequestBody LeaveApplication leaveApplication) {
        LeaveApplication toUpdate = new LeaveApplication();
        toUpdate.setId(leaveApplication.getId());
        toUpdate.setStatus("已批准");
        toUpdate.setApproverId(leaveApplication.getApproverId());
        toUpdate.setApproverName(leaveApplication.getApproverName());
        toUpdate.setApprovalTime(LocalDateTime.now());
        toUpdate.setApprovalRemark(leaveApplication.getApprovalRemark());

        boolean success = leaveApplicationService.updateById(toUpdate);
        return success ? R.success("审批通过") : R.fail("审批失败");
    }

    /**
     * 拒绝请假申请
     * @param leaveApplication 包含审批信息的请假申请实体
     * @return 审批结果
     */
    @PostMapping("/reject")
    public R reject(@RequestBody LeaveApplication leaveApplication) {
        LeaveApplication toUpdate = new LeaveApplication();
        toUpdate.setId(leaveApplication.getId());
        toUpdate.setStatus("已拒绝");
        toUpdate.setApproverId(leaveApplication.getApproverId());
        toUpdate.setApproverName(leaveApplication.getApproverName());
        toUpdate.setApprovalTime(LocalDateTime.now());
        toUpdate.setApprovalRemark(leaveApplication.getApprovalRemark());

        boolean success = leaveApplicationService.updateById(toUpdate);
        return success ? R.success("已拒绝") : R.fail("操作失败");
    }

    /**
     * 获取请假统计信息
     * @param params 包含用户ID和年份的参数Map
     * @return 统计信息
     */
    @PostMapping("/statistics")
    public R getStatistics(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String year = params.get("year");

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(year)) {
            return R.fail("参数不能为空");
        }

        QueryWrapper<LeaveApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .apply("YEAR(start_time) = {0}", year)
                .eq("status", "已批准");

        List<LeaveApplication> list = leaveApplicationService.list(queryWrapper);

        // 计算各类请假总天数
        Map<String, BigDecimal> statistics = new HashMap<>();
        for (LeaveApplication record : list) {
            String type = record.getLeaveType();
            BigDecimal duration = record.getDuration();
            statistics.merge(type, duration, BigDecimal::add);
        }

        return R.success(statistics);
    }
}
