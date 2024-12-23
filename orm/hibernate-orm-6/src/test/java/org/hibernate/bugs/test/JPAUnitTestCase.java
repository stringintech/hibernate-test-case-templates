package org.hibernate.bugs.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private static final Logger LOGGER = LogManager.getLogger();
    private static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
        try (var em = entityManagerFactory.createEntityManager()) {
            em.getTransaction().begin();
            persistData(em);
            em.getTransaction().commit();
        }
    }

    @AfterClass
    public static void destroy() {
        if (Objects.nonNull(entityManagerFactory)) {
            entityManagerFactory.close();
        }
    }


    @Test
    public void testcase() throws Exception {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            //given
            var cb = entityManager.unwrap(Session.class).getCriteriaBuilder();
            JpaCriteriaQuery<Tuple> query = cb.createTupleQuery();
            JpaRoot<Book> root = query.from(Book.class);
            query.multiselect(
                    cb.arrayToString(
                            cb.arrayAgg(cb.asc(root.get(Book_.title)), root.get(Book_.title)),
                            ","
                    ).alias("titles")
            );
            List<Tuple> list = entityManager.createQuery(query).getResultList();
            assertThat(1).isEqualByComparingTo(1);
            assertThat(list.getFirst().get("titles", String.class)).isEqualTo("title_1,title_2");
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);
            throw t;
        }
    }


    private static void persistData(EntityManager em) {
        Stream.of(
                createBook(1, "title_1"),
                createBook(2, "title_2")
        ).forEach(em::persist);
        em.flush();
        em.clear();
    }


    private static Book createBook(int id, String title) {
        var entity = new Book();
        entity.setId(id);
        entity.setTitle(title);
        return entity;
    }


}
