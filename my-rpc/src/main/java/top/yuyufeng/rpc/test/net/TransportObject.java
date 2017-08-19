package top.yuyufeng.rpc.test.net;

import java.io.Serializable;
import java.util.Date;

/**
 * 通信对象数据结构
 * created by yuyufeng on 2017/8/18.
 */
public class TransportObject implements Serializable{
    private Integer id;
    private String content;
    private Date date;

    public TransportObject() {
    }

    public TransportObject(Integer id, String content) {
        this.id = id;
        this.content = content;
        this.date = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TransportObject{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
