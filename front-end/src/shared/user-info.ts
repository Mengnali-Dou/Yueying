import { UserInfoResponse } from "@/generate/response/responses.ts";
import { useUserInfoStore } from "@/store/userStore.ts";

const userInfoStore = useUserInfoStore();

/**
 * 存储用户信息
 */
export const saveUserInfo = (userInfo: UserInfoResponse) => {
	userInfoStore.setUserInfo(convertUserInfoViewModelToUserInfoResponse(userInfo));
	sessionStorage.setItem("userId", userInfo.userId.toString());
};

const convertUserInfoViewModelToUserInfoResponse = (userInfoResponse: UserInfoResponse): UserInfoResponse => {
	return { ...userInfoResponse } as UserInfoResponse;
};
