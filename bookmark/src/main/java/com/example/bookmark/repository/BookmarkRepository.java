package com.example.bookmark.repository;

import com.example.bookmark.model.Bookmark;
import com.example.bookmark.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Page<Bookmark> findByUser(User user, Pageable pageable);

    long countByUser(User user);

    @Query("SELECT b FROM Bookmark b WHERE b.user = :user AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(b.url) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Bookmark> findByUserAndTitleContainingIgnoreCaseOrUrlContainingIgnoreCase(User user, String searchTerm, Pageable pageable);
}
