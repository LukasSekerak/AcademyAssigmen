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
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.service.AuthorServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, AuthorDAO.class })
@Transactional
public class AuthorServiceImplTest {
    @Mock
    private AuthorDAO authorDAO;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> authors;

    @Test
    void findAllTest() {
        Mockito.when(authorDAO.findAll()).thenReturn(authors);

        final List<Author> authors = authorService.findAll();

        Assertions.assertEquals(2, authors.size());
        Assertions.assertEquals("Author_1", authors.get(0).getName());
        Assertions.assertEquals("Author_2", authors.get(1).getName());
    }

    @Test
    void findAllTestEmpty() {
        Mockito.when(authorDAO.findAll()).thenReturn(new ArrayList<>());

        final List<Author> authors = authorService.findAll();

        Assertions.assertEquals(0, authors.size());
    }


    @Test
    void findAllTestNull() {
        Mockito.when(authorDAO.findAll()).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            final List<Author> authors = authorService.findAll();
        }, "No authors in database.");

        Assertions.assertEquals("No authors in database.", thrown.getMessage());
    }

    @BeforeEach
    private void init() {
        final Author author1 = new Author();
        author1.setName("Author_1");

        final Author author2 = new Author();
        author2.setName("Author_2");


        authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
    }
}
