// @flow
import $ from 'jquery';
import Page from "./Page";

export default class ClientInscriptionPage extends Page {

    constructor() {
        super('Inscription');
        // $FlowFixMe
        this.submit = this.submit.bind(this);
    }

    render(): string {
        return `<form class="clientInscriptionPage">
    <label>
        Email :
        <input type="email" name="email" class="form-control">
    </label>
    <label>
        Nom d'utilisateur :
        <input type="text" name="login" class="form-control">
    </label>
    <label>
        Mot de passe :
        <input type="password" name="password" class="form-control">
    </label>
    <button type="submit" class="btn btn-default">S'inscrire</button>
</form>`;
    }

    mount(contentElement: HTMLElement): void {
        $('form.clientInscriptionPage').submit(this.submit);
    }

    submit(event: Event): void {

    }

    static getFieldValue(fieldName: string): ?(string | Array<string>) {
        const field: ?HTMLElement = document.querySelector(`[name=${fieldName}]`);
        if (field instanceof HTMLInputElement) {
            return field.value !== '' ? field.value : null;
        } else if (field instanceof HTMLSelectElement) {
            const values: Array<string> = [];
            for (let i = 0; i < field.selectedOptions.length; i++) {
                values.push(field.selectedOptions[i].value);
            }
            return values.length ? values : null;
        }
        return null;
    }
}