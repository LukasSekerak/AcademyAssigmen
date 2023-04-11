package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

    @Resource
    private ArticleDAO articleDAO;

    @Override
    public Comment findByID(Integer commentId) throws ResourceNotFoundException, NullPointerException {
        if (commentId == null) {
            throw new NullPointerException("Request param can't be null.");
        }

        return commentDAO.findByID(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id: " + Integer.valueOf(commentId) + " doesn't exists."));
    }

    @Override
    public void deleteByID(Integer commentId) {
        if (commentId == null) {
            throw new NullPointerException("Request param can't be null.");
        }

        Comment comment = commentDAO.findByID(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id: " + Integer.valueOf(commentId) + " doesn't exists."));

        this.commentDAO.deleteByID(commentId);
    }

    @Override
    public void addComment(Integer articleId, Comment comment) {
        Optional<Article> article = this.articleDAO.findByID(articleId);
        article.get().getComments().add(comment);
        this.commentDAO.persist(comment); }

    @Override
    public List<Comment> findAllByIDOfArticle(Integer idArticle) {
        if (idArticle == null) {
            throw new NullPointerException("Request param can't be null.");
        }

        articleDAO.findByID(idArticle).orElseThrow(
                () -> new ResourceNotFoundException("Article with id: " + Integer.valueOf(idArticle) + " doesn't exists."));

        return this.commentDAO.findAllByIDOfArticle(idArticle);

    }

}
