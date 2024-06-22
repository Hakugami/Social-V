package org.spring.likeservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.likeservice.models.Dtos.LikeDto;
import org.spring.likeservice.services.LikeService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/likes")
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<EntityModel<LikeDto>> addLike(@RequestParam String userId, @RequestParam String postId){
        log.info("Received request to add like by user: {} for post: {}", userId, postId);
        LikeDto likeDto = likeService.addLike(userId, postId);

        EntityModel<LikeDto> entityModel = EntityModel.of(likeDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LikeController.class).getLikesByPostId(postId)).withRel("likes-by-post")
        );


        return ResponseEntity.ok(entityModel);


    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<EntityModel<LikeDto>> getLikesByPostId(@PathVariable String postId) {
        log.info("Received request to get likes for post id: {}", postId);
        LikeDto likeDto = likeService.getLikesByPostId(postId);
        EntityModel<LikeDto> entityModel = EntityModel.of(likeDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LikeController.class).getLikesByPostId(postId)).withSelfRel());
        return ResponseEntity.ok(entityModel);


    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> removeLike(@PathVariable String likeId) {
        log.info("Received request to remove like with id: {}", likeId);
        likeService.removeLike(likeId);
        return ResponseEntity.noContent().build();


    }






}
