document.addEventListener('DOMContentLoaded', function () {
    const itemsPerPage = 3; // Số lượng sản phẩm trên mỗi trang
    let currentPage = 1;

    function showPage(page) {
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;

        // Ẩn tất cả các sản phẩm
        const productItems = document.querySelectorAll('.cart__product-item');
        for (let i = 0; i < productItems.length; i++) {
            productItems[i].style.display = (i >= startIndex && i < endIndex) ? '' : 'none';
        }
    }

    function renderPagination() {
        const totalProducts = document.querySelectorAll('.cart__product-item').length;
        const totalPages = Math.ceil(totalProducts / itemsPerPage);
        const paginationContainer = document.createElement('div');
        paginationContainer.classList.add('pagination');

        for (let i = 1; i <= totalPages; i++) {
            const link = document.createElement('a');
            link.href = '#';
            link.textContent = i;
            link.onclick = function () {
                currentPage = i;
                showPage(currentPage);
                highlightActiveLink();
                return false;
            };

            paginationContainer.appendChild(link);
        }

        const orderBlock = document.querySelector('.order-block__products');
        orderBlock.insertAdjacentElement('afterend', paginationContainer);
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
