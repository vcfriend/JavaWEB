package cn.mrdear.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/6 14:09
 */
public interface OrderAndOrderItemRepositoryCustom {
    /**
     * 一对多，条件查询
     * @return
     */
    List<Tuple> findOrderAndOrderItemByOrderName(String orderName);

    /**
     * 多表连接查询
     * @param orderName
     * @return
     */
    List<Tuple> findAllByOrderName(String orderName);

}
