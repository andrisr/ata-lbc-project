import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class rsvpClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getAllRsvps', 'getRsvp', 'updateRsvp',
            'deleteRsvp', 'createRsvp', 'brideLogin'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getRsvp(name, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/${name}`);
            return response.data;
        } catch (error) {
            this.handleError("getRsvp", error, errorCallback)
        }
    }

    async getAllRsvps(attending, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllRsvps", error, errorCallback)
        }
    }

    async getRsvpByAttending(attending, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/attending/${attending}`);
            return response.data;
        } catch (error) {
            this.handleError("getRsvpByAttending", error, errorCallback)
        }
    }

    async createRsvp(name, email, errorCallback) {
        try {
            const response = await this.client.post(`rsvp`, {
                name: name,
                email: email
            });
            return response.data;
        } catch (error) {
            this.handleError("createRsvp", error, errorCallback)
        }
    }

    async updateRsvp(name, email, isAttending, mealChoice, plus1Name, plus1MealChoice, errorCallback) {
        try {
            const response = await this.client.put(`rsvp`, {
                name: name,
                email: email,
                isAttending: isAttending,
                mealChoice: mealChoice,
                plus1Name: plus1Name,
                plus1MealChoice: plus1MealChoice
            });
            return response.data;
        } catch (error) {
            this.handleError("updateRsvp", error, errorCallback);
        }
    }

    async deleteRsvp(name, errorCallback) {
        try {
            const response = await this.client.delete(`rsvp/${name}`, {
                name: name
            });
            return response.data;
        } catch (error) {
            this.handleError("deleteRsvp", error, errorCallback);
        }
    }

    // brideLogin is not yet a working feature
    async brideLogin(username, password, errorCallback) {
        try {
            const response = await this.client.post(`login`, {
                username: username,
                password: password
            });
            return response.data;
        } catch (error) {
            this.handleError("brideLogin", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param method
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
