// @flow
import Page from "./Page";
import Order from "../Order";
import Product from "../Product";

export default class AdminPage extends Page {

    orders: Array<Order>;
    promise: Promise<Array<{ id: number, artisanId: number, clientId: number, productId: number, status: string }>>;

    constructor() {
        super('Commandes');
        this.promise = Order.getAll();
    }

    render(): string | Promise<string> {
        const card: HTMLElement = document.createElement('div');
        card.setAttribute('class', 'card');
        card.innerHTML = `<div class="card-title">Commandes en cours</div>
<div class="card-body container"></div>`;

        const cardBody = card.querySelector('.card-body');

        return this.promise.then((json) => {
            this.orders = [];
            let i = 0;
            json.forEach(order => this.orders[i++] = Order.jsonToObj(order));
            const promises: Array<Promise<string>> = [];
            i = 0;
            this.orders.forEach(order => promises[i++] = AdminPage.makeOrder(order));
            return Promise.all(promises).then((ordersString: Array<string>) => {
                ordersString.forEach(value => cardBody.innerHTML += value);
                return card.outerHTML;
            });
        });
    }

    static makeOrder(order: Order): Promise<string> {
        return Product.get(order.productId)
            .then((json) => {
                const product: Product = Product.jsonToObj(json);
                return `<div class="row">
    <div class="col">Commande n°${order.id}</div>
    <div class="col">
        <p>Statut : ${order.status}</p>
        <p>Produit : ${product.name}</p>
        <p>Prix : ${product.price}€</p>
    </div>
</div>
<p/>`;
            });
    }
}