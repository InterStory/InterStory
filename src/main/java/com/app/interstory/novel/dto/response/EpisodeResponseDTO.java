package com.app.interstory.novel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeResponseDTO {
	private Long episodeId;
	private Long novelId;
	private String title;
	private Integer viewCount;
	private Timestamp publishedAt;
	private String thumbnailUrl;
	private Integer likeCount;
	private String content;
	private Boolean status;
}
