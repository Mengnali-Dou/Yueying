const UserManagement = () => import("@/views/user-management/UserManagement.vue");
const EmptyLayout = () => import("@/components/EmptyLayout.vue");

export const userManagementRoutes = [
	{
		path: "/user-management",
		name: "",
		component: EmptyLayout,
		children: [
			{
				path: "",
				name: "user-management",
				component: UserManagement,
				meta: {
					keepalive: true,
					breadcrumbName: "用户信息",
					permission: "UserManagement",
				},
			},
		],
		meta: {
			keepalive: true,
			breadcrumbName: "用户管理",
			permission: "UserManagement",
		},
	},
];
