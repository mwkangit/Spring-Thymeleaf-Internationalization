package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    // 페이지의 각 부분을 fragment로 불러서 메소드처럼 사용하는 것이 가능하며 값을 넣은 후 사용하는 것도 가능하다
    @GetMapping("/fragment")
    public String template(){
        return "template/fragment/fragmentMain";
    }

    // th:replace를 통해 header layout을 불러오면서 원하는 값으로 수정, 추가하여 출력한다.
    @GetMapping("/layout")
    public String layout(){
        return "template/layout/layoutMain";
    }

    //
    @GetMapping("/layoutExtend")
    public String layoutExtend(){
        return "template/layoutExtend/layoutExtendMain";
    }

}
