// @flow
import Page from "./Page";
import Product from "../Product";
import HomePage from "./HomePage";

export default class CiblePage extends Page {
    promises: Array<Promise<product>>;
    compteur: number;

    constructor(products: Promise<Array<Product>>) {
        super('<img class="logot1a" src="images/logoRect.png" alt="logo"></img>');
        this.promises = products;
        this.compteur = 0;
    }

    render(): string | Promise<string> {
        this.compteur = 0;
        const element: HTMLElement = document.createElement('div');
        element.innerHTML = `<div class="card-deck"></div>`;


        return Promise.all(this.promises).then((products: Array<Product>) => {
            let deck: ?HTMLElement = element.querySelector('.card-deck');

            if (deck) {
                products.forEach((product: Product) => {

                   if(this.compteur < 4){
                       const test: string = HomePage.makeThumbnail(product);
                       if(test !== ''){
                            deck.innerHTML += HomePage.makeThumbnail(product);
                            this.compteur++;
                       }
                        
                    }else{
                        element.innerHTML += '<div class="card-deck"></div>';
                        this.compteur = 0;
                        let elements = element.querySelectorAll('.card-deck');
                        deck = elements[elements.length -1];
                    }
                });
             
                const produit:Product = new Product(-1,1,1,1,false,'','','','','','en vente');
                while(this.compteur < 4){
                  
                    deck.innerHTML += HomePage.makeThumbnail(produit);
                    this.compteur++;
                }

            }
            return element.outerHTML;
        });
    }

}