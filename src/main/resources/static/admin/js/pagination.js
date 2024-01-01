const table = document.getElementById("myTable");
const container = table.parentElement;

const rowsPerPage = 10;
const totalRows = table.rows.length - 1;
const totalPages = Math.ceil(totalRows / rowsPerPage);

const links = document.createElement("div");
links.classList.add("pagination");

if (totalPages > 1) {
    for (let i = 1; i <= totalPages; i++) {
        const link = document.createElement("a");
        link.href = "#";
        link.textContent = i;
        link.onclick = function() {

            for (let j = 0; j < totalRows; j++) {
                const row = table.rows[j + 1];
                row.style.display = ((j >= (i - 1) * rowsPerPage) && (j < i * rowsPerPage)) ? "" : "none";
            }

            const activeLink = links.querySelector(".active");
            if (activeLink) activeLink.classList.remove("active");
            link.classList.add("active");

            return false;
        };

        links.appendChild(link);
    }

    links.querySelector("a").classList.add("active");
}

for (let i = 0; i < totalRows; i++) {
    const row = table.rows[i + 1];
    row.style.display = (i < rowsPerPage) ? "" : "none";
}

container.appendChild(links);