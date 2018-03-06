package cn.mrdear.repository;

import cn.mrdear.entity.User;
import cn.mrdear.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author 向亚林
 * 2018/3/5 11:14
 */
public interface UserRepository extends JpaRepository<User,Integer>, QueryDslPredicateExecutor<User>, UserRepositoryCustom {
}
