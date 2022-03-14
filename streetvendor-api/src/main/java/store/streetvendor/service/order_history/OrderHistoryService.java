package store.streetvendor.service.order_history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.member.MemberServiceUtils;
import store.streetvendor.service.order.OrderServiceUtils;
import store.streetvendor.service.order_history.dto.request.OrderHistoryMenusRequest;
import store.streetvendor.service.order_history.dto.response.OrderHistoryMenuResponse;
import store.streetvendor.service.order_history.dto.response.OrderHistoryResponse;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Service
public class OrderHistoryService {

    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private void getOrderHistory(Long storeId, List<OrderHistoryMenuResponse> menuResponse) {
        Store store = storeRepository.findStoreByStoreId(storeId);

        return;


    }

}
