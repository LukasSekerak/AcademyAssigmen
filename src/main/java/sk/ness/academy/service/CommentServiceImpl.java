package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

    @Override
    public void deleteByID(Integer commentId) {
        this.commentDAO.deleteByID(commentId);
    }

    @Override
    public void createComment(Comment comment) { this.commentDAO.persist(comment); }

    @Override
    public List<Comment> findAllByIDOfArticle(Integer idArticle) { return this.commentDAO.findAllByIDOfArticle(idArticle); }

    @Override
    public void ingestArticles(final String jsonComments) {
        throw new UnsupportedOperationException("Article ingesting not implemented.");
    }
}
