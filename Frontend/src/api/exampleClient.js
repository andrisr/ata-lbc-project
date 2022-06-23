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
        const methodsToBind = ['clientLoaded', 'getRsvpByAttending', 'getRsvp', 'updateRsvp', 'deleteRsvp'];
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

    /**
     * Gets the concert for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getRsvp(id, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getRsvp", error, errorCallback)
        }
    }

    async getRsvpByAttending(attending, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/${attending}`);
            return response.data;
        } catch (error) {
            this.handleError("getRsvpByAttending", error, errorCallback)
        }
    }

    async updateRsvp(name, errorCallback) {
        try {
            const response = await this.client.post(`rsvp`, {
                name: name
            });
            return response.data;
        } catch (error) {
            this.handleError("updateRsvp", error, errorCallback);
        }
    }

    async deleteRsvp(name, errorCallback) {
        try {
            const response = await this.client.delete(`rsvp`, {
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
