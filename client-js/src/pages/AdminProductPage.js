// @flow
import Page from "./Page";
import Product from "../Product";

export default class AdminProductPage extends Page {

    products: Array<Product>;
    promise: Promise<Array<{ id: number, name: string, category: string, subCategory: string, description: string, price: number, img: string, ordered: boolean }>>;

    constructor() {
        super('Produits');
        this.promise = Product.getAll();
    }

    render(): string | Promise<string> {
        const card: HTMLElement = document.createElement('div');
        card.setAttribute('class', 'card');
        card.innerHTML = `<div class="card-title">Produits en attente de validation</div>
<div class="card-body container"></div>`;

        const cardBody = card.querySelector('.card-body');

        return this.promise.then((json) => {
            this.products = [];
            let i = 0;
            json.forEach(product => this.products[i++] = Product.jsonToObj(product));
            const html: Array<string> = [];
            i = 0;
            this.products.forEach(product => html[i++] = AdminProductPage.makeProduct(product));
            html.forEach(value => cardBody.innerHTML += value);
            return card.outerHTML;
        });
    }

    static makeProduct(product: Product): string {
        if (product.status === 'En attente') {
            return `<div class="row">
    <div class="col">Produit n°${product.id}</div>
    <div class="col">
        <p>Statut : ${product.status}</p>
        <p>Nom : ${product.name}</p>
        <p>Prix : ${product.price}€</p>
    </div>
</div>
<p/>`
        }
        return '';
    }
}