package cn.mrdear.repository.custom;

import cn.mrdear.entity.QUser;
import cn.mrdear.entity.User;
import cn.mrdear.repository.BaseRepository;
import cn.mrdear.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * @author 向亚林
 * 2018/3/5 11:25
 */
@Component
@Transactional
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {
    @Autowired
    UserRepository userRepository;
    /**
     * 按用户名查询用户
     * 使用spring data QueryDSL实现
     *
     * @param userName
     * @return
     */
    @Override
    public User findUserByUserName(String userName) {
        QUser qUser = QUser.user;
        Predicate predicate = qUser.name.eq(userName);
        return userRepository.findOne(predicate);
    }

    /**
     * 单条件查询用户
     *
     * @param userName
     * @return
     */
    @Override
    public User findOneByUserName(String userName) {
        QUser qUser = QUser.user;
        User user = queryFactory.selectFrom(qUser)
                .where(qUser.name.eq(userName))
                .fetchOne();
        return user;
    }

    /**
     * 查询user表中的所有记录
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        System.out.println("重写findAll方法");
        QUser qUser = QUser.user;
        return queryFactory.selectFrom(qUser).fetch();
    }

    /**
     * 单表多条件查询
     *
     * @param userName
     * @param address
     * @return
     */
    @Override
    public User findOneByUserNameAndAddress(String userName, String address) {
        QUser qUser = QUser.user;
        User user = queryFactory.select(qUser)
                .from(qUser)
                .where(qUser.name.eq(userName).and(qUser.address.eq(address)))
                .fetchOne();
        return user;
    }

    /**
     * 使用join查询
     * @param user
     * @return
     */
    @Override
    public List<User> findUsersByJoin(QUser user) {
        QUser qUser = QUser.user;
        List<User> users = queryFactory.selectFrom(qUser)
                .innerJoin(qUser)
                .on(qUser.id.intValue().eq(user.id.intValue()))
                .fetch();
        return users;
    }

    /**
     * 将查询结果排序
     *
     * @return
     */
    @Override
    public List<User> findUsersByOrder() {
        QUser qUser = QUser.user;
        List<User> users = queryFactory.selectFrom(qUser)
                .orderBy(qUser.age.desc())
                .fetch();
        return users;
    }

    /**
     * Group By使用
     *
     * @return
     */
    @Override
    public List<String> findUserByGroup() {
        QUser qUser = QUser.user;
        return queryFactory.select(qUser.name)
                .from(qUser)
                .groupBy(qUser.name)
                .fetch();
    }

    /**
     * 删除用户
     *
     * @param userName
     * @return
     */
    @Override
    public long deleteUser(String userName) {
        QUser qUser = QUser.user;
        return queryFactory.delete(qUser).where(qUser.name.eq(userName)).execute();
    }

    /**
     * 更新记录
     *
     * @param user
     * @param userName
     * @return
     */
    @Override
    public long updateUser(User user, String userName) {
        QUser qUser = QUser.user;
        return queryFactory.update(qUser).where(qUser.name.eq(userName))
                .set(qUser.name,user.getName())
                .set(qUser.age,user.getAge())
                .set(qUser.address,user.getAddress())
                .execute();
    }

    /**
     * 使用原生Query
     *
     * @param userName
     * @return
     */
    @Override
    public User findOneUserByOriginalSql(String userName) {
        QUser qUser = QUser.user;
        Query query = queryFactory.selectFrom(qUser)
                .where(qUser.name.eq(userName)).createQuery();
        User singleResult = (User) query.getSingleResult();
        return singleResult;
    }

    /**
     * 分页查询单表
     *
     * @param offset
     * @param pageSize
     * @return
     */
    @Override
    public Page<User> findAllByPage(int offset, int pageSize) {
        Predicate predicate = QUser.user.age.lt(30);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        PageRequest pageRequest = new PageRequest(offset, pageSize, sort);
        return userRepository.findAll(predicate, pageRequest);
    }

    /**
     * 自连接查询
     * 查询来自同一个地方,年龄>age的用户信息
     *
     * @param age
     * @return
     */
    @Override
    public List<User> findUserByJoinUser(Integer age) {
        BooleanExpression expression = QUser.user.age.intValue().gt(age);
        return queryFactory.selectFrom(QUser.user)
                .leftJoin(QUser.user)
                .on((QUser.user.address).eq(QUser.user.address))
                .where(expression)
                .fetch();
    }

}
