<script setup lang="ts">
// frameworks
import { useI18n } from "vue-i18n";
import { computed, reactive, ref } from "vue";

// components
import { Rule } from "ant-design-vue/es/form";
import { message } from "ant-design-vue";

// api
import { RegisterRequest } from "@/generate/request/requests.ts";
import { ApiResponse } from "@/generate/response/responses.ts";
import { registerApi } from "@/generate/api-client/user-management.api.ts";

// shared utils
import { genderConstant } from "@/constant/gender.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { emailCheck, passwordCheck, phoneCheck, userAccountCheck } from "@/shared/form-schema.ts";
import { RegisterViewModel } from "@/@types/viewmodel/user-management/user-management.viewmodel.ts";

const formRef = ref();

// i18n
const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
}>();

const emits = defineEmits(["update:dialogVisible", "updateUserInfo"]);

const dialogVisible = computed({
	get() {
		return props.dialogVisible;
	},
	set(dialogVisible: boolean) {
		emits("update:dialogVisible", dialogVisible);
	},
});

const state = reactive({
	// 注册用户信息
	registerUserInfo: {
		gender: 0 as number,
	} as RegisterViewModel,
});

// 注册
const userRegister = async () => {
	formRef.value
		.validate()
		.then(async () => {
			const registerResponse = (await registerApi(convertRegisterViewModelToRegisterRequest(state.registerUserInfo)))
				.data as ApiResponse<string>;
			if (registerResponse.status === responseStatusConstant.OK) {
				message.success(registerResponse.message);
				dialogVisible.value = false;
				emits("updateUserInfo");
			} else {
				message.error(registerResponse.message);
			}
		})
		.catch(() => {
			return;
		});
};

// 注册表单格式校验
const registerSchema: Record<string, Rule[]> = {
	userAccount: [{ required: true, validator: userAccountCheck, trigger: "blur" }],
	userName: [
		{
			required: true,
			message: t("message.pleaseInputValue", { inputValue: t("form.userName") }),
			trigger: "blur",
		},
	],
	password: [{ required: true, validator: passwordCheck, trigger: "change" }],
	gender: [
		{
			required: true,
			message: t("message.pleaseSelectGender"),
			trigger: "blur",
		},
	],
	phone: [{ validator: phoneCheck, trigger: "blur" }],
	email: [{ validator: emailCheck, trigger: "blur" }],
};

const convertRegisterViewModelToRegisterRequest = (registerViewModel: RegisterViewModel): RegisterRequest => {
	return {
		userAccount: registerViewModel.userAccount,
		userName: registerViewModel.userName,
		password: registerViewModel.password,
		gender: registerViewModel.gender,
		avatar: registerViewModel.avatar ?? "",
		phone: registerViewModel.phone ?? "",
		email: registerViewModel.email ?? "",
	} as RegisterRequest;
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('action.register')"
		:ok-text="t('action.confirm')"
		:cancel-text="t('action.cancel')"
		@ok="userRegister"
	>
		<a-form
			ref="formRef"
			:rules="registerSchema"
			:model="state.registerUserInfo"
			:label-col="{ span: 4 }"
			:wrapper-col="{ span: 18 }"
		>
			<a-form-item name="userAccount" :label="t('form.account')">
				<a-input v-model:value="state.registerUserInfo.userAccount" :maxlength="24" />
			</a-form-item>
			<a-form-item name="userName" :label="t('form.userName')">
				<a-input v-model:value="state.registerUserInfo.userName" />
			</a-form-item>
			<a-form-item name="password" :label="t('form.password')">
				<a-input v-model:value="state.registerUserInfo.password" />
			</a-form-item>
			<a-form-item name="gender" :label="t('form.gender')">
				<a-radio-group v-model:value="state.registerUserInfo.gender">
					<a-radio :value="genderConstant.male.code">
						{{ t("data.male") }}
					</a-radio>
					<a-radio :value="genderConstant.female.code">
						{{ t("data.female") }}
					</a-radio>
				</a-radio-group>
			</a-form-item>
			<a-form-item name="phone" :label="t('form.phone')">
				<a-input v-model:value="state.registerUserInfo.phone" :maxlength="11" />
			</a-form-item>
			<a-form-item name="email" :label="t('form.email')">
				<a-input v-model:value="state.registerUserInfo.email" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>

<style scoped></style>
