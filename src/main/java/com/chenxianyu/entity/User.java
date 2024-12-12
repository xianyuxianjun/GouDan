package com.chenxianyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-11
 */
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号，作为主键
     */
    @TableId("userId")
    private String userId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Byte age;

    /**
     * 电子邮件地址，唯一
     */
    @TableField("email")
    private String email;

    /**
     * 班级名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 专业
     */
    @TableField("major")
    private String major;

    /**
     * 职位/角色
     */
    @TableField("post")
    private String post;

    /**
     * 成员状态，默认为在岗
     */
    @TableField("status")
    private String status;

    /**
     * 所属团队/项目组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 记录创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 记录更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 密码
     */
    @TableField("password")
    private String password;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
