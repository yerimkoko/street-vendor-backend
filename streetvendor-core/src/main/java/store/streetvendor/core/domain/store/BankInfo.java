package store.streetvendor.core.domain.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Getter
public class BankInfo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankType bankType;

    @Column(nullable = false)
    private String accountNumber;

    public BankInfo(BankType bankType, String accountNumber) {
        this.bankType = bankType;
        this.accountNumber = accountNumber;
    }
}
