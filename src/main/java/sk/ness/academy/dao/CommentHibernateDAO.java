package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentHibernateDAO implements CommentDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Optional<Comment> findByID(Integer commentId) throws ResourceNotFoundException, NullPointerException {
        return Optional.ofNullable((Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId));
    }

    @Override
    public void deleteByID(Integer commentId) {
        Comment comment = (Comment)this.sessionFactory.getCurrentSession().load(Comment.class, commentId);
        this.sessionFactory.getCurrentSession().delete(comment);
    }

    @Override
    public List<Comment> findAllByIDOfArticle(Integer articleId) {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments where article_id = :parameter").addEntity(Comment.class).setParameter("parameter", articleId).list();
    }

    @Override
    public void persist(Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }
}
