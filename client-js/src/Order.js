// @flow
export default class Order {

    id: number;
    artisanId: number;
    clientId: number;
    productId: number;
    status: string;

    constructor(id: number, artisanId: number, clientId: number, productId: number, status: string) {
        this.id = id;
        this.artisanId = artisanId;
        this.clientId = clientId;
        this.productId = productId;
        this.status = status;
    }

    static get(id: number): Promise<{ id: number, artisanId: number, clientId: number, productId: number, status: string }> {
        return fetch(`http://localhost:8080/api/v1/com/${id}`)
            .then((response: Response) => response.json())
            .then((json: JSON) => {
                const id: number = json.id;
                const artisanId: number = json.idArtisan;
                const clientId: number = json.idClient;
                const productId: number = json.idProduit;
                const status: string = json.statut;

                return {id, artisanId, clientId, productId, status};
            })
            .catch((error: TypeError) => console.log('Erreur de fetch get commande : ', error.message));
    }

    static getAll(): Promise<Array<{ id: number, artisanId: number, clientId: number, productId: number, status: string }>> {
        return fetch(`http://localhost:8080/api/v1/com`)
            .then((response: Response) => response.json())
            .then((json: JSON) => {
                const array: Array<{ id: number, artisanId: number, clientId: number, productId: number, status: string; }> = [];

                for (let i = 0; i < json.length; ++i) {
                    const id: number = json[i].id;
                    const artisanId: number = json[i].idArtisan;
                    const clientId: number = json[i].idClient;
                    const productId: number = json[i].idProduit;
                    const status: string = json[i].statut;

                    array[i] = {id, artisanId, clientId, productId, status};
                }

                return array;
            })
            .catch((error: TypeError) => console.log('Erreur de fetch get commande : ', error.message));
    }

    static jsonToObj(order: { id: number, artisanId: number, clientId: number, productId: number, status: string }): Order {
        return new Order(order.id, order.artisanId, order.clientId, order.productId, order.status);
    }
};