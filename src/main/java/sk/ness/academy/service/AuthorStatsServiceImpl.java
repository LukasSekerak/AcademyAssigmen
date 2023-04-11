package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.AuthorStatsDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorStatsServiceImpl implements AuthorStatsService {
    @Resource
    private AuthorStatsDAO autorStatsDAO;
    @Override
    public List<AuthorStats> authorsStats() {

        final List<AuthorStats> listOfAuthorStats = this.autorStatsDAO.authorsStats();

        if (listOfAuthorStats == null) {
            throw new NullPointerException("No authors in database.");
        }
        return listOfAuthorStats;

    }
}
