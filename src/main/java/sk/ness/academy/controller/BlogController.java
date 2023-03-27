package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.AuthorStatsService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private CommentService commentService;

  @Resource
  private AuthorService authorService;

  @Resource
  private AuthorStatsService authorStatsService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public ResponseEntity<List<Article>> getAllArticles() {
    return  new ResponseEntity<>(this.articleService.findAll(), HttpStatus.OK);
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public ResponseEntity<Article> getArticle(@PathVariable final Integer articleId) {
    Article article = this.articleService.findByID(articleId);
    return new ResponseEntity<>(article, HttpStatus.OK);
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public  ResponseEntity<HttpStatus> deleteArticle(@PathVariable final Integer articleId) {
    this.articleService.deleteByID(articleId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public ResponseEntity<List<Article>> searchArticle(@PathVariable final String searchText) {
    return new ResponseEntity<>(this.articleService.searchArticle(searchText), HttpStatus.OK);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public ResponseEntity<HttpStatus> addArticle(@RequestBody final Article article) {
    this.articleService.createArticle(article);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // ~~ Comments
  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public ResponseEntity<Comment> getComment(@PathVariable final Integer commentId) {
    Comment comment = this.commentService.findByID(commentId);
    return new ResponseEntity<>(comment, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public ResponseEntity<HttpStatus> addComment(@RequestBody final Comment comment, @PathVariable final Integer articleId) {
    this.commentService.addComment(articleId, comment);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
  public  ResponseEntity<List<Comment>>  getCommentsOfArticle(@PathVariable final Integer articleId) {
    return new ResponseEntity<>(this.commentService.findAllByIDOfArticle(articleId),HttpStatus.OK);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteComment(@PathVariable final Integer commentId) {
    this.commentService.deleteByID(commentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public ResponseEntity<List<Author>> getAllAuthors() {
    return new ResponseEntity<>(this.authorService.findAll(),HttpStatus.OK);
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public ResponseEntity<List<AuthorStats>> authorStats() {
    return new ResponseEntity<>(this.authorStatsService.authorsStats(),HttpStatus.OK);
  }

}
