package com.ming.navermyshop.service;

import com.ming.navermyshop.models.Product;
import com.ming.navermyshop.models.ProductMypriceRequestDto;
import com.ming.navermyshop.models.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional // DB 정보가 바뀌는 것을 트랜잭션이 일어난다 라고 해서
    public Long update(Long id, ProductMypriceRequestDto mypridto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 없습니다.")
        );
        product.update(mypridto);
        return id;
    }
}
