const checkBtn = document.getElementById('check');
const menu = document.querySelector('.menu');
const header = document.querySelector('.myHeader');

function isMobile() {
    const mobileBreakpoint = 768;
    return window.innerWidth <= mobileBreakpoint;
}

function toggleMenu() {
    if (menu.style.display == 'block' && isMobile()) {
        menu.style.display = 'none';
        header.style.background = "rgba(225, 225, 225, 0.3)";
    } else {
        menu.style.display = 'block';
        header.style.background = "white";
    }
}

if (isMobile()) {
    checkBtn.addEventListener('click', toggleMenu);

    const mainMenuItems = document.querySelectorAll('.main-menu-item');
    mainMenuItems.forEach((menuItem, index) => {
        const subMenu = menuItem.querySelector('.sub-menu');
        let isSubMenuVisible = false;

        menuItem.addEventListener('click', (event) => {
            event.preventDefault();

            if (isSubMenuVisible) {
                subMenu.style.display = 'none';
            } else {
                subMenu.style.display = 'block';
            }

            isSubMenuVisible = !isSubMenuVisible;
        });
    });
}