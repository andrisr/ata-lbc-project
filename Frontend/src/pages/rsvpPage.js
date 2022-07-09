import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import rsvpClient from "../api/rsvpClient";

class RSVPPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onCreateGuest', 'onDeleteGuest', 'onGetTable', 'loadIntoTable'], this);
        this.dataStore = new DataStore();
    }

    async onCreateGuest(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("name", null);

        let name = document.getElementById("create-name-field").value;
        let email = document.getElementById("create-email-field").value;

        const createdGuest = await this.client.createRsvp(name, email, this.errorHandler);
        this.dataStore.set("name", createdGuest);

        if (createdGuest) {

        this.onGetTable();

            this.showMessage(`You just invited ${createdGuest.name} to your wedding!`)
        } else {
            this.errorHandler("Error inviting!  Try again...");
        }
    }

    async onDeleteGuest(event) {
        event.preventDefault();
        let name = document.getElementById("create-name-field").value;
        let email = document.getElementById("create-email-field").value;

    
        const deletedGuest = await this.client.deleteRsvp(name, email, this.errorHandler);

        if(deletedGuest) {
        this.onGetTable();
        this.dataStore.set("name", null);

            this.showMessage(`You just removed ${deletedGuest.name} from your guest list.`)
        } else {
            this.errorHandler("Error removing guest. Try again...");
        }

        

    }

    async onGetTable() {
        let result = await this.client.getAllRsvps("", this.errorHandler);
        this.dataStore.set("attending", result);
    }


    async loadIntoTable() {
        const rsvpInfo = this.dataStore.get("attending");

        let attendanceTable = document.getElementById("attendance__table");

        if (rsvpInfo) {
            let myHtml="";
            myHtml += `<tr>
               <th>Name</th>
               <th>Email</th>
               <th>Status</th>
               <th>Meal Choice</th>
               <th>Plus One Name</th>
               <th>Plus One Meal Choice</th>
                </tr>
                `
            for(let rsvp of rsvpInfo) {
                myHtml += `<tr>
                <td>${rsvp.name}</td>
                <td>${rsvp.email}</td>
                <td>${rsvp.isAttending}</td>
                <td>${rsvp.mealChoice}</td>
                <td>${rsvp.plus1Name}</td>
                <td>${rsvp.plus1MealChoice}</td>
                </tr>
                `
            }
            attendanceTable.innerHTML = myHtml;


        

        }
        else {
            attendanceTable.innerHTML = "<tr><td> no one attending.. </td></tr>"
        }


        

    }

    async mount() {
        this.client = new rsvpClient();
        document.getElementById('inviteNewGuest').addEventListener('click', this.onCreateGuest);
        document.getElementById('removeGuest').addEventListener('click', this.onDeleteGuest);
        this.dataStore.addChangeListener(this.loadIntoTable);
        this.onGetTable();

        


    }
}

const main = async () => {
    const rsvpPage = new RSVPPage();
    rsvpPage.mount();
};


window.addEventListener('DOMContentLoaded', main);


