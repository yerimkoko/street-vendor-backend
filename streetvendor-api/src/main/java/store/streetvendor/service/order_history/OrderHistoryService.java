package store.streetvendor.service.order_history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.order_history.dto.response.OrderHistoryResponse;
import store.streetvendor.service.order_history.dto.response.OrderHistoryStoreResponse;
import store.streetvendor.domain.service.utils.StoreServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Service
public class OrderHistoryService {

    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    public List<OrderHistoryResponse> getOrderHistory(Long storeId, Long bossId) {

        StoreServiceUtils.validateExistsStore(storeRepository, storeId, bossId);
        Store store = storeRepository.findStoreByStoreId(storeId);
        OrderHistoryStoreResponse storeResponse = OrderHistoryStoreResponse.of(store);
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findOrderHistoryByStoreId(storeId);

        return orderHistoryList.stream()
            .map(orderHistory -> OrderHistoryResponse.
                of(orderHistory, storeResponse, memberRepository.findMemberById(orderHistory.getMemberId())))
            .collect(Collectors.toList());
    }

}
