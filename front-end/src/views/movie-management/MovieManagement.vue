<script lang="ts">
export default {
	name: "MovieManagement",
};
</script>

<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { computed, h, reactive } from "vue";
import { SearchOutlined } from "@ant-design/icons-vue";
import { TableColumnsType } from "ant-design-vue";
import { MovieInfoViewModel } from "@/@types/viewmodel/movie-management/movie-management.viewmodel.ts";

// i18n
const { t } = useI18n();

type Key = string | number;

const state = reactive({
	// 选择影片信息
	selectMovieInfo: {} as MovieInfoViewModel,
	// 影片信息列表
	movieInfoList: [
		{
			movieId: 12341234,
			movieName: "影片名影片名影片名影片名影片名",
			movieType: "影片类型,影片类型,影片类型,影片类型",
			releaseDate: "2025-04-01 00:00:00",
			movieDuration: "888分钟",
			mainActor: "演员A,演员B,演员C",
			movieProfile: "影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介影片简介",
		},
	] as MovieInfoViewModel[],
	// 选择行
	selectedKeys: [] as Key[],
});

// 搜索影片
const searchMovieInfoList = () => {
	console.log("searchMovieInfoList");
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
