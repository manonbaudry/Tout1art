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
    <button type="button" class="btn btn-primary accept" data-toggle="button" aria-pressed="false">
        Accepter
    </button>
</p>
<p>
    <button type="button" class="btn btn-primary reject" data-toggle="button" aria-pressed="false">
        Refuser
    </button>
</p>` : ''}
    </td>
</tr>
<p/>`
    }

    mount(contentElement: HTMLElement): void {
        $('.accept').click(this.accept);
        $('.reject').click(this.reject);
    }

    accept(event: Event): void {
        event.preventDefault();
        alert('accept');
    }

    reject(event: Event): void {
        event.preventDefault();
        alert('reject');
    }
}