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
import sk.ness.academy.dao.AuthorStatsHibernateDAO;
import sk.ness.academy.dto.AuthorStats;

import javax.transaction.Transactional;
import java.util.List;
@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, AuthorStatsHibernateDAO.class})
@Transactional
@Sql({"/initdb.sql"})
public class AuthorStatsHibernateDaoTest {
    @Autowired
    private AuthorStatsHibernateDAO authorStatsHibernateDAO;
    @Test
    void authorsStatsTest() {
        final List<AuthorStats> authorStats = authorStatsHibernateDAO.authorsStats();
        Assertions.assertEquals(3, authorStats.size());
        Assertions.assertEquals(1, authorStats.get(0).getArticleCount());
        Assertions.assertEquals(1, authorStats.get(1).getArticleCount());
        Assertions.assertEquals(1, authorStats.get(2).getArticleCount());

    }
}
