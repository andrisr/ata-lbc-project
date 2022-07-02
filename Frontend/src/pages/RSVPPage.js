import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import rsvpClient from "../api/rsvpClient";

class RSVPPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onGetTable', 'loadIntoTable'], this);
        this.dataStore = new DataStore();
        
    }

    async onGetTable() {
        let result = await this.client.getRsvpByAttending(true, this.errorHandler);
        this.dataStore.set("attending", result);
    }

    async loadIntoTable() {
        const tableHead = table.querySelector("thead");
        const tableBody = table.querySelector("tbody");

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
                <td>${rsvp.attending}</td>
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
        this.dataStore.addChangeListener(this.loadIntoTable);
        this.onGetTable();

    }
}

const main = async () => {
    const rsvpPage = new RSVPPage();
    rsvpPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

