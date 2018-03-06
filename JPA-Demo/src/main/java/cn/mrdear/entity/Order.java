package cn.mrdear.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 描述：Order 订单实体类
 * 用于演示订单表与订单项表的多对多关系查询
 * @author chhliu
 */
@Entity
@Table(name="T_ORDER")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;

    @Column(length=20, name="ORDER_NAME")
    private String orderName;

    @Column(name="COUNT")
    private Integer count;

    @OneToMany(mappedBy = "order",cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", count=" + count +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
