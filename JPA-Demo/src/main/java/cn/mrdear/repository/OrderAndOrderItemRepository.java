package cn.mrdear.repository;

import cn.mrdear.entity.Order;
import cn.mrdear.repository.custom.OrderAndOrderItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author 向亚林
 * 2018/3/6 14:08
 */
public interface OrderAndOrderItemRepository extends JpaRepository<Order, Integer>, QueryDslPredicateExecutor<Order>, OrderAndOrderItemRepositoryCustom {
}
