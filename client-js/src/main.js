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
const dropDownMobilier = document.querySelector('.mobButton');
const dropDownLuminaire = document.querySelector('.lumButton');
const dropDownDeco = document.querySelector('.decoButton');

dropDownMobilier.click((event: Event) => {
    event.preventDefault();
    selectionCible($('.mobButton').innerHTML);
});

dropDownLuminaire.click((event: Event) => {
    event.preventDefault();
    selectionCible($('.lumButton').innerHTML);
});

dropDownDeco.click((event: Event) => {
    event.preventDefault();
    selectionCible($('.decoButton').innerHTML);
});


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

// function selectionCible(){

// }

// function updateSectionDropDown(mobiliers, luminaires, decos) {

   
//     //ici requete pour aller chercher les sous sections dans la bdd
//     Product.getAll().then(json => {
//         console.log("taille: " + json.length);

//         for (let i = 0; i < json.length; i++) {
//             console.log(json[i].categorie+" "+json[i].sousCategorie);
//             if (json[i].categorie == 'mobilier') {
//                 mobiliers.innerHTML += `<a class="dropdown-item" href="#">${json[i].sousCategorie}</a>`;
//             } else if (json[i].categorie == 'luminaire') {
//                 luminaires.innerHTML += `<a class="dropdown-item" href="#">${json[i].sousCategorie}</a>`;
//             } else {
//                 decos.innerHTML += `<a class="dropdown-item" href="#">${json[i].sousCategorie}</a>`;
//             }
//         }
//     });
// }
console.log(dropDownMobilier.innerHTML.toLocaleLowerCase());
//updateSectionDropDown(dropDownMobilier, dropDownLuminaire, dropDownDeco);
