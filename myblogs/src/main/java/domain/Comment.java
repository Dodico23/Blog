package domain;

import java.util.Date;

import javax.xml.crypto.Data;


public class Comment {
// Идентификатор комментари
 private Long id;
 
 
 
 // Комментарий
 private String content;
 
 // Дата создания комментария
 private Date datecomment;
 

 // Внешний ключи -ссылки на сущности Author, Blog
 private Long idBlog;
 private Long idAuthor;
 
 // Навигационное свойства - ссылки на автора, пост
 private Blog blog;
 private Author author;
 public Comment() {
}
 
 
 public Comment(Long id, String content, Date datecomment, Long idAuthor, Author author, Long idBlog,  Blog blog) {
 this.id = id;
 this.content = content;
 this.datecomment = datecomment;
 this.author = author;
 this.blog = blog;
this.idAuthor = idAuthor;
this.idBlog = idBlog;
 }
 
 public Comment(Long id, String content, Date datecomment, Long idAuthor,  Long idBlog) {
	 this.id = id;
	 this.content = content;
	 this.datecomment = datecomment;
	
	this.idAuthor = idAuthor;
	this.idBlog = idBlog;
	 }

public String getContent() {
return content;
}
public void setContent(String content) {
this.content = content;
}
public Date getDateComment() {
return datecomment;
}
public void setDateComment(Date datecomment) {
this.datecomment = datecomment;
}
public Author author () {
return author;
}
public Blog blog () {
return blog;
}
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}


public String getAuthor() {
return author.getFirstName();
}
public void setAuthor(Author author) {
this.author = author;
}
public Long getIdAuthor() {
return idAuthor;
}
public void setIdAuthor(Long idAuthor) {
this.idAuthor = idAuthor;
}


public String getBlog() {
return blog.getTitle();
}
public void setBlog(Blog blog) {
this.blog = blog;
}
public Long getIdBlog() {
return idBlog;
}
public void setIdBlog(Long idBlog) {
this.idBlog = idBlog;
}
@Override
public String toString() {
return "Author {" + "Id = " + id + 
", title = " + getBlog() + 
", content = " + content + 
", date = " + datecomment + 
",  fistname = " + getAuthor() + 
"}";
}
}