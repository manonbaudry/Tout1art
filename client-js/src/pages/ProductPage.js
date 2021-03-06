// @flow
import Page from "./Page";
import Product from "../Product";

export default class ProductPage extends Page {

    product: Product;

    constructor(product: Product) {
        super(product.name);
        this.product = product;
        this.click = this.click.bind(this);
    }

    render(): string {
        return `<div class="container">
    <div class="row">
        <img src="${this.product.img}" class="col"/>
        <div class="col">
            <h3>${this.product.name}</h3>
            <p>${this.product.description}</p>
            <p>Prix : ${this.product.price} €</p>
            <button type="button" class="btn btn-primary sendCommand" data-toggle="button" aria-pressed="false">
                Commander
            </button>
        </div>
    </div>
</div>`
    }


    mount(contentElement: HTMLElement): void {
        $('.sendCommand').click(this.click);
    }

    click(event: Event) {
        event.preventDefault();

        fetch(`http://localhost:8080/api/v1/com`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin',
            body: JSON.stringify({
                idArtisan: this.product.artisanId,
                idClient: 1,
                idProduit: this.product.id,
                statut: 'en attente'
            })
        }).then((response: Response) => {
            if (response.ok) {
                alert('Votre commande a été envoyé avec succès.');
            } else {
                alert('Une erreur est survenue lors de la commande.');
            }
        }).catch((error: TypeError) => console.log('Erreur de fetch post commande : ', error.message));
    }
}