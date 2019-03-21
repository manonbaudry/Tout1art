// @flow
export default class Product {

    id: number;
    name: string;
    category: string;
    description: string;
    price: number;
    img: string;

    constructor(id: number, name: string, category: string, description: string, price: number, img: string) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    static get(id: number): ?Product {
        let name: string;
        let category: string;
        let description: string;
        let price: number;
        let img: string;

        fetch(`localhost:8080/api/v1/produit/${id}`)
            .then((response: Response) => {
                if (response.ok) {
                    response.json().then((json: JSON) => {
                        console.log(json);
                        name = json.name;
                        category = json.category;
                        description = json.description;
                        price = json.price;
                        img = json.img;
                    });
                } else {
                    console.log('Erreur de réponse');
                }
            })
            .catch((error: TypeError) => console.log('Erreur de fetch', error.message));

        if (name && category && description && price && img) {
            return new Product(id, name, category, description, price, img);
        }
        return null;
    }
};