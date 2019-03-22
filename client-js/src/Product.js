// @flow
export default class Product {

    id: number;
    name: string;
    category: string;
    subCategory: string;
    description: string;
    price: number;
    img: string;

    constructor(id: number, name: string, category: string, subCategory: string, description: string, price: number, img: string) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    toLiteral(): { id: number, nom: string, categorie: string, description: string, prix: number, srcImage: string } {

    }

    static get(id: number): Promise<{ id: number, name: string, category: string, subCategory: string, description: string, price: number, img: string }> {
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

                return {id, name, category, subCategory, description, price, img};
            })
            .catch((error: TypeError) => console.log('Erreur de fetch', error.message));
    }

    static jsonToObj(product) {
        return new Product(product.id, product.name, product.category, product.subCategory, product.description, product.price, product.img);
    }
};