document.addEventListener('DOMContentLoaded', function() {
    const tableContainer = document.querySelector('.category-right-content');
    const items = tableContainer.querySelectorAll('.category-right-content-item');
    const rowsPerPage = 12;

    let currentPage = 1;

    function showPage(page) {
        for (let i = 0; i < items.length; i++) {
            items[i].style.display = (i >= (page - 1) * rowsPerPage && i < page * rowsPerPage) ? '' : 'none';
        }
    }

    function renderPagination() {
        const totalPages = Math.ceil(items.length / rowsPerPage);
        const paginationContainer = document.createElement('div');
        paginationContainer.classList.add('pagination');

        for (let i = 1; i <= totalPages; i++) {
            const link = document.createElement('a');
            link.href = '#';
            link.textContent = i;
            link.onclick = function() {
                currentPage = i;
                showPage(currentPage);
                highlightActiveLink();
                return false;
            };

            paginationContainer.appendChild(link);
        }

        tableContainer.insertAdjacentElement('afterend', paginationContainer);
        highlightActiveLink();
    }

    function highlightActiveLink() {
        const links = document.querySelectorAll('.pagination a');
        links.forEach(link => link.classList.remove('active'));
        links[currentPage - 1].classList.add('active');
    }

    showPage(currentPage);
    renderPagination();
});