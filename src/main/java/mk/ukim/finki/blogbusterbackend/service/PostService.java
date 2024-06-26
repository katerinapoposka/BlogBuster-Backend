package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.dto.AddPostDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.FilterDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long postId);
    List<PostDTO> getAllByUserId(Long authorId);
    Optional<Post> addPost(AddPostDTO postDto) throws Exception;
    PostDTO editPost(AddPostDTO data) throws Exception;
    void deletePost(Long postId) throws Exception;
    List<PostDTO> filterPosts(FilterDTO filterDTO);
    List<PostDTO> getPostsByFollowedUsers(Long userId);
    List<PostDTO> getPostsFromUserProfile(Long userId);
    int totalLikesOfPost(Long postId) throws Exception;
}
