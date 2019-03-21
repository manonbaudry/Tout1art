// @flow
import $ from 'jquery';

export default class Menu {
	/**
	 * Sélectionne un lien du menu et désélectionne le précédent lien actif
	 * @param {JQuery<HTMLElement>} link lien html (objet jquery) à activer
	 */
	static setSelectedLink( link:$ ){
		// si un lien était précédemment sélectionné, on lui enlève la classe "active"
		$('.navbar li.active').removeClass('active');
		// on ajoute la classe "active" au <li> qui contient le lien cliqué
		link.parent().addClass('active');
	}
}