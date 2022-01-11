package com.ming.navermyshop.Controller;

import com.ming.navermyshop.models.Product;
import com.ming.navermyshop.models.ProductMypriceRequestDto;
import com.ming.navermyshop.models.ProductRepository;
import com.ming.navermyshop.models.ProductRequestDto;
import com.ming.navermyshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성
@RestController // JSON으로 데이터를 주고받음을 선언
public class ProductRestController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    // 네이버 API 이용해서 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> read() {
        return productRepository.findAll();
    }

    // 찜한 상품 등록
    @PostMapping("/api/products")
    public Product create(@RequestBody ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.update(id, requestDto);
    }
}
