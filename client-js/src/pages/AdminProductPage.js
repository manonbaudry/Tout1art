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
        const table: HTMLElement = document.createElement('table');
        table.setAttribute('class', 'table');

        return this.promise.then((json) => {
            this.products = [];
            let i = 0;
            json.forEach(product => this.products[i++] = Product.jsonToObj(product));
            const html: Array<string> = [];
            i = 0;
            this.products.forEach(product => html[i++] = AdminProductPage.makeProduct(product));
            html.forEach(value => table.innerHTML += value);
            return table.outerHTML;
        });
    }

    static makeProduct(product: Product): string {
        return `<tr>
    <td>Produit n°${product.id}</td>
    <td>
        <p>Statut : ${product.status}</p>
        <p>Nom : ${product.name}</p>
        <p>Prix : ${product.price}€</p>
    </td>
    <td>
        ${product.status === 'en attente' ? `<p>
            <button class="accept">Accepter</button>
        </p>
        <p>
            <button class="reject">Rejeter</button>
        </p>` : ''}
    </td>
</tr>
<p/>`
    }
}