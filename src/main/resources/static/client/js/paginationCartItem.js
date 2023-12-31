document.addEventListener('DOMContentLoaded', function () {
    const itemsPerPage = 3; // Số lượng sản phẩm trên mỗi trang
    let currentPage = 1;

    function showPage(page) {
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;

        // Ẩn tất cả các sản phẩm
        const productRows = document.querySelectorAll('.cart__item tr');
        for (let i = 1; i < productRows.length; i++) {
            productRows[i].style.display = (i > startIndex && i <= endIndex) ? '' : 'none';
        }
    }

    function renderPagination() {
        const totalProducts = document.querySelectorAll('.cart__item tr').length - 1; // Trừ 1 vì có dòng header
        const totalPages = Math.ceil(totalProducts / itemsPerPage);

        const paginationContainer = document.createElement('div');
        paginationContainer.classList.add('pagination');

        for (let i = 1; i <= totalPages; i++) {
            const link = document.createElement('a');
            link.href = '#';
            link.textContent = i;
            link.onclick = (function (pageNumber) {
                return function () {
                    currentPage = pageNumber;
                    showPage(currentPage);
                    highlightActiveLink();
                    return false;
                };
            })(i);

            paginationContainer.appendChild(link);
        }

        // Thêm phân trang vào cart-content-left
        const cartContentLeft = document.querySelector('.cart-content-left');
        const existingPagination = cartContentLeft.querySelector('.pagination');
        if (existingPagination) {
            existingPagination.replaceWith(paginationContainer);
        } else {
            cartContentLeft.appendChild(paginationContainer);
        }

        highlightActiveLink();
    }

    function highlightActiveLink() {
        const links = document.querySelectorAll('.pagination a');
        links.forEach(link => link.classList.remove('active'));
        links[currentPage - 1].classList.add('active');
    }

    // Hiển thị trang đầu tiên khi trang được tải
    showPage(currentPage);

    // Hiển thị phân trang
    renderPagination();
});
