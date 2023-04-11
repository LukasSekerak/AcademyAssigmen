package sk.ness.academy.springbootHibernate.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;
import sk.ness.academy.service.ArticleServiceImpl;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, ArticleDAO.class, CommentDAO.class })
@Transactional
public class ArticleServiceImplTest {
    @Mock
    private ArticleDAO articleDAO;

    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private List<Article> articles;

    @Test
    void findAllTest() {
        Mockito.when(articleDAO.findAll()).thenReturn(articles);

        final List<Article> articles = articleService.findAll();

        Assertions.assertEquals(2, articles.size());
        Assertions.assertEquals("Article_1", articles.get(0).getTitle());
        Assertions.assertEquals("Article_2", articles.get(1).getTitle());
    }

    @Test
    void findAllTestEmpty() {
        Mockito.when(articleDAO.findAll()).thenReturn(new ArrayList<>());

        final List<Article> articles = articleService.findAll();

        Assertions.assertEquals(0, articles.size());
    }


    @Test
    void findAllTestNull() {
        Mockito.when(articleDAO.findAll()).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            final List<Article> articles = articleService.findAll();
        }, "Articles doesn't exist in database");

        Assertions.assertEquals("Articles doesn't exist in database", thrown.getMessage());
    }

    @Test
    void findByIDTest() {
        final Article article = new Article();
        article.setTitle("Article_3");
        article.setId(3);

        Mockito.when(articleDAO.findByID(3)).thenReturn(Optional.of(article));


        final Article _article = articleService.findByID(3);
        Assertions.assertEquals(3, _article.getId());
    }

    @Test
    void findByTestWrongId() {

        Mockito.when(articleDAO.findByID(4)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            final Article _article = articleService.findByID(4);
        }, "Article with id: 4 doesn't exists.");

        Assertions.assertEquals("Article with id: 4 doesn't exists.", thrown.getMessage());
    }

    @Test
    void findByTestWithoutId() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            articleService.findByID(null);
        }, "Request param can't be null.");

        Assertions.assertEquals("Request param can't be null.", thrown.getMessage());
    }

    @Test
    void deleteByIDTest() {
        articleDAO.deleteByID(2);
        Mockito.verify(articleDAO).deleteByID(2);
    }

    @Test
    void deleteByIDTestWrongId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            articleService.deleteByID(4);
        }, "Article with id: 4 doesn't exists.");

        Assertions.assertEquals("Article with id: 4 doesn't exists.", thrown.getMessage());
    }

    @Test
    void deleteByIDTestWithoutId() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            articleService.deleteByID(null);
        }, "Request param can't be null.");

        Assertions.assertEquals("Request param can't be null.", thrown.getMessage());
    }

    @Test
    void searchArticleTest() {
        Mockito.when(articleDAO.searchArticle("Art")).thenReturn(articles);

        final List<Article> articles = articleService.searchArticle("Art");

        Assertions.assertEquals(2, articles.size());
    }

    @Test
    void createArticleTest() {
        final Article article = new Article();
        article.setTitle("Article_3");
        article.setId(3);

        articleService.createArticle(article);

        Mockito.verify(articleDAO, Mockito.times(1)).persist(article);
    }

    @Test
    void updateArticleTest() {
        final Article article = new Article();
        article.setTitle("Article_1 updated");
        article.setId(1);

        articleService.createArticle(article);

        Mockito.verify(articleDAO, Mockito.times(1)).persist(article);
    }



    @BeforeEach
    private void init() {
        final Comment comment1 = new Comment();
        comment1.setText("Comment_1");
        comment1.setId(1);

        final Comment comment2 = new Comment();
        comment2.setText("Comment_2");
        comment2.setId(1);

        final List<Comment> listOfComments1 = new ArrayList<>();
        final List<Comment> listOfComments2 = new ArrayList<>();

        listOfComments1.add(comment1);
        listOfComments2.add(comment2);

        final Article article1 = new Article();
        article1.setTitle("Article_1");
        article1.setId(1);
        article1.setComments(listOfComments1);

        final Article article2 = new Article();
        article2.setTitle("Article_2");
        article2.setId(1);

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
    }
}
