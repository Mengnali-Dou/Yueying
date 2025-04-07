<script lang="ts">
export default {
	name: "MovieTypeManagement",
};
</script>

<script setup lang="ts">
// 表头
import { message, TableColumnsType } from "ant-design-vue";
import { useI18n } from "vue-i18n";
import { MovieTypeInfoViewModel } from "@/@types/viewmodel/movie-management/movie-management.viewmodel.ts";
import { onMounted, reactive } from "vue";
import { searchMovieTypeApi } from "@/generate/api-client/movie-management.api.ts";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { MovieTypeInfoResponse } from "@/generate/response/movie-type-info-response.ts";
import { SearchMovieTypeRequest } from "@/generate/request/search-movie-type-request.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";

// i18n
const { t } = useI18n();

type Key = string | number;

onMounted(async () => {
	await searchMovieTypeInfo();
});

const state = reactive({
	// 影片id
	movieTypeId: 0 as number,
	// 影片类型信息列表
	movieTypeInfoList: [] as MovieTypeInfoViewModel[],
	// 选择影片类型
	selectedMovieTypeInfo: {} as MovieTypeInfoViewModel,
	// 选择行
	selectedKeys: [] as Key[],
});

// 搜索影片类型
const searchMovieTypeInfo = async () => {
	const searchMovieTypeInfoResponse = (await searchMovieTypeApi(convertToSearchMovieTypeRequest(state.movieTypeId)))
		.data as ApiResponse<MovieTypeInfoResponse[]>;
	if (searchMovieTypeInfoResponse.status === responseStatusConstant.OK) {
		searchMovieTypeInfoResponse.data.forEach((item) => {
			state.movieTypeInfoList.push(convertMovieTypeInfoResponseToMovieTypeInfoViewModel(item));
		});
	} else {
		message.error(searchMovieTypeInfoResponse.message);
	}
};

// 修改影片类型
const updateMovieType = () => {};

// 删除影片类型
const deleteMovieType = () => {};

// 选择行
const onSelect = (selectedRowKeys: MovieTypeInfoViewModel) => {
	if (state.selectedKeys[0] === selectedRowKeys.movieTypeId) {
		state.selectedKeys = [] as Key[];
		state.selectedMovieTypeInfo = {} as MovieTypeInfoViewModel;
	} else {
		state.selectedKeys[0] = selectedRowKeys.movieTypeId;
		state.selectedMovieTypeInfo = { ...selectedRowKeys };
	}
};

// 选择所有
const onSelectAll = () => {
	if (state.selectedKeys.length !== 0) {
		state.selectedKeys = [] as Key[];
		state.selectedMovieTypeInfo = {} as MovieTypeInfoViewModel;
	}
};

const convertToSearchMovieTypeRequest = (movieTypeId: number): SearchMovieTypeRequest => {
	return {
		movieTypeId: movieTypeId ?? 0,
	} as SearchMovieTypeRequest;
};

const convertMovieTypeInfoResponseToMovieTypeInfoViewModel = (
	movieTypeInfoResponse: MovieTypeInfoResponse,
): MovieTypeInfoViewModel => {
	return {
		movieTypeId: movieTypeInfoResponse.movieTypeId,
		movieTypeName: movieTypeInfoResponse.movieType,
	} as MovieTypeInfoViewModel;
};

const columns: TableColumnsType = [
	{
		title: t("app.movieTypeId"),
		width: 50,
		dataIndex: "movieTypeId",
		key: "movieTypeId",
	},
	{
		title: t("app.movieType"),
		width: 80,
		dataIndex: "movieTypeName",
		key: "movieType",
	},
	{
		title: "",
		dataIndex: "action",
		key: "action",
		width: 160,
	},
];
</script>

<template>
	<a-table
		:columns="columns"
		:data-source="state.movieTypeInfoList"
		:scroll="{ y: 1000 }"
		:pagination="false"
		:row-selection="{
			selectedRowKeys: state.selectedKeys,
			onSelect: onSelect,
			onSelectAll: onSelectAll,
		}"
		row-key="movieId"
		style="margin-top: 10px"
	>
		<template #bodyCell="{ column, record }">
			<template v-if="column.key === 'action'">
				<a-row :gutter="20">
					<a-col>
						<a-button @click="updateMovieType" type="primary">{{ t("app.updateMovieType") }}</a-button>
					</a-col>
					<a-col>
						<a-button @click="deleteMovieType" type="primary" danger>{{ t("app.delete") }}</a-button>
					</a-col>
				</a-row>
			</template>
		</template>
	</a-table>
</template>

<style scoped></style>
