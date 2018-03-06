package cn.mrdear.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 描述：身份ID
 * @author chhliu
 */
@Entity
@Table(name="T_IDCARD")
public class IDCard {
    @Id
    @GeneratedValue
    private Integer id;
    private String idNo;
    @OneToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST}, fetch=FetchType.EAGER)
    private Person person;

    @Override
    public String toString() {
        return "IDCard [id=" + id + ", idNo=" + idNo + ", person=" + person + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}