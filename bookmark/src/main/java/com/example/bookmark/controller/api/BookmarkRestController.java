package com.example.bookmark.controller.api;

import com.example.bookmark.model.Bookmark;
import com.example.bookmark.model.User;
import com.example.bookmark.service.BookmarkService;
import com.example.bookmark.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkRestController {

    private final BookmarkService bookmarkService;
    private final UserService userService;

    public BookmarkRestController(BookmarkService bookmarkService, UserService userService) {
        this.bookmarkService = bookmarkService;
        this.userService = userService;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    @GetMapping
    public Page<Bookmark> getBookmarks(Authentication authentication,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "3") int size,
                                       @RequestParam(defaultValue = "addedTime") String sortBy,
                                       @RequestParam(defaultValue = "desc") String sortDir,
                                       @RequestParam(required = false) String searchTerm) {
        User user = getAuthenticatedUser(authentication);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return bookmarkService.searchUserBookmarks(user, searchTerm.trim(), pageable);
        }
        return bookmarkService.getUserBookmarks(user, pageable);
    }

    @PostMapping
    public ResponseEntity<?> addBookmark(@Valid @RequestBody Bookmark bookmark, Authentication authentication) {
        bookmark.setUser(getAuthenticatedUser(authentication));
        bookmark.setAddedTime(LocalDateTime.now());
        try {
            Bookmark savedBookmark = bookmarkService.addBookmark(bookmark);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBookmark);
        } catch (IllegalStateException e) {
        	java.util.Map<String, String> errorBody = new java.util.HashMap<>();
        	errorBody.put("message", e.getMessage());
        	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
        	}
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> updateBookmark(@PathVariable Long id, @Valid @RequestBody Bookmark bookmarkDetails, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Bookmark existingBookmark = bookmarkService.getBookmarkById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Bookmark not found or access denied"));
        
        existingBookmark.setTitle(bookmarkDetails.getTitle());
        existingBookmark.setUrl(bookmarkDetails.getUrl());
        return ResponseEntity.ok(bookmarkService.updateBookmark(existingBookmark));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Bookmark bookmark = bookmarkService.getBookmarkById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Bookmark not found or access denied"));
        
        bookmarkService.deleteBookmark(bookmark.getId());
        return ResponseEntity.noContent().build();
    }
}