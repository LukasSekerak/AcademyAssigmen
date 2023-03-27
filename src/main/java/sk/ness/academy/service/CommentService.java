package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import java.util.List;

public interface CommentService {

    /** Returns {@link Comment} with provided ID */
    Comment findById(Integer commentId) throws ResourceNotFoundException, NullPointerException;

    /** Creates new {@link Comment} */
    void addComment(Integer articleId, Comment comment);

    /** Returns {@link Comment} with provided ID */
    void deleteById(Integer commentId) throws ResourceNotFoundException, NullPointerException;

    /** Returns all available {@link Comment}s of {@link Article} */
    List<Comment> findAllCommentsByArticleId(Integer idArticle) throws ResourceNotFoundException, NullPointerException;;

}
