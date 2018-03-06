package cn.mrdear.repository.custom;

import cn.mrdear.entity.QOrder;
import cn.mrdear.entity.QOrderItem;
import cn.mrdear.repository.BaseRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/6 14:19
 */
public class OrderAndOrderItemRepositoryImpl extends BaseRepository implements OrderAndOrderItemRepositoryCustom {
    /**
     * 一对多，条件查询
     *
     * @param orderName
     * @return
     */
    @Override
    public List<Tuple> findOrderAndOrderItemByOrderName(String orderName) {
        BooleanExpression expression = QOrder.order.orderName.eq(orderName);
        return queryFactory.select(QOrder.order, QOrderItem.orderItem)
                .from(QOrder.order, QOrderItem.orderItem)
                .where(QOrder.order.id.intValue().eq(QOrderItem.orderItem.order.id.intValue()), expression)
                .fetch();
    }

    /**
     * 多表连接查询
     *
     * @param orderName
     * @return
     */
    @Override
    public List<Tuple> findAllByOrderName(String orderName) {
        BooleanExpression expression = QOrder.order.orderName.eq(orderName);
        JPAQuery<Tuple> jpaQuery = queryFactory.select(QOrder.order, QOrderItem.orderItem)
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem)
                .on((QOrder.order.id.intValue()).eq(QOrderItem.orderItem.order.id.intValue()))
                .where(expression);
        return jpaQuery.fetch();

    }
}
