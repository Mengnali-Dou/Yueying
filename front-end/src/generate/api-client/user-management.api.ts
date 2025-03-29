import { LoginRequest, LogoutRequest } from "@/generate/request/requests.ts";
import requestApi from "@/generate/api-client/request.api.ts";

/**
 * 用户登录API
 * @param loginRequest 用户登录请求体
 */
export const loginApi = (loginRequest: LoginRequest) => {
	return requestApi({
		url: "/user/login",
		method: "POST",
		headers: { "Content-Type": "application/json" },
		data: loginRequest,
	});
};

/**
 * 退出登录API
 * @param logoutRequest 退出登录请求体
 */
export const logoutApi = (logoutRequest: LogoutRequest) => {
	return requestApi({
		url: "/user/logout",
		method: "POST",
		headers: { "Content-Type": "application/json" },
		data: logoutRequest,
	});
};
