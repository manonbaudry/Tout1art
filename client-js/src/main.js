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
products[0] = Product.get(1);
products[1] = Product.get(2);
products[2] = Product.get(3);
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
    const product: ?Product = Product.get(id);
    console.log('product after get', product);
    if (product) {
        productPage = new ProductPage(product);
    } else {
        productPage = new ProductPage(new Product(1, 'Chaise', 'Mobilier', 'Chaise', 20, 'images/carbonara.jpg'));
    }
    PageRenderer.renderPage(productPage);
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

    const getSectionLuminaires =null;
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

// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();