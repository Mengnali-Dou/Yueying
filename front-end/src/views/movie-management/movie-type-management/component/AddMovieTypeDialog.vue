<script setup lang="ts">
import { MovieTypeInfoViewModel } from "@/@types/viewmodel/movie-management/movie-type-management/movie-type-management.viewmodel.ts";
import { useI18n } from "vue-i18n";
import { computed, reactive, ref } from "vue";
import { Rule } from "ant-design-vue/es/form";
import { AddMovieTypeRequest } from "@/generate/request/add-movie-type-request.ts";
import { addMovieTypeApi } from "@/generate/api-client/movie-management.api.ts";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { message } from "ant-design-vue";

const formRef = ref();

// i18n
const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
}>();

const emit = defineEmits(["update:dialogVisible", "updateMovieType"]);

const dialogVisible = computed({
	get() {
		return props.dialogVisible;
	},
	set(dialogVisible) {
		emit("update:dialogVisible", dialogVisible);
	},
});

const state = reactive({
	// 添加影片类型
	addMovieType: {} as MovieTypeInfoViewModel,
});

// 添加影片类型
const addMovieType = async () => {
	formRef.value
		.validate()
		.then(async () => {
			const addMovieTypeResponse = (await addMovieTypeApi(convertToAddMovieTypeRequest(state.addMovieType.movieType)))
				.data as ApiResponse<string>;
			if (addMovieTypeResponse.status === responseStatusConstant.OK) {
				message.success(addMovieTypeResponse.message);
				emit("updateMovieType", addMovieTypeResponse);
				dialogVisible.value = false;
			} else {
				message.error(addMovieTypeResponse.message);
			}
		})
		.catch(() => {
			return;
		});
};

const convertToAddMovieTypeRequest = (movieType: string): AddMovieTypeRequest => {
	return {
		movieTypeName: movieType,
	} as AddMovieTypeRequest;
};

// 表单格式校验
const addMovieTypeFormSchema: Record<string, Rule[]> = {
	movieType: [
		{ required: true, message: t("message.pleaseInputValue", { inputValue: t("app.movieType") }), trigger: "blur" },
	],
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('app.addMovieType')"
		:ok-text="t('app.confirm')"
		:cancel-text="t('app.cancel')"
		@ok="addMovieType"
	>
		<a-form
			ref="formRef"
			:rules="addMovieTypeFormSchema"
			:model="state.addMovieType"
			:label-col="{ span: 4 }"
			:wrapper-col="{ span: 18 }"
		>
			<a-form-item name="movieType" :label="t('app.movieType')">
				<a-input v-model:value="state.addMovieType.movieType" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>

<style scoped></style>
