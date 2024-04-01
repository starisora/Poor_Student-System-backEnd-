package com.wms.springboot.common;

import lombok.Data;

//统一返回数据
@Data
public class Result {
    private int code;//编码
    private String msg;//成功或者失败
    private long total;//总记录数
    private Object data;//数据

    //返回失败
    public static Result fail(){
        return result(400,"失败",0L,false);
    }

    //返回成功
    public static Result success(){
        return result(200,"成功",0L,true);
    }

    //返回成功带数据
    public static Result sucData(Object data){
        return result(200,"成功",0L,data);
    }

    //返回数据
    public static Result result(int code,String msg,long total,Object data){
        Result res = new Result();
        res.setData(data);
        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }
}
