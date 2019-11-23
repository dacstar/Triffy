# Back-end Rest API 명세

### 1. auth_views

1) <http://10.3.17.166:8000/api/auth/signup/>

- 회원가입

- input

    ![img](images\EMB000020989996.bmp)  

- output 없음

2) <http://10.3.17.166:8000/api/auth/signin/>

- 로그인

- input

    ![img](images\EMB00002098999b.bmp)  

- output

  ```json
  {
      "user": "hwang",
      "id": 24,
      "check_balance": true
  }
  ```

3) <http://10.3.17.166:8000/api/auth/logout/>

- 로그아웃(django server에서 사용자 로그아웃)
- input 없음
- output 없음



### 2. pre_travel_views

1) <http://10.3.17.166:8000/api/get_balance/>

- 적금 계좌(신한 S힐링 여행적금)

- input

    ![img](images\EMB0000209899a2.bmp)  

- output

  ```json
  {
      "account": "230221966424",
      "name": "신한 S힐링 여행적금",
      "interest": 1.85,
      "goal_amount": 2000000,
      "now_amount": 1600000,
      "start_date": "20190927",
      "end_date": "20200427",
      "achieve": 80.0
  }
  ```

2) <http://10.3.17.166:8000/api/get_exchange/>

- 신한은행 환율 조회

- input 없음

- output

  ```json
  [
      {
          "buy": "001165.25",
          "sell": "001206.75",
          "currency": "USD",
          "now_date": "2019.11.24",
          "now_time": "03:10"
      }
  ]
  ```

3) <http://10.3.17.166:8000/api/checkList/>

- 여행 전 준비 리스트 조회(항공권 예약, 숙박 예약은 default로 입력된 상태)

- input

    ![img](images\EMB0000209899b5.bmp)  

- output

  ```json
  {
      "items": [
          {
              "id": 46,
              "user": 24,
              "content": "항공권 예약",
              "checked": false
          },
          {
              "id": 47,
              "user": 24,
              "content": "숙박 예약",
              "checked": false
          }
      ]
  }
  ```

4) <http://10.3.17.166:8000/api/check/>

- 완료된 체크 리스트 item은 체크 boolean = True로 전환

- input

    ![img](images\EMB0000209899bc.bmp)  

- output

  ```json
  {
      "items": [
          {
              "id": 46,
              "user": 24,
              "content": "항공권 예약",
              "checked": false
          },
          {
              "id": 47,
              "user": 24,
              "content": "숙박 예약",
              "checked": true
          }
      ]
  }
  ```

5) <http://10.3.17.166:8000/api/check/add_item/>

- 체크 리스트에 사용자가 항목 추가 가능

- input

    ![img](images\EMB0000209899c4.bmp)  

- output

  ```json
  {
      "items": [
          {
              "id": 46,
              "user": 24,
              "content": "항공권 예약",
              "checked": false
          },
          {
              "id": 47,
              "user": 24,
              "content": "숙박 예약",
              "checked": true
          },
          {
              "id": 48,
              "user": 24,
              "content": "렌터카 예약",
              "checked": false
          }
      ]
  }
  ```

6) <http://10.3.17.166:8000/api/check/edit_item/>

- 체크 리스트 항목 수정

- input

    ![img](images\EMB0000209899cc.bmp)  

- output

  ```json
  {
      "items": [
          {
              "id": 46,
              "user": 24,
              "content": "항공권 예약",
              "checked": false
          },
          {
              "id": 47,
              "user": 24,
              "content": "숙박 예약",
              "checked": true
          },
          {
              "id": 48,
              "user": 24,
              "content": "렌터카 예약",
              "checked": false
          }
      ]
  }
  ```

7) <http://10.3.17.166:8000/api/check/delete_item/>

- 체크 리스트 항목 삭제

- input

    ![img](images\EMB0000209899d3.bmp)  

- output

  ```json
  {
      "items": [
          {
              "id": 46,
              "user": 24,
              "content": "항공권 예약",
              "checked": false
          },
          {
              "id": 47,
              "user": 24,
              "content": "숙박 예약",
              "checked": true
          }
      ]
  }
  ```

8) <http://10.3.17.166:8000/api/check/sub_balance/>

- 항공권, 숙박 예약 시 결제 금액을 적금 목표 금액&현재 납입 금액에서 차감
- input : spent_amount(결제 금액)
- output : {account, name, interest, goal_amount, now_amount, start_date, end_date, achieve}

