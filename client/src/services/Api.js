import axios from "axios";

class Api {
    constructor(url) {
        this.adapter = axios.create({
            baseURL: url
        });
    }
    sendRequest = (url, type, payload, headers) => {
        return this.adapter.request({
            url:url,
            method: type,
            data: payload,
            headers,
        });
    };
}

export default new Api("http://localhost:9000/api/v0/");
