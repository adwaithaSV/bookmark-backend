package com.example.bookmark.service;

import com.example.bookmark.model.Bookmark;
import com.example.bookmark.model.User;
import com.example.bookmark.repository.BookmarkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private static final int max = 5; 

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Transactional(readOnly = true) // 
    public Page<Bookmark> getUserBookmarks(User user, Pageable pageable) {
        return bookmarkRepository.findByUser(user, pageable);
    }

    @Transactional(readOnly = true) 
    public Page<Bookmark> searchUserBookmarks(User user, String searchTerm, Pageable pageable) {
        return bookmarkRepository.findByUserAndTitleContainingIgnoreCaseOrUrlContainingIgnoreCase(user, searchTerm, pageable);
    }

    @Transactional 
    public Bookmark addBookmark(Bookmark bookmark) {
        long currentBookmarkCount = bookmarkRepository.countByUser(bookmark.getUser());
        if (currentBookmarkCount >= max) { 
            throw new IllegalStateException("You have reached the maximum limit of " + max + " bookmarks.");
        }
        return bookmarkRepository.save(bookmark);
    }

    @Transactional(readOnly = true) 
    public Optional<Bookmark> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id);
    }

    @Transactional
    public Bookmark updateBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }
}