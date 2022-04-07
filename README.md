# Spring-Thymeleaf-Internationalization



## Description

본 프로젝트는 3가지 프로젝트로 구성 된다. 1번째 프로젝트는 `thymeleaf-basic`이며 타임리프의 기본적인 기능을 구현하여 알아본다. 2번째 프로젝트는 `form`이며 타임리프를 이용한 체크 박스, 라디오 버튼 등 구현하여 알아본다. 3번째 프로젝트는 `message`이며 메시지 국제화를 이용하여 요청에 따라 다른 화면을 출력한다.



------



## Environment

<img alt="framework" src ="https://img.shields.io/badge/Framework-SpringBoot-green"/><img alt="framework" src ="https://img.shields.io/badge/Language-java-b07219"/> 

Framework: `Spring Boot` 2.4.4

Project: `Gradle`

Packaging: `Jar`

IDE: `Intellij`

Template Engine: `Thymeleaf`

Dependencies: `Spring Web`, `Lombok`



------



## Installation





![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black) 

```
./gradlew build
cd build/lib
java -jar hello-spring-0.0.1-SNAPSHOT.jar
```



![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white) 

```
gradlew build
cd build/lib
java -jar hello-spring-0.0.1-SNAPSHOT.jar
```



------



## Core Feature



**Project : thymeleaf-basic**

```html
<!--variable.html-->
<ul>Object
    <li>${user.username} = <span th:text="${user.username}"></span></li>
    <li>${user['username']} = <span th:text="${user['username']}"></span></li>
    <li>${user.getUsername()} = <span th:text="${user.getUsername()}"></span></li>
</ul>
<ul>List
    <li>${users[0].username} = <span th:text="${users[0].username}"></span></li>
    <li>${users[0]['username']} = <span th:text="${users[0]['username']}"></span></li>
    <li>${users[0].getUsername()} = <span th:text="${users[0].getUsername()}"></span></li>
</ul>
<ul>Map
    <li>${userMap['userA'].username} =<span th:text="${userMap['userA'].username}"></span></li>
    <li>${userMap['userA']['username']} = <span th:text="${userMap['userA']['username']}"></span></li>
    <li>${userMap['userA'].getUsername()} = <span th:text="${userMap['userA'].getUsername()}"></span></li>
</ul>
```

Spring 에서 제공하는 변수 표현식인 `SpringEL` 을 타임리프에 적용한 것이다. 순서대로 `Object`, `List`, `Map` 에 `SpringEL` 을 적용한 것이다.



**Project : form**

```html
<!--addForm.html-->
<!-- multi checkbox -->
<div>
    <div>등록 지역</div>
    <div th:each="region : ${regions}" class="form-check form-check-inline">
        <input type="checkbox" th:field="*{regions}" th:value="${region.key}" class="form-check-input">
        <label th:for="${#ids.prev('regions')}"
               th:text="${region.value}" class="form-check-label">서울</label></div>
</div>
```

다중 체크 박스를 구현한 것으로 `name`은 같아도 `id`는 모두 달라야 한다. 타임리프가 동적으로 `id` 를 시퀀스로 만들기 때문에 `ids.prev()` 를 사용한다.



**Project : message**

```html
<!--addForm.html-->
<div>
    <label for="itemName" th:text="#{label.item.itemName}">상품명</label>
    <input type="text" id="itemName" th:field="*{itemName}" class="form-control" placeholder="이름을 입력하세요">
</div>
<div>
    <label for="price" th:text="#{label.item.price}">가격</label>
    <input type="text" id="price" th:field="*{price}" class="form-control" placeholder="가격을 입력하세요">
</div>
<div>
    <label for="quantity" th:text="#{label.item.quantity}">수량</label>
    <input type="text" id="quantity" th:field="*{quantity}" class="form-control" placeholder="수량을 입력하세요">
</div>
```

```properties
# message_ko.properties
hello=hello
hello.name=hello {0}

label.item=Item
label.item.id=Item ID
label.item.itemName=Item Name
label.item.price=price
label.item.quantity=quantity
```

`properties` 파일 명을 `Accept-Language` 에 맞는 이름으로 지정 후 문자를 작성한다. 그 후 html에서 타임리프를 이용하여 `Accept-Language` 에 `properties` 의 내용을 사용한다.



------



## Demonstration Video



**Project : thymeleaf-basic**

![Spring-Thymeleaf-Internationalization1](https://user-images.githubusercontent.com/79822924/162263147-81d6f772-63dd-45a1-bb7b-fa03cf416eb1.gif)



**Project : form**

![Spring-Thymeleaf-Internationalization2](https://user-images.githubusercontent.com/79822924/162263176-74cb4f3e-25a6-4f56-a3ed-dc7d84896ec9.gif)



**Project : message**

![Spring-Thymeleaf-Internationalization3](https://user-images.githubusercontent.com/79822924/162263208-e74576dd-34d8-4cd7-b60c-019efa065de9.gif)



------



## More Explanation

[Spring-thymeleaf-basic-Note.md](https://github.com/mwkangit/Spring-Thymeleaf-Internationalization/blob/master/thymeleaf-basic/Spring-thymeleaf-basic-Note.md)
[Spring-form-Note.md](https://github.com/mwkangit/Spring-Thymeleaf-Internationalization/blob/master/form/Spring-form-Note.md)
[Spring-message-Note.md](https://github.com/mwkangit/Spring-Thymeleaf-Internationalization/blob/master/message/Spring-message-Note.md)
