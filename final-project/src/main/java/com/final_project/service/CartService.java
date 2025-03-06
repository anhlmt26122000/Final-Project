package com.final_project.service;

import com.final_project.dto.request.CartRequest;
import com.final_project.entity.Cart;
import com.final_project.entity.CartItem;
import com.final_project.entity.Product;
import com.final_project.entity.User;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
import com.final_project.repository.CartItemRepository;
import com.final_project.repository.CartRepository;
import com.final_project.repository.ProductRepository;
import com.final_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // 📌 Hàm tạo giỏ hàng mới nếu User chưa có
    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart); // Gán giỏ hàng vào User
        return cartRepository.save(cart);
    }

    // 📌 Lấy giỏ hàng theo User
    public Cart getCartByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return cartRepository.findByUser(user)
                .orElseGet(() -> createNewCart(user)); // Nếu chưa có giỏ hàng thì tạo mới
    }

    // 📌 Thêm sản phẩm vào giỏ hàng
    public Cart addItemToCart(String userId, CartRequest request) {
        // 📌 Tìm sản phẩm theo productId từ request
        Product product = productRepository.findById(String.valueOf(request.getProductId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        // 📌 Lấy giỏ hàng của user (hoặc tạo mới nếu chưa có)
        Cart cart = getCartByUser(userId);

        // 📌 Kiểm tra xem sản phẩm đã có trong giỏ chưa
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        // 📌 Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem cartItem = (CartItem) cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {
            // ✅ Nếu sản phẩm đã có, tăng số lượng
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            // ✅ Nếu chưa có, tạo mới CartItem
            cartItem = new CartItem(product, request.getQuantity(), cart);
        }
        // 📌 Lưu cartItem vào database
         cartItemRepository.save(cartItem);
        return cart;
    }

    // 📌 Cập nhật số lượng sản phẩm trong giỏ hàng
    public Cart updateItemInCart(String userId, CartRequest request) {
        // 📌 Tìm sản phẩm theo productId từ request
        Product product = productRepository.findById(String.valueOf(request.getProductId()))
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        // 📌 Lấy giỏ hàng của user (hoặc tạo mới nếu chưa có)
        Cart cart = getCartByUser(userId);

        // 📌 Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem cartItem = (CartItem) cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_EXISTED));
        // 📌 Cập nhật số lượng
        cartItem.setQuantity(request.getQuantity());
        // 📌 Lưu cartItem vào database
        cartItemRepository.save(cartItem);
        return cart;
    }
}
