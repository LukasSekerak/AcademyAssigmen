package sk.ness.academy.springbootHibernate.daoImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.domain.Article;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, ArticleHibernateDAO.class })
@Transactional
@Sql({"/initdb.sql"})
public class ArticleHibernateDAOTest {
    @Autowired
    private ArticleHibernateDAO articleHibernateDAO;

    @BeforeEach
    public void beforeEach() {
        System.out.println("### BeforeEach ###");
    }

    @Test
    void findAllTest() {
        final List<Article> articles = articleHibernateDAO.findAll();
        Assertions.assertEquals(3, articles.size());
        Assertions.assertEquals(1, articles.get(0).getId());
        Assertions.assertEquals(2, articles.get(1).getId());
    }

    @Test
    void findAllTestEmpty() {
        int size = articleHibernateDAO.findAll().size();
        //delete all articles in DB
        for (int i = 1; i < size + 1; i++)
            articleHibernateDAO.deleteByID(i);

        final List<Article> articles = articleHibernateDAO.findAll();
        Assertions.assertEquals(0, articles.size());
    }
    @Test
    void findByIDTest() {
        final Article article = articleHibernateDAO.findByID(1).get();
        Assertions.assertEquals(1, article.getId());
    }

    @Test
    void findByIDTestNull() {
        final Optional<Article> article = articleHibernateDAO.findByID(4);
        Assertions.assertTrue(!article.isPresent());
    }

    @Test
    void deleteByIDTest() {
        articleHibernateDAO.deleteByID(1);
        final Optional<Article> article = articleHibernateDAO.findByID(1);
        Assertions.assertTrue(!article.isPresent());
    }

    @Test
    void searchArticleTest() {
        final List<Article> articles = articleHibernateDAO.searchArticle("Title");
        Assertions.assertEquals(3, articles.size());
    }

    @Test
    void searchArticleTestEmpty() {
        final List<Article> articles = articleHibernateDAO.searchArticle("randomString");
        Assertions.assertEquals(0, articles.size());
    }

    @Test
    public void persistCreateTest() {
        Article newArticle = new Article();
        newArticle.setId(4);
        newArticle.setAuthor("Joseph Little");
        newArticle.setText("Text text text");
        newArticle.setTitle("New title");
        articleHibernateDAO.persist(newArticle);

        final Article article = articleHibernateDAO.findByID(4).get();
        Assertions.assertEquals(4, article.getId());
    }

    @Test
    public void persistUpdateTest() {
        final Article oldArticle = articleHibernateDAO.findByID(1).get();
        String newAuthor = "New Name";
        String newText = "New text";
        String newTitle = "New title";

        oldArticle.setAuthor(newAuthor);
        oldArticle.setText(newText);
        oldArticle.setTitle(newTitle);

        articleHibernateDAO.persist(oldArticle);

        final Article updatedArticle = articleHibernateDAO.findByID(1).get();

        Assertions.assertEquals(newAuthor, updatedArticle.getAuthor());
        Assertions.assertEquals(newText, updatedArticle.getText());
        Assertions.assertEquals(newTitle, updatedArticle.getTitle());
    }

}
