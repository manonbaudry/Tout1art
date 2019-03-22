// @flow
import $ from 'jquery';
import Page from "./Page";

export default class ClientInscriptionPage extends Page {

    constructor() {
        super('Inscription');
        // $FlowFixMe
        this.submit = this.submit.bind(this);
    }

    render():string {
        return `<form>
    <div class="form-group">
        <label for="exampleInputEmail">Adresse email:</label>
        <input type="email" class="form-control" id="exampleInputEmail" aria-describedby="emailHelp"
               placeholder="Adresse email">
    </div>

    <div class="form-group">
        <label for="exampleInputName">Nom utilisateur:</label>
        <input type="text" class="form-control" id="exampleInputName" placeholder="Nom utilisateur">
    </div>

    <div class="form-group">
        <label for="exampleInputPassword">Mot de passe:</label>
        <input type="password" class="form-control" id="exampleInputPassword" placeholder="Mot de passe">
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>
</form>`
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