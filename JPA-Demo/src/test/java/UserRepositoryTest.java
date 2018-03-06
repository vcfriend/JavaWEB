import cn.mrdear.entity.QUser;
import cn.mrdear.entity.User;
import cn.mrdear.repository.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/5 11:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    //动态条件
    QUser qUser = QUser.user;

    @Test
    public void queryTest1(){
        BooleanExpression zhangsan = qUser.name.eq("zhangsan");
        User user = userRepository.findOne(zhangsan);
        System.out.println("\n\n\n user: "+user);
    }
    @Test
    public void queryTest2(){
        User user = userRepository.findOneByUserName("zhangsan");
        System.out.println("\n\n\n user: "+user);
    }
    @Test
    public void queryTest3(){
        List<User> all = userRepository.findAll();
        pringInfo(all);
    }
    @Test
    public void queryTest4(){
        User wangwu = userRepository.findOneByUserNameAndAddress("wangwu", "3333");
        System.out.println("wangwu: "+wangwu);
    }
    @Test
    public void queryTest5(){
        QUser user = new QUser("name");
        List<User> usersByJoin = userRepository.findUsersByJoin(user);
        pringInfo(usersByJoin);
    }
    @Test
    public void queryTest6(){
        pringInfo(userRepository.findUsersByOrder());
    }
    @Test
    public void queryTest7() {
        pringInfo(userRepository.findUserByGroup());
    }
    @Test
    public void queryTest8() {
        long bao3 = userRepository.deleteUser("bao3");
        System.out.println("删除影响行数: "+bao3);
    }
    @Test
    public void queryTest9() {
        User user = new User("bao4", 88, "8888");
        long bao3 = userRepository.updateUser(user, "bao3");
        System.out.println("修改影响行数: "+bao3);
    }
    @Test
    public void queryTest10() {
        User bao4 = userRepository.findOneUserByOriginalSql("bao4");
        System.out.println("bao4: "+bao4);
    }
    @Test
    public void queryTest11() {
        Page<User> allByPage = userRepository.findAllByPage(0, 3);
        pringInfo(allByPage.getContent());
    }
    @Test
    public void queryTest12() {
        List<User> userByJoinUser = userRepository.findUserByJoinUser(18);
        pringInfo(userByJoinUser);
    }

    public <T> void pringInfo(List<T> info) {
        System.out.println("\n\n\n 显示查询结果:");
        for (T t : info) {
            System.out.println(t);
        }
        System.out.println();
    }
}
