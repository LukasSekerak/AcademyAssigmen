package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentService {

    /** Returns {@link Comment} with provided ID */
    void deleteByID(Integer commentId);

    /** Creates new {@link Comment} */
    void createComment(Comment comment);

    /** Returns all available {@link Comment}s of {@link Article} */
    List<Comment> findAllCommentsByArticleId(Integer idArticle);

}
