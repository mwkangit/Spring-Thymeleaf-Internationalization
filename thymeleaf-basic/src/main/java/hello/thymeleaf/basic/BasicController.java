package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    // [[${data}]]로 기본적인 모델 사용법
    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data", "Hello Spring!");
        return "basic/text-basic";
    }

    // HTML 엔티티의 escape 형식과 unescape 형식
    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-unescaped";
    }

    // 모델의 object, list, map, local variabla 참조법
    @GetMapping("variable")
    public String variable(Model model){
        User userA = new User("userA", 10);
        User userB = new User("userA", 10);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";

    }

    // 기본 객체들
    // #request, #session, #locale, param, @helloBean etc
    @GetMapping("basic-objects")
    public String basicObjects(HttpSession session){
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

    // #temporals에 대한 localDateTime 활용
    @GetMapping("date")
    public String date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    // PathVariable, Parameter URL 입력
    @GetMapping("link")
    public String link(Model model){
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    // 리터럴을 ''을 통해 사용하며 ||로 리터럴 대체로 ''와 +를 사용하지 않을 수 있다
    @GetMapping("literal")
    public String literal(Model model){
        model.addAttribute("data", "Spring!");
        return "basic/literal";
    }

    // 연산, 조건식, Elvis 연산, No-Operation
    @GetMapping("operation")
    public String operation(Model model){
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }

    // attrappend, attrprepend, classappend, checked
    @GetMapping("attribute")
    public String addtribute(){
        return "basic/attribute";
    }

    // 반복문 for-each로 객체 내부값, Stat으로 반복문 상태값 사용가능
    @GetMapping("each")
    public String each(Model model){
        addUser(model);
        return "basic/each";
    }

    // if, unless, switch case문과 default("*")
    @GetMapping("condition")
    public String condition(Model model){
        addUser(model);
        return "basic/condition";
    }

    // 표준 HTML 주석, 타임리프 파서 주석, 타임리프 프로토타입 주석
    // 주로 타임리프 파서 주석 쓴다
    @GetMapping("comments")
    public String comments(Model model){
        model.addAttribute("data", "Spring!");
        return "basic/comments";
    }

    // 여러 태그를 담을 수 있는 block을 만들어서 each문이 여러 div에 적용되게 하는 것이 가능하다
    // 어쩔 수 없는 경우에만 사용하자
    @GetMapping("block")
    public String block(Model model){
        addUser(model);
        return "basic/block";
    }

    // 타임리프의 inline으로 html에서 발생한 문제를 모두 해결가능하다
    // 자바스크립트에 모델을 전달하는 것이 쉬워진다
    @GetMapping("javascript")
    public String javascript(Model model){
        model.addAttribute("user", new User("user\"A\"", 10));
        addUser(model);
        return "basic/javascript";
    }

    // 여기서부터 TemplateController 클래스로 이동


    private void addUser(Model model){
        List<User> list = new ArrayList<>();
        list.add(new User("UserA", 10));
        list.add(new User("UserB", 20));
        list.add(new User("UserC", 30));

        model.addAttribute("users", list);
    }



    // 템플릿에서 직접 빈 접근하는 것을 보여준다
    @Component("helloBean")
    static class HelloBean{
        public String hello(String data){
            return "Hello " + data;
        }
    }

    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }

}
