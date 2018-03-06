import cn.mrdear.entity.QIDCard;
import cn.mrdear.entity.QPerson;
import cn.mrdear.jpa.dto.PersonIdCardDto;
import cn.mrdear.repository.PersonAndIdCardRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * @author 向亚林
 * 2018/3/5 17:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class PersonAndIdCardRepositoryTest {
    @Autowired
    private PersonAndIdCardRepository personAndIdCardRepository;

    @Test
    public void queryTest1() {
        List<Tuple> allPersonAndIdCard = personAndIdCardRepository.findAllPersonAndIdCard();
//        for (Tuple info : allPersonAndIdCard) {
//            System.out.println(info.get(QIDCard.iDCard.idNo)+" "+info.get(QPerson.person.name)+" "+info.get(QPerson.person.address));
//        }
        printInfo(allPersonAndIdCard, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address);
    }

    @Test
    public void queryTest2() {
        List<PersonIdCardDto> byDto = personAndIdCardRepository.findByDto();
        printInfo(byDto);
    }
    @Test
    public void queryTest3(){
        QueryResults<Tuple> byDtoAndPager = personAndIdCardRepository.findByDtoAndPager(2, 2);
        System.out.println("记录数: "+byDtoAndPager.getTotal());
        printInfo(byDtoAndPager.getResults());
    }
    @Test
    public void queryTest4() {
        printInfo(personAndIdCardRepository.findByDtoUseBean());
    }
    @Test
    public void queryTest5(){
        printInfo(personAndIdCardRepository.findByDtoUseFields());
    }
    @Test
    public void queryTest6() {
        printInfo(personAndIdCardRepository.findByDtoUseConstructor());
    }

    /**
     * 打印查询结果
     * @param info
     * @param <T>
     */
    public <T> void printInfo(List<T> info) {
        System.out.println("\n\n\n 打印查询查询: ");
        for (T t : info) {
            System.out.println(t);
        }
        System.out.println();
    }

    /**
     * 打印查询结果
     * @param results
     * @param infos com.querydsl.core.types.Expression 数组
     */
    public void printInfo(List<Tuple> results, Expression ... infos) {
        System.out.println("\n\n\n 打印查询结果: ");
        for (Tuple result : results) {
            for (Expression info : infos) {
                //info.toString() 为属性名,result.get(info).toString() 为获取属性的值
                System.out.print("    "+info.toString()+"   "+result.get(info).toString());
            }
            System.out.println();
        }
        System.out.println();
    }
}
