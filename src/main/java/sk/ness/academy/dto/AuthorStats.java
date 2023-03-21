package sk.ness.academy.dto;

public class AuthorStats {

	  private String authorName;
	  private Integer articleCount;

	  public String getAuthorName() {
	    return this.authorName;
	  }

	  public void setAuthorName(final String authorName) {
	    this.authorName = authorName;
	  }

	  public Integer getArticleCount() {
	    return this.articleCount;
	  }

	  public void setArticleCount(final Integer articleCount) {
	    this.articleCount = articleCount;
	  }

	}
