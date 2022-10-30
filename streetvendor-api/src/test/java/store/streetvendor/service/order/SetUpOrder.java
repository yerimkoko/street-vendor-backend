package store.streetvendor.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.service.store.SetUpStore;

@SpringBootTest
public abstract class SetUpOrder extends SetUpStore {

    @Autowired
    protected OrderRepository orderRepository;

    protected Orders order;

    @BeforeEach
    protected void setUpOrder() {
        order = Orders.newOrder(store, boss.getId(), paymentMethod);
        orderRepository.save(order);
    }

    @AfterEach
    protected void cleanup() {
        orderRepository.deleteAllInBatch();
    }

}
