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
            for (let i = 0; i < json.length; ++i) {
                this.products[i] = Product.jsonToObj(productsJson[i]);
            }
        });
    }

    render(): string {
        const card: HTMLElement = document.createElement('div');
        card.setAttribute('class', 'card');
        card.innerHTML = `<div class="card-title">Commandes en cours</div>
<div class="card-body"></div>`;

        const cardBody = card.querySelector('.card-body');
        while (this.products.length === 0) {
        }
        this.products.forEach(value => {
            cardBody.innerHTML += AdminPage.makeOrder(value);
        });
        return card;
    }

    static makeOrder(product: Product): string {
        return `<p>Commande de ${product.name}</p>`;
    }
}