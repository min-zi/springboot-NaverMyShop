let targetId; // 제일 최근에 저장된 찜한 상품의 id가 들어옴

// 페이지가 로드된 직후 실행 할 자바스크립트 코드를 넣는 곳
$(document).ready(function () {
    // id가 query인 아이 위에서 엔터를 누르면 execSearch() 함수를 실행
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();

    showProduct();
})

// 숫자 세 자리 단위 콤마 표기해 주는 함수
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


/**************** JQuery 작동 부분 ****************/

/////////////////// 상품 검색 ///////////////////
function execSearch() {
    /**
     * 검색창 input의 id: #query
     * 검색 결과 목록의 id: #search-result-box
     * 검색 결과들을 HTML로 만드는 함수: addHTML
     */
    $('#search-result-box').empty(); // 새로운 검색을 했을 때 전에 띄워준 상품을 지워줌

    let query = $('#query').val(); // 검색창의 입력값을 가져와서
    if (query == '') { // 입력값을 검사하고, 입력하지 않았을 경우 focus
        alert('찾는 상품을 검색해 주세요!');
        $('#query').focus();
        return;
    }

    // JQuery에서 요청은 ajax에 작성
    $.ajax({
        type: 'GET',
        url: `/api/search?query=${query}`,
        success: function (response) {
            // for문 마다 itemDto를 꺼내서 HTML로 만들고 검색결과를 목록에 붙이기
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addHTML(itemDto);
                $('#search-result-box').append(tempHtml);
            }
        }
    })

}

// 검색 결과를 HTML로 만들어주는 함수
function addHTML(itemDto) {
    /**
     * 검색 목록을 보여주기 위해 itemDto의 image, title, lprice 출력시키기
     * 찜 버튼을 누르면 addProduct() 함수 실행시키기
     * ItemDto는 Json형식이니까 전달해 줄 때 string형태로 보내줘야 됨 -> JSON.stringify(itemDto)
     */
    return `<div class="search-itemDto">
            <div class="search-itemDto-left">
                <img src="${itemDto.image}" alt="">
            </div>
            <div class="search-itemDto-center">
                <div>${itemDto.title}</div>
                <div class="price">
                    ${numberWithCommas(itemDto.lprice)}
                    <span class="unit">원</span>
                </div>
            </div>
            <div class="search-itemDto-right">
                <img src="images/icon-save.png" alt="" onclick='addProduct(${JSON.stringify(itemDto)})'>
            </div>
        </div>`
}


/////////////////// 찜한 상품 생성 ///////////////////
function addProduct(itemDto) {
    /**
     * addHTML에서 itemDto를 string으로 보냈지만 전달받은 itemDto를 출력해 보면 JSON으로 바뀌어 있음
     * 그 이유는 Json을 파라미터로 바로 전달하는 과정에서 알아서 Json으로 바뀌어서 들어옴
     * 그래서 서버로 전달할 때 꼭 string으로 한 번 더 변환해 줘야 됨
     *
     * data를 ajax로 전달할 때는 두 가지가 매우 중요함
     * 1. contentType: "application/json",
     * 2. data: JSON.stringify(itemDto),
     */

    // console.log(itemDto);
    // console.log(JSON.stringify(itemDto));

    // POST /api/products에 찜한 상품 생성 요청
    $.ajax({
        type: "POST",
        url: '/api/products',
        contentType: "application/json",
        data: JSON.stringify(itemDto),
        success: function (response) {
            // console.log(response);
            $('#container').addClass('active'); // 응답 함수에서 modal을 띄움
            tergetId = response.id; // myprice를 설정하기 위해 targetId를 reponse.id로 설정
        }
    })

}


/////////////////// 찜한 상품 조회  ///////////////////
function showProduct() {
    /**
     * 찜한상품 목록 id: #product-container
     * 검색결과 목록 id: #search-result-box
     * 찜한 상품을 HTML로 만드는 함수: addProductItem
     */

    // GET /api/products 요청
    $.ajax({
        type: 'GET',
        url: '/api/products',
        success: function (response) {
            // 찜한 상품 목록, 검색 결과 목록 비우기
            $('#product-container').empty();
            $('#search-result-box').empty();
            // for문마다 찜한 상품을 HTML로 만들어서 찜한 상품 목록에 붙이기
            for (let i = 0; i < response.length; i++) {
                let product = response[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml);
            }
        }
    })

}

// 찜한 상품을 HTML로 만들어주는 함수
function addProductItem(product) {
    // 검색 목록을 보여주기 위해 itemDto의 image, title, lprice 출력시키기
    // link, image, title, lprice, myprice 변수 활용하기
    return `<div class="product-card" onclick="window.location.href='${product.link}'">
            <div class="card-header">
                <img src="${product.image}"
                     alt="">
            </div>
            <div class="card-body">
                <div class="title">
                    ${product.title}
                </div>
                <div class="lprice">
                    <span>${numberWithCommas(product.lprice)}</span>원
                </div>
                <div class="isgood ${product.lprice <= product.myprice ? '' : 'none'}">
                    최저가
                </div>
            </div>
        </div>`;
}


/////////////////// myprice 값 설정 ///////////////////
function setMyprice() {
    let myprice = $('#myprice').val(); // id가 myprice인 input 태그에서 값을 가져와서
    if (myprice == '') { // 만약 값을 입력하지 않았으면 alert를 띄우고 중단
        alert('원하는 최저가를 입력해 주세요!');
        return;
    }
    // PUT /api/product/${targetId}에 data 전달
    // contentType과 data 적어줘야 됨
    $.ajax({
        type: 'PUT',
        url: `/api/product/${targetId}`,
        contentType: "application/json",
        data: JSON.stringify({myprice: myprice}),
        success: function (response) {
            $('#container').removeClass('active'); //모달 종료
            alert('최저가 설정 완료')
            window.location.reload(); // 창 새로고침
        }
    })

}