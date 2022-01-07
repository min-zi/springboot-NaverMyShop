package com.ming.navermyshop.Controller;

import com.ming.navermyshop.models.ItemDto;
import com.ming.navermyshop.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 클래스를 자동으로 생성
@RestController // JSON으로 데이터를 주고받음을 선언
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch; // 등록해준 Component가 여기서 사용됨

    @GetMapping("/api/search")
    // RequestParam : 단일 파라미터를 전달 받을 때 사용
    // 유저가 검색어를 입력 -> 컨트롤러가 전달 받음
    public List<ItemDto> getItems(@RequestParam String query) {
        // 검색어를 네이버 API에 요청함
        String resultString = naverShopSearch.search(query);    // 스프링이 어디에 넣어줘야 되는지 알 수 있게 url에서 보낸 변수명과 같아야 됨 (/api/search?query=검색아이템)
        // 그 결과를 유저에게 응답해줌
        return naverShopSearch.fromJSONtoItems(resultString);
    }
}