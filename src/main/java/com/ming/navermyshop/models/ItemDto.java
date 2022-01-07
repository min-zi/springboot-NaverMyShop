// 검색 DTO를 받은 서버가 클라이언트에 돌려주기 위한 DTO
package com.ming.navermyshop.models;

import lombok.Getter;
import org.json.JSONObject;

// Setter가 없는 이유는 생성자를 통해서 정보를 주입받을 것이기 떄문
@Getter
public class ItemDto {
    private String title;
    private String link;
    private String image;
    private int lprice;

    public ItemDto(JSONObject itemJson) {
        this.title = itemJson.getString("title"); // JSONObject, JSONArray는 get자료형 으로 가져오면 됨
        this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
    }
}