9) <http://10.3.17.166:8000/api/check/airplane/>

- 항공권 예약 현황 조회
- input 없음(django server에서 현재 로그인된 사용자 조회 가능)
- output : {price, outdeparture, outarrival, indeparture, inarrival, name}



### 3. inter_travel_views

1) <http://10.3.17.166:8000/api/calendar/add_item/>

- 가계부에 사용자가 항목을 입력(외화 현찰 사용 내역)

- card input은 제외 -> card 사용 내역은 신한카드 체크카드/신용카드 해외 일시불 결제 내역 api 반영

- input

    ![img](images\EMB0000209899e3.bmp)  

- output

  ```json
  [
      {
          "id": 2,
          "user_id": 24,
          "category": "외식",
          "time_now": "2019-11-23",
          "content": "MadamLan",
          "money": 200000,
          "card": false,
          "spent": true,
          "currency": "VND"
      },
      {
          "id": 3,
          "user_id": 24,
          "category": "외식",
          "time_now": "2019-11-23",
          "content": "HappyBread",
          "money": 70000,
          "card": false,
          "spent": true,
          "currency": "VND"
      },
      {
          "id": 4,
          "user_id": 24,
          "category": "쇼핑",
          "time_now": "2019-11-23",
          "content": "Hanmarket Onepiece",
          "money": 700000,
          "card": true,
          "spent": true,
          "currency": "VND"
      }
  ]
  ```

2) <http://10.3.17.166:8000/api/calendar/view/>

- 날짜별 소비 내역 조회

- input 없음

- output

  ```json
  {
      "2019-11-23": [
          {
              "category": "외식",
              "content": "MadamLan",
              "money": 200000,
              "card": false,
              "spent": true,
              "currency": "VND"
          },
          {
              "category": "외식",
              "content": "HappyBread",
              "money": 70000,
              "card": false,
              "spent": true,
              "currency": "VND"
          },
          {
              "category": "쇼핑",
              "content": "Hanmarket Onepiece",
              "money": 700000,
              "card": true,
              "spent": true,
              "currency": "VND"
          }
      ]
  }
  ```

3) <http://10.3.17.166:8000/api/calendar/category/>

- 소비 카테고리 별(외식, 공연/영화, 의료, 뷰티, 숙박, 쇼핑, 교통비) 사용 내역 조회(sorted)

- input 없음

- output

  ```json
  [
      [
          "쇼핑",
          700000
      ],
      [
          "외식",
          270000
      ]
  ]
  ```

4) <http://10.3.17.166:8000/api/calendar/card/>

- 카드/현금 소비내역 조회(sorted)

- input 없음

- output

  ```json
  [
      [
          "card",
          700000
      ],
      [
          "cash",
          270000
      ]
  ]
  ```

5) <http://10.3.17.166:8000/api/calendar/switch_spent/>

- 소비 예정 금액(계획)과 결제 금액(실제 소비)를 spend(boolean)을 통해 사용자가 수정 가능

- spent가 True이면 이미 소비한 내역, False이면 소비를 계획한 내역

- input(item_pk)

    ![img](images\EMB0000209899f7.bmp)  

- output

  ```json
  {
      "2019-11-23": [
          {
              "pk": 2,
              "category": "외식",
              "content": "MadamLan",
              "money": 200000,
              "card": false,
              "spent": true,
              "currency": "VND"
          },
          {
              "pk": 3,
              "category": "외식",
              "content": "HappyBread",
              "money": 70000,
              "card": false,
              "spent": true, // 소비한 내역
              "currency": "VND"
          },
          {
              "pk": 4,
              "category": "쇼핑",
              "content": "Hanmarket Onepiece",
              "money": 700000,
              "card": true,
              "spent": false, // 소비 계획
              "currency": "VND"
          }
      ]
  }
  ```

6) <http://10.3.17.166:8000/api/calendar/delete_item/>

- calendar의 입력 item을 삭제

- input

    ![img](images\EMB0000209899ff.bmp)  

- output

  ```json
  {
      "2019-11-23": [
          {
              "pk": 2,
              "category": "외식",
              "content": "MadamLan",
              "money": 200000,
              "card": false,
              "spent": true,
              "currency": "VND"
          },
          {
              "pk": 3,
              "category": "외식",
              "content": "HappyBread",
              "money": 70000,
              "card": false,
              "spent": true,
              "currency": "VND"
          }
      ]
  }
  ```

7) <http://10.3.17.166:8000/api/calendar/edit_item/>

