package sk.ness.academy.dao;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ResourceNotFoundException;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Optional<Article> findByID(final Integer articleId) {
    return Optional.ofNullable((Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId));
  }

  @Override
  public void deleteByID(Integer articleId) throws ResourceNotFoundException, NullPointerException {
      Article article = (Article)this.sessionFactory.getCurrentSession().load(Article.class, articleId);
      this.sessionFactory.getCurrentSession().delete(article);
   }

   @Override
  public List<Article> findAll() throws NullPointerException {
    //return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(Article.class).list();
    return this.sessionFactory.getCurrentSession().createCriteria(Article.class)
            .setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("text"), "text")
                    .add(Projections.property("title"), "title")
                    .add(Projections.property("author"), "author")
                    .add(Projections.property("createTimestamp"), "createTimestamp"))
            .setResultTransformer(Transformers.aliasToBean(Article.class)).list();
  }

  @Override
  public List<Article> searchArticle(String string) {
    return this.sessionFactory.getCurrentSession().createSQLQuery(
            "select * from articles where author LIKE :param1 OR title LIKE :param2 OR text LIKE :param3")
            .setParameter("param1", "%" + string + "%")
            .setParameter("param2", "%" + string + "%")
            .setParameter("param3", "%" + string + "%")
            .addEntity(Article.class).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

}
