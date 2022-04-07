# Practice Setting



**Project : thymeleaf-basic**

```groovy
plugins {
	id 'org.springframework.boot' version '2.6.3'
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

tasks.named('test') {
	useJUnitPlatform()
}
```

- #### Web, Lombok, Thymeleaf를 dependency로 한다.



# Thymeleaf



## About Thymeleaf



```
• 간단한 표현:
◦ 변수 표현식: ${...}
◦ 선택 변수 표현식: *{...}
◦ 메시지 표현식: #{...}
◦ 링크 URL 표현식: @{...}
◦ 조각 표현식: ~{...}

• 리터럴
◦ 텍스트: 'one text', 'Another one!',...
◦ 숫자: 0, 34, 3.0, 12.3,...
◦ 불린: true, false
◦ 널: null
◦ 리터럴 토큰: one, sometext, main,...

• 문자 연산:
◦ 문자 합치기: +
◦ 리터럴 대체: |The name is ${name}|

• 산술 연산:◦ Binary operators: +, -, *, /, %
◦ Minus sign (unary operator): -

• 불린 연산:
◦ Binary operators: and, or
◦ Boolean negation (unary operator): !, not

• 비교와 동등:
◦ 비교: >, <, >=, <= (gt, lt, ge, le)
◦ 동등 연산: ==, != (eq, ne)

• 조건 연산:
◦ If-then: (if) ? (then)
◦ If-then-else: (if) ? (then) : (else)
◦ Default: (value) ?: (defaultvalue)

• 특별한 토큰:
◦ No-Operation: _
```

- #### 타임리프는 서버 사이드 HTML 렌더링(SSR) 시 사용한다.

- #### 타임리프는 네츄럴 템플릿이며 스프링 통합 지원된다.



```html
<html xmlns:th="http://www.thymeleaf.org">
```

- #### 타임리프 사용 선언하는 코드이다.



## Text, utext



```html
<ul>
    <li>th:text 사용 <span th:text="${data}"></span></li>
    <li>컨텐츠 안에서 직접 출력하기 = [[${data}]] </li>
</ul>
```

- #### HTML의 콘텐츠(content)에 데이터를 출력할 때는 `th:text` 사용한다.

- #### HTML 태그의 속성이 아닌 HTML 콘텐츠 영역 안에서 직접 데이터를 출력할 때 `[[...]]`를 사용한다.



## Escape, Unescape



```html
<ul>
    <li>th:text = <span th:text="${data}"></span></li>
    <li>th:utext = <span th:utext="${data}"></span></li>
</ul>
<ul>
    <li><span th:inline="none">[[...]] = </span>[[${data}]]</li>
    <li><span th:inline="none">[(...)] = </span>[(${data})]</li>
</ul>
```

- #### `Hello <b>Spring!</b>`를 소스로 보면 `Hello &lt;b&gt;Spring!&lt;/b&gt;`로 변경되어 내용을 출력한다.

- #### HTML의 특수 문자를 표현하기 위해 문자로 그것을 표현하는 것을 HTML 엔티티라고 하며 HTML 엔티티로 변경하는 것을 이스케이프(Escape)라고 한다.

- #### `th:text`, `[[...]]`는 기본적으로 이스케이프를 제공한다.

- #### `th:utext`, `[(...)]`는 unescape를 적용하여 html코드가 적용되어 출력된다.

- #### 타임리프는 `[[...]]`를 해석하기 때문에 화면에 `[[...]]`를 출력할 수 없다. 하지만 th:inline= "none"은 태그 안에서 타임리프를 해석하지 말라는 뜻으로 문자를 그대로 출력하는 것이 가능하다.



## SpringEL



```html
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

- #### SpringEL은 타임리프에서 사용 가능한 스프링에서 제공하는 변수 표현식이다.

- #### 위 코드는 차례로 Object, List, Map에 대한 변수 표현식 방법이다.



```html
<div th:with="first=${users[0]}">
    <p>처음 사람의 이름은 <span th:text="${first.username}"></span></p>
