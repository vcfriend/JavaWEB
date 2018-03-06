package cn.mrdear.jpa.dto;

/**
 * 查询结果DTO
 * @author 向亚林
 * 2018/3/5 17:15
 */
public class PersonIdCardDto {
    private String idNo;
    private String name;
    private String address;

    public PersonIdCardDto() {
    }

    public PersonIdCardDto(String idNo, String name, String address) {
        this.idNo = idNo;
        this.name = name;
        this.address = address;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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

    @Override
    public String toString() {
        return "PersonIdCardDto{" +
                "idNo='" + idNo + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
