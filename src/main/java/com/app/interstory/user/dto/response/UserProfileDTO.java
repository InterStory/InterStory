package com.app.interstory.user.dto.response;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDTO {
	private String nickname;
	private int point;
	private Timestamp subscriptionEndAt;
}
