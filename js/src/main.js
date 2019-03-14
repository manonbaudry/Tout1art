// @flow
import HomePage from './pages/HomePage.js';
import AddPizzaPage from './pages/AddPizzaPage.js';
import PageRenderer from './PageRenderer.js';
import $ from 'jquery';
import Menu from './components/Menu.js';

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.pizzasContainer');

// déclaration des différentes page de l'app
const homePage:HomePage = new HomePage([]);
const addPizzaPage:AddPizzaPage = new AddPizzaPage();

// configuration des liens du menu
const menu:Menu = new Menu();
const logoLink = $('a.navbar-brand');
const homeLink = $('.homeLink');
const addPizzaLink = $('.addPizzaLink');

logoLink.click( (event:Event) => {
	event.preventDefault();
	renderHome();
});
homeLink.click( (event:Event) => {
	event.preventDefault();
	renderHome();
});
addPizzaLink.click( (event:Event) => {
	event.preventDefault();
	renderAddPizza();
})

function renderHome():void{
	menu.setSelectedLink(homeLink);
	PageRenderer.renderPage(homePage);
}
function renderAddPizza():void{
	menu.setSelectedLink(addPizzaLink);
	PageRenderer.renderPage(addPizzaPage);
}

// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();