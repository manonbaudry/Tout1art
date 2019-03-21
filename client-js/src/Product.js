// @flow
export default class Product {

    id: number;
    name: string;
    category: string;
    description: string;
    price: number;
    img: string;

    constructor(id: number, name: string, category: string, description: string, price: number, img: string) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.img = img;
    }
};