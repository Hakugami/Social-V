package org.spring.commentservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.commentservice.mappers.CommentMapper;
import org.spring.commentservice.models.CommentModel;
import org.spring.commentservice.models.Dtos.CommentDto;
import org.spring.commentservice.models.Dtos.CommentRequest;
import org.spring.commentservice.repositories.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentRequest addComment(CommentRequest commentRequest) {
        CommentModel commentModel = commentMapper.toEntity(commentRequest);
        log.info("Adding comment: {}", commentModel);
        commentRepository.save(commentModel);
        return commentRequest;
    }

    public List<CommentDto> getAllComments(int page, int size){
        log.info("Getting all comments with pagination - page: {}, size: {}", page, size);
        List<CommentModel> commentModels = commentRepository.findAll(PageRequest.of(page, size)).getContent();
        return commentModels.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto getComment(String commentId){
        log.info("Getting comment by id: {}", commentId);
        Optional<CommentModel> commentModel = commentRepository.findById(commentId);
        return commentModel.map(commentMapper::toDto).orElse(null);
    }

    public CommentDto getCommentByPostId(String postId){
        CommentModel commentModel = commentRepository.getByPostId(postId);
        return commentMapper.toDto(commentModel);
    }

    public void deleteComment(String commentId){
        log.info("Deleting comment by id: {}", commentId);
        commentRepository.deleteById(commentId);
        log.info("Comment by id: {} deleted", commentId);
    }




}
