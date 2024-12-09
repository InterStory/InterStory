package com.app.interstory.novel.domain.entity;

import com.app.interstory.novel.domain.enumtypes.MainTag;
import com.app.interstory.novel.domain.enumtypes.NovelStatus;
import com.app.interstory.user.domain.entity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "novel")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Novel {
	private static final NovelStatus DEFAULT_STATUS = NovelStatus.DRAFT;
	private static final Integer DEFAULT_FAVORITE_COUNT = 0;
	private static final Integer DEFAULT_LIKE_COUNT = 0;
	private static final Boolean DEFAULT_IS_FREE = false;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "novel_id")
	private Long novelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column(name = "plan", columnDefinition = "TEXT", nullable = false)
	private String plan;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private NovelStatus status = DEFAULT_STATUS;

	@Column(name = "thumbnail_renamed_filename")
	private String thumbnailRenamedFilename;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@Builder.Default
	@Column(name = "favorite_count", nullable = false)
	private Integer favoriteCount = DEFAULT_FAVORITE_COUNT;

	@Builder.Default
	@Column(name = "like_count", nullable = false)
	private Integer likeCount = DEFAULT_LIKE_COUNT;

	@Builder.Default
	@Column(name = "is_free", nullable = false)
	private Boolean isFree = DEFAULT_IS_FREE;

	@Column(name = "tag", nullable = false)
	@Enumerated(EnumType.STRING)
	private MainTag tag;
}
