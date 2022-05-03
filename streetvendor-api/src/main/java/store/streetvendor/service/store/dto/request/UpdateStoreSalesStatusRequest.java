package store.streetvendor.service.store.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import store.streetvendor.domain.domain.store.StoreSalesStatus;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStoreSalesStatusRequest {

    private StoreSalesStatus salesStatus;

}
