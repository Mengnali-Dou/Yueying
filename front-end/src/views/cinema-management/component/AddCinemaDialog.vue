<script setup lang="ts">
import { computed, reactive, ref } from "vue";
import { useI18n } from "vue-i18n";
import { CinemaInfoViewModel } from "@/@types/viewmodel/cinema-management/cinema-management.viewmodel.ts";
import { Rule } from "ant-design-vue/es/form";
import { phoneCheck } from "@/shared/form-schema.ts";
import { addCinemaApi } from "@/generate/api-client/cinema-management.api.ts";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { AddCinemaRequest } from "@/generate/request/add-cinema-request.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { message } from "ant-design-vue";

const formRef = ref();

// i18n
const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
}>();

const emit = defineEmits(["update:dialogVisible", "updateCinemaInfo"]);

const dialogVisible = computed({
	get() {
		return props.dialogVisible;
	},
	set(dialogVisible: boolean) {
		emit("update:dialogVisible", dialogVisible);
	},
});

const state = reactive({
	// 添加影院信息
	addCinemaInfo: {} as CinemaInfoViewModel,
});

// 添加影院
const addCinema = () => {
	formRef.value
		.validate()
		.then(async () => {
			const addCinemaResponse = (await addCinemaApi(convertCinemaInfoViewModelToAddCinemaRequest(state.addCinemaInfo)))
				.data as ApiResponse<string>;
			if (addCinemaResponse.status === responseStatusConstant.OK) {
				message.success(addCinemaResponse.message);
				dialogVisible.value = false;
				emit("updateCinemaInfo");
			} else {
				message.error(addCinemaResponse.message);
			}
		})
		.catch(() => {
			return;
		});
};

const convertCinemaInfoViewModelToAddCinemaRequest = (cinemaInfoViewModel: CinemaInfoViewModel): AddCinemaRequest => {
	return {
		cinemaName: cinemaInfoViewModel.cinemaName,
		cinemaAddress: cinemaInfoViewModel.cinemaAddress,
		cinemaService: cinemaInfoViewModel.cinemaService ?? "",
		cinemaPhone: cinemaInfoViewModel.cinemaPhone ?? "",
		cinemaProfile: cinemaInfoViewModel.cinemaProfile ?? "",
		cinemaTraffic: cinemaInfoViewModel.cinemaTraffic ?? "",
	} as AddCinemaRequest;
};

// 表单格式校验
const addCinemaFormSchema: Record<string, Rule[]> = {
	cinemaName: [
		{ required: true, message: t("message.pleaseInputValue", { inputValue: t("form.cinemaName") }), trigger: "blur" },
	],
	cinemaAddress: [
		{
			required: true,
			message: t("message.pleaseInputValue", { inputValue: t("form.cinemaAddress") }),
			trigger: "blur",
		},
	],
	cinemaPhone: [{ validator: phoneCheck, trigger: "blur" }],
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('action.addCinema')"
		:ok-text="t('action.confirm')"
		:cancel-text="t('action.cancel')"
		@ok="addCinema"
	>
		<a-form
			ref="formRef"
			:rules="addCinemaFormSchema"
			:model="state.addCinemaInfo"
			:label-col="{ span: 4 }"
			:wrapper-col="{ span: 18 }"
		>
			<a-form-item name="cinemaName" :label="t('form.cinemaName')">
				<a-input v-model:value="state.addCinemaInfo.cinemaName" />
			</a-form-item>
			<a-form-item name="cinemaAddress" :label="t('form.cinemaAddress')">
				<a-input v-model:value="state.addCinemaInfo.cinemaAddress" />
			</a-form-item>
			<a-form-item name="cinemaProfile" :label="t('form.cinemaProfile')">
				<a-input v-model:value="state.addCinemaInfo.cinemaProfile" />
			</a-form-item>
			<a-form-item name="cinemaService" :label="t('form.cinemaService')">
				<a-input v-model:value="state.addCinemaInfo.cinemaService" />
			</a-form-item>
			<a-form-item name="cinemaPhone" :label="t('form.cinemaPhone')">
				<a-input v-model:value="state.addCinemaInfo.cinemaPhone" />
			</a-form-item>
			<a-form-item name="cinemaTraffic" :label="t('form.cinemaTraffic')">
				<a-input v-model:value="state.addCinemaInfo.cinemaTraffic" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>

<style scoped></style>
