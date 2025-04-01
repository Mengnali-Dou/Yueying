<script lang="ts">
export default {
	name: "MovieManagement",
};
</script>

<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { computed, h, onMounted, reactive } from "vue";
import { SearchOutlined } from "@ant-design/icons-vue";
import { message, TableColumnsType } from "ant-design-vue";
import {
	MovieInfoViewModel,
	SearchMovieInfoViewModel,
} from "@/@types/viewmodel/movie-management/movie-management.viewmodel.ts";
import { SearchMovieInfoRequest } from "@/generate/request/search-movie-info-request.ts";
import { searchMovieInfoApi } from "@/generate/api-client/movie-management.api.ts";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { MovieInfoResponse } from "@/generate/response/movie-info-response.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { getDateTime } from "@/shared/date-format.ts";

// i18n
const { t } = useI18n();

type Key = string | number;

onMounted(async () => {
	await searchMovieInfoList();
});

const state = reactive({
	// 搜索影片信息表单
	searchMovieInfoForm: {} as SearchMovieInfoViewModel,
	// 选择影片信息
	selectMovieInfo: {} as MovieInfoViewModel,
	// 影片信息列表
	movieInfoList: [] as MovieInfoViewModel[],
	// 选择行
	selectedKeys: [] as Key[],
});

// 搜索影片
const searchMovieInfoList = async () => {
	state.movieInfoList = [] as MovieInfoViewModel[];
	const searchMovieInfoResponse = (
		await searchMovieInfoApi(convertSearchMovieInfoViewModelToSearchMovieInfoRequest(state.searchMovieInfoForm))
	).data as ApiResponse<MovieInfoResponse[]>;
	if (searchMovieInfoResponse.status === responseStatusConstant.OK) {
		searchMovieInfoResponse.data.forEach((item) => {
			state.movieInfoList.push(convertMovieInfoResponseToMovieInfoViewModel(item));
		});
	} else {
		message.error(searchMovieInfoResponse.message);
	}
};

// 添加影片
const addMovie = () => {
	console.log("addMovie");
};

// 修改影片信息
const updateMovieInfo = () => {
	console.log("updateMovieInfo");
};

// 删除影片
const deleteMovie = () => {
	console.log("deleteMovie");
};

// 选择行
const onSelect = (selectedRowKeys: MovieInfoViewModel) => {
	if (state.selectedKeys[0] === selectedRowKeys.movieId) {
		state.selectedKeys = [] as Key[];
		state.selectMovieInfo = {} as MovieInfoViewModel;
	} else {
		state.selectedKeys[0] = selectedRowKeys.movieId;
		state.selectMovieInfo = { ...selectedRowKeys };
	}
};

// 选择所有
const onSelectAll = () => {
	if (state.selectedKeys.length !== 0) {
		state.selectedKeys = [] as Key[];
		state.selectMovieInfo = {} as MovieInfoViewModel;
	}
};

const convertSearchMovieInfoViewModelToSearchMovieInfoRequest = (
	searchMovieInfoViewModel: SearchMovieInfoViewModel,
): SearchMovieInfoRequest => {
	return {
		movieName: searchMovieInfoViewModel.movieName ?? "",
		movieType: searchMovieInfoViewModel.movieType ?? "",
	} as SearchMovieInfoRequest;
};

const convertMovieInfoResponseToMovieInfoViewModel = (movieInfoResponse: MovieInfoResponse): MovieInfoViewModel => {
	return {
		movieId: movieInfoResponse.movieId,
		movieName: movieInfoResponse.movieName,
		movieType: movieInfoResponse.movieType,
		movieCoverLarge: movieInfoResponse.movieCoverLarge ?? "",
		movieCoverSmall: movieInfoResponse.movieCoverSmall ?? "",
		releaseDate: getDateTime(movieInfoResponse.releaseDate),
		movieDuration: movieInfoResponse.movieDuration,
		mainActor: movieInfoResponse.mainActor ?? "--",
		movieProfile: movieInfoResponse.movieProfile ?? "--",
	} as MovieInfoViewModel;
};

// 修改、删除影片按钮激活状态
const movieManagementButtonsDisabled = computed(() => {
	return state.selectedKeys.length === 0;
});

// 表头
const columns: TableColumnsType = [
	{
		title: t("app.movieId"),
		width: 50,
		dataIndex: "movieId",
		key: "movieId",
	},
	{
		title: t("app.movieName"),
		width: 120,
		dataIndex: "movieName",
		key: "movieName",
	},
	{
		title: t("app.movieType"),
		dataIndex: "movieType",
		key: "movieType",
		width: 120,
	},
	{ title: t("app.releaseDate"), dataIndex: "releaseDate", key: "releaseDate", width: 100 },
	{ title: t("app.movieDuration"), dataIndex: "movieDuration", key: "movieDuration", width: 50 },
	{ title: t("app.mainActor"), dataIndex: "mainActor", key: "mainActor", width: 100 },
	{
		title: t("app.movieProfile"),
		dataIndex: "movieProfile",
		key: "movieProfile",
		width: 150,
	},
];
</script>

<template>
	<a-space direction="horizontal">
		<a-input :placeholder="t('app.movieName')" />
		<a-input :placeholder="t('app.movieType')" />
		<a-button type="primary" @click="searchMovieInfoList()" :icon="h(SearchOutlined)" />
		<a-button type="primary" @click="addMovie">{{ t("app.addMovie") }}</a-button>
		<a-button type="primary" :disabled="movieManagementButtonsDisabled" @click="updateMovieInfo">
			{{ t("app.updateMovieInfo") }}
		</a-button>
		<a-button type="primary" :disabled="movieManagementButtonsDisabled" @click="deleteMovie" danger>
			{{ t("app.deleteMovie") }}
		</a-button>
	</a-space>
	<a-table
		:columns="columns"
		:data-source="state.movieInfoList"
		:scroll="{ y: 1000 }"
		:pagination="false"
		:row-selection="{
			selectedRowKeys: state.selectedKeys,
			onSelect: onSelect,
			onSelectAll: onSelectAll,
		}"
		row-key="userId"
		style="margin-top: 10px"
	>
		<template #bodyCell="{ column, record }">
			<template v-if="column.key === 'movieProfile'">
				<div>
					{{ record.movieProfile.length >= 50 ? record.movieProfile.slice(0, 50) + "..." : record.movieProfile }}
				</div>
			</template>
		</template>
	</a-table>
</template>

<style scoped></style>
