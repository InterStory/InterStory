package com.app.interstory.novel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.interstory.novel.domain.enumtypes.Sort;
import com.app.interstory.novel.dto.request.NovelRequestDTO;
import com.app.interstory.novel.dto.request.NovelSearchRequestDTO;
import com.app.interstory.novel.dto.response.NovelDetailResponseDTO;
import com.app.interstory.novel.dto.response.NovelResponseDTO;
import com.app.interstory.novel.service.NovelService;
import com.app.interstory.user.domain.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/novels")
@RequiredArgsConstructor
public class NovelRestController {

	private final NovelService novelService;

	// 소설 작성
	@PostMapping
	public ResponseEntity<Long> writeNovel(
		@RequestBody NovelRequestDTO novelRequestDTO,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = userDetails.getUser().getUserId();
		Long novelId = novelService.writeNovel(novelRequestDTO, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(novelId);
	}

	// 소설 수정
	@PutMapping("/{novelId}")
	public ResponseEntity<String> updateNovel(
		@PathVariable("novelId") Long novelId,
		@RequestBody NovelRequestDTO novelRequestDTO,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = userDetails.getUser().getUserId();
		novelService.updateNovel(novelId, novelRequestDTO, userId);
		return ResponseEntity.ok("Novel updated successfully");
	}

	// 소설 상세 조회
	@GetMapping("/{novelId}")
	public ResponseEntity<NovelDetailResponseDTO> readNovel(
		@PathVariable("novelId") Long novelId,
		@RequestParam(name = "sort", defaultValue = "NEW_TO_OLD") Sort sort,
		@RequestParam(name = "page", defaultValue = "1") int page
	) {
		Pageable pageable = PageRequest.of(page - 1, 4);

		NovelDetailResponseDTO response = novelService.readNovel(novelId, sort, pageable);
		return ResponseEntity.ok(response);
	}

	// 소설 목록 조회
	@GetMapping
	public ResponseEntity<Page<NovelResponseDTO>> getNovelList(
		@RequestBody NovelSearchRequestDTO searchRequestDTO
	) {
		Sort sort = searchRequestDTO.getSort() != null ? searchRequestDTO.getSort() : Sort.NEW_TO_OLD;
		int page = searchRequestDTO.getPage() > 0 ? searchRequestDTO.getPage() : 1;
		int size = searchRequestDTO.getSize() > 0 ? searchRequestDTO.getSize() : 10; // ?

		Pageable pageable = PageRequest.of(page - 1, size);

		Page<NovelResponseDTO> novels = novelService.getNovelList(
			searchRequestDTO.getStatus(),
			searchRequestDTO.getTitle(),
			searchRequestDTO.getAuthor(),
			searchRequestDTO.getMonetized(),
			searchRequestDTO.getMainTag(),
			sort,
			pageable
		);

		return ResponseEntity.ok(novels);
	}

	// 소설 삭제
	@DeleteMapping("/{novelId}")
	public ResponseEntity<Void> deleteNovel(
		@PathVariable("novelId") Long novelId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = userDetails.getUser().getUserId();
		novelService.deleteNovel(novelId, userId);
		return ResponseEntity.noContent().build();
	}
}