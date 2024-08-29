package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.controllers;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.PostDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.PostService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping(path = "/{postId}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN') OR hasAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
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
