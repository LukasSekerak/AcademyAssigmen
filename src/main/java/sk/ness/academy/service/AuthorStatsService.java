package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;

import java.util.List;

public interface AuthorStatsService {
    /** Returns {@link AuthorStats}s */
    List<AuthorStats> authorsStats();
}
