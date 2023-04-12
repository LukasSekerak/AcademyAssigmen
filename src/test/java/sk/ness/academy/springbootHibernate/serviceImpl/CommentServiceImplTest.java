package sk.ness.academy.springbootHibernate.serviceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;
import sk.ness.academy.service.ArticleServiceImpl;
import sk.ness.academy.service.CommentService;
import sk.ness.academy.service.CommentServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, ArticleDAO.class, CommentDAO.class })
@Transactional
public class CommentServiceImplTest {
    @Mock
    private ArticleDAO articleDAO;

    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void findByIDTest() {
        final Comment comment = new Comment();
        comment.setText("Comment");
        comment.setId(3);

        Mockito.when(commentDAO.findByID(3)).thenReturn(Optional.of(comment));


        final Comment _comment = commentService.findByID(3);
        Assertions.assertEquals(3, _comment.getId());
    }

    @Test
    void findByTestWrongId() {

        Mockito.when(commentDAO.findByID(4)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            final Comment _comment = commentService.findByID(4);
        }, "Comment with id: 4 doesn't exists.");

        Assertions.assertEquals("Comment with id: 4 doesn't exists.", thrown.getMessage());
    }

    @Test
    void findByTestWithoutId() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            commentService.findByID(null);
        }, "Request param can't be null.");

        Assertions.assertEquals("Request param can't be null.", thrown.getMessage());
    }

    @Test
    void deleteByIDTest() {
        commentDAO.deleteByID(2);
        Mockito.verify(commentDAO).deleteByID(2);
    }

    @Test
    void deleteByIDTestWrongId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            commentService.deleteByID(4);
        }, "Comment with id: 4 doesn't exists.");

        Assertions.assertEquals("Comment with id: 4 doesn't exists.", thrown.getMessage());
    }

    @Test
    void deleteByIDTestWithoutId() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            commentService.deleteByID(null);
        }, "Request param can't be null.");


        Assertions.assertEquals("Request param can't be null.", thrown.getMessage());
    }

    @Test
    void findAllByIDOfArticleTest() {
        final Comment comment1 = new Comment();
        comment1.setText("Comment_1");
        comment1.setId(1);

        final List<Comment> listOfComments1 = new ArrayList<>();
        listOfComments1.add(comment1);

        final Article article1 = new Article();
        article1.setTitle("Article_1");
        article1.setId(1);
        article1.setComments(listOfComments1);

        Mockito.when(articleDAO.findByID(1)).thenReturn(Optional.of(article1));
        Mockito.when(commentDAO.findAllByIDOfArticle(1)).thenReturn(listOfComments1);

        final List<Comment> comments = commentService.findAllByIDOfArticle(1);

        Assertions.assertEquals(1, comments.size());
        Assertions.assertEquals("Comment_1", comments.get(0).getText());

    }

    @Test
    void findAllByIDOfArticleTestEmpty() {
        final Article article1 = new Article();
        article1.setTitle("Article_1");
        article1.setId(1);

        Mockito.when(articleDAO.findByID(1)).thenReturn(Optional.of(article1));
        Mockito.when(commentDAO.findAllByIDOfArticle(1)).thenReturn(new ArrayList<>());

        final List<Comment> comments = commentService.findAllByIDOfArticle(1);

        Assertions.assertEquals(0, comments.size());
    }


    @Test
    void findAllByIDOfArticleTestWithoutId() {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            commentService.findAllByIDOfArticle(null);
        }, "Request param can't be null.");

        Assertions.assertEquals("Request param can't be null.", thrown.getMessage());
    }

    @Test
    void findAllByIDOfArticleTestWrongId() {
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            commentService.findAllByIDOfArticle(4);
        }, "Article with id: 4 doesn't exists.");

        Assertions.assertEquals("Article with id: 4 doesn't exists.", thrown.getMessage());
    }

    @Test
    void createCommentTest() {
        final Comment comment1 = new Comment();
        comment1.setText("Comment_1");
        comment1.setId(1);

        final List<Comment> listOfComments1 = new ArrayList<>();
        listOfComments1.add(comment1);

        final Article article1 = new Article();
        article1.setTitle("Article_1");
        article1.setId(1);
        article1.setComments(listOfComments1);

        Mockito.when(articleDAO.findByID(1)).thenReturn(Optional.of(article1));

        commentService.addComment(1, comment1);

        Mockito.verify(commentDAO, Mockito.times(1)).persist(comment1);
    }

    @Test
    void updateCommentTest() {
        final Comment comment1 = new Comment();
        comment1.setText("Comment_1");
        comment1.setId(1);

        final List<Comment> listOfComments1 = new ArrayList<>();
        listOfComments1.add(comment1);

        final Article article1 = new Article();
        article1.setTitle("Article_1");
        article1.setId(1);
        article1.setComments(listOfComments1);

        Mockito.when(articleDAO.findByID(1)).thenReturn(Optional.of(article1));

        commentService.addComment(1, comment1);

        final Comment updatedComment = new Comment();
        updatedComment.setText("Comment_1 updated");
        updatedComment.setId(1);

        commentService.addComment(1,updatedComment);

        Mockito.verify(commentDAO, Mockito.times(1)).persist(updatedComment);
    }

}