</div>
```

- #### `th:with`룰 사용하면 지역 변수를 선언해서 사용할 수 있다. 지역 변수는 선언한 태그 안에서만 사용할 수 있다.



## Basic Objects



```html
<h1>식 기본 객체 (Expression Basic Objects)</h1>
<ul>
    <li>request = <span th:text="${#request}"></span></li>
    <li>response = <span th:text="${#response}"></span></li>
    <li>session = <span th:text="${#session}"></span></li>
    <li>servletContext = <span th:text="${#servletContext}"></span></li>
    <li>locale = <span th:text="${#locale}"></span></li>
</ul>
<h1>편의 객체</h1>
<ul>
    <li>Request Parameter = <span th:text="${param.paramData}"></span></li>
    <li>session = <span th:text="${session.sessionData}"></span></li>
    <li>spring bean = <span th:text="${@helloBean.hello('Spring!')}"></span></li>
</ul>
```

- #### 모델에 자주 담는 객체는 타임리프에서 편하게 접근하게 해주는 것이다.

- ####  HTTP 요청 파라미터 접근은 `param`이다.

- #### HTTP 세션 접근은 `session`으로 한다.

- #### 스프링 빈 접근은 `@`로 한다.



## Utility Objects & Date



```html
<h1>LocalDateTime</h1>
<ul>
    <li>default = <span th:text="${localDateTime}"></span></li>
    <li>yyyy-MM-dd HH:mm:ss = <span th:text="${#temporals.format(localDateTime, 'yyyy-MM-dd HH:mm:ss')}"></span></li>
