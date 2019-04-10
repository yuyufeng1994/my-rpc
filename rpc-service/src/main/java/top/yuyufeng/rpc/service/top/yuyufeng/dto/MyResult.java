package top.yuyufeng.rpc.service.top.yuyufeng.dto;

/**
 * 测试实体类
 * @author yuyufeng
 * @date 2017/8/24
 */
public class MyResult{
    private Long id;
    private String name;
    private Integer result;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", result=" + result +
                '}';
    }
}
