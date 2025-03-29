import { UserInfoResponse } from "@/generate/response/responses.ts";
import { useUserInfoStore } from "@/store/userStore.ts";
import { storeToRefs } from "pinia";

const userInfoStore = useUserInfoStore();
const { userInfo } = storeToRefs(userInfoStore);

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

export const clearUserInfo = () => {
	userInfoStore.$reset();
	sessionStorage.removeItem("userId");
};

export const getUserId = (): number => {
	return userInfo.value.userId;
};

export const getAvatarUrl = (): string => {
	return userInfo.value.avatarUrl === ""
		? "https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202404261830168.png"
		: userInfo.value.avatarUrl;
};
