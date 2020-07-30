import axios from "axios";
import authHeader from "./AuthHeader";

const getPublicContent = () => {
    return axios.get("/api/versions/1/test/all");
};

const getUserBoard = () => {
    return axios.get( "/api/versions/1/test/user", { headers: authHeader() });
};

const getModeratorBoard = () => {
    return axios.get("/api/versions/1/test/mod", { headers: authHeader() });
};

const getAdminBoard = () => {
    return axios.get( "/api/versions/1/test/admin", { headers: authHeader() });
};

export default {
    getPublicContent,
    getUserBoard,
    getModeratorBoard,
    getAdminBoard,
};