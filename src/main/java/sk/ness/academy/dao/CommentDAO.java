package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

    /** Delete {@link Comment} with provided ID */
    void deleteByID(Integer commentId);

    /** Returns all available {@link Comment}s of  {@link Article}*/
    List<Comment> findAllByIDOfArticle(Integer articleId);

    /** Persists {@link Comment} into the DB */
    void persist(Comment comment);
}
