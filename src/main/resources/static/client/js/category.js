/************************ Menu Slider Category *****************************/
const itemSlider = document.querySelectorAll(".category-left-li");
itemSlider.forEach(function(menu, index) {
    menu.addEventListener("click", function() {
        menu.classList.toggle("block");
    })
})

/******************Filter Category *********************** */
const filterButton = document.getElementById('filterButton');
const filterDiv = document.getElementById('filterDiv');

filterButton.addEventListener('click', () => {
    // Kiểm tra trạng thái hiện tại của "filterDiv"
    if (filterDiv.style.display === 'none' || filterDiv.style.display === '') {
        // Nếu đang ẩn, thì hiển thị "filterDiv"
        filterDiv.style.display = 'block';
    } else {
        // Nếu đang hiển thị, thì ẩn "filterDiv"
        filterDiv.style.display = 'none';
    }
});