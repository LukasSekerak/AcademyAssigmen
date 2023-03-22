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
    List<Comment> findAllByIDOfArticle(Integer idArticle);

    /** Creates new {@link Comment}s by ingesting all articles from json */
    void ingestArticles(String jsonComments);

}
