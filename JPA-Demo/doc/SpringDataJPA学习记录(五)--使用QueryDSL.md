# SpringDataJPA学习记录(五)--使用QueryDSL

---
标签: QueryDSL SpringData JPA

在上章节基础上增加了三个实体<br>
User: 用于演示单表查询,自连接查询<br>
Preson, IDCard: 用于演示一对一关系查询<br>
Order, OrderItem 用于演示多对多关系查询<br>

User用户实体类

````
/**
 * User类 主要用于单表操作。
 */
@Entity
@Table(name="t_user")
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int age;
    private String address;
    //…………省略getter，setter方法…………  
    /** 
     * attention: 
     * Details：方便查看测试结果 
     * @author chhliu 
     */  
    @Override  
    public String toString() {  
          return "User [id=" + id + ", name=" + name + ", address=" + address + ", age=" + age + "]";  
      }  
  } 
````
IDCard实体类
````
/**
 * 描述：身份ID
 * @author chhliu
 */
@Entity
@Table(name="T_IDCARD")
public class IDCard {
    @Id
    @GeneratedValue
    private Integer id;
    private String idNo;
    @OneToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST}, fetch=FetchType.EAGER)
    private Person person;
    //---省略get set方法---
    @Override
    public String toString() {
        return "IDCard [id=" + id + ", idNo=" + idNo + ", person=" + person + "]";
    }
}    
````
Person个人实体类
````
/**
 * 描述：个人表
 * 用于演示一对一关系查询
 * @author chhliu
 */
@Entity
@Table(name="T_PERSON")
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String address;

    @OneToOne(mappedBy="person", cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private IDCard idCard;
    //---省略get set方法---
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", address=" + address + ", idCard=" + idCard + "]";
    }
}    
````
Order订单表实体

````
/**
 * 描述：Order 订单实体类
 * 用于演示订单表与订单项表的多对多关系查询
 * @author chhliu
 */
@Entity
@Table(name="T_ORDER")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;

    @Column(length=20, name="ORDER_NAME")
    private String orderName;

    @Column(name="COUNT")
    private Integer count;

    @OneToMany(mappedBy = "order",cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
    //---省略get set 方法---
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", count=" + count +
                '}';
    }
}
````
订单项实体
````
/**
 * 描述：OrderItem 订单项实体类
 * @author chhliu
 */
@Entity
@Table(name="ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="ID", nullable=false)
    private Integer id;

    @Column(name="ITEM_NAME", length=20)
    private String itemName;

    @Column(name="PRICE")
    private Integer price;

    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private Order order;
    //---省略get set 方法---
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", orderId=" + order.getId() +
                '}';
    }
}
````
单表查询实现类
````

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
````
一对一关系查询实现类
````

/**
 * @author 向亚林
 * 2018/3/5 17:24
 */
@Component
@Transactional
public class PersonAndIdCardRepositoryImpl extends BaseRepository implements PersonAndIdCardRepositoryCustom {
    /**
     * 多表动态查询
     * Person与IDCard是一对一的关系
     *
     * @return
     */
    @Override
    public List<Tuple> findAllPersonAndIdCard() {
        Predicate predicate = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.id.intValue());
        return queryFactory.select(QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address)
                .from(QIDCard.iDCard, QPerson.person)
                .where(predicate)
                .fetch();
    }

    /**
     * 将查询结果以DTO的方式输出
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDto() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        JPAQuery<Tuple> jpaQuery = queryFactory.select(QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address)
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression);
        List<Tuple> tuples = jpaQuery.fetch();
        List<PersonIdCardDto> personIdCardDtoList = new ArrayList<>();
        if (null != tuples && !tuples.isEmpty()) {
            for (Tuple tuple : tuples) {
                String idCard = tuple.get(QIDCard.iDCard.idNo);
                String name = tuple.get(QPerson.person.name);
                String address = tuple.get(QPerson.person.address);
                PersonIdCardDto personIdCardDto = new PersonIdCardDto(idCard, name, address);
                personIdCardDtoList.add(personIdCardDto);
            }
        }
        return personIdCardDtoList;
    }

    /**
     * 多表动态查询，并分页
     *
     * @param offset
     * @param pageSize
     * @return
     */
    @Override
    public QueryResults<Tuple> findByDtoAndPager(int offset, int pageSize) {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        return queryFactory.select(QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address)
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .offset(offset)
                .limit(pageSize)
                .fetchResults();
    }

    /**
     * QueryDSL使用Bean进行投影查询
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDtoUseBean() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        List<PersonIdCardDto> fetch = queryFactory.select(Projections.bean(PersonIdCardDto.class, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address))
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .fetch();
        return fetch;
    }

    /**
     * QueryDSL使用fields来代替setter
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDtoUseFields() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        return queryFactory.select(Projections.fields(PersonIdCardDto.class, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address))
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .fetch();
    }

    /**
     * QueryDSL使用构造方法，注意构造方法中属性的顺序必须和构造器中的顺序一致
     *
     * @return
     */
    @Override
    public List<PersonIdCardDto> findByDtoUseConstructor() {
        BooleanExpression expression = (QPerson.person.id.intValue()).eq(QIDCard.iDCard.person.id.intValue());
        return queryFactory.select(Projections.constructor(PersonIdCardDto.class, QIDCard.iDCard.idNo, QPerson.person.name, QPerson.person.address))
                .from(QIDCard.iDCard, QPerson.person)
                .where(expression)
                .fetch();
    }
}

````
多对多关系查询实现类
````

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

````