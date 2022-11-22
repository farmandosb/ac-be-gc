package com.armandocode.gclouddemo.security;

import com.armandocode.gclouddemo.dto.PostDto;
import com.armandocode.gclouddemo.exception.PostNotFoundException;
import com.armandocode.gclouddemo.model.Post;
import com.armandocode.gclouddemo.repository.PostRepository;
import com.armandocode.gclouddemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {

	@Autowired
	private AuthService authService;
	@Autowired
	DatastoreTemplate datastoreTemplate;

	public List<PostDto> showAllPosts() {
		List<Post> posts = this.datastoreTemplate.findAll(Post.class).stream().toList();
		return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
	}

	public void createPost(PostDto postDto) {
		System.out.println("before Create Post : "+postDto.getContent());
		Post post = mapFromDtoToPost(postDto);
		System.out.println("after Create Post : "+post.getContent());
		this.datastoreTemplate.save(post);

	}


	private PostDto mapFromPostToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getUsername());
		return postDto;

	}

	private Post mapFromDtoToPost(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		post.setUsername(loggedInUser.getUsername());
		post.setUpdateOn(Instant.now());
		return post;

	}
	

	public PostDto readSinglePost(Long id) throws PostNotFoundException {
		Post post = this.datastoreTemplate.findById(id, Post.class);
		return mapFromPostToDto(post);
	}

}
