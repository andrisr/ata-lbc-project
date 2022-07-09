import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import rsvpClient from "../api/rsvpClient";


class HomePage extends BaseClass {

    constructor() {
        super();
        const methodsToBind = ['onCreate', 'onPopulateRequest'];
        this.bindClassMethods(methodsToBind, this);
        // this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById("button").addEventListener("click", this.onCreate);
        document.getElementById("button2").addEventListener("click", this.onPopulateRequest);
        this.client = new rsvpClient();
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let name = document.getElementById("name").value;
        const findRSVP = await this.client.getRsvp(name, this.errorHandler);

        if (findRSVP) {
            displaySuccessfulInvite()
            onButtonClick();
            onButtonClickHideNameField();
        } else {
            displayError();
        }
    }

    async onPopulateRequest(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let name = document.getElementById("name").value;
        const findRSVP = await this.client.getRsvp(name, this.errorHandler);

        let email = "";
        for (const property in findRSVP){
            if (property === 'email'){
                email = property.value
            }
        }

        let names = document.getElementById("name").value;
        let isAttending = document.getElementById("attendingOptions").value;
        let mealChoice = document.getElementById("entreeOption").value;
        let plus1Name = document.getElementById("user_plus_one").value;
        let plus1MealChoice = document.getElementById("guest_entree").value;

        const updateRSVP = await this.client.updateRsvp(names, email, isAttending, mealChoice, plus1Name, plus1MealChoice, this.errorHandler);

        if (updateRSVP) {
            onButtonClick();
            displaySuccessfulRsvp();
            onButtonClickThanks();
        } else {
            displayErrorRsvp();
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