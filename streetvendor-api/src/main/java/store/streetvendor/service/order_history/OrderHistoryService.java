package store.streetvendor.service.order_history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.service.utils.MemberServiceUtils;
import store.streetvendor.service.order_history.dto.response.MemberOrderHistoryResponse;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Service
public class OrderHistoryService {

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;


    public List<MemberOrderHistoryResponse> allOrders(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
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
}
