// @flow
import Page from "./Page";
import Product from "../Product";
import HomePage from "./HomePage";

export default class CiblePage extends Page {
    promises: Array<Promise<product>>;

    constructor(products: Promise<Array<Product>>) {
        super('<img class="logot1a" src="images/logoRect.png" alt="logo"></img>');
        this.promises = products;
    }

    render(): string | Promise<string> {
        const element: HTMLElement = document.createElement('div');
        element.innerHTML = `<div class="card-deck"></div>`;


        return Promise.all(this.promises).then((products: Array<Product>) => {
            const deck: ?HTMLElement = element.querySelector('.card-deck');
            if (deck) {
                products.forEach((product: Product) => {
                    deck.innerHTML += HomePage.makeThumbnail(product);
                });
            }
            return element.outerHTML;
        });
    }

}