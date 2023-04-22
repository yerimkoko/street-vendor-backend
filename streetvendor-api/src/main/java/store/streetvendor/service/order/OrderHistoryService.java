package store.streetvendor.service.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.utils.dto.order_history.response.OrderDetailResponse;
import store.streetvendor.core.utils.service.MemberServiceUtils;
import store.streetvendor.core.utils.dto.order_history.MemberOrderHistoryResponse;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Service
public class OrderHistoryService {

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;


    @Transactional(readOnly = true)
    public List<MemberOrderHistoryResponse> allOrders(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        MemberServiceUtils.validateMember(member, memberId);
        Set<MemberOrderHistoryResponse> memberOrderHistories = new HashSet<>();
        memberOrderHistories.addAll(getMemberOrderHistory(member.getId()));
        memberOrderHistories.addAll(getOrderList(member.getId()));

        return memberOrderHistories.stream()
            .sorted(Comparator.comparing(MemberOrderHistoryResponse::getOrderId)
                .reversed())
            .collect(Collectors.toList());
    }

    private List<MemberOrderHistoryResponse> getMemberOrderHistory(Long memberId) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findOrderHistoryByMemberId(memberId);
        return orderHistoryList.stream()
            .map(MemberOrderHistoryResponse::historyOf)
            .collect(Collectors.toList());
    }


    private List<MemberOrderHistoryResponse> getOrderList(Long memberId) {
        List<Orders> orderList = orderRepository.findOrdersByMemberId(memberId);
        return orderList.stream()
            .map(MemberOrderHistoryResponse::orderOf)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long memberId, Long orderId) {
        Orders order = orderRepository.findByOrderAndMemberId(orderId, memberId);
        return OrderDetailResponse.of(order, order.getStore());
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderHistoryDetail(Long memberId, Long orderHistoryId) {
        OrderHistory orderHistory = orderHistoryRepository.findOrderHistoryByOrderIdAndMemberId(orderHistoryId, memberId).get(0);
        return OrderDetailResponse.historyOf(orderHistory, orderHistory.getStoreInfo().getStoreId());
    }

}
