// @flow
import Page from "./Page";

export default class AdminPage extends Page {

    constructor() {
        super('Commandes');
    }

    render(): string {
        const card: HTMLElement = document.createElement('div');
        card.setAttribute('class', 'card');
        card.innerHTML = `<div class="card-title">Commandes en cours</div>
<div class="card-body"></div>`;

        const cardBody = card.querySelector('.card-body');

        return card.outerHTML;
    }
}