// @flow
import PageRenderer from './PageRenderer.js';
import Menu from './components/Menu.js';
import NewHomePage from "./pages/NewHomePage";
import ClientInscriptionPage from "./pages/ClientInscriptionPage";
import ConnectionPage from "./pages/ConnectionPage";
import $ from 'jquery';
import ProductPage from "./pages/ProductPage";
import Product from "./Product";

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.pageContent');

// déclaration des différentes page de l'app
const products: Array<Product> = [];
for (let i = 0; i < 3; ++i) {
    products[i] = Product.get(i + 1);
    products[i].then(product => products[i] = Product.jsonToObj(product));
}
Promise.all(products).then(() => renderHome());

const homePage: NewHomePage = new NewHomePage(products);
const inscriptionPage: ClientInscriptionPage = new ClientInscriptionPage();
const connectionPage: ConnectionPage = new ConnectionPage();
let productPage: ProductPage;

//recupération dropdown "mobilier, luminaire et déco" navbar
const dropDownMobilier = document.querySelectorAll('.mobItem a');
const dropDownLuminaire = document.querySelectorAll('.lumItem a');
const dropDownDeco = document.querySelectorAll('.decoItem a');

// configuration des liens du menu
const homeLink = $('.homeLink');
const inscriptionLink = $('.inscriptionLink');
const connectionLink = $('.connectionLink');

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

function renderHome(): void {
    console.log(products);
    Menu.setSelectedLink(homeLink);
    PageRenderer.renderPage(homePage);
    $('.productLink').click((event: Event) => {
        event.preventDefault();
        renderProduct(event.currentTarget.getAttribute('id'));
    });
    // updateSectionDropDown(dropDownMobilier, dropDownLuminaire, dropDownDeco);
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
        product = Product.jsonToObj(json)
        productPage = new ProductPage(product);
        PageRenderer.renderPage(productPage);
    });
}

function updateSectionDropDown(mobiliers, luminaires, decos) {

    //ici requete pour aller chercher les sous sections dans la bdd
    const getSecionMobiliers = null;
    getSecionMobiliers.forEach(element => {

        mobiliers.innerHTML += `<a class="dropdown-item" href="#">${element}</a>`;
        element.click((event: Event) => {
            event.preventDefault();
        });
    });

    const getSectionLuminaires = null;
    getSectionLuminaires.forEach(element => {

        luminaires.innerHTML += `<a class="dropdown-item" href="#">${element}</a>`;
        element.click((event: Event) => {
            event.preventDefault();
        });
    });

    const getSectionDecos = null;
    getSectionDecos.forEach(element => {

        decos.innerHTML += `<a class="dropdown-item" href="#">${element}</a>`;
        element.click((event: Event) => {
            event.preventDefault();
        });
    });
}
