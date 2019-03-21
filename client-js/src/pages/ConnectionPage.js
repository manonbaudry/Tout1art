// @flow
import Page from "./Page";
import $ from "jquery";

export default class ConnectionPage extends Page {

    constructor() {
        super('Connexion');
        // $FlowFixMe
        this.submit = this.submit.bind(this);
    }

    mount(contentElement: HTMLElement): void {
        $('form.connectionPage').submit(this.submit);
    }

    render() {
        return `<form class="form-signin connectionPage">
    <h1 class="h3 mb-3 font-weight-normal">Veuillez vous connecter</h1>

    <div class="form-group">
        <label for="exampleInputEmail1">Email address:</label>
        <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Email address">
    </div>

    <div class="form-group">
        <label for="exampleInputName">Mot de passe:</label>
        <input type="username" class="form-control" id="exampleInputPassword1" placeholder="Mot de passe">
    </div>

    
    <button type="submit" class="btn btn-primary">Se connecter</button>
    </form>`;
    }

    submit(event: Event) {

    }
};