package sk.ness.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleRepository;
import sk.ness.academy.dao.CommentRepository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Comment findById(Integer commentId) throws ResourceNotFoundException, NullPointerException {
        if (commentId == null) {
            throw new NullPointerException("Request param can't be null.");
        }

        return commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id: " + Integer.valueOf(commentId) + " doesn't exists."));
    }

    @Override
    public void addComment(Integer articleId, Comment comment) {
        Optional<Article> article = this.articleRepository.findById(articleId);
        article.get().getComments().add(comment);
        this.articleRepository.save(article.get());
    }

    @Override
    public void deleteById(Integer commentId) {
        if (commentId == null) {
            throw new NullPointerException("Request param can't be null.");
        }

        if (commentRepository.existsById(commentId)) {
            this.commentRepository.deleteByid(commentId);
        } else {
            throw new ResourceNotFoundException("Comment with id: " + Integer.valueOf(commentId) + " doesn't exists.");
        }
    }

    @Override
    public List<Comment> findAllCommentsByArticleId(Integer idArticle) {
        if (idArticle == null) {
            throw new NullPointerException("Request param can't be null.");
        }

        if (articleRepository.existsById(idArticle)) {
            return this.commentRepository.findAllByArticleId(idArticle).get();
        } else {
            throw new ResourceNotFoundException("Article with id: " + Integer.valueOf(idArticle) + " doesn't exists.");
        }

    }

}
