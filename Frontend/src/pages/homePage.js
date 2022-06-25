import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import exampleClient from "../api/exampleClient";

class HomePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetComments', 'onCreate', 'renderComments'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('questions-form').addEventListener('submit', this.onCreate);
        this.client = new exampleClient();
        await this.onGetComments();

        this.dataStore.addChangeListener(this.renderComments)
    }

    /** Render Methods -----------------------------------------------------------------------------------------------*/

    async renderComments() {
        let resultArea = document.getElementById("result-info");

        const comments = this.dataStore.get("rsvp");

        let content = ""
        content += "<ul>"

        for(let comment of comments){
            content += `
        <li>
               <h3>Name: ${comment.name}</h3>
               <h4>Attending: ${comment.attending}</h4>
               <h4>Entree: ${comment.entree}</h4>
        </li>
      `
        }
        content += "</ul>"

        if (comments) {
            resultArea.innerHTML = content
        } else {
            resultArea.innerHTML = "No rsvp";
        }
    }

    /** Event Handlers -----------------------------------------------------------------------------------------------*/

    async onGetComments() {
        let result = await this.client.getRsvpByAttending(this.errorHandler);
        this.dataStore.set("rsvp", result);
    }


    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let name = document.getElementById("name").value;
        let attending = document.getElementById("attending").value;
        let entree = document.getElementById("entree").value;

        const createdComment = await this.client.createRsvp(name, attending, entree, this.onGetComments());

        if (createdComment) {
            this.showMessage(`Posted a rsvp!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homePage = new HomePage();
    await homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
