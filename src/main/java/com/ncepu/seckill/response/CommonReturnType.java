package com.ncepu.seckill.response;

public class CommonReturnType {

    // 表明对应请求的返回处理结果是"success"或"fail"
    private String status;
    // status = "success" data返回前端需要的数据
    // status = "fail" data返回通用错误码格式
    private Object data;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
