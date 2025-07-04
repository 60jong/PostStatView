# PostStatsView
<hr/>

자신이 운영 중인 Velog에 게재한 Post에 대한 통계를 이미지로 받아볼 수 있습니다 :)




## 서비스 내용

>velog에 작성한 게시글에 대한 통계를 이미지로 제공

- 게시글 수 (`Total Posts`)

- 게시글 태그 (`Tags (most 3)`)

- 총 방문자 수 (`Total Visitors`) - 선택

### 사용 예시

<img width="812" alt="스크린샷 2025-06-29 23 04 41" src="https://github.com/user-attachments/assets/134dba1e-2cd8-4fca-ab3c-5d9e2421a671" />


(빨간 박스 내 이미지)

## 필요 정보

- 필수

  - velog username

- 선택

  - velog에서 발행한 refresh_token (개인 velog 웹에서 개발자 도구를 통해 찾을 수 있음)

    - 총 방문자 수 표시를 위해 필요

## 사용 방법

- 게시글 수, 게시글 태그만 표시하고 싶은 경우

GET `https://poststatview.60jong.dev/api/v2/velog-stats?username={username}`

- 추가로 총 방문자 수를 표시하고 싶은 경우

POST `https://poststatview.60jong.dev/api/v2/velog-stats/users/{username}/token`

with Request body : {"refreshToken":"yourTokenValue"}

GET `https://poststatview.60jong.dev/api/v2/velog-stats?username={username}&show_visitors=true`

## 사용 기술

- Java 11

- Spring Boot 2.7.8

- Spring-Data-JPA

- AWS Ec2 Ubuntu 20.04.5 LTS

- MySQL 8.0.32

- JUNIT 5

GitHub stat을 보여주는 https://github.com/anuraghazra/github-readme-stats를 참고해서 만들어봤습니다!

개발 블로그로 velog를 사용 중이신 분은 한 번 사용해보시면 좋을 것 같아요:)

github : https://github.com/60jong/PostStatView
https://github.com/anuraghazra/github-readme-stats 에서 영감 받아 제작한 프로젝트입니다.
