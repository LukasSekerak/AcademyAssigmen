package sk.ness.academy.springbootHibernate.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.dao.ArticleHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.service.ArticleServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ArticleServiceImplTest {
    @Mock
    private ArticleHibernateDAO articleHibernateDAO;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private List<Article> articles;

    @Test
    void testFindAll() {
        Mockito.when(articleHibernateDAO.findAll()).thenReturn(articles);

        final List<Article> articles = articleService.findAll();

        Assertions.assertEquals(2, articles.size());
        Assertions.assertEquals("X_Book_1", articles.get(0).getName());
        Assertions.assertEquals("X_Book_2", articles.get(1).getName());
    }

    @Test
    void testFindAllEmpty() {
        Mockito.when(articleHibernateDAO.findAll()).thenReturn(new ArrayList<>());

        final List<Article> articles = articleService.findAll();

        Assertions.assertEquals(0, articles.size());
    }


    @Test
    void testFindAllNull() {
        Mockito.when(articleHibernateDAO.findAll()).thenReturn(null);

        final List<Article> articles = articleService.findAll();

        Assertions.assertTrue(articles.isEmpty());
    }

    @BeforeEach
    private void init() {
   /*     final Book book1 = new Book();
        book1.setName("Book_1");
        book1.setId(1L);

        final Book book2 = new Book();
        book2.setName("Book_2");
        book2.setId(2L);

        books = new ArrayList<>();
        books.add(book1);*/
//    books.add(book2);
    }
}
