package com.example.bookmark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "URL cannot be empty")
    @Column(nullable = false)
    private String url;

    @NotBlank(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @Column(name = "added_time", nullable = false)
    private LocalDateTime addedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // This prevents the infinite loop
    private User user;
    public Bookmark() {
        super();
    }

    public Bookmark(String url, String title, User user) {
        this.url = url;
        this.title = title;
        this.user = user;
        this.addedTime = LocalDateTime.now(); 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) { 
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(id, bookmark.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bookmark{" +
               "id=" + id +
               ", url='" + url + '\'' +
               ", title='" + title + '\'' +
               ", addedTime=" + addedTime +
               ", userId=" + (user != null ? user.getId() : "null") +
               '}';
    }
}