
insert into member
(created_at, updated_at, email, name, nick_name, profile_url, provider)
values
    (now(), now(), 'gochi97@naver.com', '고예림', '토끼', 'fff', 'GOOGLE');

insert into member
(created_at, updated_at, email, name, nick_name, profile_url, provider)
values
    (now(), now(), 'whrmawnrmawn@gmail.com', '조금주', '꿍주', 'fff', 'GOOGLE');


insert into member
(created_at, updated_at, email, name, nick_name, profile_url, provider)
values
    (now(), now(), 'pdom0327@gmail.com', '안호빈', 'Pdom', null, 'GOOGLE');

insert into member
(created_at, updated_at, email, name, nick_name, profile_url, provider)
values
    (now(), now(), 'starpaks7@gmail.com', '윤운일', 'star', null, 'GOOGLE');


insert into notification(
                      title, content, created_at, updated_at, notification_type
)
values ( '[사장님 전용]사장님과 손님 페이지는 무엇일까요?', '무엇이냐면요', now(), now(), 'FAQ_BOSS');


insert into notification(
    title, content, created_at, updated_at, notification_type
)
values ( '[손님 전용] 메뉴 변경이 되지 않아요', '메뉴 변경이 되지 않을 때에는 아래의 연락처로 연락주세요.', now(), now(), 'FAQ_USER');

insert into notification(
    title, content, created_at, updated_at, notification_type
)
values ( '[공지사항] 공지사항입니다.', '공지사항입니다.', now(), now(), 'NOTIFICATION');


insert into store(
                  created_at, updated_at, boss_id, category, latitude, longitude,
                  location_description, name, sales_status, status, store_description, average_value
)
values (now(), now(), 1, 'TTEOK_BOKKI', 37.7708104, 126.7021019, '국민은행 앞', '사거리 떡볶이',
        'CLOSED', 'ACTIVE', '떡볶이 맛집', 0.0);

insert into store(
    created_at, updated_at, boss_id, category, latitude, longitude,
    location_description, name, sales_status, status, store_description, average_value
)
values (now(), now(), 1, 'TTEOK_BOKKI', 37.7718104, 126.7021019, '우리은행 앞', '삼거리 떡볶이',
        'OPEN', 'ACTIVE', '떡볶이가 맛있는 집', 0.0);

insert into store(
    created_at, updated_at, boss_id, category, latitude, longitude,
    location_description, name, sales_status, status, store_description, average_value
)
values (now(), now(), 1, 'BUNG_EO_PPANG', 37.7718104, 126.7031019, '파리바게트 앞', '신호등 떡볶이',
        'OPEN', 'ACTIVE', '슈크림 붕어빵이 맛있는 집', 0.0);

insert into store(
    created_at, updated_at, boss_id, category, latitude, longitude,
    location_description, name, sales_status, status, store_description,
    average_value
)
values (now(), now(), 1, 'BUNG_EO_PPANG', 37.45064589999992, 126.71990259999976, '재민이네 집앞', '재민이가 만든 떡볶이',
        'OPEN', 'ACTIVE', '정성을 팔고 있어요', 0.0);


insert into payment(created_at, updated_at, payment_method, store_id)
values ( now(), now(), 'ACCOUNT_TRANSFER', 1);

insert into payment(created_at, updated_at, payment_method, store_id)
values ( now(), now(), 'ACCOUNT_TRANSFER', 2);

insert into payment(created_at, updated_at, payment_method, store_id)
values ( now(), now(), 'ACCOUNT_TRANSFER', 3);

insert into payment(created_at, updated_at, payment_method, store_id)
values ( now(), now(), 'ACCOUNT_TRANSFER', 4);



insert into menu(created_at, updated_at, menu_count, name, picture_url, price, sales_status, store_id)
values ( now(), now(), 1, '떡볶이', 'sdfsdf', 4000, 'ON_SALE', 1);

insert into menu(created_at, updated_at, menu_count, name, picture_url, price, sales_status, store_id)
values ( now(), now(), 1, '떡볶이', 'sdfsdf', 4000, 'ON_SALE', 2);

insert into menu(created_at, updated_at, menu_count, name, picture_url, price, sales_status, store_id)
values ( now(), now(), 3, '슈크림 붕어빵', 'sdfsdf', 2000, 'ON_SALE', 3);

insert into menu(created_at, updated_at, menu_count, name, picture_url, price, sales_status, store_id)
values ( now(), now(), 1, '떡볶이', 'sdfsdf', 4000, 'ON_SALE', 4);



insert into business_hours(days, end_time, start_time, store_id)
values ( 'MON', '12:00:00', '08:00:00', 2);

insert into business_hours(days, end_time, start_time, store_id)
values ( 'TUE', '12:00:00', '08:00:00', 3);

insert into business_hours(days, end_time, start_time, store_id)
values ( 'SUN', '12:00:00', '08:00:00', 1);

insert into business_hours(days, end_time, start_time, store_id)
values ( 'SUN', '12:00:00', '08:00:00', 4);


insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 1, '설명', '위치 설명', 1, 1, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 1, '설명', '위치 설명', 1, 2, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 2, '설명', '위치 설명', 1, 3, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 2, '설명', '위치 설명', 1, 4, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 3, '설명', '위치 설명', 1, 1, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 3, '설명', '위치 설명', 1, 2, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 4, '설명', '위치 설명', 1, 3, 1, 'CASH', now(), 'CANCELED', now(), now());

insert into order_history(store_id, description, location_description, boss_id, member_id, order_id, payment_method, order_create_time, order_canceled_status, created_at, updated_at)
values ( 4, '설명', '위치 설명', 1, 4, 1, 'CASH', now(), 'CANCELED', now(), now());


insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 1, 1, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 2, 2, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 3, 3, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 4, 4, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 5, 1, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 6, 2, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 7, 3, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );

insert into review(order_history_id, member_id, comment, rate, created_at, updated_at)
values ( 8, 4, '여기는 진짜 맛있어요 인정', 'FIVE', now(), now() );


insert into review_image(review_id, member_id, image_url, status)
values ( 1, 1, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 2, 2, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 3, 3, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 4, 4, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 5, 1, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 6, 2, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 7, 3, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

insert into review_image(review_id, member_id, image_url, status)
values ( 8, 4, 'review/645f45c6-54a7-46d1-8b5d-cf2b90c11167.jpg', 'ACTIVE' );

