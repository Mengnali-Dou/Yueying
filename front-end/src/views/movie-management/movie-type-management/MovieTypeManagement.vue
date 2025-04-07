<script lang="ts">
export default {
	name: "MovieTypeManagement",
};
</script>

<script setup lang="ts">
// frameworks
import { useI18n } from "vue-i18n";
import { onMounted, reactive } from "vue";

// components
import { message, TableColumnsType } from "ant-design-vue";

// api
import { ApiResponse, MovieTypeInfoResponse } from "@/generate/response/responses.ts";
import { SearchMovieTypeRequest, UpdateMovieTypeRequest } from "@/generate/request/requests.ts";
import { searchMovieTypeApi, updateMovieTypeApi } from "@/generate/api-client/movie-management.api.ts";

// shared utils
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import { MovieTypeManagementViewModel } from "@/@types/viewmodel/movie-management/movie-type-management/movie-type-management.viewmodel.ts";

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
	movieTypeInfoList: [] as MovieTypeManagementViewModel[],
	// 选择影片类型
	selectedMovieTypeInfo: {} as MovieTypeManagementViewModel,
	// 选择行
	selectedKeys: [] as Key[],
});

// 搜索影片类型
const searchMovieTypeInfo = async () => {
	const searchMovieTypeInfoResponse = (await searchMovieTypeApi(convertToSearchMovieTypeRequest(state.movieTypeId)))
		.data as ApiResponse<MovieTypeInfoResponse[]>;
	if (searchMovieTypeInfoResponse.status === responseStatusConstant.OK) {
		state.movieTypeInfoList = [] as MovieTypeManagementViewModel[];
		searchMovieTypeInfoResponse.data.forEach((item) => {
			state.movieTypeInfoList.push(convertMovieTypeInfoResponseToMovieTypeInfoViewModel(item));
		});
	} else {
		message.error(searchMovieTypeInfoResponse.message);
	}
};

// 修改影片类型
const updateMovieType = async (movieTypeId: number, movieType: string, index: number) => {
	if (!state.movieTypeInfoList[index].isUpdate) {
		state.movieTypeInfoList[index].isUpdate = true;
	} else {
		const updateMovieTypeResponse = (await updateMovieTypeApi(convertToUpdateMovieTypeRequest(movieTypeId, movieType)))
			.data as ApiResponse<string>;
		if (updateMovieTypeResponse.status === responseStatusConstant.OK) {
			message.success(updateMovieTypeResponse.message);
			await searchMovieTypeInfo();
		} else {
			message.error(updateMovieTypeResponse.message);
		}
	}
};

// 删除影片类型
const deleteMovieType = () => {};

// 选择行
const onSelect = (selectedRowKeys: MovieTypeManagementViewModel) => {
	if (state.selectedKeys[0] === selectedRowKeys.movieTypeId) {
		state.selectedKeys = [] as Key[];
		state.selectedMovieTypeInfo = {} as MovieTypeManagementViewModel;
	} else {
		state.selectedKeys[0] = selectedRowKeys.movieTypeId;
		state.selectedMovieTypeInfo = { ...selectedRowKeys };
	}
};

// 选择所有
const onSelectAll = () => {
	if (state.selectedKeys.length !== 0) {
		state.selectedKeys = [] as Key[];
		state.selectedMovieTypeInfo = {} as MovieTypeManagementViewModel;
	}
};

const convertToSearchMovieTypeRequest = (movieTypeId: number): SearchMovieTypeRequest => {
	return {
		movieTypeId: movieTypeId ?? 0,
	} as SearchMovieTypeRequest;
};

const convertMovieTypeInfoResponseToMovieTypeInfoViewModel = (
	movieTypeInfoResponse: MovieTypeInfoResponse,
): MovieTypeManagementViewModel => {
	return {
		movieTypeId: movieTypeInfoResponse.movieTypeId,
		movieType: movieTypeInfoResponse.movieType,
		isUpdate: false,
	} as MovieTypeManagementViewModel;
};

const convertToUpdateMovieTypeRequest = (movieTypeId: number, movieType: string): UpdateMovieTypeRequest => {
	return {
		movieTypeId: movieTypeId,
		movieTypeName: movieType,
	} as UpdateMovieTypeRequest;
};

// 表头
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
		dataIndex: "movieType",
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
		<template #bodyCell="{ column, index, record }">
			<template v-if="column.key === 'movieType'">
				<span v-if="!record.isUpdate">{{ record.movieType }}</span>
				<a-input
					v-model:value="record.movieType"
					:placeholder="t('app.movieType')"
					v-if="record.isUpdate"
					style="width: 200px"
				/>
			</template>
			<template v-if="column.key === 'action'">
				<a-row :gutter="20">
					<a-col>
						<a-button
							@click="updateMovieType(record.movieTypeId, record.movieType, index)"
							:disabled="record.movieType.length === 0"
							type="primary"
						>
							{{ record.isUpdate ? t("app.confirmUpdate") : t("app.updateMovieType") }}
						</a-button>
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
