package com.chenxianyu.entity;

public class R {
    private int code;
    private String msg;
    private Object data;

    public R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R success(Object data) {
        return new R(1, "success", data);
    }
    public static R success(Object data,String meg) {
        return new R(1, meg, data);
    }


    public static R success() {
        return new R(1, "success", null);
    }
    public static R success(String msg) {
        return new R(1, msg, null);
    }

    public static R fail(String msg) {
        return new R(0, msg, null);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
