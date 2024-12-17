package com.app.interstory.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.interstory.user.domain.entity.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
	Page<Point> findByUser_UserId(Long userId, Pageable pageable);

	Optional<Point> findFirstByUser_UserIdOrderByUsedAtDesc(Long userId);
}
