package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ResourceNotFoundException;

public interface ArticleService {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId) throws ResourceNotFoundException, NullPointerException;

	  /** Delete {@link Article} with provided ID */
	  void deleteByID(Integer articleId) throws ResourceNotFoundException, NullPointerException;

	  /** Returns all available {@link Article}s */
	  List<Article> findAll() throws NullPointerException;

	  /** Returns all available {@link Article}s where author, title or text contains the searched text.*/
	  List<Article> searchArticle(String string);

	  /** Creates new {@link Article} */
	  void createArticle(Article article) throws ResourceNotFoundException, NullPointerException;;

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles);

	}
