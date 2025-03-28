import { defineStore } from "pinia";
import { UserInfoViewModel } from "@/@types/viewmodel/login.viewmodel.ts";

export const useUserInfoStore = defineStore("UserInfoStore", {
	state: () => ({
		userInfo: {} as UserInfoViewModel,
	}),
	actions: {
		setUserInfo(userInfo: UserInfoViewModel) {
			this.userInfo = { ...userInfo };
		},
	},
	persist: {},
});
