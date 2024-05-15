package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCategoryIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.UserNotFoundException;
import mk.ukim.finki.blogbusterbackend.repository.CategoryRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements mk.ukim.finki.blogbusterbackend.service.UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }


        };

    }


    @Override
    @Transactional
    public void followUser(Long loggedInUserId, Long followeeId) {
        User loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("Logged-in user not found with id: " + loggedInUserId));

        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new UserNotFoundException("User to be followed not found with id: " + followeeId));

        if (loggedInUser.getId().equals(followee.getId())) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        if (loggedInUser.getFollowingUsers().contains(followee)) {
            throw new IllegalArgumentException("User is already following the followee");
        }

        loggedInUser.getFollowingUsers().add(followee);

        userRepository.save(loggedInUser);
    }

    @Override
    @Transactional
    public void unfollowUser(Long loggedInUserId, Long followeeId) {

        User loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("Logged-in user not found with id: " + loggedInUserId));

        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new UserNotFoundException("User to be unfollowed not found with id: " + followeeId));

        if (loggedInUser.getId().equals(followee.getId())) {
            throw new IllegalArgumentException("Cannot unfollow yourself");
        }
        if (!loggedInUser.getFollowingUsers().contains(followee)) {
            throw new IllegalArgumentException("User is not following the followee");
        }

        loggedInUser.getFollowingUsers().remove(followee);

        userRepository.save(loggedInUser);
    }

    @Override
    public void followCategory(Long loggedInUserId, Long categoryId) {
        User loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("Logged-in user not found with id: " + loggedInUserId));

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new InvalidCategoryIdException();
        }

        Category category = categoryOptional.get();

        List<Category> followingCategories = loggedInUser.getFollowingCategories();
        if (!followingCategories.contains(category)) {
            followingCategories.add(category);
            loggedInUser.setFollowingCategories(followingCategories);
            userRepository.save(loggedInUser);
        }


    }

    @Override
    public void unfollowCategory(Long loggedInUserId, Long categoryId) {
        User loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new UserNotFoundException("Logged-in user not found with id: " + loggedInUserId));

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new InvalidCategoryIdException();
        }
        Category category = categoryOptional.get();

        if (!loggedInUser.getFollowingCategories().contains(category)) {
            throw new IllegalArgumentException("User is not following the category");
        }

        loggedInUser.getFollowingCategories().remove(category);

        userRepository.save(loggedInUser);

    }

    @Override
    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getId();
    }


}
