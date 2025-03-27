import axios from "axios";

const requestApi = axios.create({
	baseURL: "/api",
	timeout: 5000,
});

requestApi.interceptors.request.use((config) => {
	return config;
});

requestApi.interceptors.response.use((response) => {
	return response.data;
});

export default requestApi;
