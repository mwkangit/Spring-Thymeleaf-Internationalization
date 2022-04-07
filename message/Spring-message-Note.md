# Practice Setting



**Project : message**

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
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
	useJUnitPlatform()
}
```

- #### Locale에 따른 메시지, 국제화를 다룬다.

- #### 브라우저 기본 설정이 `en`으로 되어 있어서 본 내용의 `ko` 는 `en`을 뜻하고 default가 `ko` 를 뜻 한다.



# Message & International



## Message



```properties
item=상품
item.id=상품 ID
item.itemName=상품명
item.price=가격
item.quantity=수량
```

- #### HTML은 key값을 불러서 메시지를 사용한다.

  `<label for="itemName" th:text="#{item.itemName}"></label>`



## International



```properties
# message_ko.properties
item=상품
item.id=상품 ID
item.itemName=상품명
item.price=가격
item.quantity=수량
```

```properties
# message_en.properties
item=Item
item.id=Item ID
item.itemName=Item Name
item.price=price
item.quantity=quantity
```

- #### HTTP `accept-language` 헤더 값을 사용하거나 사용자가 직접 언어를 선택하게 하고 쿠기 등을 사용해서 국제화를 선택, 처리한다.

- #### 스프링은 기본적인 메시지와 국제화 기능을 모두 제공하며 타임리프도 스프링이 제공하는 메시지와 국제화 기능을 편리하게 통합해서 제공한다.



## MessageSource



```java
@Bean
public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new
    ResourceBundleMessageSource();
    messageSource.setBasenames("messages", "errors");
    messageSource.setDefaultEncoding("utf-8");
    return messageSource;
}
```

- #### 메시지 관리 기능은 MessageSource 인터페이스의 구현체인 ResourceBundleMessageSource를 스프링 빈으로 등록한다.

- #### `basenames` 는 설정 파일의 이름을 지정한다. 지정한 값이 default 값이 되며 `_` 뒤에 언어 정보를 줘서 설정가능하다. 파일 위치는 `/resources`아래에 `.properties`로 저장하면 된다.

- #### `defaultEncoding` 은 인코딩 정보로 `utf-8` 로 한다.

- #### 스프링 부트는 MessageSource를 자동으로 스프링 빈에 등록한다.

  ```properties
  spring.messages.basename=messages,config.i18n.messages
  ```

  - #### `basename` 에 `resources` 경로 아래의 이름을 지정한다.

  - #### `config` 는 `resources` 아래의 경로를 지정한다.

  - #### `i18n`은 internalization을 의미한다.

- #### 별도의 설정이 없으면 `messages` 라는 이름으로 기본 등록 된다.



```java
@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    // locale없이 messages.properties의 hello 호출
    @Test
    void helloMessage(){
        String result = ms.getMessage("hello", null, null);
        System.out.println(result);
        assertThat(result).isEqualTo("안녕");

    }

    // getMessage()했는데 코드의 값을 찾을 수 없을 때
    @Test
    void notFoundMessageCode(){
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    // 코드가 없을 때 default 값 입력 가능하다 (3번째 매개변수)
    @Test
    void notFoundMessageCodeDefaultMessage(){
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    // properties의 매개변수 사용
    // {0}에 값을 넣어서 치환한다(2번째 매개변수 이용).
    @Test
    void argumentMessage(){
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }

    // 국제화
    // 여기서 Locale.English라는 properties는 없다
    void defaultLang(){
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("안녕");

    }

    // Locale.KOREA를 요구한다
    @Test
    void enLang(){
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("hello");

    }

}
```

- #### `getMessage()`를 통해 국제화에 접근할 수 있으며 코드이름, args, default, locale을 매개변수로 넣을 수 있다.

- #### `{0}` 은 매개변수를 전달하여 치환할 수 있다.



## Thymeleaf & International



```html
<!--addForm.html-->
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link th:href="@{/css/bootstrap.min.css}"
          href="../css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 560px;
        }
    </style>
</head>
<body>

<div class="container">

    <div class="py-5 text-center">
        <h2 th:text="#{page.addItem}">상품 등록 폼</h2>
    </div>

    <form action="item.html" th:action th:object="${item}" method="post">
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

        <hr class="my-4">

        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit" th:text="#{button.save}">저장</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='items.html'"
                        th:onclick="|location.href='@{/message/items}'|"
                        type="button" th:text="#{button.cancel}" >취소</button>
            </div>
        </div>

    </form>

</div> <!-- /container -->
</body>
</html>
```

```properties
# messages.properties
hello=안녕
hello.name=안녕 {0}

label.item=상품
label.item.id=상품 ID
label.item.itemName=상품명
label.item.price=가격
label.item.quantity=수량

page.items=상품 목록
page.item=상품 상세
page.addItem=상품 등록
page.updateItem=상품 수정

button.save=저장
button.cancel=취소
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

page.items=Item List
page.item=Item Detail
page.addItem=Item Add
page.updateItem=Item Update

button.save=Save
button.cancel=Cancel
```

- #### 타임리프의 메시지 표현식 `#{...}` 를 사용하여 메시지 조회가능하다.

  `<h2 th:text="#{page.addItem}">상품 등록 폼</h2>`

- #### 메시지의 파라미터 형식에 변수로 변환할 수 있다.

  `hello.name=안녕 {0}`

  `<p th:text="#{hello.name(${item.itemName})}"></p>`

- #### 웹 브라우저에서 크롬 브라우저 -> 설정 -> 언어에서 언어 우선 순위를 변경하여 요청할 수 있다.

- #### 스프링은 Locale 정보를 기본으로 `Accept-Language` 헤더 값을 사용한다. 하지만 사용자가 `Accept-Language` 값을 바꾸기는 어렵기 때문에 alert로  쿠키, 세션을 설정하는 방법을 하기도 한다. 이때 스프링은 Locale 선택 방법을 바꿀 수 있게 지원한다.



## Locale Resolver



```java
public interface LocaleResolver {
	Locale resolveLocale(HttpServletRequest request);
	void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale);
}
```

- #### 스프링은 Locale 선택 방식을 변경할 수 있도록 LocaleResolver 인터페이스를 제공하며 기본으로 `Accept-Language` 를 활용하는 `AcceptHeaderLocaleResolver` 을 사용한다.

- #### 변경하고자 한다면 LocaleResolver의 구현체를 변경해서 쿠키, 세션 기반 Locale 선택 기능을 사용할 수 있다.