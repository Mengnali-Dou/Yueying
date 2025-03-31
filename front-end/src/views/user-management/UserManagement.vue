<script lang="ts">
export default {
	name: "UserManagement",
};
</script>

<script setup lang="ts">
// frameworks
import { useI18n } from "vue-i18n";
import { computed, h, onMounted, reactive } from "vue";

// components
import { SearchOutlined } from "@ant-design/icons-vue";
import { message, TableColumnsType } from "ant-design-vue";
import RegisterDialog from "@/views/user-management/component/RegisterDialog.vue";

// api
import { SearchUserRequest } from "@/generate/request/requests.ts";
import { ApiResponse, UserInfoResponse } from "@/generate/response/responses.ts";
import { searchUserInfoApi } from "@/generate/api-client/user-management.api.ts";

// shared utils
import {
	SearchUserViewModel,
	UserInfoViewModel,
} from "@/@types/viewmodel/user-management/user-management.viewmodel.ts";
import { getDate } from "@/shared/date-format.ts";
import { genderConstant } from "@/constant/gender.ts";
import { userRoleConstant } from "@/constant/user-role.ts";
import { accountStatus } from "@/constant/account-status.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";

// i18n
const { t } = useI18n();

type Key = string | number;

const state = reactive({
	// 用户信息列表
	userInfoList: [] as UserInfoViewModel[],
	// 选择行
	selectedKeys: [] as Key[],
	// 选择用户信息
	selectedUserInfo: {} as UserInfoViewModel,
	// 搜索用户表单
	searchUser: {} as SearchUserViewModel,
	// 注册对话框显示状态
	registerDialogVisible: false as boolean,
});

onMounted(async () => {
	await searchUserInfoList();
});

// 搜索用户信息列表
const searchUserInfoList = async () => {
	state.userInfoList = [] as UserInfoViewModel[];
	const searchUserInfoResponse = (
		await searchUserInfoApi(convertSearchUserViewModelToSearchUserRequest(state.searchUser))
	).data as ApiResponse<UserInfoResponse[]>;
	if (searchUserInfoResponse.status === responseStatusConstant.OK) {
		searchUserInfoResponse.data.forEach((item) => {
			state.userInfoList.push(convertUserInfoResponseToUserInfoViewModel(item));
		});
	} else {
		message.error(searchUserInfoResponse.message);
	}
};

// 注册
const registerUser = () => {
	state.registerDialogVisible = true;
};

// 修改用户信息
const updateUserInfo = () => {
	console.log("updateUserInfo");
};

// 重置密码
const resetPassword = () => {
	console.log("resetPassword");
};

// 删除用户
const deleteUser = () => {
	console.log("deleteUser");
};

// 选择行
const onSelect = (selectedRowKeys: UserInfoViewModel) => {
	if (state.selectedKeys[0] === selectedRowKeys.userId) {
		state.selectedKeys = [] as Key[];
		state.selectedUserInfo = {} as UserInfoViewModel;
	} else {
		state.selectedKeys[0] = selectedRowKeys.userId;
		state.selectedUserInfo = { ...selectedRowKeys };
	}
};

// 选择所有
const onSelectAll = () => {
	if (state.selectedKeys.length !== 0) {
		state.selectedKeys = [] as Key[];
		state.selectedUserInfo = {} as UserInfoViewModel;
	}
};

// 修改用户信息、修改密码、删除用户按钮激活状态
const userManagementButtonsDisabled = computed(() => {
	return state.selectedKeys.length === 0;
});

