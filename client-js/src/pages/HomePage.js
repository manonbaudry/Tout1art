// @flow
import Component from '../components/Component.js';
import PizzaThumbnail from '../components/PizzaThumbnail.js';
import Page from './Page.js';
import $ from 'jquery';

export default class HomePage extends Page {
	#data;

	constructor( data:Array<{nom:string, base:string, prix_petite:number, prix_grande:number}> ){
		super( 'La carte' );
		this.attribute = {name:'class', value:'home'};
		this.data = data;
	}

	set data(value:Array<{nom:string, base:string, prix_petite:number, prix_grande:number}>):void {
		this.#data = value;
		this.children = this.#data.map( pizza => new PizzaThumbnail(pizza) )
	}

	mount(container:HTMLElement):void {
		container.classList.add('is-loading');
		fetch('http://localhost:8080/api/v1/pizzas')
			.then( (response:Response) => response.json() )
			.then( (data:any) => {
				this.data = data;
				container.innerHTML = this.render();
				container.classList.remove('is-loading');
				$(container).find('a').click( this.onThumbnailClick )
			});
	}

	onThumbnailClick(event:Event):void {
		event.preventDefault();
		window.open(
			$(event.currentTarget).attr('href'),
			'popup',
			'width=350,height=200,menubar=0,toolbar=0,location=0,personalbar=0,status=0'
		);
	}
}