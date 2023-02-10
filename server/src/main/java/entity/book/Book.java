package entity.book;

import org.json.JSONObject;

/*
* Book class
* */
public class Book {
    private int id;
    private String isbn;
    private String title;
    private String description;
    private int pages;
    private String author;

    public int getId() {
        return this.id;
    }

    public Book setId(int id) {
        this.id = id;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPages() {
        return this.pages;
    }

    public Book setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public String toString() {
        return
            new JSONObject()
                .put("id", this.getId())
                .put("title", this.getTitle())
                .put("isbn", this.getTitle())
                .put("description", this.getDescription())
                .put("pages", this.getPages())
                .put("author", this.getAuthor())
                .toString()
        ;
    }

    public String getInsertQuery() {
        return
            "INSERT INTO Book (title, isbn, description, pages, author) " +
            "VALUES (" +
                "'" + this.getTitle() + "', " +
                "'" + this.getIsbn() + "', " +
                "'" + this.getDescription() + "', " +
                "'" + this.getPages() + "', " +
                "'" + this.getAuthor() + "'" +
            ");"
        ;
    }

}
