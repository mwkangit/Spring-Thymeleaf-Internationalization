# Practice Setting



**Project : form**

```groovy
plugins {
	id 'org.springframework.boot' version '2.4.4'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'hello'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	tes	tImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
	useJUnitPlatform()
}

```

- #### 타임리프는 스프링 없이도 동작 가능하지만 스프링과 통합하여 다양한 기능이 제공한다.

[기본 메뉴얼](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

[스프링 통합 메뉴얼](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)



# Thymeleaf Spring Integration



## Object & Field



```html
<!--addForm.html-->
<form action="item.html" th:action th:object="${item}" method="post">
        <div>
            <label for="itemName">상품명</label>
            <input type="text" id="itemName" th:field="*{itemName}" class="form-control" placeholder="이름을 입력하세요">
        </div>
        <div>
            <label for="price">가격</label>
            <input type="text" id="price" th:field="*{price}" class="form-control" placeholder="가격을 입력하세요">
        </div>
        <div>
            <label for="quantity">수량</label>
            <input type="text" id="quantity" th:field="*{quantity}" class="form-control" placeholder="수량을 입력하세요">
        </div>
```

```html
<!--editForm.html-->
<form action="item.html" th:action th:object="${item}" method="post">
        <div>
            <label for="id">상품 ID</label>
            <input type="text" id="id" class="form-control" th:field="*{id}" readonly>
        </div>
        <div>
            <label for="itemName">상품명</label>
            <input type="text" id="itemName" class="form-control" th:field="*{itemName}">
        </div>
        <div>
            <label for="price">가격</label>
            <input type="text" id="price" class="form-control" th:field="*{price}">
        </div>
        <div>
            <label for="quantity">수량</label>
            <input type="text" id="quantity" class="form-control" th:field="*{quantity}">
        </div>
```

- #### `th:object`를 통해 `<form>`에서 사용할 객체를 지정한다. 선택 변수 식(`*{...}`)을 적용할 수 있다. 또 다른 컨트롤러로 보내면서 서로 동일한 객체를 확보하는 것이 가능하며 사용자 오류가 발생하면 오류를 알릴 수 있다는 장점이 있다.

- #### `th:field="*{itemName}"`에서 `*{itemName}`은 선택 변수 식을 사용하여 `${item.itemName}`과 같은 의미이다.

  - #### `th:field`는 `id`, `name`, `value`속성을 모두 자동으로 만들어준다. id, name은 지정한 변수 이름과 같으며 value는 지정한 변수의 값을 사용한다.

- #### `id`속성을 제거해도 `th:field`가 자동으로 속성을 만들어준다.

- #### `<label for="itemName">`과 `id="itemName"`으로 `itemName`을 가리키는 것을 표현한다. id, name을 없애도 되지만 명확성을 위해 살려둔다.



## Check Box



### Single



```html
<!--addForm.html-->
<!-- single checkbox -->
<div>판매 여부</div>
<div>
    <div class="form-check">
        <input type="checkbox" id="open" th:field="*{open}" class="form-check-input">
        <!--<input type="hidden" name="_open" value="on"/>--> <!-- 히든 필드 추가 -->
        <label for="open" class="form-check-label">판매 오픈</label>
    </div>
</div>
```

```html
<!--editForm.html-->
<!-- single checkbox -->
<div>판매 여부</div>
<div>
    <div class="form-check">
        <input type="checkbox" id="open" th:field="*{open}" class="form-check-input">
        <label for="open" class="form-check-label">판매 오픈</label>
    </div>
</div>
```

- #### HTML에서 체크 박스를 선택하지 않고 폼을 전송하면 체크 박스 필드 자체가 서버에 전송되지 않아서 서버는 null 값을 받게 된다. 수정 시 체크를 해제해도 아무 값이 전송되지 않아서 변경이 일어나지 않을 수 있는 문제가 있다.

- #### 스프링 MVC는 `_`를 사용하여 히든 필드를 만들며 히든 필드는 항상 전송되어 체크 박스 문제를 해결할 수 있다. 즉, 체크 해제한 경우 `open`은 전송되지 않지만 `_open`은 전송된다. `open`값이 없어면 `_open`이 위임 받아서 처리한다. `open`에 값이 있으면 `_open`은 무시한다. 스프링에서는 둘다 `getOpen()`으로 값을 처리 가능하다.

- #### `th:field`사용 시 자동으로 히든 필드를 생성하여 번거로움을 없앨 수 있다.

- #### 체크 박스는 `disabled`로 선택할 수 없게 만들 수 있다.

- #### 체크 박스에 판매 여부를 선택해서 저장하면 `checked`속성이 자동으로 추가된다. 타임리프는 `item.open` 확인 후 `false`이면 `checked`를 없앤다. 즉, `th:field`, `th:value` 값을 비교하여 체크를 자동으로 처리한다.



### Multiple



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

```html
<!--editForm.html-->
<!-- multi checkbox -->
<div>
    <div>등록 지역</div>
    <div th:each="region : ${regions}" class="form-check form-check-inline">
        <input type="checkbox" th:field="*{regions}" th:value="${region.key}" class="form-check-input">
        <label th:for="${#ids.prev('regions')}"
               th:text="${region.value}" class="form-check-label">서울</label></div>
</div>
```

- #### ModelAttribute를 메소드로 꺼내면 클래스 내 모든 컨트롤러에 적용되어 요청 시 자동으로 정보가 담긴다.

- #### 멀티 체크 박스 시 html 태그 속성에서 `name`은 같아도 되지만 `id`는 모두 달라야 한다. 이때 타임리프는 체크 박스 `each`안에서 반복해서 만들 때 임의로 `1`, `2`, `3` 숫자를 뒤에 붙여준다. 타임리프가 동적으로 만들기 때문에 `label`에 `ids.prev()`, `ids.next()`를 사용한다.

  ```
  <input type="checkbox" value="SEOUL" class="form-check-input" id="regions1"
  name="regions">
  <input type="checkbox" value="BUSAN" class="form-check-input" id="regions2"
  name="regions">
  <input type="checkbox" value="JEJU" class="form-check-input" id="regions3"
  name="regions">
  ```

 

## Radio Button



```html
<!--addForm.html-->
<!-- radio button -->
<div>
    <div>상품 종류</div>
    <div th:each="type : ${itemTypes}" class="form-check form-check-inline">
        <input type="radio" th:field="*{itemType}" th:value="${type.name()}"class="form-check-input">
        <label th:for="${#ids.prev('itemType')}" th:text="${type.description}" class="form-check-label">
            BOOK
        </label>
    </div>
</div>
```

```html
<!--editForm.html-->
<!-- radio button -->
<div>
    <div>상품 종류</div>
    <div th:each="type : ${T(hello.itemservice.domain.item.ItemType).values()}" class="form-check form-check-inline">
        <input type="radio" th:field="*{itemType}" th:value="${type.name()}"class="form-check-input">
        <label th:for="${#ids.prev('itemType')}" th:text="${type.description}" class="form-check-label">
            BOOK
        </label>
    </div>
</div>
```

- #### `ItemType.values()` 메소드로 ENUM의 모든 정보를 배열로 반환할 수 있다. 이 정보도 클래스 내 모든 컨트롤러에서 사용하므로 ModelAttribute로 만든다.

- #### `${T(hello.itemservice.domain.item.ItemType).values()}` 와 같이 스프링EL 문법으로 ENUM을 직접 사용할 수 있지만 패키지 위치가 변경되면 번거로워져서 사용하지 않는다.

- #### 라디오 버튼은 하나의 버튼 선택 후 항상 하나의 버튼을 선택해야하므로 체크 박스와 달리 별도의 히든 필드를 사용할 필요가 없다.

- #### 체크된 라디오 버튼은 `checked` 속성이 생긴다.



## Select Box



```html
<!--addForm.html-->
<!-- SELECT -->
<div>
    <div>배송 방식</div>
    <select th:field="*{deliveryCode}" class="form-select">
        <option value="">==배송 방식 선택==</option>
        <option th:each="deliveryCode : ${deliveryCodes}" th:value="${deliveryCode.code}"
                th:text="${deliveryCode.displayName}">FAST</option>
    </select>
</div>
```

```html
<!--editForm.html-->
<!-- SELECT -->
<div>
    <div>배송 방식</div>
    <select th:field="*{deliveryCode}" class="form-select">
        <option value="">==배송 방식 선택==</option>
        <option th:each="deliveryCode : ${deliveryCodes}" th:value="${deliveryCode.code}"
                th:text="${deliveryCode.displayName}">FAST</option>
    </select>
</div>
```

- #### 클래스 내 모든 컨트롤러가 사용하므로 리스트 형식으로 ModelAttribute에 넣는다. 리스트와 같은 객체는 호출 시 계속 생성되므로 static으로 생성하여 재사용하는 것이 좋다.

- #### 렌더링 시 선택 항목에 `selected="selected"` 가 생성된다. 즉, 옵션과 이미 선택한 항목 비교 후 속성 생성한다.