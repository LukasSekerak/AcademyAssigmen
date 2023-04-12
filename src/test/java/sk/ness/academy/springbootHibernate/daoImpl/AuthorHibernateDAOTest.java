package sk.ness.academy.springbootHibernate.daoImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.dao.AuthorHibernateDAO;
import sk.ness.academy.dto.Author;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, AuthorHibernateDAO.class, ArticleHibernateDAO.class })
@Transactional
@Sql({"/initdb.sql"})
public class AuthorHibernateDAOTest {
    @Autowired
    private AuthorHibernateDAO authorHibernateDAO;

    @Autowired
    private ArticleHibernateDAO articleHibernateDAO;

    @Test
    void findAllTest() {
        final List<Author> authors = authorHibernateDAO.findAll();
        Assertions.assertEquals(3, authors.size());
        Assertions.assertNotNull( authors.get(0));
        Assertions.assertNotNull( authors.get(1));
        Assertions.assertNotNull( authors.get(2));
    }
}
