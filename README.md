# 💰 와구와구 결제, 정산 서버 
### 👤 담당자 : 조진호

## <br>📃 핵심 기능
### 결제 내역, 정산금 관리

- 결제 내역 생성, 취소 및 가져오기
- 배달 완료 후 정산금 계산(수수료 및 세금 고려) 후 가게 및 라이더 서버에 정산금 전달

## <br>🏷️ 전체 프로젝트 링크
https://github.com/WAGUWAGUUU/WAGUWAGU

## <br>⚙️ 기술스택

### ✔️ Server Framework
![Spring-Boot](https://img.shields.io/badge/spring--boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)

### ✔️ Database
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

### ✔️ Message Broker  
![apachekafka](https://img.shields.io/badge/apachekafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
   
## <br>🧾 API 명세서 (with Swagger API)<br><br>

<img width="697" alt="image" src="https://github.com/user-attachments/assets/09bf8e8d-e54e-4322-b632-e096bc6bd0e3">
<img width="698" alt="image" src="https://github.com/user-attachments/assets/6f6a7947-ce75-4095-85e0-84f12ee9f135">
<br><br>

## <br>🧾 데이터 흐름도<br><br>

<img width="847" alt="image" src="https://github.com/user-attachments/assets/0d22f59d-8899-4e0a-a640-75b357fcfe4f">
<br>
<img width="685" alt="image" src="https://github.com/user-attachments/assets/105720eb-c2d4-426b-81b7-9ac770775ffa">



## 🔗 ERD<br>
<img width="767" alt="image" src="https://github.com/user-attachments/assets/a9f34fd2-c922-4704-9377-e815be0ad69e"><br><br>

## 🧱 새로운 서비스 구조 적용<br>
* 여러 서비스를 호출하는 독자적인 서비스 클래스 생성<br>
<img width="737" alt="image" src="https://github.com/user-attachments/assets/c051c5c5-5fb7-40bd-b40d-27b53b1d3c35">


##  <br>🔧 트러블 슈팅

**1. Linux 환경에서 ```export postgres-user=root```를 하면 ```-bash: export: `postgres-user=root': not a valid identifier```와 같은 에러가 뜬다.<br><br>**
> * 원인 : linux 환경에서 환경 변수이름에 - (dash)를 쓸 수 없다. 대신 _ (underscore)를 써야 한다.<br><br>
> * 해결 : ```export postgres_user=root``` 후 ```echo $postgres_user``` 를 하면 root로 제대로 뜬다.

<br>

**2. Application.yaml file에서 환경 변수 설정 시 ${ POSTGRES-USER }와 같이 중괄호와 변수명 사이에 space가 있으면 환경 변수 주입이 되지 않는다.<br><br>**
> * 원인 : Yaml file은 space가 있고 없음을 구분한다. (space-sensitive) <br><br>
> * 해결 : ${POSTGRES-USER} 로 진행해야 배포할 때 docker run 혹은 kubernetes의 env 항목의 환경 변수 값이 제대로 주입된다.

<br>

**3. Jenkins pipeline 무한 로딩 발생<br><br>**
> * 현상 : ```Started by GitHub push by jinho9482 [Pipeline] 
Start of Pipeline [Pipeline] 
node Still waiting to schedule task Waiting for next available executor```
<br><br>
> * 원인 : /var/jenkins_home 폴더에 용량 부족 @ Jenkins docker container <br><br>
> * 임시 해결 : 아래 버튼을 눌러 강제로 pipeline을 실행 시킨다.<br><br>
> <img width="1226" alt="image" src="https://github.com/user-attachments/assets/3ca8f4b0-43df-4608-b076-5a22a810530b"><br><br>
> * 근본 해결 : pipeline 실행 후 폴더의 log들을 지우도록 설정한다. "Workspace Cleanup" plugin 사용

<br>

**4. Ingress에 들어온 모든 api 요청 (ex. /api/v1/riders, /api/v1/orders)이 path : / 로 설정된 곳으로 이동한다. @ ingress-controller.yaml<br><br>**
> * 원인 : root path ( / ) 이 제일 처음 설정되어 있어 /로 시작하는 모든 api 요청은 / 와 연결된 service로 보내진다.<br><br>
> * 해결 : root path를 제일 뒤로 보냄
>```yaml
> spec:
>   ingressClassName: nginx
>   rules:
>     - http:
>         paths:
>           - path: /api/v1/orders
>             pathType: Prefix
>             backend:
>               service:
>                 name: order-waguwagu-order
>                 port:
>                   number: 8080
>           - path: /api/v1/auth
>             pathType: Prefix
>             backend:
>               service:
>                 name: wgwg-auth-server
>                 port:
>                   number: 8080
>           // root path를 제일 뒤로    
>           - path: / 
>             pathType: Prefix
>             backend:
>               service:
>                 name: wgwg-store-front
>                 port:
>                   number: 80
>```

<br>

**5. App과 AWS EKS cluster server 간 https 통신 불량 (미해결)<br><br>**
> * 현상 : web ~ server간 https 통신은 잘 되나, app ~ server 간 통신 불가<br><br>
> * 확인 내용 : Axios network error 발생 @app (Error code 없음)SSL 인증서를 가지고 있는 nginx 에도 log가 찍히지 않음<br><br>
> * 추측 원인 : Web browser에서는 인증이 되나, Android device에서는 해당 인증서를 인증할 인증서가 없음<br><br>
> * 취한 Action : Android xml 파일에 직접 인증서를 넣음<br><br>
> * 결과  : 해당 설정이 http 통신 (OAuth 인증)을 막아 진행 불가<br><br>
> * 현재 상황 : http로 통신<br><br>
> * 추후 Action : 추가 원인 분석을 통해 https로 원복 예정<br><br>
> * 참고 : 현재 network flow (nginx load balancer가 2개 존재하여 1개로 합칠 필요 있음)
> <img width="1145" alt="image" src="https://github.com/user-attachments/assets/f2a32a48-d519-4f32-af1f-8865a7572ea6">
