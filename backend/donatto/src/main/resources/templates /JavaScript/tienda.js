// Variables
let allContainerCart = document.querySelector('.products');
let containerBuyCart = document.querySelector('.card-items');
let priceTotal = document.querySelector('.price-total');
let amountProduct = document.querySelector('.count-product');
let searchInput = document.getElementById('search-input');


let buyThings = [];
let totalCard = 0;
let countProduct = 0;

// Funciones
loadEventListeners();
function loadEventListeners(){
    allContainerCart.addEventListener('click', addProduct);
    containerBuyCart.addEventListener('click', deleteProduct);
    searchInput.addEventListener('input', filterProducts); // Agregamos un listener para la búsqueda en tiempo real
}

function addProduct(e){
    e.preventDefault();
    if (e.target.classList.contains('btn-add-cart')) {
        const selectProduct = e.target.parentElement;
        const talleDropdown = selectProduct.querySelector('.talle-dropdown');
        const selectedTalle = talleDropdown.value;

        const infoProduct = {
            image: selectProduct.querySelector('div img').src,
            title: selectProduct.querySelector('.title').textContent,
            price: selectProduct.querySelector('div p span').textContent,
            id: selectProduct.querySelector('a').getAttribute('data-id'),
            amount: 1,
            talle: selectedTalle
        }

        totalCard = parseFloat(totalCard) + parseFloat(infoProduct.price);
        totalCard = totalCard.toFixed(2);

        const existIndex = buyThings.findIndex(product => product.id === infoProduct.id && product.talle === selectedTalle);
        if (existIndex !== -1) {
            buyThings[existIndex].amount++;
        } else {
            buyThings.push(infoProduct);
            countProduct++;
        }

        loadHtml();
    }
}

function deleteProduct(e) {
    if (e.target.classList.contains('delete-product')) {
        const deleteId = e.target.getAttribute('data-id');
        const deleteTalle = e.target.getAttribute('data-talle');

        const foundIndex = buyThings.findIndex(product => product.id === deleteId && product.talle === deleteTalle);
        if (foundIndex !== -1) {
            if (buyThings[foundIndex].amount > 1) {
                buyThings[foundIndex].amount--;
            } else {
                buyThings.splice(foundIndex, 1);
                countProduct--;
            }
        }
    }

    if (buyThings.length === 0) {
        priceTotal.innerHTML = 0;
        amountProduct.innerHTML = 0;
    }
    loadHtml();
}

function filterProducts() {
    const searchValue = searchInput.value.toLowerCase();
    const productCards = document.querySelectorAll('.carts');

    productCards.forEach(card => {
        const title = card.querySelector('.title').textContent.toLowerCase();
        if (title.includes(searchValue)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

function loadHtml() {
    clearHtml();
    buyThings.forEach(product => {
        const { image, title, price, amount, id, talle } = product;
        const row = document.createElement('div');
        row.classList.add('item');
        row.innerHTML = `
            <img src="${image}" alt="">
            <div class="item-content">
                <h5>${title}</h5>
                <h5 class="cart-price">${formatPrice(price, 'ARS')}
                <h6>Cantidad: ${amount}</h6>
                <h6>Talle: ${talle}</h6>
            </div>
            <span class="delete-product" data-id="${id}" data-talle="${talle}">X</span>
        `;

        containerBuyCart.appendChild(row);

        priceTotal.innerHTML = formatPrice(totalCard, 'ARS');
        amountProduct.innerHTML = countProduct;
    });
}


function formatPrice(price, currencyCode) {
    return new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: currencyCode,
    }).format(price);
}

function clearHtml(){
    containerBuyCart.innerHTML = '';
}
