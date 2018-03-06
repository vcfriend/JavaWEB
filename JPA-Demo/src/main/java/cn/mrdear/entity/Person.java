package cn.mrdear.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 描述：个人表
 * 用于演示一对一关系查询
 * @author chhliu
 */
@Entity
@Table(name="T_PERSON")
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String address;

    @OneToOne(mappedBy="person", cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private IDCard idCard;

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", address=" + address + ", idCard=" + idCard + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public IDCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }
}
