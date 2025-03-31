<script lang="ts">
export default {
	name: "SystemLayout",
};
</script>

<script setup lang="ts">
// frameworks
import { useI18n } from "vue-i18n";
import { reactive, ref } from "vue";

// components
import { message } from "ant-design-vue";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons-vue";
import BreadCrumb from "@/components/system-layout/component/BreadCrumb.vue";

// api
import { LogoutRequest } from "@/generate/request/requests.ts";
import { ApiResponse } from "@/generate/response/responses.ts";
import { logoutApi } from "@/generate/api-client/user-management.api.ts";

// shared utils
import router from "@/router";
import { clearUserInfo, getAvatarUrl, getUserId } from "@/shared/user-info.ts";
import { responseStatusConstant } from "@/constant/response-status-constant.ts";

// i18n
const { t } = useI18n();

const selectedKeys = ref<string[]>(["1"]);

const state = reactive({
	avatarUrl: getAvatarUrl(),
	collapsed: false,
});

const toggleCollapsed = () => {
	state.collapsed = !state.collapsed;
};

const sideNavInfo = reactive({
	sideNav: [
		{
			navId: "0",
			title: t("app.home"),
			path: "/",
			child: [],
		},
		{
			navId: "1",
			title: t("app.userManagement"),
			path: "/user-management",
			child: [],
		},
	],
});

// 退出登录
const logout = async () => {
	const logoutRequest = {
		userId: getUserId(),
	} as LogoutRequest;
	const logoutResponse = (await logoutApi(logoutRequest)).data as ApiResponse<object>;
	if (logoutResponse.status === responseStatusConstant.OK) {
		clearUserInfo();
		message.success(logoutResponse.message);
		await router.push("/login");
	} else {
		message.error(logoutResponse.message);
	}
};
</script>

<template>
	<a-layout style="height: 100vh">
		<!-- side bar -->
		<a-layout-sider v-model:collapsed="state.collapsed" :trigger="null" collapsible>
			<a-menu
				v-model:selectedKeys="selectedKeys"
				mode="inline"
				theme="dark"
				:style="{ height: '100%', borderRight: 0 }"
			>
				<template v-for="item in sideNavInfo.sideNav">
					<a-sub-menu :key="item.path" v-if="item.child.length > 0">
						<template #title>
							<span>{{ item.title }}</span>
						</template>
						<template v-for="childItem in item.child">
							<a-sub-menu :key="childItem.path" v-if="childItem.child.length > 0">
								<template #title>
									{{ childItem.title }}
								</template>
								<a-menu-item v-for="innerItem in childItem.child" :key="innerItem.path">
									<router-link :to="innerItem.path">
										{{ innerItem.title }}
									</router-link>
								</a-menu-item>
							</a-sub-menu>
							<a-menu-item :key="childItem.path ?? ''" v-else>
								<router-link :to="childItem.path">
									{{ childItem.title }}
								</router-link>
							</a-menu-item>
						</template>
					</a-sub-menu>
					<a-menu-item :key="item.path ?? ''" v-else>
						<router-link :to="item.path">
							{{ item.title }}
						</router-link>
					</a-menu-item>
				</template>
			</a-menu>
		</a-layout-sider>
		<a-layout style="background-color: #f5f5f5">
			<!-- header -->
			<a-layout-header id="header">
				<a-flex class="flex" align="center" style="justify-content: space-between">
					<div>
						<menu-unfold-outlined v-if="state.collapsed" class="trigger" @click="toggleCollapsed" />
						<menu-fold-outlined v-else class="trigger" @click="toggleCollapsed" />
					</div>
					<a-popover placement="bottomRight">
						<template #content>
							<a-button danger type="text" @click="logout">{{ t("app.logout") }}</a-button>
						</template>
						<a-avatar id="avatar" :src="state.avatarUrl" size="large" />
					</a-popover>
				</a-flex>
			</a-layout-header>
			<!-- content -->
			<a-layout-content id="content">
				<BreadCrumb />
				<router-view />
			</a-layout-content>
		</a-layout>
	</a-layout>
</template>
