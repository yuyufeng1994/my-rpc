package top.yuyufeng.rpc.service.impl;

import top.yuyufeng.rpc.service.CalService;
import top.yuyufeng.rpc.service.top.yuyufeng.dto.MyResult;

/**
 * Created by yuyufeng on 2017/8/24.
 */
public class CalServiceImpl implements CalService {
    private String name = "yyf";

    @Override
    public MyResult getResult(int a, int b) {
        MyResult myResult = new MyResult();
        myResult.setId(2017l);
        myResult.setName(name);
        myResult.setResult(a + b);
        return myResult;
    }
}
