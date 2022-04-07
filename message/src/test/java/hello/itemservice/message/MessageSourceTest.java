package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

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
