package sk.ness.academy.projections;

import java.util.Date;

public interface ArticlesWithoutComments {
    Integer getId();
    String getTitle();
    String getAuthor();
    String getText();
    Date getCreateTimestamp();
}
