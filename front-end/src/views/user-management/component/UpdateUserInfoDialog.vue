<script setup lang="ts">
// frameworks
import { computed } from "vue";
import { useI18n } from "vue-i18n";

// components
import { message } from "ant-design-vue";

// api
import { ApiResponse } from "@/generate/response/api-response.ts";
import { updateUserInfoApi } from "@/generate/api-client/user-management.api.ts";
import { UpdateUserInfoRequest } from "@/generate/request/update-user-info-request.ts";

// shared utils
import { genderConstant } from "@/constant/gender.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { UserInfoViewModel } from "@/@types/viewmodel/user-management/user-management.viewmodel.ts";

// i18n
const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
	modelValue: UserInfoViewModel;
}>();

const emit = defineEmits(["update:dialogVisible", "updateUserInfo"]);

const dialogVisible = computed({
	get() {
		return props.dialogVisible;
	},
	set(dialogVisible: boolean) {
		emit("update:dialogVisible", dialogVisible);
	},
});

// 修改用户信息
const confirmUpdateUserInfo = async () => {
	const updateUserInfoResponse = (
		await updateUserInfoApi(convertUserInfoViewModelToUpdateUserInfoRequest(props.modelValue))
	).data as ApiResponse<string>;
	if (updateUserInfoResponse.status === responseStatusConstant.OK) {
		message.success(updateUserInfoResponse.message);
		dialogVisible.value = false;
		emit("updateUserInfo", updateUserInfoResponse);
	} else {
		message.error(updateUserInfoResponse.message);
	}
};

const convertUserInfoViewModelToUpdateUserInfoRequest = (
	userInfoViewModel: UserInfoViewModel,
): UpdateUserInfoRequest => {
	return {
		userId: userInfoViewModel.userId,
		userName: userInfoViewModel.userName,
		userAccount: userInfoViewModel.userAccount,
		avatarUrl: userInfoViewModel.avatarUrl,
		gender:
			userInfoViewModel.gender === genderConstant.male.gender ? genderConstant.male.code : genderConstant.female.code,
		phone: userInfoViewModel.phone,
		email: userInfoViewModel.email,
	} as UpdateUserInfoRequest;
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('app.modifyUserInfo')"
		:ok-text="t('app.confirm')"
		:cancel-text="t('app.cancel')"
		@ok="confirmUpdateUserInfo"
	>
		<a-form :model="props.modelValue" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }">
			<a-form-item :label="t('app.userId')">
				<a-input v-model:value="props.modelValue.userId" disabled />
			</a-form-item>
			<a-form-item :label="t('app.account')">
				<a-input v-model:value="props.modelValue.userAccount" disabled />
			</a-form-item>
			<a-form-item :label="t('app.userName')">
				<a-input v-model:value="props.modelValue.userName" />
			</a-form-item>
			<a-form-item :label="$t('app.gender')">
				<a-radio-group v-model:value="props.modelValue.gender">
					<a-radio :value="genderConstant.male.gender">{{ t("app.male") }}</a-radio>
					<a-radio :value="genderConstant.female.gender">{{ t("app.female") }}</a-radio>
				</a-radio-group>
			</a-form-item>
			<a-form-item :label="t('app.phone')">
				<a-input v-model:value="props.modelValue.phone" />
			</a-form-item>
			<a-form-item :label="t('app.email')">
				<a-input v-model:value="props.modelValue.email" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>

<style scoped></style>
