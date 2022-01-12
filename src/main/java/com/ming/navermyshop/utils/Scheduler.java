package com.ming.navermyshop.utils;

import com.ming.navermyshop.models.ItemDto;
import com.ming.navermyshop.models.Product;
import com.ming.navermyshop.models.ProductRepository;
import com.ming.navermyshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가함
public class Scheduler {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final NaverShopSearch naverShopSearch;

    @Scheduled(cron = "0 0 1 * * *") // 초, 분, 시, 일, 월, 주 순서임. *은 모든 값
    public void updatePrice() throws InterruptedException {
        System.out.println("가격 업데이트 실행");
        List<Product> productList = productRepository.findAll(); // 저장된 모든 찜한 상품을 조회

        for (int i=0; i<productList.size(); i++) {
            TimeUnit.SECONDS.sleep(1); // 1초에 한 상품 씩 조회 (짧은 시간에 요청이 많이 오면 막힘 Naver 제한)

            Product p = productList.get(i); // i번째 찜 상품을 꺼내고
            String title = p.getTitle(); // i번째 찜 상품의 제목으로
            String resultString = naverShopSearch.search(title); //검색을 실행

            // 유사성으로 검색할 테니까 i번째 찜한 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼냄
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
            ItemDto itemDto = itemDtoList.get(0);

            Long id = p.getId(); // i번째 찜한 상품 정보를 업데이트해줌
            productService.updateBySearch(id, itemDto);
        }
    }
}