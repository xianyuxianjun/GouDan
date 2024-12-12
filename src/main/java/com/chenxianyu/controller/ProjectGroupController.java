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
 *  前端控制器
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-12
 */
@CrossOrigin
@RestController
@RequestMapping("/projectGroup")
public class ProjectGroupController {

    @Autowired
    private IProjectGroupService projectGroupService;

    @PostMapping("/list")
    public R List() {
        return R.success(projectGroupService.list());
    }

    @PostMapping("/add")
    public R add(@RequestBody ProjectGroup projectGroup) {
        boolean save = projectGroupService.save(projectGroup);
        if (save){
            return R.success("添加成功");
        }
        return R.fail("添加失败");
    }

    @PostMapping("/detect")
    public R detect(@RequestBody ProjectGroup projectGroup) {
        boolean b = projectGroupService.removeById(projectGroup.getId());
        if (b){
            return R.success("删除成功");
        }
        return R.fail("删除失败");
    }

    @PostMapping("get")
    public R get(@RequestBody ProjectGroup projectGroup) {
        return R.success(projectGroupService.getById(projectGroup.getId()));
    }

    @PostMapping("/update")
    public R update(@RequestBody ProjectGroup projectGroup) {
        boolean b = projectGroupService.updateById(projectGroup);
        if (b){
            return R.success("修改成功");
        }
        return R.fail("修改失败");
    }

    @PostMapping("/ky")
    public R ky() {
        List<ProjectGroup> list = projectGroupService.list();
        List<KeyValue> keyValues = new ArrayList<>();
        for (ProjectGroup projectGroup : list) {
            KeyValue keyValue = new KeyValue();
            keyValue.setValue(projectGroup.getName());
            keyValue.setTitle(projectGroup.getName());
            keyValues.add(keyValue);
        }
        return R.success(keyValues);
    }

}
