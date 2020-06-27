# money-spread
api server demo test

#Common Requirement
1. 뿌리기, 받기, 조회 REST API
2. 요청 사용자 REQUEST HEADER 항목 X-USER-ID, X-ROOM-ID를 사용
3. 각 기능 단위 테스트

#Api Requirement & implement check list
1.뿌리기(POST)
- 뿌릴 금액, 뿌릴 인원(대화방 이용자들 ID)을 Request 값으로 받음. (O)
- Unique token 발급 난수 문자열 3자리 (O)
- 인원에 따라 뿌릴 금액 분배 정보 저장 (O)
- Response 메시지 발급된 Unique Token 포함. (O)
 
2.받기(GET)
- 발급된 Unique token을  Request 값으로 받음. (O)
- 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만 받을수 있음. (O)  
- 뿌리기로 발급된 token은 10분까지 유효함. (O)
- Response 메시지에 분배된 금액을 포함. (O)

3.조회(GET)
- 발급된 Unique token을  Request 값으로 받음. (O)
- 뿌린 사용자만 조회가능 (O)
- 조회기능은 7일까지 유효함. (O)
- Response 메시지에 뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료 정보([받은금액, 받은 사용자 아이디] 리스트) 포함. (O)

# 문제해결 전략
- 각 필요한 서비스 생성 (난수 문자열 3자리 발급 서비스, 인원에 따라 금액 분배 서비스)
- 뿌릴 현황 정보, 금액 분배 정보, 받은 사용자 정보 Schema 분리
- 각 Entity에 접근하는 서비스 개별 생성
- 현황 정보 기준으로 token 만료 시간 검증
- 받은 사용자 정보를 따로 저장하여 update 가능성을 제거함.

# 제약조건으로 인한 문제 및 개선 방안
- token 길이가 3자리로인해 DB조회를 할수 밖에 없음. 제한이 없다면, token내부에 정보(발급시간, 발급자)를 넣고 DB접근을 줄이는 방법으로 개선이 필요.

