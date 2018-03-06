package cn.mrdear.repository.custom;

import cn.mrdear.entity.IDCard;
import cn.mrdear.entity.QIDCard;
import cn.mrdear.entity.QPerson;
import cn.mrdear.jpa.dto.PersonIdCardDto;
import cn.mrdear.repository.BaseRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 向亚林
 * 2018/3/5 17:24
 */
@Component
@Transactional
public class PersonAndIdCardRepositoryImpl extends BaseRepository implements PersonAndIdCardRepositoryCustom {
    /**
     * 多表动态查询
     * Person与IDCard是一对一的关系
     *
     * @return
     */
    @Override
    public List<Tuple> findAllPersonAndIdCard() {
        Predicate predicate = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.id.intValue());
        return queryFactory.select(QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address)
                .from(QIDCard.iDCard, QPerson.person)
                .where(predicate)
                .fetch();
    }

    /**
     * 将查询结果以DTO的方式输出
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDto() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        JPAQuery<Tuple> jpaQuery = queryFactory.select(QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address)
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression);
        List<Tuple> tuples = jpaQuery.fetch();
        List<PersonIdCardDto> personIdCardDtoList = new ArrayList<>();
        if (null != tuples && !tuples.isEmpty()) {
            for (Tuple tuple : tuples) {
                String idCard = tuple.get(QIDCard.iDCard.idNo);
                String name = tuple.get(QPerson.person.name);
                String address = tuple.get(QPerson.person.address);
                PersonIdCardDto personIdCardDto = new PersonIdCardDto(idCard, name, address);
                personIdCardDtoList.add(personIdCardDto);
            }
        }
        return personIdCardDtoList;
    }

    /**
     * 多表动态查询，并分页
     *
     * @param offset
     * @param pageSize
     * @return
     */
    @Override
    public QueryResults<Tuple> findByDtoAndPager(int offset, int pageSize) {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        return queryFactory.select(QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address)
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .offset(offset)
                .limit(pageSize)
                .fetchResults();
    }

    /**
     * QueryDSL使用Bean进行投影查询
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDtoUseBean() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        List<PersonIdCardDto> fetch = queryFactory.select(Projections.bean(PersonIdCardDto.class, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address))
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .fetch();
        return fetch;
    }

    /**
     * QueryDSL使用fields来代替setter
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDtoUseFields() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        return queryFactory.select(Projections.fields(PersonIdCardDto.class, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address))
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .fetch();
    }

    /**
     * QueryDSL使用构造方法，注意构造方法中属性的顺序必须和构造器中的顺序一致
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDtoUseConstructor() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        return queryFactory.select(Projections.constructor(PersonIdCardDto.class, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address))
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .fetch();
    }
}
