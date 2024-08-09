package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services;


import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    
    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO postDTO);

    PostDTO getPostById(Long id);

    PostDTO updatePost(Long postId, PostDTO postDTO);
}
