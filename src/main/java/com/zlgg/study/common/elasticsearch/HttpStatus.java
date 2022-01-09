package com.zlgg.study.common.elasticsearch;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 23:04
 * Description:
 */
public enum HttpStatus {
    /**
     * 正常返回
     */
    OK(200, "Ok"),
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "UnAuthorized"),
    /**
     * 服务端拒绝执行该请求
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * 错误
     */
    ERROR(500, "Error");


    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态描述
     */
    private final String status;

    /**
     * @param code   状态码
     * @param status 状态名称
     * @author Aylvn
     * @date 2020-01-01
     */
    HttpStatus(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    /**
     * 根据状态码获取状态描述
     *
     * @param code 状态码
     * @return 状态描述
     * @author Aylvn
     * @date 2020-01-01
     */
    public static String status(int code) {
        for (HttpStatus httpStatus : HttpStatus.values()) {
            if (httpStatus.code() == code) {
                return httpStatus.status();
            }
        }
        return "Unknown";
    }

    /**
     * 状态码
     *
     * @return 状态码
     * @author Aylvn
     * @date 2020-01-01
     */
    public int code() {
        return this.code;
    }

    /**
     * 状态描述
     *
     * @return 状态描述
     * @author Aylvn
     * @date 2020-01-01
     */
    public String status() {
        return this.status;
    }
}
