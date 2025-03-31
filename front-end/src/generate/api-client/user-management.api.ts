import {
	LoginRequest,
	LogoutRequest,
	RegisterRequest,
	SearchUserRequest,
	UpdateUserInfoRequest,
} from "@/generate/request/requests.ts";
import requestApi from "@/generate/api-client/request.api.ts";

/**
 * 注册API
 * @param registerRequest 注册请求体
 */
export const registerApi = (registerRequest: RegisterRequest) => {
	return requestApi({
		url: "/user/register",
		method: "POST",
		headers: { "Content-Type": "application/json" },
		data: registerRequest,
	});
};

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

/**
 * 搜索用户API
 * @param searchUserRequest 搜索用户请求体
 */
export const searchUserInfoApi = (searchUserRequest: SearchUserRequest) => {
	return requestApi({
		url: `/user/search?userAccount=${searchUserRequest.userAccount}&userName=${searchUserRequest.userName}`,
		method: "GET",
		headers: { "Content-Type": "application/json" },
	});
};

/**
 * 修改用户信息API
 * @param updateUserInfoRequest 修改用户信息请求体
 */
export const updateUserInfoApi = (updateUserInfoRequest: UpdateUserInfoRequest) => {
	return requestApi({
		url: "/user/update",
		method: "PUT",
		headers: { "Content-Type": "application/json" },
		data: updateUserInfoRequest,
	});
};

/**
 * 删除用户
 * @param userId 用户id
 */
export const deleteUserApi = (userId: number) => {
	return requestApi({
		url: `/user/delete?userId=${userId}`,
		method: "DELETE",
		headers: { "Content-Type": "application/json" },
	});
};
