package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import java.util.List;

public interface CommentService {

    /** Returns {@link Comment} with provided ID */
    Comment findByID(Integer commentId) throws ResourceNotFoundException, NullPointerException;

    /** Returns {@link Comment} with provided ID */
    void deleteByID(Integer commentId);

    /** Creates new {@link Comment} */
    void addComment(Integer articleId, Comment comment);

    /** Returns all available {@link Comment}s of {@link Article} */
    List<Comment> findAllByIDOfArticle(Integer idArticle);

    /** Creates new {@link Comment}s by ingesting all articles from json */
    void ingestArticles(String jsonComments);

}
