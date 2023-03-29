package sk.ness.academy.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "articles")
@SequenceGenerator(name = "articles_seq_store", sequenceName = "article_seq", allocationSize = 1)
public class Article {

  public Article() {
    this.createTimestamp = new Date();
  }

  @Id
  @Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articles_seq_store")
  private Integer id;

  @Column(name = "title", length = 250)
  private String title;

  @Column(name = "text", length = 2000)
  private String text;

  @Column(name = "author", length = 250)
  private String author;

  @Column(name = "create_timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTimestamp;

  @OneToMany(targetEntity=sk.ness.academy.domain.Comment.class, cascade= CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name="ARTICLE_ID")
  private List<Comment> comments = new ArrayList<>();

  public Integer getId() {
    return this.id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getText() {
    return this.text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(final String author) {
    this.author = author;
  }

  public Date getCreateTimestamp() {
    return this.createTimestamp;
  }

  public void setCreateTimestamp(final Date createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
