package com.clone.twitter.postservice.service.comment;

import com.clone.twitter.postservice.dto.comment.CommentDto;
import com.clone.twitter.postservice.entity.Comment;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.kafka.event.State;
import com.clone.twitter.postservice.kafka.producer.comment.CommentProducer;
import com.clone.twitter.postservice.mapper.comment.CommentMapper;
import com.clone.twitter.postservice.repository.comment.CommentRepository;
import com.clone.twitter.postservice.repository.post.PostRepository;
import com.clone.twitter.postservice.service.commonMethods.CommonServiceMethods;
import com.clone.twitter.postservice.validator.comment.CommentValidatorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentValidatorImpl commentValidatorImpl;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentProducer commentProducer;
    private final CommonServiceMethods commonServiceMethods;

    @Override
    public CommentDto createComment(CommentDto commentDto, long userId, long postId) {
        Post post = commonServiceMethods.findEntityById(postRepository, postId, "Post");
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthorId(userId);
        comment.setPost(post);

        commentValidatorImpl.validateCreateComment(userId);

        comment = commentRepository.save(comment);

        commentProducer.produce(commentMapper.toKafkaEvent(comment, State.ADD));

        log.info("Created comment on post {} authored by {}", postId, userId);

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long userId, long commentId) {
        Comment commentToUpdate = commonServiceMethods.findEntityById(commentRepository, commentId, "Comment");

        commentMapper.update(commentDto, commentToUpdate);
        commentToUpdate = commentRepository.save(commentToUpdate);

        commentProducer.produce(commentMapper.toKafkaEvent(commentToUpdate, State.UPDATE));

        log.info("Updated comment {} on post {} authored by {}", commentId, commentToUpdate.getPost().getId(), userId);

        return commentMapper.toDto(commentToUpdate);
    }

    @Override
    public List<CommentDto> getPostComments(long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto  deleteComment(long commentId, long userId, long postId) {
        Comment comment = commonServiceMethods.findEntityById(commentRepository, commentId, "Comment");
        CommentDto commentToDelete = commentMapper.toDto(comment);

        commentRepository.deleteById(commentId);

        commentProducer.produce(commentMapper.toKafkaEvent(comment, State.DELETE));

        log.info("Deleted comment {} on post {} authored by {}", commentId, comment.getPost().getId(), userId);

        return commentToDelete;
    }
}