</ul>
<h1>LocalDateTime - Utils</h1>
<ul>
    <li>${#temporals.day(localDateTime)} = <span th:text="${#temporals.day(localDateTime)}"></span></li>
    <li>${#temporals.month(localDateTime)} = <span th:text="${#temporals.month(localDateTime)}"></span></li>
    <li>${#temporals.monthName(localDateTime)} = <span th:text="${#temporals.monthName(localDateTime)}"></span></li>
    <li>${#temporals.monthNameShort(localDateTime)} = <span th:text="${#temporals.monthNameShort(localDateTime)}"></span></li>
    <li>${#temporals.year(localDateTime)} = <span th:text="${#temporals.year(localDateTime)}"></span></li>
    <li>${#temporals.dayOfWeek(localDateTime)} = <span th:text="${#temporals.dayOfWeek(localDateTime)}"></span></li>
    <li>${#temporals.dayOfWeekName(localDateTime)} = <span th:text="${#temporals.dayOfWeekName(localDateTime)}"></span></li>
    <li>${#temporals.dayOfWeekNameShort(localDateTime)} = <span th:text="${#temporals.dayOfWeekNameShort(localDateTime)}"></span></li>
    <li>${#temporals.hour(localDateTime)} = <span th:text="${#temporals.hour(localDateTime)}"></span></li>
    <li>${#temporals.minute(localDateTime)} = <span th:text="${#temporals.minute(localDateTime)}"></span></li>
    <li>${#temporals.second(localDateTime)} = <span th:text="${#temporals.second(localDateTime)}"></span></li>
    <li>${#temporals.nanosecond(localDateTime)} = <span th:text="${#temporals.nanosecond(localDateTime)}"></span></li>
</ul>
```

> #message : 메시지, 국제화 처리
> #uris : URI 이스케이프 지원
> #dates : java.util.Date 서식 지원
> #calendars : java.util.Calendar
> #temporals : 자바8 날짜 서식 지원
> #numbers : 숫자 서식 지원
> #strings : 문자 관련 편의 기능
> #objects : 객체 관련 기능 제공
> #bools : boolean 관련 기능 제공
> #arrays : 배열 관련 기능 제공
> #lists , #sets , #maps : 컬렉션 관련 기능 제공
> #ids : 아이디 처리 관련 기능 제공

[타임리프 유틸리티 객체](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expression-utility-
objects)

[유틸리티 객체 예시](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-b-expression-
utility-objects)

- #### 자바 8 날짜인  `LocalDate`, `LocalDateTime`, `Instant`를 사용하려면 추가 라이브러리가 필요한데 스프링 푸트 타임리프가 해당 라이브러리를 자동으로 추가되고 통합된다.

  - #### 타임리프 자바 8 날짜 지원 라이브러리 - `thymeleaf-extras-java8time`



## URL Link



```html
<ul>
    <li><a th:href="@{/hello}">basic url</a></li>
    <li><a th:href="@{/hello(param1=${param1}, param2=${param2})}">hello query param</a></li>
    <li><a th:href="@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}">path variable</a></li>
    <li><a th:href="@{/hello/{param1}(param1=${param1}, param2=${param2})}">path variable + query parameter</a></li>
</ul>
```

- #### 단순 URL부터 쿼리 파라미터, 경로 변수를 설정하는 것이 가능하다.

- #### URL 경로상에 변수가 있으면 생성한 변수는 경로 변수로 처리된다. 즉, 경로 변수가 쿼리 파리미터보다 우선권을 가지게 된다.

- #### `/hello`, `hello`와 같이 절대 경로와 상대 경로를 설정하는 것이 간으하다.

[타임리프 링크](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#link-urls)



## Literal



```html
<ul>
    <!--주의! 다음 주석을 풀면 예외가 발생함-->
    <!--<li>"hello world!" = <span th:text="hello world!"></span></li>-->
    <li>'hello' + ' world!' = <span th:text="'hello' + ' world!'"></span></li>
    <li>'hello world!' = <span th:text="'hello world!'"></span></li>
    <li>'hello ' + ${data} = <span th:text="'hello ' + ${data}"></span></li>
    <li>리터럴 대체 |hello ${data}| = <span th:text="|hello ${data}|"></span></li>
</ul>
```

- #### 리터럴은 소스 코드상에 고정된 값을 말하는 용어이다.

- #### 타임리프에서 문자 리터럴은  항상 `'`로 감싸야 한다.

- #### 공백 없이 쭉 이어진 문자는 하나의 의미있는 토큰으로 인지해서 `'`생략 가능하다.

  - #### 룰 : `A-Z`, `a-z`, `0-9` , `[]`, `.`, `-`, `_`

  - #### 띄어쓰기는 토큰을 2개로 봐서 연속적이지 않다고 판단한다.

- #### 리터럴 대체(Literal substitutions)를 이용하여 템플릿을 사용하는 효과를 볼 수 있다.



## Operation



```html
	<li>산술 연산
        <ul>
            <li>10 + 2 = <span th:text="10 + 2"></span></li>
            <li>10 % 2 == 0 = <span th:text="10 % 2 == 0"></span></li>
        </ul>
    </li>
    <li>비교 연산
        <ul>
            <li>1 > 10 = <span th:text="1 &gt; 10"></span></li>
            <li>1 gt 10 = <span th:text="1 gt 10"></span></li>
            <li>1 >= 10 = <span th:text="1 >= 10"></span></li>
            <li>1 ge 10 = <span th:text="1 ge 10"></span></li>
            <li>1 == 10 = <span th:text="1 == 10"></span></li>
            <li>1 != 10 = <span th:text="1 != 10"></span></li>
        </ul>
    </li>
    <li>조건식
        <ul>
            <li>(10 % 2 == 0)? '짝수':'홀수' = <span th:text="(10 % 2 == 0)?'짝수':'홀수'"></span></li>
        </ul>
    </li>
    <li>Elvis 연산자
        <ul>
            <li>${data}?: '데이터가 없습니다.' = <span th:text="${data}?: '데이터가없습니다.'"></span></li>
            <li>${nullData}?: '데이터가 없습니다.' = <span th:text="${nullData}?:'데이터가 없습니다.'"></span></li>
        </ul>
    </li>
    <li>No-Operation
        <ul>
            <li>${data}?: _ = <span th:text="${data}?: _">데이터가 없습니다.</span></li>
            <li>${nullData}?: _ = <span th:text="${nullData}?: _">데이터가없습니다.</span></li>
        </ul>
    </li>
```

- #### 비교연산은 HTML 엔티티를 사용해야 한다.

  - `> (gt)`, `< (lt)`, `>= (ge)`, `<= (le)`, `! (not)`, `== (eq)`, `!= (neq, ne)`

- #### 조건식은 자바의 조건식과 유사하며 Elvis 연산자는 조건식의 편의 버전으로 변수가 존재하면 변수를 출력하고 없으면 설정한 문자가 출력된다.

- #### No-Operation(`_`)는 마치 타임리프가 실행되지 않는 것 처럼 동작한다. 데이터가 없다면 타임리프를 실행하지 않고 html을 그대로 출력한다.



## Attribute



```html
<h1>속성 설정</h1>
<input type="text" name="mock" th:name="userA" />
<h1>속성 추가</h1>
- th:attrappend = <input type="text" class="text" th:attrappend="class=' large'" /><br/>
- th:attrprepend = <input type="text" class="text" th:attrprepend="class='large '" /><br/>
- th:classappend = <input type="text" class="text" th:classappend="large" /><br/>
<h1>checked 처리</h1>
- checked o <input type="checkbox" name="active" th:checked="true" /><br/>
- checked x <input type="checkbox" name="active" th:checked="false" /><br/>
- checked=false <input type="checkbox" name="active" checked="false" /><br/>
```

- #### `th:*`로 속성을 적용하면 기존 속성을 대체하며 기존 속성이 없다면 새로 만든다.

- #### `th:attrappend`는 속성 값의 뒤에 값을 추가한다.

- #### `th:attrprepend`는 속성 값의 앞에 값을 추가한다. 즉, 0번 인덱스에 추가한다.

- #### `th:classappend`는 class 속성에 자연스럽게 추가한다. 

- #### HTML은 `checked` 속성이 있으면 속성의 값에 상관없이 체크된 상태로 출력된다.

- #### `th:checked`는 값이 false인 경우 `checked`속성 자체를 제거하여 체크를 없앤다.



## Each



```html
<h1>기본 테이블</h1>
<table border="1">
    <tr>
        <th>username</th>
        <th>age</th>
    </tr>
    <tr th:each="user : ${users}">
        <td th:text="${user.username}">username</td>
        <td th:text="${user.age}">0</td>
    </tr>
</table>
<h1>반복 상태 유지</h1>
<table border="1">
    <tr>
        <th>count</th>
        <th>username</th>
        <th>age</th>
        <th>etc</th>
    </tr>
    <tr th:each="user, userStat : ${users}">
        <td th:text="${userStat.count}">username</td>
        <td th:text="${user.username}">username</td>
        <td th:text="${user.age}">0</td>
        <td>
            index = <span th:text="${userStat.index}"></span>
            count = <span th:text="${userStat.count}"></span>
            size = <span th:text="${userStat.size}"></span>
            even? = <span th:text="${userStat.even}"></span>
            odd? = <span th:text="${userStat.odd}"></span>
            first? = <span th:text="${userStat.first}"></span>
            last? = <span th:text="${userStat.last}"></span>
            current = <span th:text="${userStat.current}"></span>
        </td>
    </tr>
</table>
```

> index : 0부터 시작하는 값
> count : 1부터 시작하는 값
> size : 전체 사이즈
> even , odd : 홀수, 짝수 여부( boolean )
> first , last :처음, 마지막 여부( boolean )
> current : 현재 객체

- #### `th:each`는 자바의 for-each와 같은 역할을 한다.

- #### `th:each`는 리스트 뿐 아니라 배열, `java.util.Iterable`, `java.util.Enumeration`을 구현한 모든 객체를 반복해서 사용 가능하다.  `Map`도 사용 가능한데 이 경우 변수에 담기는 값은 `Map.Entry`이다.

- #### `th:each`의 첫 번째 파라미터 + `Stat`문자를 두 번째 파라미터에 넣으면 반복의 상태를 확인하는 것이 가능하다.



##  Condition



```html
<h1>if, unless</h1>
<table border="1">
    <tr>
        <th>count</th>
        <th>username</th>
        <th>age</th>
    </tr>
    <tr th:each="user, userStat : ${users}">
        <td th:text="${userStat.count}">1</td>
        <td th:text="${user.username}">username</td>
        <td>
            <span th:text="${user.age}">0</span>
            <span th:text="'미성년자'" th:if="${user.age lt 20}"></span>
            <span th:text="'미성년자'" th:unless="${user.age ge 20}"></span>
        </td>
    </tr>
</table>
<h1>switch</h1>
<table border="1">
    <tr>
        <th>count</th>
        <th>username</th>
        <th>age</th>
    </tr>
    <tr th:each="user, userStat : ${users}">
        <td th:text="${userStat.count}">1</td>
        <td th:text="${user.username}">username</td>
        <td th:switch="${user.age}">
            <span th:case="10">10살</span>
            <span th:case="20">20살</span>
            <span th:case="*">[[${user.age}]]살</span>
        </td>
    </tr>
</table>
```

- #### `if`, `unless`는 해당 조건이 맞지 않으면 태그 자체를 렌더링하지 않는다. 즉, false이면 구문을 삭제한다.

- #### `switch`에서 `*`은 만족하는 조건이 없을 때 사용하는 디폴트이다.



## Comment



```HTML
<h1>예시</h1>
<span th:text="${data}">html data</span>
<h1>1. 표준 HTML 주석</h1>
<!--
<span th:text="${data}">html data</span>-->
<h1>2. 타임리프 파서 주석</h1>
<!--/* [[${data}]] */-->
<!--/*-->
<span th:text="${data}">html data</span>
<!--*/-->
<h1>3. 타임리프 프로토타입 주석</h1>
<!--/*/
<span th:text="${data}">html data</span>
/*/-->
```

- #### 표준 HTML 주석은 `<!-- -->`로 타임리프가 렌더링 하지 않고 그대로 남겨둔다.

- #### 타임리프 파서 주석은 `<!--*/--> <!--/*-->`로 타임리프의 진짜 주석이다. 렌더링에서 주석 부분을 제거한다. 대부분 타임리프 주석으로 이 방법을 사용한다.

- #### 타임리프 프로토타입 주석은 `<!--/*/ /*/-->`로 우선 HTML 주석이어서 파일로 보면 주석처리가 되어 렌더링하지 않는다. 즉, 웹 브라우저에서 파일ㄹ로 보면 주석처리 되지만 타임리프 렌더링 하면 주석으로 간주하지 않고 코드를 실행한다.



## Block



```html
<th:block th:each="user : ${users}">
    <div>
        사용자 이름1 <span th:text="${user.username}"></span>
        사용자 나이1 <span th:text="${user.age}"></span>
    </div>
    <div>
    요약 <span th:text="${user.username} + ' / ' + ${user.age}"></span>
    </div>
</th:block>
```

- #### 타임리프는 보통 속성으로 동작하지만 해결하기 어려운 케이스를 위해 자세 태그인 블록을 이용한다.

- #### `th:each`는 div 1개를 돌리기 가능하지만 `th:block`은 div 2개 가능하다. 즉, 블록 구간을 하나의 뭉치(local)로 보는 것이다.

- #### `th:block`은 렌더링시 제거되며 애매한 경우에만 사용하는 것을 권장한다.



## Javascript Inline



```html
<!-- 자바스크립트 인라인 사용 전 -->
<script>
    var username = [[${user.username}]];
    var age = [[${user.age}]];

    //자바스크립트 내추럴 템플릿
    var username2 = /*[[${user.username}]]*/ "test username";

    //객체
    var user = [[${user}]];
</script>
<!-- 자바스크립트 인라인 사용 후 -->
<script th:inline="javascript">
    var username = [[${user.username}]];
    var age = [[${user.age}]];

    //자바스크립트 내추럴 템플릿
    var username2 = /*[[${user.username}]]*/ "test username";

    //객체
    var user = [[${user}]];
</script>

<!-- 자바스크립트 인라인 each -->
<script th:inline="javascript">
    [# th:each="user, stat : ${users}"]
    var user[[${stat.count}]] = [[${user}]];
    [/]
</script>
```

- #### 인라인 사용 시 문자 타입인 경우 `"`를 포함해준다. 또한, 자바스크립트에서 문제가 될 수 있는 문자가 포함되어 있으면 이스케이프 처리도 해준다.

- #### 자바스크립트 인라인 기능을 사용하면 주석을 활용해서 자바스크립트 내추럴 템플릿을 만들 수 있다.

- #### 객체는 자바스크립트 인라인 기능에서 자동으로 JSON으로 변환된다. 인라인 전 객체 사용은 toString() 호출이지만 인라인 후 객체 사용은 JSON으로 변환한다.



## Template Piece



```html
<!--footer.html-->
<footer th:fragment="copy">
    푸터 자리 입니다.
</footer>
<footer th:fragment="copyParam (param1, param2)">
    <p>파라미터 자리 입니다.</p>
    <p th:text="${param1}"></p>
    <p th:text="${param2}"></p>
</footer>
```

```html
<!--fragmentMain.html-->
<h1>부분 포함</h1>

<h2>부분 포함 insert</h2>
<div th:insert="~{template/fragment/footer :: copy}"></div>

<h2>부분 포함 replace</h2>
<div th:replace="~{template/fragment/footer :: copy}"></div>

<h2>부분 포함 단순 표현식</h2>
<div th:replace="template/fragment/footer :: copy"></div>

<h1>파라미터 사용</h1>
<div th:replace="~{template/fragment/footer :: copyParam ('데이터1', '데이터2')}"></div>
```

- #### 웹 페이지의 하단 영역이다. 즉, 여러 html에서 이 하단 영역을 동일하게 사용하는 것이다.

- #### `template/fragment/footer :: copy` 는 `template/fragment/footer.html`템플릿에 있는 `th:fragment="copy"`라는 부분을 템플릿 조각으로 가져와서 사용한다는 의미이다.

- #### `th:insert`를 사용하면 현재 태그(div) 내부에 추가한다.

- #### `th:replace`를 사용하면 현재 태그(div)를 대체한다. 즉, div가 현재 태그라면  div가 사라진다.

- #### `~{...}`를 사용하는 것이 원칙이지만 템플릿 조각을 사용하는 코드가 단순하면 이 부분을 생략할 수 있다.

- #### 파라미터를 전달하여 동적으로 조각을 렌더링 하는 것이 가능하다.



## Template Layout



```html
<!--base.html-->
<head th:fragment="common_header(title,links)">
    <title th:replace="${title}">레이아웃 타이틀</title>
    <!-- 공통 -->
    <link rel="stylesheet" type="text/css" media="all" th:href="@{/css/awesomeapp.css}">
    <link rel="shortcut icon" th:href="@{/images/favicon.ico}">
    <script type="text/javascript" th:src="@{/sh/scripts/codebase.js}"></script>
    <!-- 추가 -->
    <th:block th:replace="${links}" />
</head>
```

```html
<!--layoutMain.html-->
<head th:replace="template/layout/base :: common_header(~{::title},~{::link})">
    <title>메인 타이틀</title>
    <link rel="stylesheet" th:href="@{/css/bootstrap.min.css}">
    <link rel="stylesheet" th:href="@{/themes/smoothness/jquery-ui.css}">
</head>
```

- #### `commom_header(~{::title}, ~{::link})`에서 `::title`은 현재 페이지의 title 태그들을 전달한다. `::link`는 현재 페이지의 link 태그들을 전달한다.

- #### `th:replace`를 이용하여 넘어온 파라미터로 대체한다.

- #### 레이아웃 개념을 두고 그 레이아웃에 필요한 코드 조각을 전달해서 완성하는 것이다.



```html
<!--layoutFile.html-->
<!DOCTYPE html>
<html th:fragment="layout (title, content)" xmlns:th="http://
www.thymeleaf.org">
<head>
    <title th:replace="${title}">레이아웃 타이틀</title>
</head>
<body>
<h1>레이아웃 H1</h1>
<div th:replace="${content}"><p>레이아웃 컨텐츠</p>
</div>
<footer>
    레이아웃 푸터
</footer>
</body>
</html>
```

```html
<!--layoutExtendMain.html-->
<!DOCTYPE html>
<html th:replace="~{template/layoutExtend/layoutFile :: layout(~{::title},~{::section})}"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>메인 페이지 타이틀</title>
</head>
<body>
<section>
    <p>메인 페이지 컨텐츠</p>
    <div>메인 페이지 포함 내용</div>
</section>
</body>
</html>
```

- #### Head 만이 아닌 HTML 전체를 레이아웃으로 만들고 그것에 코드 조각을 넣는 방식이다.

- #### layoutExtendMain.html은 html 파일 자체가 다른 레이아웃 불러서 사용한다는 뜻이다.

- #### `th:fragment`속성으로 레이아웃 파일을 기본으로 하고 여기에 필요한 내용을 전달해서 부분 변경하는 것이다.

- #### 모든 페이지마다 적절하고 쉽게 변경하는 것이 가능한 장점이 있다. 즉, 레이아웃만 변경하면 모두 적용이 된다는 것이다.

- #### 체계적으로 잘 관리해야 하는 단점이 있다.



# Keyboard Shortcut

- #### ctrl + g : 원하는 행, 열로 이동한다.