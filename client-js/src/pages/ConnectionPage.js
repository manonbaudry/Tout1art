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
    <label for="inputEmail" class="sr-only">Email</label>
    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Adresse email" required
           autofocus>
    <label for="inputPassword" class="sr-only">Mot de passe</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Mot de passe" required>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Se connecter</button>
</form>`;
    }

    submit(event: Event) {

    }
};