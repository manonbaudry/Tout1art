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
        return `<div class="container">
    <div class="row">
        <img src="${this.product.img}" class="col"/>
        <div class="col">
            <h3>${this.product.name}</h3>
            <p>${this.product.description}</p>
            <p>Prix : ${this.product.price}</p>
        </div>
    </div>
</div>`
    }
}