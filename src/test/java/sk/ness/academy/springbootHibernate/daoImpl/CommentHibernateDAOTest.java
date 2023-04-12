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
import sk.ness.academy.dao.CommentHibernateDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class, ArticleHibernateDAO.class, CommentHibernateDAO.class })
@Transactional
@Sql({"/initdb.sql"})
public class CommentHibernateDAOTest {
    @Autowired
    private CommentHibernateDAO commentHibernateDAO;

    @Autowired
    private ArticleHibernateDAO articleHibernateDAO;

    @BeforeEach
    public void beforeEach() {
        System.out.println("### BeforeEach ###");
    }

    @Test
    void findByIDTest() {
        final Comment comment = commentHibernateDAO.findByID(1).get();
        Assertions.assertEquals(1, comment.getId());
    }

    @Test
    void findByIDTestNull() {
        final Optional<Comment> comment = commentHibernateDAO.findByID(4);
        Assertions.assertFalse(comment.isPresent());
    }

    @Test
    void deleteByIDTest() {
        commentHibernateDAO.deleteByID(1);
        final  Optional<Comment> comment = commentHibernateDAO.findByID(1);
        Assertions.assertFalse(comment.isPresent());
    }

    @Test
    void findAllByIDOfArticleTest() {
        final List<Comment> comments = commentHibernateDAO.findAllByIDOfArticle(1);
        Assertions.assertEquals(1, comments.size());
    }

    @Test
    void findAllByIDOfArticleTestEmpty() {
        final List<Comment> comments = commentHibernateDAO.findAllByIDOfArticle(5);
        Assertions.assertEquals(0, comments.size());
    }

    @Test
    public void persistCreateTest() {
        final Article newArticle = new Article();
        final Comment newComment1 = new Comment();
        final Comment newComment2 = new Comment();
        final List<Comment> commentsOfArticle = new ArrayList<>();

        newArticle.setId(4);
        newArticle.setAuthor("Joseph Little");
        newArticle.setText("Text text text");
        newArticle.setTitle("New title");

        newComment1.setId(4);
        newComment1.setAuthor("Joseph Little");
        newComment1.setText("Text text text");
        commentsOfArticle.add(newComment1);

        newComment2.setId(5);
        newComment2.setAuthor("Joseph Little_2");
        newComment2.setText("Text text text 2");
        commentsOfArticle.add(newComment2);

        newArticle.setComments(commentsOfArticle);

        articleHibernateDAO.persist(newArticle);

        final Article _newArticle = articleHibernateDAO.findByID(4).get();
        final Comment _newComment1 = commentHibernateDAO.findByID(4).get();
        final Comment _newComment2 = commentHibernateDAO.findByID(5).get();

        Assertions.assertEquals(4, _newComment1.getId());
        Assertions.assertEquals(5, _newComment2.getId());
        Assertions.assertEquals(2, _newArticle.getComments().size());

    }

    @Test
    public void persistUpdateTest() {
        final Article oldArticle = articleHibernateDAO.findByID(1).get();
        final Comment oldComment = oldArticle.getComments().get(0);
        final List<Comment> commentsOfOldArticle = oldArticle.getComments();

        String newAuthor = "New Name";
        String newText = "New text";

        oldComment.setAuthor(newAuthor);
        oldComment.setText(newText);

        commentsOfOldArticle.set(0, oldComment);
        oldArticle.setComments(commentsOfOldArticle);
        articleHibernateDAO.persist(oldArticle);

        final Comment updatedComment = commentHibernateDAO.findByID(oldComment.getId()).get();

        Assertions.assertEquals(newAuthor, updatedComment.getAuthor());
        Assertions.assertEquals(newText, updatedComment.getText());
    }


}
