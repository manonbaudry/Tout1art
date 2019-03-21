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
const homePage: NewHomePage = new NewHomePage([]);
const inscriptionPage: ClientInscriptionPage = new ClientInscriptionPage();
const connectionPage: ConnectionPage = new ConnectionPage();
let productPage: ProductPage;

//recupération des sous sections "mobilier, luminaire et déco" navbar
const dropDownMobilier = document.querySelectorAll('.mobItem a');
const dropDownLuminaire = document.querySelectorAll('.lumItem a');
const dropDownDeco = document.querySelector('.decoItem a');

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
    //updateSectionDropDown(dropDownMobilier);
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
    productPage = new ProductPage(new Product(1, 'Chaise', 'Mobilier', 'Chaise', 20, 'images/carbonara.jpg'));
    PageRenderer.renderPage(productPage);
}

function updateSectionDropDown(tabSection){
 
    tabSection.forEach(element => {
        element.innerHTML = "ok";    
    });
}

// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();

$('.productLink').click((event: Event) => {
    event.preventDefault();
    renderProduct(event.currentTarget.id);
});