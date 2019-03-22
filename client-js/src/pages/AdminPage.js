// @flow
import Page from "./Page";
import Product from "../Product";

export default class AdminPage extends Page {

    products;

    constructor() {
        super('Commandes');
        this.products = [];
        const productsJson = Product.getAll();
        productsJson.then(json => {
            let i = 0;
            json.forEach(product => this.products[i++] = Product.jsonToObj(product));
        });
    }

    render(): string {
        const card: HTMLElement = document.createElement('div');
        card.setAttribute('class', 'card');
        card.innerHTML = `<div class="card-title">Commandes en cours</div>
<div class="card-body"></div>`;

        const cardBody = card.querySelector('.card-body');
        cardBody.innerHTML = '';
        this.products.forEach(value => {
            let innerHTML = AdminPage.makeOrder(value);
            cardBody.innerHTML += innerHTML;
        });
        return card.outerHTML;
    }

    static makeOrder(product: Product): string {
        return product.ordered ? `<p>Commande de ${product.name}</p>` : '';
    }
}