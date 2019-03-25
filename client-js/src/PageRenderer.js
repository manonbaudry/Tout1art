// @flow
import Page from './pages/Page';

export default class PageRenderer {

    static titleElement: ?HTMLElement;
    static contentElement: ?HTMLElement;

    static renderPage(page: Page): void {
        if (this.titleElement) {
            // $FlowFixMe
            this.titleElement.innerHTML = page.renderTitle();
        }
        if (this.contentElement) {
            const render = page.render();
            if (render instanceof Promise) {
                render.then((string: string) => this.contentElement.innerHTML = string);
            } else {
                // $FlowFixMe
                this.contentElement.innerHTML = render;
            }
            // $FlowFixMe
            page.mount(this.contentElement);
        }
    }
}
