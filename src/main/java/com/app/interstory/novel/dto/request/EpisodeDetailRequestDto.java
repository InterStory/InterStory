package com.app.interstory.novel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDetailRequestDto {
	private Long userId;
	private Boolean isFree;
}
