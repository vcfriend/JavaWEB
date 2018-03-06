package cn.mrdear.repository;

import cn.mrdear.entity.Person;
import cn.mrdear.repository.custom.PersonAndIdCardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author 向亚林
 * 2018/3/5 16:52
 */
public interface PersonAndIdCardRepository extends JpaRepository<Person,Integer>, QueryDslPredicateExecutor<Person>, PersonAndIdCardRepositoryCustom {
}
