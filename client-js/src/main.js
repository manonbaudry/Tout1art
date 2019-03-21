// @flow
import PageRenderer from './PageRenderer.js';
import Menu from './components/Menu.js';
import HomePage from './pages/HomePage.js';
import ClientInscriptionPage from "./pages/ClientInscriptionPage";
import ConnectionPage from "./pages/ConnectionPage";
import $ from 'jquery';

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.pageContent');

// déclaration des différentes page de l'app
const homePage: HomePage = new HomePage([]);
const inscriptionPage: ClientInscriptionPage = new ClientInscriptionPage();
const connectionPage: ConnectionPage = new ConnectionPage();

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
}

function renderInscription(): void {
    Menu.setSelectedLink(inscriptionLink);
    PageRenderer.renderPage(inscriptionPage);
}

function renderConnection(): void {
    Menu.setSelectedLink(connectionLink);
    PageRenderer.renderPage(connectionPage);
}

// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();