package com.zlh.mybatis.bean;

public enum EmpStatus {

    /**
     * 让数据库保存的是状态码
     */
    LOGIN(100,"用户登录"),
    LOGOUT(200,"用户登出"),
    REMOVE(300,"用户不存在");

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private  EmpStatus(Integer code, String msg) {
       this.code = code;
       this.msg = msg;
    }

    public static EmpStatus getEmpStatusByCode(Integer code){
        switch (code){
            case 100 : return LOGIN;
            case 200 : return LOGOUT;
            case 300 : return REMOVE;
            default: return LOGOUT;
        }
    }

}
