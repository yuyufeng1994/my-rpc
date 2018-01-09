package top.yuyufeng.rpc.service.impl;

import top.yuyufeng.rpc.service.CalService;
import top.yuyufeng.rpc.service.top.yuyufeng.dto.MyResult;


/**
 * 测试Service CalServiceImpl
 * @author yuyufeng
 */
public class CalServiceImpl implements CalService {
    private String name = "yyf";

    @Override
    public MyResult getResult(int a, int b) {
        MyResult myResult = new MyResult();
        myResult.setId(2017L);
        myResult.setName(name);
        myResult.setResult(a + b);
        return myResult;
    }
}
