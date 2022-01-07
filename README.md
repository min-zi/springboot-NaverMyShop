# 🛍NaverMyShop API
> 원하는 가격을 설정해서 상품을 찜 하면 설정해둔 가격보다 낮은 최저가가 있을 경우 알려주는 서버(네이버 쇼핑 검색 API 활용)

|Method|URL|기능|
|:------:|---|---|
|GET|/api/search?query=검색아이템|키워드로 상품을 검색하고 그 결과를 목록으로 보여주기|
|POST|/api/products|찜 상품 등록하기|
|GET|/api/products|찜 상품 조회하기|
|PUT|/api/products/{id}|찜 할 상품에 원하는 가격을 설정하고 그 가격보다 낮을 경우에 표시해주기|
