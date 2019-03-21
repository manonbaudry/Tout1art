// @flow
import Page from "./Page";
import Product from "../Product";

export default class NewHomePage extends Page {

    products: Array<Product>;

    constructor(products: Array<Product>) {
        super('Accueil');
        this.products = products;
        console.log(products);
    }

    render(): string {
        const element: HTMLElement = document.createElement('div');
        element.innerHTML = `<div class="card">
    <div class="card-body">
        <p>Ex turba vero imae sortis et paupertinae in tabernis aliqui pernoctant vinariis, non nulli velariis
            umbraculorum theatralium latent, quae Campanam imitatus lasciviam Catulus in aedilitate sua suspendit omnium
            primus; aut pugnaciter aleis certant turpi sono fragosis naribus introrsum reducto spiritu concrepantes; aut
            quod est studiorum omnium maximum ab ortu lucis ad vesperam sole fatiscunt vel pluviis, per minutias
            aurigarum equorumque praecipua vel delicta scrutantes.</p>
    </div>
</div>
<p/>
<div class="card-deck"></div>`;

        const deck: ?HTMLElement = element.querySelector('.card-deck');
        if (deck) {
            this.products.forEach((product: Product) => {
                deck.innerHTML += NewHomePage.makeThumbnail(product);
            });
        }
        return element.outerHTML;
    }

    static makeThumbnail(product: Product): string {
        return `<div class="card">
    <a href="#" class="productLink" id="${product.id}">
        <img src="${product.img}" class="card-img-top" alt="${product.name}" width="200px">
        <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text">${product.description}</p>
            <p class="card-text">
                <small class="text-muted">Disponible sous 2 semaines</small>
            </p>
        </div>
    </a>
</div>`
    }
};