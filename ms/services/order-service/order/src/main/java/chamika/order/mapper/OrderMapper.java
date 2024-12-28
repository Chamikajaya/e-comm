package chamika.order.mapper;

import chamika.order.dto.order.CreateOrderRequest;
import chamika.order.dto.order.OrderAddressDTO;
import chamika.order.dto.order.OrderResponse;
import chamika.order.model.Order;
import chamika.order.model.OrderItem;
import chamika.order.model.OrderShipmentAddress;
import chamika.order.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.DoubleStream;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public Order toEntity(CreateOrderRequest request) {

        Order order = Order.builder()
                .customerId(request.customerId())
                .status(OrderStatus.PENDING)  // Default status is PENDING when the order is created
                .paymentMethod(request.paymentMethod())
                .address(
                        OrderShipmentAddress.builder()
                                .street(request.shippingAddress().street())
                                .city(request.shippingAddress().city())
                                .zipCode(request.shippingAddress().zipCode())
                                .country(request.shippingAddress().country())
                                .build()
                )
                .build();

        // Map and add order items
        request.items().forEach(itemRequest -> {
            OrderItem orderItem = orderItemMapper.toEntity(itemRequest);
            order.addOrderItem(orderItem);
        });

        return order;


    }

    public OrderResponse toResponse(Order order) {

        return new OrderResponse(

                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getOrderTrackingReferenceString(),
                order.getTotalOrderPrice(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                new OrderAddressDTO(
                        order.getAddress().getStreet(),
                        order.getAddress().getCity(),
                        order.getAddress().getZipCode(),
                        order.getAddress().getCountry()
                ),
                order.getOrderItems().stream()
                        .map(orderItemMapper::toResponse)
                        .toList()
        );
    }
}
