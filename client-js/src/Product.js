// @flow
export default class Product {

    id: number;
    name: string;
    category: string;
    subCategory: string;
    description: string;
    price: number;
    img: string;
    ordered: boolean;

    constructor(id: number, name: string, category: string, subCategory: string, description: string, price: number, img: string, ordered: boolean) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.price = price;
        this.img = img;
        this.ordered = ordered;
    }

    toLiteral(): { id: number, nom: string, categorie: string, description: string, prix: number, srcImage: string } {

    }

    static get(id: number): Promise<{ id: number, name: string, category: string, subCategory: string, description: string, price: number, img: string, ordered: boolean }> {
        return fetch(`http://localhost:8080/api/v1/produit/${id}`)
            .then((response: Response) => response.json())
            .then((json: JSON) => {
                const id: number = json.id;
                const name: string = json.nom;
                const category: string = json.categorie;
                const subCategory: string = json.sousCategorie;
                const description: string = json.description;
                const price: number = json.prix;
                const img: string = json.srcImage;
                const ordered: boolean = json.commande;

                return {id, name, category, subCategory, description, price, img, ordered};
            })
            .catch((error: TypeError) => console.log('Erreur de fetch', error.message));
    }

    static getAll(): Promise<{category: string, sousCategorie: string }> {
        return fetch(`http://localhost:8080/api/v1/produit`)
            .then((response: Response) => response.json())
            .then((json: JSON) => {

                return json;
            })
            .catch((error: TypeError) => console.log('Erreur de fetch', error.message));
    }

    static jsonToObj(product) {
        return new Product(product.id, product.name, product.category, product.subCategory, product.description, product.price, product.img, product.ordered);
    }
};