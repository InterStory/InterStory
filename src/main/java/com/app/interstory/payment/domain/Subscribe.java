package com.app.interstory.payment.domain;

import java.sql.Timestamp;

import com.app.interstory.user.domain.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "subscribe")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscribe_id")
	private Long subscribeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User userId;

	@Column(name = "end_at", nullable = false)
	private Timestamp endAt;
}