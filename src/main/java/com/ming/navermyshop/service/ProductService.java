package com.ming.navermyshop.service;

import com.ming.navermyshop.models.ItemDto;
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

    @Transactional // DB 정보가 바뀌는 것을 트랜잭션이 일어난다 라고 해서, 메소드 동작이 SQL 쿼리문임을 선언
    public Long update(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 없습니다.")
        );
        product.update(requestDto);
        return id;
    }

    // 스케줄러 업데이트 서비스
    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
        return id;
    }
}
