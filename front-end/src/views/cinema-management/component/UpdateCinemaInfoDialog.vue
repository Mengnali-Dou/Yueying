<script setup lang="ts">
import { computed, ref } from "vue";
import { useI18n } from "vue-i18n";
import { Rule } from "ant-design-vue/es/form";
import { phoneCheck } from "@/shared/form-schema.ts";
import { CinemaInfoViewModel } from "@/@types/viewmodel/cinema-management/cinema-management.viewmodel.ts";
import { updateCinemaInfoApi } from "@/generate/api-client/cinema-management.api.ts";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { UpdateCinemaInfoRequest } from "@/generate/request/update-cinema-info-request.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { message } from "ant-design-vue";

const formRef = ref();

// i18n
const { t } = useI18n();

const props = defineProps<{
	modelValue: CinemaInfoViewModel;
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

// 修改影院信息
const updateCinemaInfo = () => {
	formRef.value
		.validate()
		.then(async () => {
			const updateCinemaInfoResponse = (
				await updateCinemaInfoApi(convertCinemaInfoViewModelToUpdateCinemaInfoRequest(props.modelValue))
			).data as ApiResponse<string>;
			if (updateCinemaInfoResponse.status === responseStatusConstant.OK) {
				message.success(updateCinemaInfoResponse.message);
				dialogVisible.value = false;
				emit("updateCinemaInfo");
			} else {
				message.error(updateCinemaInfoResponse.message);
			}
		})
		.catch(() => {
			return;
		});
};

const convertCinemaInfoViewModelToUpdateCinemaInfoRequest = (
	cinemaInfoViewModel: CinemaInfoViewModel,
): UpdateCinemaInfoRequest => {
	return {
		cinemaId: cinemaInfoViewModel.cinemaId,
		cinemaName: cinemaInfoViewModel.cinemaName,
		cinemaAddress: cinemaInfoViewModel.cinemaAddress,
		cinemaService: cinemaInfoViewModel.cinemaService ?? "",
		cinemaPhone: cinemaInfoViewModel.cinemaPhone ?? "",
		cinemaProfile: cinemaInfoViewModel.cinemaProfile ?? "",
		cinemaTraffic: cinemaInfoViewModel.cinemaTraffic ?? "",
	} as UpdateCinemaInfoRequest;
};

// 表单格式校验
const updateCinemaInfoSchema: Record<string, Rule[]> = {
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
		@ok="updateCinemaInfo"
	>
		<a-form
			ref="formRef"
			:rules="updateCinemaInfoSchema"
			:model="props.modelValue"
			:label-col="{ span: 4 }"
			:wrapper-col="{ span: 18 }"
		>
			<a-form-item name="cinemaName" :label="t('form.cinemaName')">
				<a-input v-model:value="props.modelValue.cinemaName" />
			</a-form-item>
			<a-form-item name="cinemaAddress" :label="t('form.cinemaAddress')">
				<a-input v-model:value="props.modelValue.cinemaAddress" />
			</a-form-item>
			<a-form-item name="cinemaProfile" :label="t('form.cinemaProfile')">
				<a-input v-model:value="props.modelValue.cinemaProfile" />
			</a-form-item>
			<a-form-item name="cinemaService" :label="t('form.cinemaService')">
				<a-input v-model:value="props.modelValue.cinemaService" />
			</a-form-item>
			<a-form-item name="cinemaPhone" :label="t('form.cinemaPhone')">
				<a-input v-model:value="props.modelValue.cinemaPhone" />
			</a-form-item>
			<a-form-item name="cinemaTraffic" :label="t('form.cinemaTraffic')">
				<a-input v-model:value="props.modelValue.cinemaTraffic" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>
