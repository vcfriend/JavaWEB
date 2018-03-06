import cn.mrdear.entity.QOrder;
import cn.mrdear.entity.QOrderItem;
import cn.mrdear.repository.OrderAndOrderItemRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/6 14:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class OrderAndOrderItemRepositoryTest {
    @Autowired
    OrderAndOrderItemRepository orderAndOrderItemRepository;

    @Test
    public void queryTest1(){
        List<Tuple> cn111 = orderAndOrderItemRepository.findOrderAndOrderItemByOrderName("CN111");
        printInfo(cn111, QOrder.order, QOrderItem.orderItem);
    }
    @Test
    public void queryTest2(){
        List<Tuple> cn111 = orderAndOrderItemRepository.findAllByOrderName("CN222");
        printInfo(cn111,QOrder.order, QOrderItem.orderItem);
    }

    /**
     * 打印查询结果
     * @param results
     * @param <T>
     */
    public <T> void printInfo(List<T> results) {
        System.out.println("\n\n\n 打印查询结果: ");
        if (null == results ) return;
        for (T result : results) {
            System.out.print(result);
        }
        System.out.println();
    }

    public void printInfo(List<Tuple> results, Expression... infos) {
    System.out.println("\n\n\n 打印查询结果: ");
    if (null == results) return;
        for (Tuple result : results) {
            for (Expression info : infos) {
                System.out.print("   "+info.toString()+"   "+result.get(info).toString());
            }
            System.out.println();
        }
        System.out.println();
    }
}
