package com.app.interstory.novel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.interstory.novel.domain.enumtypes.SortType;
import com.app.interstory.novel.dto.request.CommentRequestDto;
import com.app.interstory.novel.dto.response.CommentListResponseDto;
import com.app.interstory.novel.service.CommentService;
import com.app.interstory.user.domain.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CommentRestController {
	private final CommentService commentService;

	// 댓글 작성
	@PostMapping("/novels/episodes/{episodeId}/comments")
	public ResponseEntity<String> writeComment(@RequestBody CommentRequestDto requestDto, @PathVariable(name = "episodeId") long episodeId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		commentService.writeComment(requestDto, episodeId, userDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성이 완료되었습니다.");
	}

	// 회차 댓글 조회
	@GetMapping("/novels/episodes/{episodeId}/comments")
	public ResponseEntity<CommentListResponseDto> getEpisodeComment(@PathVariable(name = "episodeId") Long episodeId,
		@RequestParam(defaultValue = "NEW_TO_OLD", name = "sort") SortType sort, @RequestParam(defaultValue = "0", name = "page") Integer page,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		CommentListResponseDto commentList = commentService.getEpisodeComment(episodeId, sort, page, userDetails);
		return ResponseEntity.status(HttpStatus.OK).body(commentList);
	}

	// 소설 댓글 조회
	@GetMapping("/novels/{novelId}/comments/list")
	public ResponseEntity<CommentListResponseDto> getNovelComment(@PathVariable(name = "novelId") Long novelId,
		@RequestParam(defaultValue = "NEW_TO_OLD", name = "sort") SortType sort, @RequestParam(defaultValue = "0", name = "page") Integer page,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		CommentListResponseDto commentList = commentService.getNovelComment(novelId, sort, page, userDetails);
		return ResponseEntity.status(HttpStatus.OK).body(commentList);
	}

	// 댓글 삭제
	@DeleteMapping("/novels/episodes/comments/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable(name = "commentId") Long commentId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		commentService.deleteComment(commentId, userDetails);
		return ResponseEntity.noContent().build();
	}

	// 댓글 추천
	@PostMapping("/novels/episodes/comments/{commentId}/like")
	public ResponseEntity<String> likeComment(@PathVariable(name = "commentId") Long commentId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		String afterLikeMessage = commentService.likeComment(commentId, userDetails);
		return ResponseEntity.ok(afterLikeMessage);
	}
}