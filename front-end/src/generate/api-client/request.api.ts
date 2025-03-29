import axios from "axios";
import { httpRequestMessageConstant } from "@/constant/http-request-message-constant.ts";
import router from "@/router";

const requestApi = axios.create({
	baseURL: "/api",
	timeout: 5000,
});

requestApi.interceptors.request.use((config) => {
	return config;
});

requestApi.interceptors.response.use(
	(response) => {
		return response;
	},
	(error) => {
		if (error.response.data.message === httpRequestMessageConstant.userNotLoggedIn) {
			router.push("/login").then(() => {
				return;
			});
		}
		return error.response.data;
	},
);

export default requestApi;
