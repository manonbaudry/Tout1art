// @flow
import Page from "./Page";

export default class NewHomePage extends Page {

    constructor() {
        super('Accueil');
    }

    render(): string {
        return `<div class="card">
    <div class="card-body">
        <p>Ex turba vero imae sortis et paupertinae in tabernis aliqui pernoctant vinariis, non nulli velariis
            umbraculorum theatralium latent, quae Campanam imitatus lasciviam Catulus in aedilitate sua suspendit omnium
            primus; aut pugnaciter aleis certant turpi sono fragosis naribus introrsum reducto spiritu concrepantes; aut
            quod est studiorum omnium maximum ab ortu lucis ad vesperam sole fatiscunt vel pluviis, per minutias
            aurigarum equorumque praecipua vel delicta scrutantes.</p>
    </div>
</div>
<p/>
<div class="card-deck">
    <div class="card">
        <a href="#" class="productLink">
            <img src="images/logo.png" class="card-img-top" alt="">
            <div class="card-body">
                <h5 class="card-title">Chaise</h5>
                <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional
                    content. This content is a little bit longer.</p>
                <p class="card-text">
                    <small class="text-muted">Dispo sous 2 semaines</small>
                </p>
            </div>
        </a>
    </div>
    <div class="card">
        <a href="#" class="productLink">
            <img src="images/logo.png" class="card-img-top" alt="">
            <div class="card-body">
                <h5 class="card-title">Bureau</h5>
                <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
                <p class="card-text">
                    <small class="text-muted">Dispo sous 2 semaines</small>
                </p>
            </div>
        </a>
    </div>
    <div class="card">
        <a href="#" class="productLink">
            <img src="images/logo.png" class="card-img-top" alt="">
            <div class="card-body">
                <h5 class="card-title">Lampe</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                    content. This card has even longer content than the first to show that equal height action.</p>
                <p class="card-text">
                    <small class="text-muted">Dispo sous 2 semaines</small>
                </p>
            </div>
        </a>
    </div>
</div>`
    }
};