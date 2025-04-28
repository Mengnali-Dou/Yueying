<script setup lang="ts">
// frameworks
import { computed } from "vue";
import { useI18n } from "vue-i18n";

// components
import { message } from "ant-design-vue";

// api
import { ApiResponse } from "@/generate/response/api-response.ts";
import { updateMovieInfoApi } from "@/generate/api-client/movie-management.api.ts";
import { UpdateMovieInfoRequest } from "@/generate/request/update-movie-info-request.ts";

// shared utils
import {
	MovieInfoViewModel,
	MovieTypeInfoViewModel,
} from "@/@types/viewmodel/movie-management/movie-management.viewmodel.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";

const { t } = useI18n();

const props = defineProps<{
	dialogVisible: boolean;
	modelValue: MovieInfoViewModel;
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

// 添加影片
const updateMovieInfo = async () => {
	const updateMovieInfoResponse = (await updateMovieInfoApi(convertToDto(props.modelValue)))
		.data as ApiResponse<string>;
	if (updateMovieInfoResponse.status === responseStatusConstant.OK) {
		message.success(updateMovieInfoResponse.message);
		dialogVisible.value = false;
		emit("updateMovieInfo");
	} else {
		message.error(updateMovieInfoResponse.message);
	}
};

const convertToDto = (movieInfoViewModel: MovieInfoViewModel): UpdateMovieInfoRequest => {
	return {
		movieId: movieInfoViewModel.movieId,
		movieName: movieInfoViewModel.movieName,
		movieTypeId: movieInfoViewModel.movieTypeId,
		movieCoverLarge: movieInfoViewModel.movieCoverLarge ?? "",
		movieCoverSmall: movieInfoViewModel.movieCoverSmall ?? "",
		releaseDate: movieInfoViewModel.releaseDate,
		movieDuration: movieInfoViewModel.movieDuration,
		mainActor: movieInfoViewModel.mainActor ?? "",
		movieProfile: movieInfoViewModel.movieProfile ?? "",
	} as UpdateMovieInfoRequest;
};
</script>

<template>
	<a-modal
		v-model:open="dialogVisible"
		:title="t('action.addMovie')"
		:ok-text="t('action.confirm')"
		:cancel-text="t('action.cancel')"
		@ok="updateMovieInfo"
	>
		<a-form ref="formRef" :model="props.modelValue" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }">
			<a-form-item name="movieName" :label="t('form.movieName')">
				<a-input v-model:value="props.modelValue.movieName" />
			</a-form-item>
			<a-form-item name="movieTypeId" :label="t('form.movieType')">
				<a-select v-model:value="props.modelValue.movieTypeId">
					<a-select-option v-for="item in props.movieTypeInfo" :value="item.movieTypeId" :key="item.movieTypeId">
						{{ item.movieTypeName }}
					</a-select-option>
				</a-select>
			</a-form-item>
			<a-form-item name="releaseDate" :label="t('form.releaseDate')">
				<a-input v-model:value="props.modelValue.releaseDate" />
			</a-form-item>
			<a-form-item name="movieDuration" :label="t('form.movieDuration')">
				<a-input v-model:value="props.modelValue.movieDuration" />
			</a-form-item>
			<a-form-item name="mainActor" :label="t('form.mainActor')">
				<a-input v-model:value="props.modelValue.mainActor" />
			</a-form-item>
			<a-form-item name="movieProfile" :label="t('form.movieProfile')">
				<a-textarea v-model:value="props.modelValue.movieProfile" />
			</a-form-item>
		</a-form>
	</a-modal>
</template>

<style scoped></style>
