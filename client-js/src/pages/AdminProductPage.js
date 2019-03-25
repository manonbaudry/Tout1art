// @flow
import Page from "./Page";
import Product from "../Product";
import PageRenderer from "../PageRenderer";

export default class AdminProductPage extends Page {

    products: Array<Product>;
    promise: Promise<Array<{ id: number, name: string, category: string, subCategory: string, description: string, price: number, img: string, ordered: boolean }>>;

    constructor() {
        super('Produits');
        this.promise = Product.getAll();
        this.accept = this.accept.bind(this);
        this.reject = this.reject.bind(this);
    }

    render(): string | Promise<string> {
        const table: HTMLElement = document.createElement('table');
        table.setAttribute('class', 'table');

        return this.promise.then((json) => {
            this.products = [];
            let i = 0;
            json.forEach(product => this.products[i++] = Product.jsonToObj(product));
            this.products.sort(Product.compare);
            const html: Array<string> = [];
            i = 0;
            this.products.forEach(product => html[i++] = AdminProductPage.makeProduct(product));
            html.forEach(value => table.innerHTML += value);
            return table.outerHTML;
        });
    }

    static makeProduct(product: Product): string {
        return product.status === 'refuse' ? '' : `<tr>
    <td>Produit n°${product.id}</td>
    <td>
        <p>Statut : ${product.status}</p>
        <p>Nom : ${product.name}</p>
        <p>Prix : ${product.price}€</p>
    </td>
    <td>
        ${product.status === 'en attente' ? `<p>
    <button id=${product.id} type="button" class="btn btn-primary accept" data-toggle="button" aria-pressed="false">
        Accepter
    </button>
</p>
<p>
    <button id=${product.id} type="button" class="btn btn-primary reject" data-toggle="button" aria-pressed="false">
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

    send(event: Event, accepted: boolean): void {
        event.preventDefault();
        let id = event.currentTarget.getAttribute('id');
        fetch(`http://localhost:8080/api/v1/produit/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin',
            body: JSON.stringify({
                statut: accepted ? 'en vente' : 'refuse'
            })
        }).then((response: Response) => {
            if (response.ok) {
                alert(accepted ? 'Le produit a été accepté avec succés' : 'Le produit a été refusé avec succés');
            } else {
                alert('Une erreur est survenue lors de la demande.');
            }
            PageRenderer.renderPage(new AdminProductPage());
        }).catch((error: TypeError) => console.log('Erreur de fetch post commande : ', error.message));
    }

    accept(event: Event): void {
        this.send(event, true);
    }

    reject(event: Event): void {
        this.send(event, false);
    }
}