# 💰 WaguWagu Payment and Settlement Server
### 👤 Manager : Jinho Jo

## <br>📃 Core Features
### Payment History, Settlement Management

- Create, cancel, and retrieve payment history
- Calculate settlement amount (considering fees and taxes) after delivery is completed, then send the settlement amount to store and rider servers

## <br>🏷️ Project Link
https://github.com/WAGUWAGUUU/WAGUWAGU

## <br>⚙️ Key skills

### ✔️ Server Framework
![Spring-Boot](https://img.shields.io/badge/spring--boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)

### ✔️ Database
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

### ✔️ Message Broker  
![apachekafka](https://img.shields.io/badge/apachekafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
   
## <br>🧾 API Documentation  (with Swagger API)<br><br>

<img width="687" alt="image" src="https://github.com/user-attachments/assets/6b86cbe3-cdb6-4caa-b192-06e41ca0489a">
<br><br>

## <br>🧾 Data Flow Diagram<br><br>

<img width="889" alt="image" src="https://github.com/user-attachments/assets/8ed8ac3d-f5ee-446f-9027-3207e90da4cc">
<br>
<img width="730" alt="image" src="https://github.com/user-attachments/assets/18eeade9-33d9-4ac5-9d3d-706447063e89">




## 🔗 ERD<br>
<img width="767" alt="image" src="https://github.com/user-attachments/assets/a9f34fd2-c922-4704-9377-e815be0ad69e"><br><br>

##  <br>🔧 Troubleshooting

**1. When running export postgres-user=root in a Linux environment, the error -bash: export: 'postgres-user=root': not a valid identifier occurs.<br><br>**
> * Cause: Linux environment does not allow dashes (-) in environment variable names. You should use underscores (_) instead.<br><br>
> * Solution: Use export postgres_user=root, then run echo $postgres_user to confirm that the value is correctly set to root.
<br>

**2. When setting environment variables in the application.yaml file with a space between braces and the variable name, like ${ POSTGRES-USER }, the environment variable is not injected.<br><br>**
> * Cause: YAML files are space-sensitive. <br><br>
> * Solution: Ensure there are no spaces: ${POSTGRES-USER}. This way, the environment variable is properly injected when running in Docker or Kubernetes.

<br>

**3. Jenkins pipeline stuck on infinite loading<br><br>**
> * Symptom : ```Started by GitHub push by jinho9482 [Pipeline] 
Start of Pipeline [Pipeline] 
node Still waiting to schedule task Waiting for next available executor```
<br><br>
> * Cause: The /var/jenkins_home folder in the Jenkins Docker container has run out of space. <br><br>
> * Temporary Solution: Click the button below to forcibly trigger the pipeline:<br><br>
> <img width="1226" alt="image" src="https://github.com/user-attachments/assets/3ca8f4b0-43df-4608-b076-5a22a810530b"><br><br>
> * Permanent Solution: Configure the pipeline to clean up logs after execution using the "Workspace Cleanup" plugin.


<br>

**4. All API requests (e.g., /api/v1/riders, /api/v1/orders) received by Ingress are routed to the path / in ingress-controller.yaml.<br><br>**
> * Cause: The root path (/) is set first, so any API requests starting with / are sent to the service linked to /.<br><br>
> * Solution: Move the root path to the end of the configuration:
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
>           # Move root path to the end
>           - path: / 
>             pathType: Prefix
>             backend:
>               service:
>                 name: wgwg-store-front
>                 port:
>                   number: 80
>```

<br>

**5. HTTPS communication failure between the app and AWS EKS cluster server (unresolved)<br><br>**
> * Phenomenon: HTTPS communication works well between web and server, but fails between app and server.<br><br>
> * Findings: Axios network error occurs @app (no error code), and no logs appear in nginx with the SSL certificate.<br><br>
> * Suspected Cause: The web browser can authenticate, but the Android device lacks a certificate to authenticate that certificate.<br><br>
> * Action Taken: Directly included the certificate in the Android XML file.<br><br>
> * Result: This configuration blocks HTTP communication (OAuth authentication), preventing progress.<br><br>
> * Current Status: Communication via HTTP.<br><br>
> * Future Action: Analyze additional causes to restore HTTPS communication.<br><br>
> * Note: Current network flow requires consolidation of two existing nginx load balancers into one. 
> <img width="1145" alt="image" src="https://github.com/user-attachments/assets/f2a32a48-d519-4f32-af1f-8865a7572ea6">

