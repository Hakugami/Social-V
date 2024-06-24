package org.spring.commentservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.commentservice.events.Notification;
import org.spring.commentservice.mappers.CommentMapper;
import org.spring.commentservice.models.CommentModel;
import org.spring.commentservice.models.Dtos.CommentDto;
import org.spring.commentservice.models.Dtos.CommentRequest;
import org.spring.commentservice.repositories.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public CommentModel addComment(CommentRequest commentRequest) {
        CommentModel commentModel = commentMapper.toEntity(commentRequest);
        log.info("Adding comment: {}", commentModel);
        commentModel = commentRepository.save(commentModel);
        kafkaTemplate.send("notifications-topic", Notification.builder()
                .receiverUsername(commentRequest.getPostOwnerUsername())
                .senderUsername(commentRequest.getUsername())
                .id(commentRequest.getPostId())
                .notificationType("COMMENT")
                .message("You have a new comment").build());
        return commentModel;

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

    public List<CommentDto> getCommentByPostId(String postId , int page, int size){
        List<CommentModel> commentModel = commentRepository.getByPostId(postId, PageRequest.of(page, size));
        return commentModel.stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public void deleteComment(String commentId){
        log.info("Deleting comment by id: {}", commentId);
        commentRepository.deleteById(commentId);
        log.info("Comment by id: {} deleted", commentId);
    }




}
