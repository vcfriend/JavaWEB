package cn.mrdear.repository.custom;

import cn.mrdear.entity.QUser;
import cn.mrdear.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/5 11:17
 */
public interface UserRepositoryCustom {
    /**
     * 按用户名查询用户
     * 使用spring data QueryDSL实现
     * @param userName
     * @return
     */
    User findUserByUserName(final String userName);

    /**
     * 单条件查询用户
     * @param userName
     * @return
     */
    User findOneByUserName(final String userName);
    /**
     * 查询user表中的所有记录
     * @return
     */
    List<User> findAll();

    /**
     * 单表多条件查询
     * @param userName
     * @param address
     * @return
     */
    User findOneByUserNameAndAddress(final String userName, final String address);

    /**
     * 使用join查询
     * @param user
     * @return
     */
    List<User> findUsersByJoin(final QUser user);

    /**
     * 将查询结果排序
     * @return
     */
    List<User> findUsersByOrder();

    /**
     * Group By使用
     * @return
     */
    List<String> findUserByGroup();

    /**
     * 删除用户
     * @param userName
     * @return
     */
    long deleteUser(String userName);

    /**
     * 更新记录
     * @param user
     * @param userName
     * @return
     */
    long updateUser(final User user, final String userName);

    /**
     * 使用原生Query
     * @param userName
     * @return
     */
    User findOneUserByOriginalSql(final String userName);

    /**
     * 分页查询单表
     * @param offset
     * @param pageSize
     * @return
     */
    Page<User> findAllByPage(final int offset, final int pageSize);

    /**
     * 自连接查询
     * 查询来自同一个地方,年龄>age的用户信息
     * @param age
     * @return
     */
    List<User> findUserByJoinUser(final Integer age);
}
