package com.example.training.service;

import static com.example.training.constant.Messages.ExceptionMessages.EMPTY_SHOPPING_CART;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import com.example.training.converter.ProductConverter;
import com.example.training.model.domain.OrderDto;
import com.example.training.model.domain.ProductDto;
import com.example.training.model.jpa.Order;
import com.example.training.model.jpa.OrderItem;
import com.example.training.model.jpa.ShoppingCart;
import com.example.training.repository.OrderItemRepository;
import com.example.training.repository.OrderRepository;
import com.example.training.repository.ShoppingCartItemRepository;
import com.example.training.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final TelegramNotificationService telegramNotificationService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public PaymentService(TelegramNotificationService telegramNotificationService,
                          ShoppingCartRepository shoppingCartRepository,
                          OrderRepository orderRepository,
                          OrderItemRepository orderItemRepository,
                          ShoppingCartItemRepository shoppingCartItemRepository) {
        this.telegramNotificationService = telegramNotificationService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Transactional
    public OrderDto pay(Long accountId) {
        ShoppingCart jpaShoppingCart = shoppingCartRepository.findShoppingCartByAccountId(accountId).orElseThrow();
        if (isEmpty(jpaShoppingCart.getItems())) {
            throw new IllegalStateException(EMPTY_SHOPPING_CART);
        }
        Order jpaOrder = saveOrder(jpaShoppingCart);
        shoppingCartItemRepository.deleteByShoppingCartId(jpaShoppingCart.getId());
        telegramNotificationService.send();
        return convertJpaOrder(jpaOrder);
    }

    private Order saveOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setAccount(shoppingCart.getAccount());
        order.setTotalCost(shoppingCart.getTotalCost());
        order.setDate(LocalDate.now());
        Order save = orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        shoppingCart.getItems().forEach(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(save);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        });
        order.setItems(orderItems);
        orderItemRepository.saveAll(orderItems);
        return save;
    }

    private OrderDto convertJpaOrder(Order source) {
        List<ProductDto> products = source.getItems()
                                          .stream()
                                          .map(item -> ProductConverter.convertJpaProductToProduct(item.getProduct(), item.getQuantity()))
                                          .toList();
        return new OrderDto(source.getTotalCost(), products);
    }
}
