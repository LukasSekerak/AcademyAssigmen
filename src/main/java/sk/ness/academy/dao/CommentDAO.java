package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {

    /** Returns {@link Comment} with provided ID */
    Optional<Comment> findByID(Integer commentId) throws ResourceNotFoundException, NullPointerException;

    /** Delete {@link Comment} with provided ID */
    void deleteByID(Integer commentId);

    /** Returns all available {@link Comment}s of  {@link Article}*/
    List<Comment> findAllByIDOfArticle(Integer articleId);

    /** Persists {@link Comment} into the DB */
    void persist(Comment comment);
}
