package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/{followeeId}/followUser")
    public ResponseEntity<String> followUser(@PathVariable Long followeeId) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        userService.followUser(loggedInUserId, followeeId);
        return ResponseEntity.ok("User successfully followed.");
    }


    @PostMapping("/{followeeId}/unfollowUser")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followeeId) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        userService.unfollowUser(loggedInUserId, followeeId);
        return ResponseEntity.ok("User successfully unfollowed.");
    }

    @PostMapping("/{categoryId}/followCategory")
    public ResponseEntity<String> followCategory(@PathVariable Long categoryId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        userService.followCategory(loggedInUserId, categoryId);
        return ResponseEntity.ok("Category successfully followed.");

    }

    @PostMapping("/{categoryId}/unfollowCategory")
    public ResponseEntity<String> unfollowCategory(@PathVariable Long categoryId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        userService.unfollowCategory(loggedInUserId, categoryId);

        return ResponseEntity.ok("Category successfully unfollowed.");
    }


}
