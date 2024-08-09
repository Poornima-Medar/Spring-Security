package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.impl;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.PostDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.PostEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.exceptions.ResourceNotFoundException;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.repositories.PostRepository;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
       Optional<PostEntity> postEntity = postRepository.findById(id);
       return postEntity
               .map(post -> modelMapper.map(post, PostDTO.class))
               .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        PostEntity oldPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Resource not found exception"));
        postDTO.setId(postId);
        modelMapper.map(postDTO,oldPost);
        postRepository.save(oldPost);
        return modelMapper.map(oldPost,PostDTO.class);
    }
}
