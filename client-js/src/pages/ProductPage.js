// @flow
import Page from "./Page";
import Product from "../Product";

export default class ProductPage extends Page {

    product: Product;

    constructor(product: Product) {
        super(product.name);
        this.product = product;
    }

    render(): string {
        return `<div class="row">
    <img src="${this.product.img}"/>
    <ul>
        <li>${this.product.name}</li>
        <li>${this.product.description}</li>
        <li>${this.product.price} €</li>
    </ul>
</div>`
    }
}