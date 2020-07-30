import axios from "axios";

const registerUser = (data) => {
    return axios.post( "/api/versions/1/auth/signup",data);
};

const login = (data) => {
    return axios
        .post( "/api/versions/1/auth/signin",data )
        .then((response) => {
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

export default {
    registerUser,
    login,
    logout,
    getCurrentUser
};
