package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import javax.annotation.Resource;
import java.util.List;
@Repository
public class AuthorStatsHibernateDAO implements AuthorStatsDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    @Override
    public List<AuthorStats> authorsStats() {
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT author AS authorName, COUNT (*) AS articleCount FROM articles GROUP BY author")
                .addScalar("authorName", StringType.INSTANCE)
                .addScalar("articleCount", IntegerType.INSTANCE)
                .setResultTransformer(new AliasToBeanResultTransformer(AuthorStats.class)).list();
    }
}
