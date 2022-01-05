package com.ming.navermyshop.Controller;

import com.ming.navermyshop.models.Product;
import com.ming.navermyshop.models.ProductRepository;
import com.ming.navermyshop.models.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성
@RestController // JSON으로 데이터를 주고받음을 선언
public class ProductRestController {

    private final ProductRepository productRepository;

    // 네이버 API 이용해서 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> read() {
        return productRepository.findAll();
    }

    // 관심상품 등록
    @PostMapping("/api/products")
    public Product create(@RequestBody ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }
}