- calendar의 item을 수정

- item_pk는 필수값, category, content, money 등의 값은 옵션

- input

    ![img](images\EMB000020989a06.bmp)  

- output

  ```json
  {
      "2019-11-23": [
          {
              "pk": 2,
              "category": "쇼핑",
              "content": "MadamLan",
              "money": 200000,
              "card": false,
              "spent": true,
              "currency": "VND"
          },
          {
              "pk": 3,
              "category": "외식",
              "content": "HappyBread",
              "money": 70000,
              "card": false,
              "spent": true,
              "currency": "VND"
          }
      ]
  }
  ```

### 4. post_travel_views

1) <http://10.3.17.166:8000/api/post/write/>

- 여행 후기 포스트 작성

- input

    ![img](images\EMB000020989a0f.bmp)  

- output 없음(response.status 200 리턴 받으면 포스팅 리스트 화면으로 가기)

2) <http://10.3.17.166:8000/api/post/list/>

- 전체 포스팅 조회

- input 없음

- output

  ```json
  [
      {
          "id": 1,
          "writer": 2,
          "city": 2,
          "title": "Day2. 아이카에서",
          "photo": "https://www.deginvest.de/Bilder-und-Grafiken/Projekte_Branchen_Regionen/Lima_Peru_Andenstaaten_Responsive_1280x520.jpg",
          "content": "",
          "created_at": "2019-11-23T19:46:36.515051+09:00",
          "updated_at": "2019-11-23T19:46:36.515051+09:00",
          "agreement": true,
          "like_users": [],
          "like_count": 0
      },
      {
          "id": 2,
          "writer": 2,
          "city": 2,
          "title": "Day3. 페루 아이카 전통 음식과 함께",
          "photo": "https://www.deginvest.de/Bilder-und-Grafiken/Projekte_Branchen_Regionen/Lima_Peru_Andenstaaten_Responsive_1280x520.jpg",
          "content": "청량한 색감의 산들로 눈을 정화하기에 좋은 곳이었습니다. :)",
          "created_at": "2019-11-24T01:26:05.192521+09:00",
          "updated_at": "2019-11-24T01:26:05.192521+09:00",
          "agreement": true,
          "like_users": [],
          "like_count": 0
      }
  ]
  ```

3) <http://10.3.17.166:8000/api/post/like/>

- 포스트에 좋아요 누르기

- 클릭하여 True(좋아요) -> False(좋아요 취소) 전환

- input

    ![img](images\EMB000020989a1b.bmp)  

- output

  ```json
  {
      "id": 2,
      "writer": 2,
      "city": 2,
      "title": "Day3. 페루 아이카 전통 음식과 함께",
      "photo": "https://www.deginvest.de/Bilder-und-Grafiken/Projekte_Branchen_Regionen/Lima_Peru_Andenstaaten_Responsive_1280x520.jpg",
      "content": "청량한 색감의 산들로 눈을 정화하기에 좋은 곳이었습니다. :)",
      "created_at": "2019-11-24T01:26:05.192521+09:00",
      "updated_at": "2019-11-24T01:26:05.192521+09:00",
      "agreement": true,
      "like_users": [
          2
      ],
      "like_count": 1
  }
  ```



### 5. reservation_views

1) http://10.3.17.166:8000/api/save_airline/

- 항공권 예약 결제

- input

  ![img](images\선택한 항공권 저장.PNG)

- output 없음

2) http://10.3.17.166:8000/api/save_house/

- 숙박 예약 결제

- input

  ![img](images\숙박 저장.PNG)

- output 없음

3) http://10.3.17.166:8000/api/show_house/

- 숙박 시설 조회

- input

  ![img](images\숙박 조회.PNG)

- output

  ![img](images\숙박 조회 결과.PNG)

4) http://10.3.17.166:8000/api/show_reserve_house/

- 예약된 숙박 내역 조회

- input

  ![img](images\저장한 숙박 조회.PNG)

- output

  ![img](images\저장한 숙박 조회 결과.PNG)

5) http://10.3.17.166:8000/api/show_reserve_airline/

- 예약된 항공 내역 조회

- input

  ![img](images\저장한 항공권 조회.PNG)

- output

  ![img](images\저장한 항공권 조회 결과.PNG)

6) http://10.3.17.166:8000/api/show_airline/

- 항공권  리스트 조회

- input

  ![img](images\항공권검색.PNG)

- output

  ![img](images\항공권검색결과.PNG)