// 表头
const columns: TableColumnsType = [
	{
		title: t("app.userId"),
		width: 80,
		dataIndex: "userId",
		key: "userId",
	},
	{
		title: t("app.account"),
		width: 100,
		dataIndex: "userAccount",
		key: "userAccount",
	},
	{
		title: t("app.userName"),
		dataIndex: "userName",
		key: "userName",
		width: 100,
	},
	{ title: t("app.gender"), dataIndex: "gender", key: "gender", width: 50 },
	{ title: t("app.phone"), dataIndex: "phone", key: "phone", width: 100 },
	{ title: t("app.email"), dataIndex: "email", key: "email", width: 150 },
	{
		title: t("app.registerDateTime"),
		dataIndex: "createTime",
		key: "createTime",
		width: 150,
	},
	{
		title: t("app.userRole"),
		dataIndex: "userRole",
		key: "userRole",
		width: 100,
	},
	{
		title: t("app.userStatus"),
		dataIndex: "userStatus",
		key: "userStatus",
		width: 100,
	},
];

const convertSearchUserViewModelToSearchUserRequest = (searchUserViewModel: SearchUserViewModel): SearchUserRequest => {
	return {
		userName: searchUserViewModel.userName ?? "",
		userAccount: searchUserViewModel.userAccount ?? "",
	} as SearchUserRequest;
};

const convertUserInfoResponseToUserInfoViewModel = (userInfoResponse: UserInfoResponse): UserInfoViewModel => {
	return {
		userId: userInfoResponse.userId,
		userName: userInfoResponse.userName,
		userAccount: userInfoResponse.userAccount,
		avatarUrl: userInfoResponse.avatarUrl,
		gender: userInfoResponse.gender === genderConstant.male.code ? t("app.male") : t("app.female"),
		phone: userInfoResponse.phone,
		email: userInfoResponse.email,
		createTime: getDate(userInfoResponse.createTime),
		userRole: userInfoResponse.userRole,
		userStatus: userInfoResponse.userStatus,
	};
};
</script>

<template>
	<a-space direction="horizontal">
		<a-input v-model:value="state.searchUser.userAccount" :placeholder="t('app.account')" allow-clear />
		<a-input v-model:value="state.searchUser.userName" :placeholder="t('app.userName')" allow-clear />
		<a-button type="primary" @click="searchUserInfoList()" :icon="h(SearchOutlined)" />
		<a-button type="default" @click="registerUser">{{ t("app.register") }}</a-button>
		<a-button type="primary" :disabled="userManagementButtonsDisabled" @click="updateUserInfo">
			{{ t("app.modifyUserInfo") }}
		</a-button>
		<a-button type="primary" :disabled="userManagementButtonsDisabled" @click="resetPassword">
			{{ t("app.resetPassword") }}
		</a-button>
		<a-button type="primary" :disabled="userManagementButtonsDisabled" @click="deleteUser" danger>
			{{ t("app.deleteUser") }}
		</a-button>
	</a-space>
	<a-table
		:columns="columns"
		:data-source="state.userInfoList"
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
			<template v-if="column.key === 'userRole'">
				<a-tag v-if="record.userRole === userRoleConstant.normalUser.code" color="blue">
					{{ t("app.normalUser") }}
				</a-tag>
				<a-tag v-if="record.userRole === userRoleConstant.admin.code" color="red">
					{{ t("app.systemAdmin") }}
				</a-tag>
				<a-tag v-if="record.userRole === userRoleConstant.cinemaAdmin.code" color="green">
					{{ t("app.cinemaAdmin") }}
				</a-tag>
				<a-tag v-if="record.userRole === userRoleConstant.eventAdmin.code" color="cyan">
					{{ t("app.eventAdmin") }}
				</a-tag>
			</template>
			<template v-if="column.key === 'userStatus'">
				<a-tag v-if="record.userStatus === accountStatus.normal.code" color="green">
					{{ t("app.normalAccount") }}
				</a-tag>
				<a-tag v-if="record.userStatus === accountStatus.passwordReset.code" color="pink">
					{{ t("app.passwordReset") }}
				</a-tag>
				<a-tag v-if="record.userStatus === accountStatus.cancellation.code" color="red">
					{{ t("app.cancellation") }}
				</a-tag>
			</template>
		</template>
	</a-table>
	<RegisterDialog v-model:dialogVisible="state.registerDialogVisible" @updateUserInfo="searchUserInfoList" />
</template>

<style scoped></style>
