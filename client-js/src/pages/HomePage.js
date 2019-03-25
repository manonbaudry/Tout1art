// @flow
import Page from "./Page";
import Product from "../Product";

export default class HomePage extends Page {

    promises: Array<Promise<Product>>;


    constructor(products: Promise<Array<Product>>) {
        super('<img class="logot1a" src="images/logoRect.png" alt="logo"></img>');
        this.promises = products;
    }

    render(): string | Promise<string> {
        const element: HTMLElement = document.createElement('div');
        element.innerHTML = `<div>
    <div class="card-body">

        <img class="imgAccueil" src="images/Ambiance.jpg" alt="acceuil"/>
        </br>
        <p>Vous trouverez sur Tout1Art des créations « taillées » dans de belles matières, des réalisations dont vous
            serez fiers et que vous ne trouverez pas ailleurs. Elles racontent toutes une histoire : celle de l’artisan
            qui les ont imaginées, conçues, travaillées à la main.</p>

    </div>
</div>
</br>
<div class="card-deck"></div>`;


        return Promise.all(this.promises).then((products: Array<Product>) => {
            const deck: ?HTMLElement = element.querySelector('.card-deck');
            if (deck) {
                products.forEach((product: Product) => {
                    deck.innerHTML += HomePage.makeThumbnail(product);
                });
            }
            return element.outerHTML;
        });
    }

    static makeThumbnail(product: Product): string {
        return product.status === 'en vente' ? `<div class="card">
    <a href="#" class="productLink" id="${product.id}">
        <div class="embed-responsive embed-responsive-1by1">
            <img alt="${product.name}" class="card-img-top embed-responsive-item" src="${product.img}"
                 style="object-fit: cover"/>
        </div>
        <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text">
                <small class="text-muted">Disponible sous ${product.deliveryTime} semaines</small>
            </p>
        </div>
    </a>
</div>` : '';
    }
}