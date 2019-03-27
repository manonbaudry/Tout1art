// @flow
export default class Product {

    id: number;
    artisanId: number;
    price: number;
    deliveryTime: number;
    ordered: boolean;
    name: string;
    description: string;
    category: string;
    subCategory: string;
    img: string;
    status: string;

    constructor(id: number, artisanId: number, price: number, deliveryTime: number, ordered: boolean, name: string, description: string, category: string, subCategory: string, img: string, status: string) {
        this.id = id;
        this.artisanId = artisanId;
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.ordered = ordered;
        this.name = name;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.img = img;
        this.status = status;
    }

    static get(id: number): Promise<{ id: number, artisanId: number, price: number, deliveryTime: number, ordered: boolean, name: string, description: string, category: string, subCategory: string, img: string, status: string }> {
        return fetch(`http://vps648942.ovh.net:8080/api/v1/produit/${id}`)
            .then((response: Response) => response.json())
            .then((json: JSON) => {
                const id: number = json.id;
                const artisanId: number = json.idArtisan;
                const price: number = json.prix;
                const deliveryTime: number = json.delai;
                const ordered: boolean = json.commande;
                const name: string = json.nom;
                const description: string = json.description;
                const category: string = json.categorie;
                const subCategory: string = json.sousCategorie;
                const img: string = json.srcImage;
                const status: string = json.statut;

                return {
                    id,
                    artisanId,
                    price,
                    deliveryTime,
                    ordered,
                    name,
                    description,
                    category,
                    subCategory,
                    img,
                    status
                };
            })
            .catch((error: TypeError) => console.log('Erreur de fetch', error.message));
    }

    static getAll(): Promise<Array<{ id: number, artisanId: number, price: number, deliveryTime: number, ordered: boolean, name: string, description: string, category: string, subCategory: string, img: string, status: string }>> {
        return fetch(`http://vps648942.ovh.net:8080/api/v1/produit`)
            .then((response: Response) => response.json())
            .then((json: JSON) => {
                const array = [];
                for (let i = 0; i < json.length; ++i) {
                    const id: number = json[i].id;
                    const artisanId: number = json[i].idArtisan;
                    const price: number = json[i].prix;
                    const deliveryTime: number = json[i].delai;
                    const ordered: boolean = json[i].commande;
                    const name: string = json[i].nom;
                    const description: string = json[i].description;
                    const category: string = json[i].categorie;
                    const subCategory: string = json[i].sousCategorie;
                    const img: string = json[i].srcImage;
                    const status: string = json[i].statut;

                    array[i] = {
                        id,
                        artisanId,
                        price,
                        deliveryTime,
                        ordered,
                        name,
                        description,
                        category,
                        subCategory,
                        img,
                        status
                    };
                }
                return array;
            })
            .catch((error: TypeError) => console.log('Erreur de fetch', error.message));
    }

    static jsonToObj(product) {
        return new Product(product.id, product.artisanId, product.price, product.deliveryTime, product.ordered, product.name, product.description, product.category, product.subCategory, product.img, product.status);
    }

    static compare(a: Product, b: Product):number {
        if (a === b) {
            return 0;
        }
        if (a.status === b.status) {
            return b.id - a.id;
        }
        if (a.status === 'en attente') {
            return -1;
        }
        return 1;
    }
};