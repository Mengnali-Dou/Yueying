<script lang="ts">
export default {
	name: "CinemaManagement",
};
</script>

<script setup lang="ts">
import { computed, h, onMounted, reactive } from "vue";
import { useI18n } from "vue-i18n";
import { SearchOutlined } from "@ant-design/icons-vue";
import { message, TableColumnsType } from "ant-design-vue";
import { CinemaInfoViewModel } from "@/@types/viewmodel/cinema-management/cinema-management.viewmodel.ts";
import ToolTipText from "@/components/text/ToolTipText.vue";
import { ApiResponse } from "@/generate/response/api-response.ts";
import { deleteCinemaApi, searchCinemaInfoApi } from "@/generate/api-client/cinema-management.api.ts";
import { CinemaInfoResponse } from "@/generate/response/cinema-info-response.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";
import DeleteDialog from "@/components/DeleteDialog.vue";

// i18n
const { t } = useI18n();

type Key = string | number;

const state = reactive({
	// 搜索影院
	searchCinemaName: "" as string,
	// 影院信息列表
	cinemaInfoList: [] as CinemaInfoViewModel[],
	// 选择行
	selectedKeys: [] as Key[],
	// 选择影院信息
	selectCinemaInfo: {} as CinemaInfoViewModel,
	// 删除影院对话框显示状态
	deleteCinemaDialogVisible: false as boolean,
});

onMounted(async () => {
	await searchCinemaList();
});

// 修改、删除影院按钮激活状态
const cinemaManagementButtonsDisabled = computed(() => {
	return state.selectedKeys.length === 0;
});

// 搜索影院
const searchCinemaList = async () => {
	const searchCinemaResponse = (await searchCinemaInfoApi(state.searchCinemaName)).data as ApiResponse<
		CinemaInfoResponse[]
	>;
	if (searchCinemaResponse.status === responseStatusConstant.OK) {
		state.cinemaInfoList = [] as CinemaInfoViewModel[];
		searchCinemaResponse.data.forEach((item) => {
			state.cinemaInfoList.push(convertCinemaInfoResponseToCinemaInfoViewModel(item));
		});
	} else {
		message.error(searchCinemaResponse.message);
	}
};

// 添加影院
const addCinema = () => {};

// 修改影院信息
const updateCinemaInfo = () => {};

// 删除影院
const deleteCinema = async () => {
	const deleteCinemaResponse = (await deleteCinemaApi(state.selectCinemaInfo.cinemaId)).data as ApiResponse;
	if (deleteCinemaResponse.status === responseStatusConstant.OK) {
		message.success(deleteCinemaResponse.message);
		state.deleteCinemaDialogVisible = false;
		await searchCinemaList();
	} else {
		message.error(deleteCinemaResponse.message);
	}
};

// 选择行
const onSelect = (selectedRowKeys: CinemaInfoViewModel) => {
	if (state.selectedKeys[0] === selectedRowKeys.cinemaId) {
		state.selectedKeys = [] as Key[];
		state.selectCinemaInfo = {} as CinemaInfoViewModel;
	} else {
		state.selectedKeys[0] = selectedRowKeys.cinemaId;
		state.selectCinemaInfo = { ...selectedRowKeys };
	}
};

// 选择所有
const onSelectAll = () => {
	if (state.selectedKeys.length !== 0) {
		state.selectedKeys = [] as Key[];
		state.selectCinemaInfo = {} as CinemaInfoViewModel;
	}
};

const convertCinemaInfoResponseToCinemaInfoViewModel = (
	cinemaInfoResponse: CinemaInfoResponse,
): CinemaInfoViewModel => {
	return {
		cinemaId: cinemaInfoResponse.cinemaId,
		cinemaName: cinemaInfoResponse.cinemaName,
		cinemaAddress: cinemaInfoResponse.cinemaAddress ?? "",
		cinemaProfile: cinemaInfoResponse.cinemaProfile ?? "",
		cinemaService: cinemaInfoResponse.cinemaService ?? "",
		cinemaPhone: cinemaInfoResponse.cinemaPhone ?? "",
		cinemaTraffic: cinemaInfoResponse.cinemaTraffic ?? "",
	} as CinemaInfoViewModel;
};

// 表头
const columns: TableColumnsType = [
	{
		title: t("form.cinemaId"),
		width: 40,
		dataIndex: "cinemaId",
		key: "cinemaId",
	},
	{
		title: t("form.cinemaName"),
		width: 100,
		dataIndex: "cinemaName",
		key: "cinemaName",
	},
	{
		title: t("form.cinemaAddress"),
		dataIndex: "cinemaAddress",
		key: "cinemaAddress",
		width: 100,
	},
	{ title: t("form.cinemaProfile"), dataIndex: "cinemaProfile", key: "cinemaProfile", width: 150 },
	{ title: t("form.cinemaService"), dataIndex: "cinemaService", key: "cinemaService", width: 50 },
	{ title: t("form.cinemaPhone"), dataIndex: "cinemaPhone", key: "cinemaPhone", width: 80 },
	{
		title: t("form.cinemaTraffic"),
		dataIndex: "cinemaTraffic",
		key: "cinemaTraffic",
		width: 120,
	},
];
</script>

<template>
	<a-space direction="horizontal">
		<a-input v-model:value="state.searchCinemaName" :placeholder="t('form.cinemaName')" />
		<a-button type="primary" @click="searchCinemaList()" :icon="h(SearchOutlined)" />
		<a-button type="primary" @click="addCinema()">{{ t("action.addCinema") }}</a-button>
		<a-button type="primary" :disabled="cinemaManagementButtonsDisabled" @click="updateCinemaInfo()">
			{{ t("action.updateCinemaInfo") }}
		</a-button>
		<a-button
			type="primary"
			:disabled="cinemaManagementButtonsDisabled"
			@click="state.deleteCinemaDialogVisible = true"
			danger
		>
			{{ t("action.deleteCinema") }}
		</a-button>
	</a-space>
	<a-table
		:columns="columns"
		:data-source="state.cinemaInfoList"
		:scroll="{ y: 1000 }"
		:pagination="false"
		:row-selection="{
			selectedRowKeys: state.selectedKeys,
			onSelect: onSelect,
			onSelectAll: onSelectAll,
		}"
		row-key="cinemaId"
		style="margin-top: 10px"
	>
		<template #bodyCell="{ column, record }">
			<template v-if="column.key === 'cinemaProfile'">
				<ToolTipText :text="record.cinemaProfile" :maxLength="130" />
			</template>
			<template v-if="column.key === 'cinemaService'">
				<a-tag v-for="item in record.cinemaService.split(',')" :key="item">
					{{ item }}
				</a-tag>
			</template>
		</template>
	</a-table>
	<DeleteDialog
		:dialogVisible="state.deleteCinemaDialogVisible"
		:deleteType="t('form.cinema')"
		:deleteName="state.selectCinemaInfo.cinemaName"
		@confirmDelete="deleteCinema"
	/>
</template>

<style scoped></style>
