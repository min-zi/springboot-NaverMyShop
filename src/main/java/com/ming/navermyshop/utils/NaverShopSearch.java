package com.ming.navermyshop.utils;

import com.ming.navermyshop.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component // 스프링이 자동으로 필요한 클래스를 필요한 곳에 생성할 수 있도록 컴포넌트 등록해줌
public class NaverShopSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        // HTTP POST를 요청할때 보내는 데이터(Body)를 설명해주는 헤더(Header)도 만들어서 같이 보내줘야 함
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "c8wYR0_mGTvdERJvuYdj"); // API 이용 신청할 때 등록하고 받은 내 애플리케이션의 Client ID
        headers.add("X-Naver-Client-Secret", "QfUA9Refsx"); // Client Secret
        String body = "";

        // HttpEntity : 요청하기 위해 헤더(Header)와 데이터(Body) 합치기
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        // exchange() : 모든 HTTP 요청 메소드를 지원하며 원하는 서버에 요청시켜주는 메소드
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); // 요청이 올바르며 원하는 응답이 클라이언트로 전송되었는지 요청 상태 (성공 상태 코드는 200)
        String response = responseEntity.getBody(); // 데이터가 문자열 하나에 쭉 들어옴
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }


    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result); // 검색한 결과의 문자열을 JSONObject로 변경해줌
        //System.out.println(rjson);
        JSONArray items = rjson.getJSONArray("items"); // 배열 형태(대괄호)로 들어있는 키 값이 items인 요소 추출하기

        List<ItemDto> itemList = new ArrayList<>();

        for (int i=0; i<items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i); // JSONArray는 JSONObject로 이루어진 배열이라서 JSONObject로 꺼내옴
            //System.out.println(itemJson); // 검색한 아이템들이 하나하나 출력됨
            ItemDto itemDto = new ItemDto(itemJson);
            itemList.add(itemDto);
        }
        return itemList;
    }

}
