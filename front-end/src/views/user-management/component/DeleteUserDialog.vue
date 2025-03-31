<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { UserInfoViewModel } from "@/@types/viewmodel/user-management/user-management.viewmodel.ts";
import { computed } from "vue";
import { deleteUserApi } from "@/generate/api-client/user-management.api.ts";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { message } from "ant-design-vue";

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
	set(dialogVisible) {
		emit("update:dialogVisible", dialogVisible);
	},
});

// 删除用户
const deleteUser = async () => {
	const deleteUserResponse = (await deleteUserApi(props.modelValue.userId)).data as ApiResponse<string>;
	if (deleteUserResponse.status === responseStatusConstant.OK) {
		message.success(deleteUserResponse.message);
		dialogVisible.value = false;
		emit("updateUserInfo");
	} else {
		message.error(deleteUserResponse.message);
	}
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('app.deleteUser')"
		:ok-text="t('app.confirm')"
		:cancel-text="t('app.cancel')"
		@ok="deleteUser"
	>
		{{
			t("message.confirmThatYouWantToDeleteTheUser", {
				userName: props.modelValue.userName,
			})
		}}
	</a-modal>
</template>

<style scoped></style>
