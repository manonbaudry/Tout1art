// @flow
import PageRenderer from './PageRenderer.js';
import Menu from './components/Menu.js';
import HomePage from "./pages/HomePage";
import ClientInscriptionPage from "./pages/ClientInscriptionPage";
import ConnectionPage from "./pages/ConnectionPage";
import $ from 'jquery';
import ProductPage from "./pages/ProductPage";
import Product from "./Product";
import AdminPage from "./pages/AdminPage";

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.pageContent');

// déclaration des différentes page de l'app
const products: Array<Promise<Product>> = [];
for (let i = 0; i < 3; ++i) {
    products[i] = Product.get(i + 1);
}

const homePage: HomePage = new HomePage(products);
const inscriptionPage: ClientInscriptionPage = new ClientInscriptionPage();
const connectionPage: ConnectionPage = new ConnectionPage();
let adminPage: AdminPage = new AdminPage();
let productPage: ProductPage;

const selection: Array<Product> = [];
const selectionPage: HomePage = new HomePage(selection);


//recupération dropdown "mobilier, luminaire et déco" navbar
const dropDownMobilier = document.querySelector('.mobButton');
const dropDownLuminaire = document.querySelector('.lumButton');
const dropDownDeco = document.querySelector('.decoButton');

dropDownMobilier.addEventListener("click", function () {

    selectionCible(dropDownMobilier.innerHTML.toLowerCase().trim());
});

dropDownLuminaire.addEventListener("click", function () {

    selectionCible(dropDownLuminaire.innerHTML.toLowerCase().trim());
});


dropDownDeco.addEventListener("click", function () {

    selectionCible(dropDownDeco.innerHTML.toLowerCase().trim());
});


// configuration des liens du menu
const homeLink = $('.homeLink');
const inscriptionLink = $('.inscriptionLink');
const connectionLink = $('.connectionLink');
const adminLink = $('.adminLink');

homeLink.click((event: Event) => {
    event.preventDefault();
    renderHome();
});
inscriptionLink.click((event: Event) => {
    event.preventDefault();
    renderInscription();
});
connectionLink.click((event: Event) => {
    event.preventDefault();
    renderConnection();
});
adminLink.click((event: Event) => {
    event.preventDefault();
    renderAdmin();
});

function renderHome(): void {
    Menu.setSelectedLink(homeLink);
    PageRenderer.renderPage(homePage);

    $('.productLink').click((event: Event) => {
        event.preventDefault();
        renderProduct(event.currentTarget.getAttribute('id'));
    });
}

function renderInscription(): void {
    Menu.setSelectedLink(inscriptionLink);
    PageRenderer.renderPage(inscriptionPage);
}

function renderConnection(): void {
    Menu.setSelectedLink(connectionLink);
    PageRenderer.renderPage(connectionPage);
}

function renderProduct(id: number): void {
    Menu.setSelectedLink(homeLink);
    let product = Product.get(id);
    product.then(json => {
        product = Product.jsonToObj(json);
        productPage = new ProductPage(product);
        PageRenderer.renderPage(productPage);
    });
}

function selectionCible(choix) {
    let tabId = [];
    let indice = 0;

    Product.getAll().then(json => {

        for (let i = 0; i < json.length; i++) {

            if (json[i].category == choix) {
                tabId[indice] = json[i].id;
                indice++;
            }
        }

        if (tabId.length != 0) {
            selection.length = 0;
            for (let i = 0; i < tabId.length; ++i) {
                selection[i] = Product.get(tabId[i]);
                selection[i].then(product => selection[i] = Product.jsonToObj(product));
            }
            Promise.all(selection).then(() => renderSelection());
        }
    });
}

function renderSelection(): void {

    Menu.setSelectedLink(homeLink);
    PageRenderer.renderPage(selectionPage);

    $('.productLink').click((event: Event) => {
        event.preventDefault();
        renderProduct(event.currentTarget.getAttribute('id'));
    });
}


function renderAdmin(): void {
    Menu.setSelectedLink(adminLink);
    adminPage = new AdminPage();
    PageRenderer.renderPage(adminPage);
}

renderHome();