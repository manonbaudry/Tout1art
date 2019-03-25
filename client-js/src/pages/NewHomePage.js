// @flow
import Page from "./Page";
import Product from "../Product";

export default class NewHomePage extends Page {

    products: Array<Product>;

    constructor(products: Array<Product>) {
        super('Qui sommes-nous?');
        this.products = products;
    }

    render(): string {
        const element: HTMLElement = document.createElement('div');
        element.innerHTML = `<div>
    <div class="card-body">
        <p>Vous trouverez sur Tout1Art des créations «taillées » dans de belles matières, des réalisations dont vous serez fiers et que vous ne trouverez pas ailleurs. Elles racontent toutes une histoire : celle de l’artisan qui les ont imaginées, conçues, travaillées à la main.</p>
    </div>
</div>
<p/>
<div class="card-deck"></div>`;

        const deck: ?HTMLElement = element.querySelector('.card-deck');
        if (deck) {
            this.products.forEach((product: Product) => {
                deck.innerHTML += NewHomePage.makeThumbnail(product);
            });
        }
        return element.outerHTML;
    }

    static makeThumbnail(product: Product): string {
        return `<div class="card">
    <a href="#" class="productLink" id="${product.id}">
        <div class="embed-responsive embed-responsive-1by1">
            <img alt="${product.name}" class="card-img-top embed-responsive-item" src="${product.img}"
                 style="object-fit: cover"/>
        </div>
        <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text">${product.description}</p>
            <p class="card-text">
                <small class="text-muted">Disponible sous 2 semaines</small>
            </p>
        </div>
    </a>
</div>`
    }
}