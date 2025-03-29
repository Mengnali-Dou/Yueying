<script setup lang="ts">
// frameworks
import { useI18n } from "vue-i18n";
import { computed, reactive, ref } from "vue";
import type { Rule } from "ant-design-vue/es/form";

// components
import { message } from "ant-design-vue";
import { UserOutlined, LockOutlined } from "@ant-design/icons-vue";
import LoginUserInfoViewModel from "@/@types/viewmodel/login.viewmodel.ts";

// shared utils
import { ApiResponse, UserInfoResponse } from "@/generate/response/responses.ts";
import { LoginRequest } from "@/generate/request/login-request.ts";
import { loginApi } from "@/generate/api-client/user-management.api.ts";
import { passwordCheck, userAccountCheck } from "@/shared/form-schema.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { saveUserInfo } from "@/shared/user-info.ts";
import { userStatusConstant } from "@/constant/user-status-constant.ts";
import router from "@/router";

const formRef = ref();

// i18n
const { t } = useI18n();

const state = reactive({
	// 用户登录信息
	userInfo: {
		userAccount: "" as string,
		password: "" as string,
	} as LoginUserInfoViewModel,
	// 登录按钮激活状态
	loginButtonDisabled: true as boolean,
});

// 登录表单校验
const loginSchema: Record<string, Rule[]> = {
	userAccount: [{ required: true, validator: userAccountCheck, trigger: "change" }],
	password: [{ required: true, validator: passwordCheck, trigger: "change" }],
};

// 登录按钮激活状态
const loginButtonDisabled = computed(() => {
	return !(state.userInfo.userAccount && state.userInfo.password);
});

// 登录
const login = async () => {
	formRef.value
		.validate()
		.then(async () => {
			const responseData = (await loginApi(convertLoginUserInfoViewModelToLoginRequest(state.userInfo)))
				.data as ApiResponse<UserInfoResponse>;
			if (responseData.status === responseStatusConstant.OK) {
				saveUserInfo(responseData.data);
				message.success(responseData.message);
				if (responseData.data.userStatus === userStatusConstant.passwordReset.code) {
					// TODO: 重置密码页面完成后添加路由
					await router.push("");
				} else {
					await router.push("/");
				}
			} else {
				message.error(responseData.message);
			}
		})
		.catch(() => {
			return;
		});
};

const convertLoginUserInfoViewModelToLoginRequest = (loginUserInfoViewModel: LoginUserInfoViewModel): LoginRequest => {
	return {
		userAccount: loginUserInfoViewModel.userAccount,
		password: loginUserInfoViewModel.password,
	} as LoginRequest;
};
</script>

<template>
	<div id="login">
		<a-flex style="width: 100%; height: 90%" align="center" justify="center">
			<a-card id="login-card">
				<div class="login-page-title">
					{{ t("app.loginTitle") }}
				</div>
				<a-form ref="formRef" :rules="loginSchema" :model="state.userInfo" class="login-form">
					<a-form-item name="userAccount">
						<a-input v-model:value="state.userInfo.userAccount" :placeholder="t('app.account')" size="large" autofocus>
							<template #prefix>
								<UserOutlined />
							</template>
						</a-input>
					</a-form-item>
					<a-form-item name="password">
						<a-input-password v-model:value="state.userInfo.password" :placeholder="t('app.password')" size="large">
							<template #prefix>
								<LockOutlined />
							</template>
						</a-input-password>
					</a-form-item>
					<a-form-item>
						<a-button style="width: 100%" type="primary" size="large" :disabled="loginButtonDisabled" @click="login">
							{{ t("app.login") }}
						</a-button>
					</a-form-item>
				</a-form>
			</a-card>
		</a-flex>
	</div>
</template>
