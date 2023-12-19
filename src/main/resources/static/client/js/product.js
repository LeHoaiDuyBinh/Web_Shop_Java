// **************** PRODUCT ******************
const bigImg = document.querySelector(".product-content-left-big-img img")
const smallImg = document.querySelectorAll(".product-content-left-small-img img")
smallImg.forEach(function(imgItem, X) {
    imgItem.addEventListener("click", function() {
        bigImg.src = imgItem.src
    })
})


const instroduction = document.querySelector(".introduction")
const detail = document.querySelector(".detail")
const preserve = document.querySelector(".preserve")
if (instroduction) {
    instroduction.addEventListener("click", function() {
        document.querySelector(".product-content-right-bottom-content-introduction").style.display = "block"
        document.querySelector(".product-content-right-bottom-content-detail").style.display = "none"
        document.querySelector(".product-content-right-bottom-content-preserve").style.display = "none"

    })
}

if (detail) {
    detail.addEventListener("click", function() {
        document.querySelector(".product-content-right-bottom-content-detail").style.display = "block"
        document.querySelector(".product-content-right-bottom-content-introduction").style.display = "none"
        document.querySelector(".product-content-right-bottom-content-preserve").style.display = "none"
    })
}

if (preserve) {
    preserve.addEventListener("click", function() {
        document.querySelector(".product-content-right-bottom-content-preserve").style.display = "block"
        document.querySelector(".product-content-right-bottom-content-detail").style.display = "none"
        document.querySelector(".product-content-right-bottom-content-introduction").style.display = "none"
    })
}

const button = document.querySelector(".product-content-right-bottom-top")
if (button) {
    button.addEventListener("click", function() {
        document.querySelector(".product-content-right-bottom-content-big").classList.toggle("active")
    })
}