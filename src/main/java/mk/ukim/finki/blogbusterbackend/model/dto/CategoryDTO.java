package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;

import java.util.List;

@Getter
public class CategoryDTO {

    private Long id;
    private String name;
    private List<User> followers;
    private List<Post>posts;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public CategoryDTO() { }
    public CategoryDTO(Long id, @NonNull String name, List<User> followers, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.posts = posts;
    }
}