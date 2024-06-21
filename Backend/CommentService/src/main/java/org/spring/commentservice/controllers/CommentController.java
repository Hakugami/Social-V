package org.spring.commentservice.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.commentservice.models.Dtos.CommentDto;
import org.spring.commentservice.models.Dtos.CommentRequest;
import org.spring.commentservice.services.CommentService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    @ApiResponse(description = "Adding comment to a post", responseCode = "201")
    public ResponseEntity<EntityModel<CommentRequest>> addComment(@RequestBody CommentRequest commentRequest){
        log.info("Received request to add comment: {}", commentRequest);
        CommentRequest savedComment = commentService.addComment(commentRequest);

        EntityModel<CommentRequest> entityModel = EntityModel.of(savedComment,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getComment(savedComment.getPostId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllComments(0, 10)).withRel("comments")
        );

        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getComment(savedComment.getPostId())).toUri())
                .body(entityModel);

    }

    @GetMapping()
    public ResponseEntity<List<EntityModel<CommentDto>>> getAllComments(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to get all comments with page: {} and size: {}", page, size);
        List<EntityModel<CommentDto>> comments = commentService.getAllComments(page, size).stream()
                .map(commentDto -> EntityModel.of(commentDto,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getComment(commentDto.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllComments(page, size)).withRel("comments")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CommentDto>> getComment(@PathVariable("id") String postId) {
        log.info("Received request to get comment with id: {}", postId);
        CommentDto commentDto = commentService.getCommentByPostId(postId);
        if (commentDto != null) {
            EntityModel<CommentDto> entityModel = EntityModel.of(commentDto,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getComment(postId)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllComments(0, 10)).withRel("comments")
            );
            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
