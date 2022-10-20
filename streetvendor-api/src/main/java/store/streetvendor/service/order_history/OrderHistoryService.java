package store.streetvendor.service.order_history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.order_history.dto.response.MemberOrderHistoryResponse;
import store.streetvendor.service.order_history.dto.response.OrderHistoryResponse;
import store.streetvendor.service.order_history.dto.response.OrderHistoryStoreResponse;
import store.streetvendor.domain.service.utils.StoreServiceUtils;

import java.util.*;
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

    public List<MemberOrderHistoryResponse> allOrders(Long memberId) {
        Set<MemberOrderHistoryResponse> memberOrderHistories = new HashSet<>();
        memberOrderHistories.addAll(getMemberOrderHistory(memberId));
        memberOrderHistories.addAll(getOrderList(memberId));

        return memberOrderHistories.stream()
            .sorted(Comparator.comparing(MemberOrderHistoryResponse::getOrderId)
                .reversed())
            .collect(Collectors.toList());
    }

    private List<MemberOrderHistoryResponse> getMemberOrderHistory(Long memberId) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findOrderHistoryByMemberId(memberId);
        return orderHistoryList.stream()
            .map(MemberOrderHistoryResponse::of)
            .collect(Collectors.toList());
    }

    private List<MemberOrderHistoryResponse> getOrderList(Long memberId) {
        List<Orders> orderList = orderRepository.findOrdersByMemberId(memberId);
        return orderList.stream()
            .map(MemberOrderHistoryResponse::orderOf)
            .collect(Collectors.toList());
    }
}
