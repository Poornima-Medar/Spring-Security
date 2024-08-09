package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.controllers;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.PostDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "postId") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostDTO> createNewPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createNewPost(postDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long postId){
        return new ResponseEntity<>(postService.updatePost(postId,postDTO), HttpStatus.ACCEPTED);
    }


}
