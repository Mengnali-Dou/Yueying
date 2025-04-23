const EmptyLayout = () => import("@/components/EmptyLayout.vue");
const CinemaManagement = () => import("@/views/cinema-management/CinemaManagement.vue");

export const cinemaManagementRoutes = [
	{
		path: "",
		name: "",
		component: EmptyLayout,
		children: [
			{
				path: "/cinema-management",
				name: "cinema-management",
				component: CinemaManagement,
				meta: {
					keepalive: true,
					breadcrumbName: "影院信息",
					permission: "CinemaManagement",
				},
			},
		],
		meta: {
			keepalive: true,
			breadcrumbName: "影院管理",
			permission: "CinemaManagement",
		},
	},
];
