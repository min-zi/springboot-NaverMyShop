// 유저가 자신의 브라우저에서 입력한 데이터를 DTO에 넣어서 전송함
package com.ming.navermyshop.models;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String title;
    private String link;
    private String image;
    private int lprice;

}
