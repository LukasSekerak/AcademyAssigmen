package sk.ness.academy.dto;

public class AuthorStats {

	  private String authorName;
	  private Long articleCount;

	public AuthorStats(String authorName, Long articleCount) {
		this.authorName = authorName;
		this.articleCount = articleCount;
	}

	public String getAuthorName() {
	    return this.authorName;
	  }

	  public void setAuthorName(final String authorName) {
	    this.authorName = authorName;
	  }

	  public Long getArticleCount() {
	    return this.articleCount;
	  }

	  public void setArticleCount(final Long articleCount) {
	    this.articleCount = articleCount;
	  }

	}
