package cn.mrdear.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 描述：OrderItem 订单项实体类
 * @author chhliu
 */
@Entity
@Table(name="ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;

    @Column(name="ITEM_NAME", length=20)
    private String itemName;

    @Column(name="PRICE")
    private Integer price;

    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", orderId=" + order.getId() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
