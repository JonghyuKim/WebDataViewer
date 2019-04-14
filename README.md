# WebDataViewer

## 간단 기능 명세
### 1. 프로젝트 설명
    특정 Api 에 대한 요청 결과를 리스트뷰에 보여주고, 선택 시 상세 페이지를 보여주는 Android Application.
### 2. 구현 되어있는 Open Api 
    https://www.amiiboapi.com/api/amiibo
### 3. 이 프로젝트에 적용 된 오픈소스 들
    - Koin 
      목적 : DI
      이유 : 코틀린에 잘 어울리며 직관적인 간편한 사용방법
    - rxjava 
      목적 : 서비스 로직 구현
      이유 : 서비스 로직을 스트림 형태로 간결하게 조립하여 사용할 수 있어서 사용.
    - gson
      목적 : 서버로부터 받은 데이터 파싱
      이유 : 클래스와 json형식변환이 간편해
    - glide
      목적 : Image Loader
      이유 : 기본적인 이미지형식 지원부터 optionalTransform를 이용해 추가적인 확장까지 가능하게 만들어져있음.
    - okhttp3
      목적 : Http Client
      이유 : http / https 연결에 대해 동기 / 비동기 모두 지원하며 다양한 연결옵션(timeout, intersepter)지원
