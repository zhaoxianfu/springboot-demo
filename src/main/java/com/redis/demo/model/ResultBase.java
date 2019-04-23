package com.redis.demo.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:ResultBase
 * @Despriction: 封装的返回值对象
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/18  15:24
 * @Version1.0
 **/

public class ResultBase<T> implements Serializable {
    private static final long serialVersionUID = -5618198094661572939L;

    private boolean isSuccess;
    private String code;
    private String msg;
    private T value;
    private String trackId;
    private boolean reality;
    private Map<String, Object> additionalInfo;

    public ResultBase() {
        this.isSuccess = false;
        this.reality = true;
        this.additionalInfo = new HashMap(1);
    }

    public ResultBase(T value) {
        this.isSuccess = false;
        this.reality = true;
        this.additionalInfo = new HashMap(1);
        this.isSuccess = true;
        this.value = value;
    }

    public ResultBase(String msg, String code) {
        this(false, msg, code);
    }

    public ResultBase(boolean success, String msg, String code) {
        this.isSuccess = false;
        this.reality = true;
        this.additionalInfo = new HashMap(1);
        this.isSuccess = success;
        this.msg = msg;
        this.code = code;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Map<String, Object> getAdditionalInfo() {
        return this.additionalInfo;
    }

    public void setAdditionalInfo(Map<String, Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setAdditionalInfo(String codeTemp, String msgTemp) {
        this.additionalInfo.put(codeTemp, msgTemp);
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTrackId() {
        return this.trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public ResultBase<T> fail(String code) {
        this.setSuccess(false);
        this.setCode(code);
        return this;
    }

    public ResultBase<T> fail(String code, String... msg) {
        this.fail(code);
        if (msg != null) {
            this.setMsg(StringUtils.join(msg, "|"));
        }

        return this;
    }

    public ResultBase<T> success(T responseDTo) {
        this.setSuccess(true);
        this.setValue(responseDTo);
        return this;
    }

    public ResultBase<T> mapper(ResultBase<?> biz) {
        this.setCode(biz.getCode());
        this.setMsg(biz.getMsg());
        this.setSuccess(biz.isSuccess());
        this.setTrackId(biz.getTrackId());
        this.setAdditionalInfo(biz.getAdditionalInfo());
        return this;
    }

    public void setTemplateMsg(String templateMsg) {
        this.msg = templateMsg;
        this.additionalInfo.put("additionalInfoTemplateCodeKey", true);
    }

    public boolean isGetMsgByCode() {
        if (StringUtils.isNotBlank(this.code)) {
            if (!this.code.startsWith("BIZ_") && !this.code.startsWith("SYS_")) {
                return false;
            } else {
                Object templateMsgObj = this.additionalInfo.get("additionalInfoTemplateCodeKey");
                return null == templateMsgObj || !((Boolean) templateMsgObj).equals(true);
            }
        } else {
            return false;
        }
    }

    public boolean isReality() {
        return this.reality;
    }

    public void setReality(boolean reality) {
        this.reality = reality;
    }

    @Override
    public String toString() {
        return "ResultBase{" +
                "isSuccess=" + isSuccess +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", value=" + value +
                ", trackId='" + trackId + '\'' +
                ", reality=" + reality +
                ", additionalInfo=" + additionalInfo +
                '}';
    }

}
