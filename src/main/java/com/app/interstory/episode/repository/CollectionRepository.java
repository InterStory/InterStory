package com.app.interstory.episode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.interstory.novel.domain.entity.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
	boolean existsByUserAndEpisode(Long userId, Long episodeId);
}
