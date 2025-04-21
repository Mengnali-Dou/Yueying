<script setup lang="ts">
// frameworks
import { useI18n } from "vue-i18n";
import { computed, reactive, ref } from "vue";
import { Rule } from "ant-design-vue/es/form";

// api
import { ApiResponse } from "@/generate/response/responses.ts";
import { AddMovieRequest } from "@/generate/request/requests.ts";
import { addMovieApi } from "@/generate/api-client/movie-management.api.ts";

// components
import { message } from "ant-design-vue";

// shared utils
import {
	MovieInfoViewModel,
	MovieTypeInfoViewModel,
} from "@/@types/viewmodel/movie-management/movie-management.viewmodel.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { getDateTime, getTime } from "@/shared/date-format.ts";

const formRef = ref();

// i18n
const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
	movieTypeInfo: MovieTypeInfoViewModel[];
}>();

const emit = defineEmits(["update:dialogVisible", "updateMovieInfo"]);

const dialogVisible = computed({
	get() {
		return props.dialogVisible;
	},
	set(dialogVisible: boolean) {
		emit("update:dialogVisible", dialogVisible);
	},
});

const state = reactive({
	// 添加影片信息
	addMovieInfo: {} as MovieInfoViewModel,
});

// 添加
const addMovie = () => {
	formRef.value
		.validate()
		.then(async () => {
			const addMovieResponse = (await addMovieApi(convertMovieInfoViewModelToAddMovieRequest(state.addMovieInfo)))
				.data as ApiResponse<string>;
			if (addMovieResponse.status === responseStatusConstant.OK) {
				message.success(addMovieResponse.message);
				dialogVisible.value = false;
				emit("updateMovieInfo", addMovieResponse);
			} else {
				message.error(addMovieResponse.message);
			}
		})
		.catch(() => {
			return;
		});
};

const convertMovieInfoViewModelToAddMovieRequest = (movieInfoViewModel: MovieInfoViewModel): AddMovieRequest => {
	return {
		movieName: movieInfoViewModel.movieName,
		movieTypeId: movieInfoViewModel.movieTypeId,
		movieCoverLarge: movieInfoViewModel.movieCoverLarge ?? "",
		movieCoverSmall: movieInfoViewModel.movieCoverSmall ?? "",
		releaseDate: getDateTime(movieInfoViewModel.releaseDate),
		movieDuration: getTime(state.addMovieInfo.movieDuration),
		mainActor: movieInfoViewModel.mainActor ?? "",
		movieProfile: movieInfoViewModel.movieProfile ?? "",
	} as AddMovieRequest;
};

// 表单格式校验
const addMovieFormSchema: Record<string, Rule[]> = {
	movieName: [
		{ required: true, message: t("message.pleaseInputValue", { inputValue: t("form.movieName") }), trigger: "blur" },
	],
	movieTypeId: [
		{ required: true, message: t("message.pleaseInputValue", { inputValue: t("form.movieType") }), trigger: "blur" },
	],
	releaseDate: [
		{ required: true, message: t("message.pleaseInputValue", { inputValue: t("form.releaseDate") }), trigger: "blur" },
	],
	movieDuration: [
		{
			required: true,
			message: t("message.pleaseInputValue", { inputValue: t("form.movieDuration") }),
			trigger: "blur",
		},
	],
	movieProfile: [{ max: 200, trigger: "blur" }],
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('action.addMovie')"
		:ok-text="t('action.confirm')"
		:cancel-text="t('action.cancel')"
		@ok="addMovie"
	>
		<a-form
			ref="formRef"
			:rules="addMovieFormSchema"
			:model="state.addMovieInfo"
			:label-col="{ span: 4 }"
			:wrapper-col="{ span: 18 }"
		>
			<a-form-item name="movieName" :label="t('form.movieName')">
				<a-input v-model:value="state.addMovieInfo.movieName" />
			</a-form-item>
			<a-form-item name="movieTypeId" :label="t('form.movieType')">
				<a-select v-model:value="state.addMovieInfo.movieTypeId">
					<a-select-option v-for="item in props.movieTypeInfo" :value="item.movieTypeId" :key="item.movieTypeId">
						{{ item.movieTypeName }}
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item name="releaseDate" :label="t('form.releaseDate')">
				<a-date-picker v-model:value="state.addMovieInfo.releaseDate" show-time />
			</a-form-item>
			<a-form-item name="movieDuration" :label="t('form.movieDuration')">
				<a-time-picker v-model:value="state.addMovieInfo.movieDuration" />
			</a-form-item>
			<a-form-item name="mainActor" :label="t('form.mainActor')">
				<a-input v-model:value="state.addMovieInfo.mainActor" />
			</a-form-item>
			<a-form-item name="movieProfile" :label="t('form.movieProfile')">
				<a-textarea v-model:value="state.addMovieInfo.movieProfile" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>

<style scoped></style>
