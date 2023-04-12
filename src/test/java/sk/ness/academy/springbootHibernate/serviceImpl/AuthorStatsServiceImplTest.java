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
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dao.AuthorStatsDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.AuthorServiceImpl;
import sk.ness.academy.service.AuthorStatsService;
import sk.ness.academy.service.AuthorStatsServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, AuthorStatsDAO.class })
@Transactional
public class AuthorStatsServiceImplTest {
    @Mock
    private AuthorStatsDAO authorStatsDAO;

    @InjectMocks
    private AuthorStatsServiceImpl authorStatsService ;

    private List<AuthorStats> authorStats;

    @Test
    void authorStatsTest() {
        Mockito.when(authorStatsDAO.authorsStats()).thenReturn(authorStats);

        final List<AuthorStats> authorStats = authorStatsService.authorsStats();

        Assertions.assertEquals(2, authorStats.size());
        Assertions.assertEquals("Author_1", authorStats.get(0).getAuthorName());
        Assertions.assertEquals(2, authorStats.get(0).getArticleCount());
        Assertions.assertEquals("Author_2", authorStats.get(1).getAuthorName());
        Assertions.assertEquals(1, authorStats.get(1).getArticleCount());
    }

    @Test
    void authorStatsTestEmpty() {
        Mockito.when(authorStatsDAO.authorsStats()).thenReturn(new ArrayList<>());

        final List<AuthorStats> authorsStats = authorStatsService.authorsStats();

        Assertions.assertEquals(0, authorsStats.size());
    }


    @Test
    void authorStatsTestNull() {
        Mockito.when(authorStatsDAO.authorsStats()).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            final List<AuthorStats> authorStats = authorStatsService.authorsStats();
        }, "No authors in database.");

        Assertions.assertEquals("No authors in database.", thrown.getMessage());
    }

    @BeforeEach
    public void init() {
        final AuthorStats as1 = new AuthorStats();
        as1.setAuthorName("Author_1");
        as1.setArticleCount(2);

        final AuthorStats as2 = new AuthorStats();
        as2.setAuthorName("Author_2");
        as2.setArticleCount(1);

        authorStats = new ArrayList<>();
        authorStats.add(as1);
        authorStats.add(as2);
    }

}
