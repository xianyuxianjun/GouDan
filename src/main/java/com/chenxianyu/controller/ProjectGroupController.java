package com.chenxianyu.controller;

import com.chenxianyu.entity.ProjectGroup;
import com.chenxianyu.entity.R;
import com.chenxianyu.entity.vo.KeyValue;
import com.chenxianyu.service.IProjectGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 项目组管理控制器
 * 处理项目组相关的所有HTTP请求
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-12
 */
@CrossOrigin  // 允许跨域请求
@RestController
@RequestMapping("/projectGroup")
public class ProjectGroupController {

    /**
     * 注入项目组服务
     */
    @Autowired
    private IProjectGroupService projectGroupService;

    /**
     * 获取所有项目组列表
     * @return 返回所有项目组数据
     */
    @PostMapping("/list")
    public R list() {
        return R.success(projectGroupService.list());
    }

    /**
     * 添加新的项目组
     * @param projectGroup 项目组实体
     * @return 添加操作的结果
     */
    @PostMapping("/add")
    public R add(@RequestBody ProjectGroup projectGroup) {
        boolean result = projectGroupService.save(projectGroup);
        if (result){
            return R.success("添加成功");
        }
        return R.fail("添加失败");
    }

    /**
     * 删除项目组
     * @param projectGroup 需要删除的项目组实体（仅需ID）
     * @return 删除操作的结果
     */
    @PostMapping("/detect")
    public R detect(@RequestBody ProjectGroup projectGroup) {
        boolean result = projectGroupService.removeById(projectGroup.getId());
        if (result){
            return R.success("删除成功");
        }
        return R.fail("删除失败");
    }

    /**
     * 根据ID获取项目组信息
     * @param projectGroup 包含目标ID的项目组实体
     * @return 查询到的项目组信息
     */
    @PostMapping("get")
    public R get(@RequestBody ProjectGroup projectGroup) {
        return R.success(projectGroupService.getById(projectGroup.getId()));
    }

    /**
     * 更新项目组信息
     * @param projectGroup 需要更新的项目组实体
     * @return 更新操作的结果
     */
    @PostMapping("/update")
    public R update(@RequestBody ProjectGroup projectGroup) {
        boolean result = projectGroupService.updateById(projectGroup);
        if (result){
            return R.success("修改成功");
        }
        return R.fail("修改失败");
    }

    /**
     * 获取项目组的键值对列表
     * 将项目组信息转换为前端下拉框所需的键值对格式
     * @return 包含项目组名称的键值对列表
     */
    @PostMapping("/ky")
    public R ky() {
        List<ProjectGroup> groups = projectGroupService.list();
        List<KeyValue> keyValues = new ArrayList<>();
        for (ProjectGroup group : groups) {
            KeyValue keyValue = new KeyValue();
            keyValue.setValue(group.getName());
            keyValue.setTitle(group.getName());
            keyValues.add(keyValue);
        }
        return R.success(keyValues);
    }
}
