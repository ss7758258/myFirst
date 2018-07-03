package com.xz.framework.common.base;

/**
 * ajax请求返回Bean
 *
 * @param <T>
 * @author jack
 */
public class AjaxBean<T> extends BasicBean {

    private static final long serialVersionUID = 1L;

    /**
     * 返回数据集
     */
    private T data;
    /**
     * 返回状态
     */
    private AjaxStatus status;
    /**
     * 返回错误消息
     */
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AjaxStatus getStatus() {
        return status;
    }

    public void setStatus(AjaxStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
