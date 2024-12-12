package com.app.interstory.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.interstory.user.domain.entity.CartItem;
import com.app.interstory.user.domain.entity.Point;
import com.app.interstory.user.domain.entity.User;
import com.app.interstory.user.dto.response.CartItemResponseDTO;
import com.app.interstory.user.repository.CartItemRepository;
import com.app.interstory.user.repository.PointRepository;
import com.app.interstory.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final PointRepository pointRepository;

	// 장바구니 아이템 조회
	public List<CartItemResponseDTO> getCartItems(Long userId) {
		List<CartItem> cartItems = cartItemRepository.findByUser_UserId(userId);

		return cartItems.stream()
			.map(item -> new CartItemResponseDTO(
				item.getCartItemId(),
				item.getEpisode().getEpisodeId(),
				item.getEpisode().getTitle()))
			.collect(Collectors.toList());
	}

	// 장바구니 아이템 선택 삭제
	public void deleteCartItems(Long userId, List<Long> cartItemIds) {
		List<CartItem> cartItems = cartItemRepository.findByUser_UserIdAndCartItemIdIn(userId, cartItemIds);

		if (cartItems.isEmpty()) {
			throw new RuntimeException("No matching cart items found for deletion.");
		}

		cartItemRepository.deleteAll(cartItems);
	}

	// 장바구니 아이템 선택 결제
	@Transactional
	public void purchaseCartItems(Long userId, List<Long> cartItemIds) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		List<CartItem> cartItems = cartItemRepository.findByCartItemIdInAndUser_UserId(cartItemIds, userId);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("No matching cart items found for purchase.");
		}

		Long episodePrice = 500L;
		Long totalPrice = episodePrice * cartItems.size();

		user.reducePointsForPurchase(totalPrice);

		Point point = Point.builder()
			.user(user)
			.balance(-totalPrice)
			.description("Cart items purchase - Count: " + cartItems.size())
			.build();
		pointRepository.save(point);

		cartItemRepository.deleteAll(cartItems);
	}
}
