package cn.mrdear.repository.custom;

import cn.mrdear.jpa.dto.PersonIdCardDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/5 16:53
 */
public interface PersonAndIdCardRepositoryCustom {
    /**
     * 多表动态查询
     * @return
     */
    List<Tuple> findAllPersonAndIdCard();

    /**
     * 将查询结果以DTO的方式输出
     * @return
     */
    List<PersonIdCardDto> findByDto();

    /**
     * 多表动态查询，并分页
     * @param offset
     * @param pageSize
     * @return
     */
    QueryResults<Tuple> findByDtoAndPager(final int offset, final int pageSize);

    /**
     * QueryDSL使用Bean进行投影查询
     * @return
     */
    List<PersonIdCardDto> findByDtoUseBean();

    /**
     * QueryDSL使用fields来代替setter
     * @return
     */
    List<PersonIdCardDto> findByDtoUseFields();

    /**
     * QueryDSL使用构造方法，注意构造方法中属性的顺序必须和构造器中的顺序一致
     * @return
     */
    List<PersonIdCardDto> findByDtoUseConstructor();
}
