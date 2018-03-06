package cn.mrdear.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.NoRepositoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Niu Li
 * @date 2017/1/8
 */
@NoRepositoryBean
public class BaseRepository {

    @PersistenceContext(unitName = "TestJPA")
    protected EntityManager em;

    protected JPAQueryFactory queryFactory;

    @PostConstruct
    public void init(){
        System.out.println("\n init()方法调用");
        queryFactory = new JPAQueryFactory(em);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("\n destroy()方法调用");
    }

}
